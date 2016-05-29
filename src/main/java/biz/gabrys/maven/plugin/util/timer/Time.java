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

    private static final int MILLISECONDS_IN_SECOND = 1000;
    private static final int SECONDS_IN_MINUTE = 60;
    private static final int MINUTES_IN_HOUR = 60;

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
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Time other = (Time) obj;
        return milliseconds == other.milliseconds;
    }

    /**
     * Returns a string representation of the time in human-readable format. Examples:
     * 
     * <pre>
     * 0.000 seconds
     * 0.589 seconds
     * 1.000 second
     * 57.042 seconds
     * 1 minute 0.000 seconds
     * 7 minutes 1.000 second
     * 12 minutes 28.321 seconds
     * 1 hour 0 minutes 0.000 seconds
     * 5 hours 0 minutes 0.000 seconds
     * 5 hours 1 minute 1.000 second
     * 5 hours 1 minute 54.132 seconds
     * 5 hours 9 minutes 4.123 seconds
     * </pre>
     * 
     * @return a string representation of the time in human-readable format.
     * @since 1.3
     */
    @Override
    public String toString() {
        final long millis = milliseconds % MILLISECONDS_IN_SECOND;
        long seconds = milliseconds / MILLISECONDS_IN_SECOND;
        long minutes = seconds / SECONDS_IN_MINUTE;
        if (minutes > 0) {
            seconds -= minutes * SECONDS_IN_MINUTE;
        }
        final long hours = minutes / MINUTES_IN_HOUR;
        if (hours > 0) {
            minutes -= hours * MINUTES_IN_HOUR;
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
