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
package biz.gabrys.maven.plugin.util.classpath;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import biz.gabrys.maven.plugin.util.parameter.ParameterUtils;

/**
 * Responsible for adding Maven project dependencies to classpath.
 * @since 1.4.0
 */
public class ContextClassLoaderExtender {

    private final MavenProject project;
    private final Log logger;

    /**
     * Creates a new instance.
     * @param project the Maven project.
     * @param logger the logger.
     * @throws IllegalArgumentException if the Maven project or/and the logger is equal to {@code null}.
     * @since 1.4.0
     */
    public ContextClassLoaderExtender(final MavenProject project, final Log logger) {
        ParameterUtils.verifyNotNull("project", project);
        ParameterUtils.verifyNotNull("logger", logger);
        this.project = project;
        this.logger = logger;
    }

    /**
     * Adds all dependencies with specified types to context class loader.
     * @param types the supported types.
     * @throws IllegalArgumentException if the types are equal to {@code null}.
     * @since 1.4.0
     */
    public void addDependencies(final String... types) {
        ParameterUtils.verifyNotNull("types", types);
        addDependencies(new HashSet<String>(Arrays.asList(types)));
    }

    /**
     * Adds all dependencies with specified types to context class loader.
     * @param types the supported types.
     * @throws IllegalArgumentException if the types collection is equal to {@code null}.
     * @since 1.4.0
     */
    public void addDependencies(final Collection<String> types) {
        ParameterUtils.verifyNotNull("types", types);
        final Set<Artifact> artifacts = project.getArtifacts();
        final List<Artifact> filtered = filterArtifacts(artifacts, types);
        final List<URL> urls = resolveArtifactsUrls(filtered);
        addToContextClassLoader(urls);
    }

    /**
     * Filters artifacts based on the type.
     * @param artifacts the collection which stores artifacts.
     * @param types the supported types.
     * @return the list with artifacts whose types fit to the supported types.
     * @since 1.4.0
     */
    protected List<Artifact> filterArtifacts(final Collection<Artifact> artifacts, final Collection<String> types) {
        final List<Artifact> filtered = new LinkedList<Artifact>();
        for (final Artifact artifact : artifacts) {
            if (types.contains(artifact.getType())) {
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("Include %s", createDisplayText(artifact)));
                }
                filtered.add(artifact);
            } else if (logger.isDebugEnabled()) {
                logger.debug(String.format("Exclude %s", createDisplayText(artifact)));
            }
        }
        return filtered;
    }

    /**
     * Returns URLs whose represents artifacts.
     * @param artifacts the collection which stores artifacts.
     * @return the artifacts' URLs.
     * @since 1.4.0
     */
    protected List<URL> resolveArtifactsUrls(final Collection<Artifact> artifacts) {
        final List<URL> urls = new ArrayList<URL>(artifacts.size());
        for (final Artifact artifact : artifacts) {
            try {
                urls.add(artifact.getFile().toURI().toURL());
            } catch (final MalformedURLException e) {
                // never
                throw new IllegalStateException(String.format("Cannot add %s to the classpath!", createDisplayText(artifact)), e);
            }
        }
        return urls;
    }

    /**
     * Adds artifacts URLs to context class loader.
     * @param urls the artifacts URLs.
     * @since 1.4.0
     */
    protected void addToContextClassLoader(final List<URL> urls) {
        final Thread currentThread = Thread.currentThread();
        final URLClassLoader classLoader = new URLClassLoader(urls.toArray(new URL[0]), currentThread.getContextClassLoader());
        currentThread.setContextClassLoader(classLoader);
    }

    /**
     * Creates a text representation of the {@link Artifact}.
     * @param artifact the artifact.
     * @return the text representation of the {@link Artifact}.
     * @since 1.4.0
     */
    protected String createDisplayText(final Artifact artifact) {
        return String.format("%s:%s-%s.%s (%s)", artifact.getGroupId(), artifact.getArtifactId(), artifact.getVersion(), artifact.getType(),
                artifact.getScope());
    }
}
