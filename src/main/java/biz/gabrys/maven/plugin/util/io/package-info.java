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
/**
 * <p>
 * Contains classes related with I/O operations. It provides tools to:
 * </p>
 * <ul>
 * <li>scan directories for files whose match certain criteria (see {@link biz.gabrys.maven.plugin.util.io.FileScanner
 * FileScanner})</li>
 * <li>create a virtual (not physical) destination file in the output directory based on the source directory, the
 * source file and the output file name pattern (see {@link biz.gabrys.maven.plugin.util.io.DestinationFileCreator
 * DestinationFileCreator})</li>
 * </ul>
 */
package biz.gabrys.maven.plugin.util.io;
