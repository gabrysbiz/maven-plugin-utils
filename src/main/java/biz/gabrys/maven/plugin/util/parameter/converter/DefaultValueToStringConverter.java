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
package biz.gabrys.maven.plugin.util.parameter.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Default {@link ValueToStringConverter} which allows to converts basic types. Supported types:
 * <ul>
 * <li>arrays (see {@link ArrayToStringConverter})</li>
 * <li>collections (see {@link CollectionToStringConverter})</li>
 * <li>objects (see {@link ObjectToStringConverter})</li>
 * </ul>
 * @since 1.3.0
 */
public class DefaultValueToStringConverter extends AbstractValueToStringConverter {

    private final Collection<Converter> converters;
    private final ValueToStringConverter fallback;

    /**
     * Constructs a new instance.
     * @since 1.3.0
     */
    public DefaultValueToStringConverter() {
        converters = new ArrayList<Converter>(2);
        converters.add(new Converter(Object[].class, new ArrayToStringConverter()));
        converters.add(new Converter(Collection.class, new CollectionToStringConverter()));
        fallback = new ObjectToStringConverter();
    }

    // for tests
    DefaultValueToStringConverter(final Map<Class<?>, ValueToStringConverter> converters, final ValueToStringConverter fallback) {
        this.converters = new ArrayList<Converter>(converters.size());
        for (final Entry<Class<?>, ValueToStringConverter> entry : converters.entrySet()) {
            this.converters.add(new Converter(entry.getKey(), entry.getValue()));
        }
        this.fallback = fallback;
    }

    @Override
    public String convert2(final Object value) {
        for (final Converter converter : converters) {
            if (converter.isSupported(value)) {
                return converter.convert(value);
            }
        }
        return fallback.convert(value);
    }

    private static final class Converter {

        private final Class<?> clazz;
        private final ValueToStringConverter valueConverter;

        private Converter(final Class<?> clazz, final ValueToStringConverter converter) {
            this.clazz = clazz;
            valueConverter = converter;
        }

        private boolean isSupported(final Object value) {
            return clazz.isInstance(value);
        }

        private String convert(final Object value) {
            return valueConverter.convert(value);
        }
    }
}
