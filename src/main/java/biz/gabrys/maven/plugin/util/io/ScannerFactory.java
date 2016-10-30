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

import org.apache.maven.plugin.logging.Log;

import biz.gabrys.maven.plugin.util.parameter.ParameterUtils;

/**
 * Responsible for creating new instances of the {@link FileScanner} by the {@link ScannerPatternFormat}.
 * @since 1.0
 */
public class ScannerFactory {

    /**
     * Constructs a new instance.
     * @since 1.0
     */
    public ScannerFactory() {
        // do nothing
    }

    /**
     * Creates a new scanner related with the pattern format. Returns an instance of the {@link RegexFileScanner} if
     * {@code patternFormat} is equal to {@code null}.
     * @param patternFormat the scanner pattern format.
     * @param logger the logger used to log included/excluded files (only in debug mode).
     * @return the new scanner.
     * @throws IllegalArgumentException if the logger is equal to {@code null}.
     * @since 1.0
     */
    public FileScanner create(final ScannerPatternFormat patternFormat, final Log logger) {
        ParameterUtils.verifyNotNull("logger", logger);
        if (patternFormat == ScannerPatternFormat.ANT) {
            return new AntFileScanner(logger);
        }
        return new RegexFileScanner(logger);
    }
}
