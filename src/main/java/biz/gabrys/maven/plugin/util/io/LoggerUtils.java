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

import org.apache.maven.plugin.logging.Log;

/**
 * Provides tools for log information related with files.
 * @since 1.2
 */
public final class LoggerUtils {

    private LoggerUtils() {
        // do nothing
    }

    /**
     * Logs (in debug mode) information whether {@link File#isFile() normal file} is included or excluded. Logged
     * message contains the file {@link File#getAbsolutePath() absolute path}.
     * @param logger the logger.
     * @param file the included or excluded file.
     * @param included {@code true} whether file is included, otherwise {@code false}.
     * @since 1.2
     */
    public static void debugInclusion(final Log logger, final File file, final boolean included) {
        if (logger.isDebugEnabled() && file.isFile()) {
            logger.debug((included ? "Include " : "Exclude ") + file.getAbsolutePath());
        }
    }
}
