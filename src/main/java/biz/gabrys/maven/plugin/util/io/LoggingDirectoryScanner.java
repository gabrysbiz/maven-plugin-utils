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

import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.util.DirectoryScanner;

class LoggingDirectoryScanner extends DirectoryScanner {

    private final Log logger;

    LoggingDirectoryScanner(final Log logger) {
        this.logger = logger;
    }

    @Override
    protected boolean isExcluded(final String name) {
        final boolean excluded = super.isExcluded(name);
        final File file = new File(getBasedir(), name);
        if (file.isFile()) {
            logger.debug((excluded ? "Exclude " : "Include ") + file.getAbsolutePath());
        }
        return excluded;
    }
}
