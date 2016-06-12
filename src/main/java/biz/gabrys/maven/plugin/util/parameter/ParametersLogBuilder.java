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

/**
 * <p>
 * Builder which allows to log Mojos parameters values. It supports basic types like arrays and collections. All other
 * types are threat as {@link Object}. The builder also provides API to use custom:
 * </p>
 * <ul>
 * <li>{@link ValueToStringConverter} - responsible for converting parameter value to string representation</li>
 * <li>{@link ValueSanitizer} - responsible for sanitizing Mojo parameter value</li>
 * </ul>
 * <p>
 * Example:
 * </p>
 * 
 * <pre>
 * public class ExampleMojo extends AbstractMojo
 *
 *     public void execute() {
 *         logParameters();
 *         ...
 *     }
 *
 *     private void logParameters() {
 *         ParametersLogger logger = new ParametersLogger(getLog());
 *
 *         // overwrite parameter
 *         logger.append("parameter", 1);
 *         logger.append("parameter", 2);
 *
 *         // default converter and sanitizer
 *         logger.append("booleanParameter", true);
 *         logger.append("stringArray", {"one", "two", "three"});
 *
 *         // custom converter
 *         logger.append("cutomObject", new Object(), new ValueToStringConverter() {
 *
 *             public String convert(final Object value) {
 *                 return "custom object";
 *             }
 *         });
 *
 *         // simple sanitizer
 *         logger.append("booleanTrueValue", true, new SimpleSanitizer(true, Boolean.TRUE));
 *         logger.append("booleanFalseValue", false, new SimpleSanitizer(false, Boolean.TRUE));
 *
 *         // lazy sanitizer
 *         logger.append("stringLazyNotExecuted", "lazy-not-executed", new LazySimpleSanitizer(true, new ValueContainer() {
 *
 *             public Object getValue() {
 *                 // never executed, because condition is equal to true 
 *                 return "heavyOperation1()";
 *             }
 *         }));
 *         logger.append("stringLazyExecuted", "lazy-executed", new LazySimpleSanitizer(false, new ValueContainer() {
 *
 *             public Object getValue() {
 *                 // executed, because condition is equal to false 
 *                 return "heavyOperation2()";
 *             }
 *         }));
 *
 *         // custom sanitizer
 *         final String customSanitizerValue = null;
 *         logger.append("customSanitizerValue", customSanitizerValue, new ValueSanitizer() {
 *
 *             private Object sanitizedValue;
 *
 *             public boolean isValid(final Object value) {
 *                 if (customSanitizerValue != null) {
 *                     return true;
 *                 }
 *                 sanitizedValue = "heavyOperation3()";
 *                 return sanitizedValue == null;
 *             }
 *
 *             public Object sanitize(final Object value) {
 *                 return sanitizedValue;
 *             }
 *         });
 *
 *         // log data
 *         logger.debug();
 *     }
 *
 *     ...
 * }
 * </pre>
 * <p>
 * Output:
 * </p>
 * 
 * <pre>
 * [DEBUG] Job parameters:
 * [DEBUG]       parameter = 2
 * [DEBUG]       booleanParameter = true
 * [DEBUG]       stringArray = ["one", "two", "three"]
 * [DEBUG]       cutomObject = custom object
 * [DEBUG]       booleanTrueValue = true
 * [DEBUG]       booleanFalseValue = false (calculated: true)
 * [DEBUG]       stringLazyNotExecuted = lazy-not-executed
 * [DEBUG]       stringLazyExecuted = lazy-executed (calculated: heavyOperation2())
 * [DEBUG]       customSanitizerValue = null (calculated: heavyOperation3())
 * </pre>
 * 
 * @since 1.3.0
 */
// TODO add tests
public class ParametersLogBuilder {

    /**
     * Stores parameters names and associated containers.
     * @since 1.3.0
     * @see Container
     */
    protected final Map<String, Container> parameters = new LinkedHashMap<String, Container>();
    /**
     * Logger used to log parameters.
     * @since 1.3.0
     */
    protected final Log logger;

