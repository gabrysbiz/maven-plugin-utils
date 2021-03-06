/*
 * Maven Plugin Utils
 * https://gabrysbiz.github.io/maven-plugin-utils/
 *
 * Copyright (c) 2015-2020 Adam Gabrys
 *
 * This file is licensed under the BSD 3-Clause (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain:
 * - a copy of the License at project page
 * - a template of the License at https://opensource.org/licenses/BSD-3-Clause
 */
/**
 * <p>
 * Contains types responsible for counting the execution time.
 * </p>
 * <p>
 * Example:
 * </p>
 * 
 * <pre>
 * public class ExampleMojo extends AbstractMojo {
 *
 *     public void execute() {
 *         {@link biz.gabrys.maven.plugin.util.timer.Timer Timer} timer = {@link biz.gabrys.maven.plugin.util.timer.SystemTimer SystemTimer}.getStartedTimer();
 *
 *         // logic
 *         ...
 *
 *         getLog().info("Finished in " + timer.stop());
 *     }
 * }
 * </pre>
 */
package biz.gabrys.maven.plugin.util.timer;
