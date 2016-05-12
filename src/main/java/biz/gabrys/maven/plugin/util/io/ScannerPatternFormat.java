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
package biz.gabrys.maven.plugin.util.io;

/**
 * Represents inclusion and exclusion fileset patterns format used by the {@link FileScanner}.
 * @since 1.0
 */
public enum ScannerPatternFormat {

    /**
     * The <a href="http://ant.apache.org/">Ant</a>
     * <a href="http://ant.apache.org/manual/dirtasks.html#patterns">patterns</a> format.
     * @since 1.0
     */
    ANT,

    /**
     * The regular expressions format.
     * @since 1.0
     */
    REGEX;

    /**
     * Returns a scanner pattern format by name (case insensitive).
     * @param name the scanner pattern format name.
     * @return the scanner pattern format.
     * @throws IllegalArgumentException if cannot find search pattern format related with name.
     * @since 1.0
     */
    public static ScannerPatternFormat toPattern(final String name) {
        for (final ScannerPatternFormat pattern : values()) {
            if (pattern.name().equalsIgnoreCase(name)) {
                return pattern;
            }
        }
        throw new IllegalArgumentException(String.format("Cannot find search pattern format for name \"%s\"", name));
    }
}
