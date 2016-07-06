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
package biz.gabrys.maven.plugin.util.timer;

/**
 * Default implementation of the {@link Timer} which based on the {@link System#currentTimeMillis()} method.
 * @since 1.0
 */
public class SystemTimer implements Timer {

    private final Object mutex = new Object();

    private Long startTime;
    private volatile Time time;

    /**
     * Constructs a new instance.
     * @since 1.0
     */
    public SystemTimer() {
        // do nothing
    }

    public void start() {
        synchronized (mutex) {
            time = null;
            startTime = System.currentTimeMillis();
        }
    }

    public Time stop() {
        if (time == null) {
            synchronized (mutex) {
                if (time == null) {
                    time = getTime();
                }
            }
        }
        return time;
    }

    /**
     * {@inheritDoc} The current counted time is equal to:
     * <ul>
     * <li>{@code null} when timer has not been started</li>
     * <li>{@code current time - start time} when timer has been started and has not been stopped</li>
     * <li>{@code counted time during stop} when timer has been stopped</li>
     * </ul>
     * @since 1.0
     */
    public Time getTime() {
        if (startTime == null) {
            return null;
        }
        if (time == null) {
            return new Time(System.currentTimeMillis() - startTime);
        }
        return time;
    }

    /**
     * Returns a new started timer.
     * @return the new timer.
     * @since 1.0
     * @see #start()
     */
    public static SystemTimer getStartedTimer() {
        final SystemTimer timer = new SystemTimer();
        timer.start();
        return timer;
    }
}
