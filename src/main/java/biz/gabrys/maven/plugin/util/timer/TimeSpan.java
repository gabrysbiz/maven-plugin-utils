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
 * Represents a time span counted by an instance of the {@link Timer}.
 * @since 2.0.0
 */
public class TimeSpan {

    private static final int MILLISECONDS_IN_SECOND = 1000;
    private static final int SECONDS_IN_MINUTE = 60;
    private static final int MINUTES_IN_HOUR = 60;

    private final long milliseconds;

    /**
     * Constructs a new instance.
     * @param milliseconds a counted time span in the milliseconds.
     * @since 2.0.0
     */
    public TimeSpan(final long milliseconds) {
        this.milliseconds = milliseconds;
    }

    /**
     * Returns milliseconds representation of {@code this} object.
     * @return this in milliseconds.
     * @since 2.0.0
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
        final TimeSpan other = (TimeSpan) obj;
        return milliseconds == other.milliseconds;
    }

    /**
     * Returns a string representation of the time span in human-readable format. Examples:
     * 
     * <pre>
     * 0 seconds
     * 589 milliseconds
     * 1 second
     * 57 seconds 42 milliseconds
     * 1 minute
     * 2 minutes 1 millisecond
     * 7 minutes 1 second
     * 12 minutes 28 seconds 321 milliseconds
     * 1 hour
     * 5 hours
     * 5 hours 1 second
     * 5 hours 1 minute 54 seconds 132 milliseconds
     * 5 hours 9 minutes 4 seconds 123 milliseconds
     * </pre>
     * 
     * @return a string representation of the time in human-readable format.
     * @since 2.0.0
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
            appendTimeWithUnit(time, hours, "hour");
        }
        if (minutes > 0) {
            appendTimeWithUnit(time, minutes, "minute");
        }
        if (seconds > 0) {
            appendTimeWithUnit(time, seconds, "second");
        }
        if (millis > 0) {
            appendTimeWithUnit(time, millis, "millisecond");
        }
        final String text = time.toString();
        if (text.length() == 0) {
            return createText(0, "second");
        }
        return text;
    }

    private static void appendTimeWithUnit(final StringBuilder text, final long time, final String unit) {
        if (text.length() > 0) {
            text.append(' ');
        }
        text.append(createText(time, unit));
    }

    private static String createText(final long value, final String unit) {
        final StringBuilder text = new StringBuilder();
        text.append(value);
        text.append(' ');
        text.append(unit);
        if (value != 1) {
            text.append('s');
        }
        return text.toString();
    }
}
