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

/**
 * Responsible for scanning a directory for files/directories which match certain criteria.
 * @since 1.0
 */
public interface FileScanner {

    /**
     * Returns files contained by a directory.
     * @param directory the directory to be scanned.
     * @param includes a list of include patterns.
     * @param excludes a list of exclude patterns
     * @return files contained by a directory.
     * @since 1.0
     */
    Collection<File> getFiles(File directory, String[] includes, String[] excludes);
}
