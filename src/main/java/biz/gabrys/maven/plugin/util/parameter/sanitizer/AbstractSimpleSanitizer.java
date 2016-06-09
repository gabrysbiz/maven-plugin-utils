/*
 * Maven Plugin Utils
 * http://maven-project-utils.projects.gabrys.biz/
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
 * Helps in implementing {@link ValueSanitizer} which specifies that the parameter value is valid based on a simple
 * condition.
 * @since 1.3.0
 */
public abstract class AbstractSimpleSanitizer implements ValueSanitizer {

    /**
     * The information whether parameter current value is valid.
     * @since 1.3.0
     */
    protected final boolean valid;

    /**
     * Constructs a new instance and sets information on whether parameter current value is valid.
     * @param valid {@code true} whether parameter current value is valid, otherwise {@code false}.
     * @since 1.3.0
     */
    protected AbstractSimpleSanitizer(final boolean valid) {
        this.valid = valid;
    }

    /**
     * Returns a condition value specified during construction of {@code this} object.
     * @param value the parameter value (ignored).
     * @return {@code true} whether the parameter value is valid, otherwise {@code false}.
     * @since 1.3.0
     * @see #AbstractSimpleSanitizer(boolean)
     */
    public boolean isValid(final Object value) {
        return valid;
    }

    /**
     * Sanitizes a parameter invalid value to valid form. Should be not be executed if {@link #isValid(Object)} returns
     * {@code true}.
     * @param value the parameter invalid value.
     * @return the valid value.
     * @throws UnsupportedOperationException if the condition is equal to {@code true}.
     * @since 1.3.0
     */
    public Object sanitize(final Object value) {
        if (valid) {
            throw new UnsupportedOperationException();
        }
        return sanitize2(value);
    }

    /**
     * Sanitizes a parameter invalid value to valid form. Executed only whether the condition is equal to {@code false}.
     * @param value the parameter invalid value.
     * @return the valid value.
     * @since 1.3.0
     */
    protected abstract Object sanitize2(Object value);
}
