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
import java.io.IOException;

import org.apache.commons.io.filefilter.IOFileFilter;

/**
 * File filter which uses regular expressions to accept files.
 * @since 1.2
 */
public class RegexFileFilter implements IOFileFilter {

    private final String rootDirectoryPath;
    private final String[] includes;
    private final String[] excludes;

    /**
     * Constructs a new instance.
     * @param directory the directory to be scanned.
     * @param includes an array of include patterns.
     * @param excludes an array of exclude patterns.
     * @throws RegexFileFilterException if an error occurs while resolving canonical path.
     * @since 1.2
     */
    public RegexFileFilter(final File directory, final String[] includes, final String[] excludes) {
        rootDirectoryPath = getCanonicalPath(directory);
        this.includes = includes.clone();
        this.excludes = excludes.clone();
    }

    /**
     * {@inheritDoc}<br>
     * This method delegates logic to {@link RegexFileFilter#isAcceptable(File)}.
     * @return {@code true} whether the file should be accepted by this filter, otherwise {@code false}.
     * @since 1.2
     */
    public final boolean accept(final File dir, final String name) {
        return isAcceptable(new File(dir, name));
    }

    /**
     * {@inheritDoc}<br>
     * This method delegates logic to {@link RegexFileFilter#isAcceptable(File)}.
     * @return {@code true} whether the file should be accepted by this filter, otherwise {@code false}.
     * @since 1.2
     */
    public final boolean accept(final File file) {
        return isAcceptable(file);
    }

    /**
     * Checks whether a file should be accepted by this filter.
     * @param file the file to check.
     * @return {@code true} whether the file should be accepted by this filter, otherwise {@code false}.
     * @since 1.2
     */
    protected boolean isAcceptable(final File file) {
        final String path = getCanonicalPath(file);
        if (!path.startsWith(rootDirectoryPath)) {
            return false;
        }
        final String nameWithoutRoot = path.substring(rootDirectoryPath.length() + 1).replace('\\', '/');
        return !matches(nameWithoutRoot, excludes) && matches(nameWithoutRoot, includes);
    }

    /**
     * Checks whether a file path matches at least one patter from patterns.
     * @param path the file path to check.
     * @param patterns the regular expressions patterns.
     * @return {@code true} whether the file path matches at least one patter from patterns, otherwise {@code false}.
     * @since 1.2
     */
    protected static boolean matches(final String path, final String[] patterns) {
        for (final String pattern : patterns) {
            if (path.matches(pattern)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns canonical path for a file.
     * @param file the file.
     * @return file canonical path.
     * @throws RegexFileFilterException if an error occurs while resolving canonical path.
     * @since 1.2
     */
    protected static String getCanonicalPath(final File file) {
        try {
            return file.getCanonicalPath();
        } catch (final IOException e) {
            throw new RegexFileFilterException(e);
        }
    }
}
