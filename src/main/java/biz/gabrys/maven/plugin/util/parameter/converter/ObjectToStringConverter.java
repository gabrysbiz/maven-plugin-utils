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
 * Responsible for converting objects to string representation.
 * @since 1.3.0
 */
public class ObjectToStringConverter implements ValueToStringConverter {

    /**
     * Constructs a new instance.
     * @since 1.3.0
     */
    public ObjectToStringConverter() {
        // do nothing
    }

    /**
     * {@inheritDoc} This method is null safe.
     * @return the string representation of the parameter value (never {@code null}).
     * @since 1.3.0
     */
    public String convert(final Object value) {
        return String.valueOf(value);
    }
}
