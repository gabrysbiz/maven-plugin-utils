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
 * Represent time counted by a {@link Timer}.
 * @since 1.0
 */
public class Time {

    private final long milliseconds;

    /**
     * Constructs a new instance.
     * @param milliseconds a counted time in the milliseconds.
     * @since 1.0
     */
    public Time(final long milliseconds) {
        this.milliseconds = milliseconds;
    }

    /**
     * Returns milliseconds representation of {@code this} object.
     * @return this in milliseconds.
     * @since 1.0
     */
    public long toMilliseconds() {
        return milliseconds;
    }

    @Override
    public synchronized String toString() {
        final long seconds = milliseconds / 1000 % 60;
        final long millis = milliseconds % 1000;
        return String.format("%s.%03d seconds", seconds, millis);
    }
}
