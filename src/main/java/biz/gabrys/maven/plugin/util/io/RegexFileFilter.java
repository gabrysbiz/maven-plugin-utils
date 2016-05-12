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

class RegexFileFilter implements IOFileFilter {

    private final String rootDirectoryPath;
    private final String[] includes;
    private final String[] excludes;

    RegexFileFilter(final File rootDirectory, final String[] includes, final String[] excludes) {
        rootDirectoryPath = getCanonicalPath(rootDirectory);
        this.includes = includes.clone();
        this.excludes = excludes.clone();
    }

    public final boolean accept(final File dir, final String name) {
        return accept(new File(dir, name));
    }

    public boolean accept(final File file) {
        final String name = getCanonicalPath(file);
        if (!name.startsWith(rootDirectoryPath)) {
            return false;
        }
        final String nameWithoutRoot = name.substring(rootDirectoryPath.length() + 1).replace('\\', '/');
        return !isExcluded(nameWithoutRoot, excludes) && isIncluded(nameWithoutRoot, includes);
    }

    private static boolean isExcluded(final String name, final String[] excludes) {
        for (final String exclude : excludes) {
            if (name.matches(exclude)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isIncluded(final String name, final String[] includes) {
        for (final String include : includes) {
            if (name.matches(include)) {
                return true;
            }
        }
        return false;
    }

    private static String getCanonicalPath(final File file) {
        try {
            return file.getCanonicalPath();
        } catch (final IOException e) {
            throw new RegexFileFilterException(e);
        }
    }
}
