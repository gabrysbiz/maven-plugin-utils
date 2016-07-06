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
import java.util.ArrayList;
import java.util.Collection;

import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.util.DirectoryScanner;

class AntFileScanner implements FileScanner {

    private final Log logger;

    AntFileScanner(final Log logger) {
        this.logger = logger;
    }

    public Collection<File> getFiles(final File directory, final String[] includes, final String[] excludes) {
        final DirectoryScanner scanner = logger.isDebugEnabled() ? new LoggingDirectoryScanner(logger) : new DirectoryScanner();
        scanner.setBasedir(directory);
        scanner.setIncludes(includes.clone());
        scanner.setExcludes(excludes.clone());
        scanner.scan();
        final Collection<File> files = new ArrayList<File>();
        for (final String path : scanner.getIncludedFiles()) {
            files.add(new File(directory, path));
        }
        return files;
    }
}
