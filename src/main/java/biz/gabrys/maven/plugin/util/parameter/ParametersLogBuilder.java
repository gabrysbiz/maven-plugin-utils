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
package biz.gabrys.maven.plugin.util.parameter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.plugin.logging.Log;

import biz.gabrys.maven.plugin.util.parameter.converter.DefaultValueToStringConverter;
import biz.gabrys.maven.plugin.util.parameter.converter.ValueToStringConverter;
import biz.gabrys.maven.plugin.util.parameter.sanitizer.AlwaysValidSanitizer;
import biz.gabrys.maven.plugin.util.parameter.sanitizer.ValueSanitizer;

public class ParametersLogBuilder {

    protected final Map<String, Container> parameters = new LinkedHashMap<String, Container>();
    protected final Log logger;

    public ParametersLogBuilder(final Log logger) {
        this.logger = logger;
    }

    /**
     * Appends a parameter with a valid value. If parameter has been appended before, then it removes its previous value
     * and adds a new.
     * @param name the parameter name.
     * @param value the parameter value.
     * @return {@code this} builder.
     * @since 1.3.0
     */
    public ParametersLogBuilder append(final String name, final Object value) {
        return append(name, value, new DefaultValueToStringConverter(), new AlwaysValidSanitizer());
    }

    /**
     * Appends a parameter with a valid value. If parameter has been appended before, then it removes its previous value
     * and adds a new.
     * @param name the parameter name.
     * @param value the parameter value.
     * @param converter the converter responsible for converting parameter value to string representation.
     * @return {@code this} builder.
     * @since 1.3.0
     */
    public ParametersLogBuilder append(final String name, final Object value, final ValueToStringConverter converter) {
        return append(name, value, converter, new AlwaysValidSanitizer());
    }

    public ParametersLogBuilder append(final String name, final Object value, final ValueSanitizer sanitizer) {
        return append(name, value, new DefaultValueToStringConverter(), sanitizer);
    }

    public ParametersLogBuilder append(final String name, final Object value, final ValueToStringConverter converter,
            final ValueSanitizer sanitizer) {
        parameters.remove(name);
        parameters.put(name, new Container(value, converter, sanitizer));
        return this;
    }

    public void info() {
        if (logger.isInfoEnabled()) {
            for (final String line : createLines()) {
                logger.info(line);
            }
        }
    }

    public void debug() {
        if (logger.isDebugEnabled()) {
            for (final String line : createLines()) {
                logger.debug(line);
            }
        }
    }

    protected List<String> createLines() {
        final List<String> lines = new ArrayList<String>(parameters.size() + 1);
        lines.add("Job parameters:");
        for (final Map.Entry<String, Container> entry : parameters.entrySet()) {
            lines.add(createLine(entry.getKey(), entry.getValue()));
        }
        lines.add("");
        return lines;
    }

    protected String createLine(final String name, final Container container) {
        final StringBuilder line = new StringBuilder();
        line.append("    ");
        line.append(name);
        line.append(" = ");
        line.append(createLineValue(container));
        return line.toString();
    }

    protected String createLineValue(final Container container) {
        final StringBuilder line = new StringBuilder();
        line.append(container.converter.convert(container.value));
        if (!container.sanitizer.isValid(container.value)) {
            line.append(" (calculated: ");
            final Object correctedValue = container.sanitizer.sanitize(container.value);
            line.append(container.converter.convert(correctedValue));
            line.append(')');
        }
        return line.toString();
    }

    protected static final class Container {

        protected final Object value;
        protected final ValueToStringConverter converter;
        protected final ValueSanitizer sanitizer;

        protected Container(final Object value, final ValueToStringConverter converter, final ValueSanitizer sanitizer) {
            this.value = value;
            this.converter = converter;
            this.sanitizer = sanitizer;
        }
    }
}
