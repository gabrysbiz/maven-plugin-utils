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

/**
 * Responsible for creating new instances of {@link FileScanner}.
 * @since 1.0
 */
public class ScannerFactory {

    /**
     * Creates a new scanner related with the pattern format.
     * @param patternFormat the scanner pattern format.
     * @param logger the plugin logger.
     * @return the new scanner.
     * @throws IllegalArgumentException if logger is {@code null}.
     * @since 1.0
     */
    public FileScanner create(final ScannerPatternFormat patternFormat, final Log logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger cannot be null");
        }
        if (ScannerPatternFormat.ANT.equals(patternFormat)) {
            return new AntFileScanner(logger);
        }
        return new RegexFileScanner(logger);
    }
}
