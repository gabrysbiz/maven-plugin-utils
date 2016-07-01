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
package biz.gabrys.maven.plugin.util.timer;

/**
 * Represents timer responsible for counting the execution time.
 * @since 1.0
 */
public interface Timer {

    /**
     * Starts counting time.
     * @since 1.0
     */
    void start();

    /**
     * Stops counting time.
     * @return the counted time.
     * @since 1.0
     */
    Time stop();

    /**
     * Returns a current counted time.
     * @return the current counted time.
     * @since 1.0
     */
    Time getTime();
}
