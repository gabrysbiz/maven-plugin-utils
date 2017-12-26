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
package biz.gabrys.maven.plugin.util.timer;

/**
 * Represents timer responsible for counting the execution time.
 * @since 1.0
 */
public interface Timer {

    /**
     * Starts counting execution time.
     * @since 1.0
     */
    void start();

    /**
     * Stops counting execution time.
     * @return the counted execution time.
     * @since 1.0
     */
    TimeSpan stop();

    /**
     * Returns a current counted execution time.
     * @return the current counted execution time.
     * @since 1.0
     */
    TimeSpan getTime();
}
