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
 * Represents time counted by a {@link Timer}.
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
    public int hashCode() {
        return (int) (milliseconds ^ milliseconds >>> 32);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Time other = (Time) obj;
        return milliseconds == other.milliseconds;
    }

    @Override
    public String toString() {
        final long millis = milliseconds % 1000;
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        if (minutes > 0) {
            seconds -= minutes * 60;
        }
        final long hours = minutes / 24;
        if (hours > 0) {
            minutes -= hours * 24;
        }

        final StringBuilder time = new StringBuilder();
        if (hours > 0) {
            time.append(hours);
            time.append(String.format(" hour%s ", hours == 1 ? "" : "s"));
        }
        if (hours > 0 || minutes > 0) {
            time.append(minutes);
            time.append(String.format(" minute%s ", minutes == 1 ? "" : "s"));
        }
        time.append(String.format("%s.%03d second%s", seconds, millis, seconds == 1 && millis == 0 ? "" : "s"));
        return time.toString();
    }
}