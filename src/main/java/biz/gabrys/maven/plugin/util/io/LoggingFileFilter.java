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

import java.io.File;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.maven.plugin.logging.Log;

/**
 * Decorator which adds logger instructions (in debug mode) to an instance of the {@link IOFileFilter}.
 * @since 1.2
 */
public class LoggingFileFilter implements IOFileFilter {

    private final IOFileFilter filter;
    private final Log logger;

    /**
     * Constructs a new instance.
     * @param filter the decorated filter.
     * @param logger logger the logger used to log included/excluded files (only in debug mode).
     * @throws IllegalArgumentException if the filter or the logger is equal to {@code null}.
     * @since 1.2
     */
    public LoggingFileFilter(final IOFileFilter filter, final Log logger) {
        if (filter == null) {
            throw new IllegalArgumentException("Filter cannot be null");
        }
        if (logger == null) {
            throw new IllegalArgumentException("Logger cannot be null");
        }
        this.filter = filter;
        this.logger = logger;
    }

    /**
     * {@inheritDoc} It additionally logs (in debug mode) for {@link File#isFile() normal file} information whether it
     * is included or excluded.
     * @since 1.2
     */
    public boolean accept(final File file) {
        final boolean accepted = filter.accept(file);
        if (!logger.isDebugEnabled()) {
            return accepted;
        }
        LoggerUtils.debugInclusion(logger, file, accepted);
        return accepted;
    }

    /**
     * {@inheritDoc} It additionally logs (in debug mode) for {@link File#isFile() normal file} information whether it
     * is included or excluded.
     * @since 1.2
     */
    public boolean accept(final File dir, final String name) {
        final boolean accepted = filter.accept(dir, name);
        if (!logger.isDebugEnabled()) {
            return accepted;
        }
        LoggerUtils.debugInclusion(logger, new File(dir, name), accepted);
        return accepted;
    }
}
