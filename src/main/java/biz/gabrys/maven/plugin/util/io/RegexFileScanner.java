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

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.maven.plugin.logging.Log;

/**
 * File scanner which uses regular expressions to match files.
 * @since 1.2
 */
public class RegexFileScanner implements FileScanner {

    private final Log logger;

    /**
     * Constructs a new instance.
     * @since 1.2
     */
    public RegexFileScanner() {
        this(null);
    }

    /**
     * Constructs a new instance.
     * @param logger the logger used to log included/excluded files (only in debug mode).
     * @since 1.2
     */
    public RegexFileScanner(final Log logger) {
        this.logger = logger;
    }

    /**
     * {@inheritDoc} You must use '/' as path separator in include/exclude patterns.
     */
    public Collection<File> getFiles(final File directory, final String[] includes, final String[] excludes) {
        IOFileFilter filter = new RegexFileFilter(directory, includes, excludes);
        if (logger != null && logger.isDebugEnabled()) {
            filter = new LoggingFileFilter(filter, logger);
        }
        return FileUtils.listFiles(directory, filter, TrueFileFilter.INSTANCE);
    }
}
