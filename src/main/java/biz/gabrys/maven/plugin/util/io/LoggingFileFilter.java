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

class LoggingFileFilter implements IOFileFilter {

    private final IOFileFilter filter;
    private final Log logger;

    LoggingFileFilter(final IOFileFilter filter, final Log logger) {
        this.filter = filter;
        this.logger = logger;
    }

    public boolean accept(final File file) {
        final boolean accepted = filter.accept(file);
        if (file.isFile()) {
            logger.debug((accepted ? "Include " : "Exclude ") + file.getAbsolutePath());
        }
        return accepted;
    }

    public boolean accept(final File dir, final String name) {
        return filter.accept(dir, name);
    }
}
