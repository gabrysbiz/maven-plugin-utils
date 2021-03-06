/*
 * Maven Plugin Utils
 * https://gabrysbiz.github.io/maven-plugin-utils/
 *
 * Copyright (c) 2015-2020 Adam Gabrys
 *
 * This file is licensed under the BSD 3-Clause (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain:
 * - a copy of the License at project page
 * - a template of the License at https://opensource.org/licenses/BSD-3-Clause
 */
package biz.gabrys.maven.plugin.util.parameter.converter;

/**
 * Responsible for converting parameters values to string representation.
 * @since 1.3.0
 */
public interface ValueToStringConverter {

    /**
     * Converts a parameter value to string representation.
     * @param value the parameter value.
     * @return the string representation of the parameter value.
     * @since 1.3.0
     */
    String convert(Object value);
}
