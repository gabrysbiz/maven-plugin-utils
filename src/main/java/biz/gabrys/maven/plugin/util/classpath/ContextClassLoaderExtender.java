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
package biz.gabrys.maven.plugin.util.classpath;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
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
     * @since 1.4.0
     */
    public ContextClassLoaderExtender(final MavenProject project) {
        this(project, null);
    }

    /**
     * Creates a new instance.
     * @param project the Maven project.
     * @param logger the logger.
     * @since 1.4.0
     */
    public ContextClassLoaderExtender(final MavenProject project, final Log logger) {
        this.project = project;
        this.logger = logger;
    }

    /**
     * Adds all dependencies with specified types to context class loader.
     * @param types the supported types.
     * @since 1.4.0
     */
    public void addDependencies(final String... types) {
        ParameterUtils.verifyNotNull("types", types);
        addDependencies(new HashSet<String>(Arrays.asList(types)));
    }

    /**
     * Adds all dependencies with specified types to context class loader.
     * @param types the supported types.
     * @since 1.4.0
     */
    public void addDependencies(final Collection<String> types) {
        ParameterUtils.verifyNotNull("types", types);
        @SuppressWarnings("unchecked")
        final Set<Artifact> artifacts = project.getArtifacts();
        final List<Artifact> filtered = filterArtifacts(artifacts, types);
        final List<URL> urls = resolveArtifactsUrls(filtered);
        final URLClassLoader classLoader = new URLClassLoader(urls.toArray(new URL[0]), Thread.currentThread().getContextClassLoader());
        Thread.currentThread().setContextClassLoader(classLoader);
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
            if (!types.contains(artifact.getType())) {
                logger.debug(String.format("Exclude %s", createDisplayString(artifact)));
            } else {
                logger.debug(String.format("Include %s", createDisplayString(artifact)));
                filtered.add(artifact);
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
        final List<URL> urls = new LinkedList<URL>();
        for (final Artifact artifact : artifacts) {
            try {
                urls.add(artifact.getFile().toURI().toURL());
            } catch (final MalformedURLException e) {
                // never
                throw new IllegalStateException(String.format("Cannot add dependency %s to classpath!", createDisplayString(artifact)), e);
            }
        }
        return urls;
    }

    /**
     * Creates a text representation of the {@link Artifact}.
     * @param artifact the artifact.
     * @return the text representation of the {@link Artifact}.
     * @since 1.4.0
     */
    protected String createDisplayString(final Artifact artifact) {
        return String.format("%s:%s-%s.%s (%s)", artifact.getGroupId(), artifact.getArtifactId(), artifact.getVersion(), artifact.getType(),
                artifact.getScope());
    }
}
