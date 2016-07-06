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
package biz.gabrys.maven.plugin.util.io;

/**
 * Thrown to indicate that an error occurred during file-filtering process by regular expressions.
 * @since 1.2
 */
public class RegexFileFilterException extends RuntimeException {

    private static final long serialVersionUID = -2209439425443207057L;

    /**
     * Constructs a new instance with the specified cause.
     * @param cause the cause.
     * @since 1.2
     */
    public RegexFileFilterException(final Throwable cause) {
        super(cause);
    }
}
