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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.util.DirectoryScanner;

/**
 * File scanner which uses <a href="https://ant.apache.org/manual/dirtasks.html#patterns">Ant patterns</a> to match
 * files.
 * @since 1.2
 */
public class AntFileScanner implements FileScanner {

    private final Log logger;

    /**
     * Constructs a new instance.
     * @since 1.2
     */
    public AntFileScanner() {
        this(null);
    }

    /**
     * Constructs a new instance.
     * @param logger the logger used to log included/excluded files (only in debug mode).
     * @since 1.2
     */
    public AntFileScanner(final Log logger) {
        this.logger = logger;
    }

    @Override
    public Collection<File> getFiles(final File directory, final String[] includes, final String[] excludes) {
        final DirectoryScanner scanner = createDirectoryScanner();
        scanner.setBasedir(directory);
        scanner.setIncludes(includes.clone());
        scanner.setExcludes(excludes.clone());
        scanner.scan();
        return convertToFiles(directory, scanner.getIncludedFiles());
    }

    /**
     * Creates a {@link DirectoryScanner} which will be used to find all files whose match filters.
     * @return a not initialized instance of the {@link DirectoryScanner} or its subclass.
     * @since 1.3.0
     * @see #getFiles(File, String[], String[])
     */
    protected DirectoryScanner createDirectoryScanner() {
        final boolean loggerEnabled = logger != null && logger.isDebugEnabled();
        return loggerEnabled ? new LoggingDirectoryScanner(logger) : new DirectoryScanner();
    }

    /**
     * Converts paths found by scanner to files collection with absolute paths.
     * @param directory the base directory.
     * @param paths the found paths.
     * @return the collection with files.
     * @since 1.2
     * @see #getFiles(File, String[], String[])
     */
    protected List<File> convertToFiles(final File directory, final String[] paths) {
        final List<File> files = new ArrayList<File>(paths.length);
        for (final String path : paths) {
            files.add(new File(directory, path));
        }
        return files;
    }
}
