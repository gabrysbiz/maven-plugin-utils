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
 * Responsible for sanitizing (convert from invalid to valid form) Mojo parameters values.
 * @since 1.3.0
 */
public interface ValueSanitizer {

    /**
     * Checks whether a parameter value is valid or not.
     * @param value the parameter value.
     * @return {@code true} whether the parameter value is valid, otherwise {@code false}.
     * @since 1.3.0
     */
    boolean isValid(Object value);

    /**
     * Sanitizes a parameter invalid value to valid form. Should be not be executed if {@link #isValid(Object)} returns
     * {@code true}.
     * @param value the parameter invalid value.
     * @return the valid value.
     * @since 1.3.0
     */
    Object sanitize(Object value);
}
