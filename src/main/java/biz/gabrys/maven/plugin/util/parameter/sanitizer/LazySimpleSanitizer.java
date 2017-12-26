/*
 * Maven Plugin Utils
 * http://maven-plugin-utils.projects.gabrys.biz/
 *
 * Copyright (c) 2015 Adam Gabrys
 *
 * This file is licensed under the BSD 3-Clause (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain:
 * - a copy of the License at project page
 * - a template of the License at https://opensource.org/licenses/BSD-3-Clause
 */
package biz.gabrys.maven.plugin.util.parameter.sanitizer;

/**
 * Simple value sanitizer which returns a declared lazy loaded sanitized value when specified condition returns
 * {@code true}. The sanitized value will be returned by {@link ValueContainer#getValue()} method.
 * @since 1.3.0
 */
public class LazySimpleSanitizer extends AbstractSimpleSanitizer {

    /**
     * Sanitized value container.
     * @since 1.3.0
     */
    protected final ValueContainer conatiner;

    /**
     * Constructs a new instance.
     * @param valid {@code true} whether parameter current value is valid, otherwise {@code false}.
     * @param container the container which stores a sanitized value.
     * @since 1.3.0
     */
    public LazySimpleSanitizer(final boolean valid, final ValueContainer container) {
        super(valid);
        conatiner = container;
    }

    /**
     * Returns a sanitized value returned by the container (specified during construction of {@code this} object).
     * @param value the parameter value (ignored).
     * @return the sanitized value.
     * @since 1.3.0
     * @see #LazySimpleSanitizer(boolean, ValueContainer)
     */
    @Override
    protected Object sanitize2(final Object value) {
        return conatiner.getValue();
    }

    /**
     * Responsible for returning sanitized value.
     * @since 1.3.0
     */
    public interface ValueContainer {

        /**
         * Returns a sanitized value.
         * @return the sanitized value.
         * @since 1.3.0
         */
        Object getValue();
    }
}
