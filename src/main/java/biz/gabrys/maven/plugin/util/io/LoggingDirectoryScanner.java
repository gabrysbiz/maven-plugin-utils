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
package biz.gabrys.maven.plugin.util.io;

import java.io.File;

import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.util.DirectoryScanner;

import biz.gabrys.maven.plugin.util.parameter.ParameterUtils;

/**
 * Extended version of the {@link DirectoryScanner} which adds logger instructions in debug mode.
 * @since 1.2
 */
public class LoggingDirectoryScanner extends DirectoryScanner {

    private final Log logger;

    /**
     * Constructs a new instance.
     * @param logger the logger used to log included/excluded files (only in debug mode).
     * @throws IllegalArgumentException if the logger is equal to {@code null}.
     * @since 1.2
     */
    public LoggingDirectoryScanner(final Log logger) {
        ParameterUtils.verifyNotNull("logger", logger);
        this.logger = logger;
    }

    /**
     * {@inheritDoc} It additionally logs (in debug mode) for {@link File#isFile() normal file} information whether it
     * is included or excluded.
     * @since 1.2
     */
    @Override
    protected boolean isExcluded(final String name) {
        final boolean excluded = super.isExcluded(name);
        final File file = new File(getBasedir(), name);
        LoggerUtils.debugInclusion(logger, file, !excluded);
        return excluded;
    }
}
