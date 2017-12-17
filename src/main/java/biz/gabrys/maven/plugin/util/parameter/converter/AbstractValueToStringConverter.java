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
package biz.gabrys.maven.plugin.util.parameter.converter;

/**
 * Helps in implementing {@link ValueToStringConverter converters} which are null safe.
 * @since 1.3.0
 */
public abstract class AbstractValueToStringConverter implements ValueToStringConverter {

    /**
     * String representation of {@code null} value.
     * @since 1.3.0
     */
    protected static final String NULL_STRING = "null";

    /**
     * Constructs a new instance.
     * @since 1.3.0
     */
    protected AbstractValueToStringConverter() {
        // do nothing
    }

    /**
     * {@inheritDoc} This method is null safe.
     * @param value the parameter value (can be {@code null}).
     * @return the string representation of the parameter value (never {@code null}).
     * @since 1.3.0
     */
    @Override
    public String convert(final Object value) {
        if (value == null) {
            return NULL_STRING;
        }
        final String converted = convert2(value);
        return converted == null ? NULL_STRING : converted;
    }

    /**
     * Converts a parameter value to string representation.
     * @param value the parameter value (never {@code null}).
     * @return the string representation of the parameter value (can be {@code null}).
     * @since 1.3.0
     */
    protected abstract String convert2(final Object value);
}
