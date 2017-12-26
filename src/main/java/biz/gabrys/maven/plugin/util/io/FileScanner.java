/*
 * Maven Plugin Utils
 * http://maven-plugin-utils.projects.gabrys.biz/
 *
 * Copyright (c) 2015 Adam Gabrys
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
 * <p>
 * Responsible for scanning a directory for files whose match certain criteria.
 * </p>
 * <p>
 * Example:
 * </p>
 * 
 * <pre>
 * public class ExampleMojo extends AbstractMojo {
 *
 *     // source directory
 *     protected File sourceDirectory;
 *     // included files pattern
 *     protected String[] includes;
 *     // excluded files pattern
 *     protected String[] excludes;
 *     // fileset pattern format
 *     protected String filesetPatternFormat;
 *
 *     public void execute() {
 *         Collection&lt;File&gt; files = getFiles();
 *         if (files.isEmpty()) {
 *             getLog().warn("No sources to process");
 *             return;
 *         }
 *
 *         // logic which process files
 *         ...
 *
 *     }
 * 
 *     private Collection&lt;File&gt; getFiles() {
 *        {@link ScannerPatternFormat} patternFormat = {@link ScannerPatternFormat}.toPattern(filesetPatternFormat);
 *        FileScanner scanner = new {@link ScannerFactory}().create(patternFormat, getLog());
 *        return scanner.getFiles(sourceDirectory, includes, excludes);
 *    }
 * }
 * </pre>
 * 
 * @since 1.0
 */
public interface FileScanner {

    /**
     * Returns files contained by a directory.
     * @param directory the directory to be scanned.
     * @param includes an array of include patterns.
     * @param excludes an array of exclude patterns
     * @return files contained by a directory.
     * @since 1.0
     */
    Collection<File> getFiles(File directory, String[] includes, String[] excludes);
}
