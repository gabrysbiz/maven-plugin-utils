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
 * <p>
 * Default implementation of the {@link Timer} which based on the {@link System#currentTimeMillis()} method.
 * </p>
 * <p>
 * Example:
 * </p>
 * 
 * <pre>
 * public class ExampleMojo extends AbstractMojo {
 *
 *     public void execute() {
 *         {@link Timer} timer = {@link SystemTimer#getStartedTimer() SystemTimer.getStartedTimer()};
 *
 *         // logic
 *         ...
 *
 *         getLog().info("Finished in " + timer.stop());
 *     }
 * }
 * </pre>
 * 
 * @since 1.0
 */
public class SystemTimer implements Timer {

    private final Object mutex = new Object();

    private Long startTime;
    private volatile TimeSpan time;

    /**
     * Constructs a new instance.
     * @since 1.0
     */
    public SystemTimer() {
        // do nothing
    }

    @Override
    public void start() {
        synchronized (mutex) {
            time = null;
            startTime = System.currentTimeMillis();
        }
    }

    @Override
    public TimeSpan stop() {
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
     * {@inheritDoc} The current counted time span is equal to:
     * <ul>
     * <li>{@code null} when timer has not been started</li>
     * <li>{@code current time - start time} when timer has been started and has not been stopped</li>
     * <li>{@code counted time during stop} when timer has been stopped</li>
     * </ul>
     * @since 2.0.0
     */
    @Override
    public TimeSpan getTime() {
        if (startTime == null) {
            return null;
        }
        if (time == null) {
            return new TimeSpan(System.currentTimeMillis() - startTime);
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