    /**
     * Constructs a new object.
     * @param logger the logger used to log parameters.
     * @throws IllegalArgumentException if the logger is equal to {@code null}.
     * @since 1.3.0
     */
    public ParametersLogBuilder(final Log logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger cannot be null");
        }
        this.logger = logger;
    }

    /**
     * Appends a parameter with a valid value. If parameter has been appended before, then it removes its previous value
     * and adds a new.
     * @param name the parameter name.
     * @param value the parameter value.
     * @return {@code this} builder.
     * @since 1.3.0
     * @see DefaultValueToStringConverter
     * @see AlwaysValidSanitizer
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
     * @see AlwaysValidSanitizer
     */
    public ParametersLogBuilder append(final String name, final Object value, final ValueToStringConverter converter) {
        return append(name, value, converter, new AlwaysValidSanitizer());
    }

    /**
     * Appends a parameter with a valid value. If parameter has been appended before, then it removes its previous value
     * and adds a new.
     * @param name the parameter name.
     * @param value the parameter value.
     * @param sanitizer the sanitizer responsible for sanitizing parameter value.
     * @return {@code this} builder.
     * @since 1.3.0
     * @see DefaultValueToStringConverter
     */
    public ParametersLogBuilder append(final String name, final Object value, final ValueSanitizer sanitizer) {
        return append(name, value, new DefaultValueToStringConverter(), sanitizer);
    }

    /**
     * Appends a parameter with a valid value. If parameter has been appended before, then it removes its previous value
     * and adds a new.
     * @param name the parameter name.
     * @param value the parameter value.
     * @param converter the converter responsible for converting parameter value to string representation.
     * @param sanitizer the sanitizer responsible for sanitizing parameter value.
     * @return {@code this} builder.
     * @since 1.3.0
     */
    public ParametersLogBuilder append(final String name, final Object value, final ValueToStringConverter converter,
            final ValueSanitizer sanitizer) {
        parameters.remove(name);
        parameters.put(name, new Container(value, converter, sanitizer));
        return this;
    }

    /**
     * Logs appended parameters using info mode.
     * @return {@code true} whether the logger logged any data, otherwise {@code false}.
     * @since 1.3.0
     */
    public boolean info() {
        return log(new InternalLogger() {

            public boolean isEnabled() {
                return logger.isInfoEnabled();
            }

            public void log(final CharSequence line) {
                logger.info(line);
            }
        });
    }

    /**
     * Logs appended parameters using debug mode.
     * @return {@code true} whether the logger logged any data, otherwise {@code false}.
     * @since 1.3.0
     */
    public boolean debug() {
        return log(new InternalLogger() {

            public boolean isEnabled() {
                return logger.isDebugEnabled();
            }

            public void log(final CharSequence line) {
                logger.debug(line);
            }
        });
    }

    /**
     * Logs appended parameters using {@link InternalLogger}.
     * @param internalLogger the logger used to log parameters.
     * @return {@code true} whether the logger logged any data, otherwise {@code false}.
     * @since 1.3.0
     * @see #info()
     * @see #debug()
     */
    protected boolean log(final InternalLogger internalLogger) {
        if (!internalLogger.isEnabled()) {
            return false;
        }
        final List<String> lines = createLines();
        if (lines.isEmpty()) {
            return false;
        }
        for (final String line : lines) {
            internalLogger.log(line);
        }
        return true;
    }

    /**
     * Creates all lines which will be logged by the logger.
     * @return the lines which will be logged by the logger.
     * @since 1.3.0
     * @see #log(InternalLogger)
     */
    protected List<String> createLines() {
        final List<String> lines = new ArrayList<String>(parameters.size() + 2);
        lines.add("Job parameters:");
        lines.addAll(createParametersLines());
        lines.add("");
        return lines;
    }

    /**
     * Creates one line for every appended parameter.
     * @return the lines with parameters.
     * @since 1.3.0
     * @see #createLines()
     */
    protected List<String> createParametersLines() {
        final List<String> lines = new ArrayList<String>(parameters.size());
        for (final Map.Entry<String, Container> entry : parameters.entrySet()) {
            lines.add(createParemeterLine(entry.getKey(), entry.getValue()));
        }
        return lines;
    }

    /**
     * Creates a line for single parameter.
     * @param name the parameter name.
     * @param container the container which stores the parameter value and associated converter and sanitizer.
     * @return the line with the parameter name and value.
     * @since 1.3.0
     * @see #createParametersLines()
     */
    protected String createParemeterLine(final String name, final Container container) {
        final StringBuilder line = new StringBuilder();
        line.append("      ");
        line.append(name);
        line.append(" = ");
        line.append(createParameterValue(container));
        return line.toString();
    }

    /**
     * Creates a string representation of the parameter value.
     * @param container the container which stores the parameter value and associated converter and sanitizer.
     * @return the string representation of the parameter value.
     * @since 1.3.0
     * @see #createParemeterLine(String, Container)
     */
    protected String createParameterValue(final Container container) {
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

    /**
     * Container which stores parameter value and associated converter and sanitizer.
     * @since 1.3.0
     */
    protected static class Container {

        /**
         * Parameter value.
         * @since 1.3.0
         */
        protected final Object value;
        /**
         * Converter responsible for converting parameter value to string representation.
         * @since 1.3.0
         */
        protected final ValueToStringConverter converter;
        /**
         * Sanitizer responsible for sanitizing parameter value.
         * @since 1.3.0
         */
        protected final ValueSanitizer sanitizer;

        /**
         * Constructs a new instance.
         * @param value the parameter value.
         * @param converter the converter responsible for converting parameter value to string representation.
         * @param sanitizer the sanitizer responsible for sanitizing parameter value.
         * @since 1.3.0
         */
        protected Container(final Object value, final ValueToStringConverter converter, final ValueSanitizer sanitizer) {
            this.value = value;
            this.converter = converter;
            this.sanitizer = sanitizer;
        }
    }

    /**
     * <p>
     * Used to adaptation instance of the {@link org.apache.maven.plugin.logging.Log} to logger used in the
     * {@link ParametersLogBuilder#log(InternalLogger)} method.
     * </p>
     * <p>
     * Example:
     * </p>
     * 
     * <pre>
     * public boolean info() {
     *     return log(new InternalLogger() {
     *
     *         public boolean isEnabled() {
     *             return logger.isInfoEnabled();
     *         }
     *
     *         public void log(final CharSequence line) {
     *             logger.info(line);
     *         }
     *     });
     * }
     * </pre>
     * 
     * @since 1.3.0
     */
    protected static interface InternalLogger {

        /**
         * Checks whether a logger is enabled.
         * @return {@code true} whether the logger is enabled, otherwise {@code false}.
         * @since 1.3.0
         */
        boolean isEnabled();

        /**
         * Logs a line.
         * @param line the line to log.
         * @since 1.3.0
         */
        void log(CharSequence line);
    }
}
