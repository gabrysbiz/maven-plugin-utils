/*
 * Maven Plugin Utils
 * http://maven-plugin-utils.projects.gabrys.biz/
 *
 * Copyright (c) 2015 Adam Gabryś
 *
 * This file is licensed under the BSD 3-Clause (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain:
 * - a copy of the License at project page
 * - a template of the License at https://opensource.org/licenses/BSD-3-Clause
 */
package biz.gabrys.maven.plugin.util.parameter.sanitizer;

/**
 * Simple value sanitizer which returns a declared sanitized value when specified condition returns {@code true}.
 * @since 1.3.0
 */
public class SimpleSanitizer extends AbstractSimpleSanitizer {

    /**
     * The sanitized value.
     * @since 1.3.0
     */
    protected final Object sanitizedValue;

    /**
     * Constructs a new instance.
     * @param valid {@code true} whether parameter current value is valid, otherwise {@code false}.
     * @param sanitizedValue the sanitized value which will be returned by {@link #sanitize(Object)} method.
     * @since 1.3.0
     */
    public SimpleSanitizer(final boolean valid, final Object sanitizedValue) {
        super(valid);
        this.sanitizedValue = sanitizedValue;
    }

    /**
     * Returns a sanitized value specified during construction of {@code this} object.
     * @param value the parameter value (ignored).
     * @return the sanitized value.
     * @since 1.3.0
     * @see #SimpleSanitizer(boolean, Object)
     */
    @Override
    protected Object sanitize2(final Object value) {
        return sanitizedValue;
    }
}
