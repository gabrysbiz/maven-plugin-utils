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
 * Value sanitizer which treats all values as valid.
 * @since 1.3.0
 */
public class AlwaysValidSanitizer implements ValueSanitizer {

    /**
     * Constructs a new instance.
     * @since 1.3.0
     */
    public AlwaysValidSanitizer() {
        // do nothing
    }

    /**
     * Returns {@code true}.
     * @param value the parameter value (ignored).
     * @return {@code true}.
     * @since 1.3.0
     */
    @Override
    public boolean isValid(final Object value) {
        return true;
    }

    /**
     * Throws an {@link UnsupportedOperationException}.
     * @param value the parameter invalid value (ignored).
     * @return nothing, because throws the {@link UnsupportedOperationException}.
     * @throws UnsupportedOperationException always.
     * @since 1.3.0
     */
    @Override
    public Object sanitize(final Object value) {
        throw new UnsupportedOperationException();
    }
}
