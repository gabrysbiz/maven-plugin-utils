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
package biz.gabrys.maven.plugin.util.parameter;

/**
 * Provides tools to work with method parameters.
 * @since 1.4.0
 */
public final class ParameterUtils {

    private ParameterUtils() {
        // blocks the possibility of create a new instance
    }

    /**
     * Verifies that a parameter value is not {@code null}.
     * @param name the parameter name.
     * @param value the parameter value.
     * @throws IllegalArgumentException if the parameter value is equal to {@code null}.
     * @since 1.4.0
     */
    public static void verifyNotNull(final String name, final Object value) {
        if (value == null) {
            final String message;
            if (name == null) {
                message = "Parameter is null";
            } else {
                message = String.format("Parameter \"%s\" is null", name);
            }
            throw new IllegalArgumentException(message);
        }
    }
}
