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
package biz.gabrys.maven.plugin.util.parameter.converter;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    private final Map<Class<?>, ValueToStringConverter> converters;
    private final ValueToStringConverter fallback;

    /**
     * Constructs a new instance.
     * @since 1.3.0
     */
    public DefaultValueToStringConverter() {
        converters = new ConcurrentHashMap<Class<?>, ValueToStringConverter>();
        converters.put(Object[].class, new ArrayToStringConverter());
        converters.put(Collection.class, new CollectionToStringConverter());
        fallback = new ObjectToStringConverter();
    }

    // for tests
    DefaultValueToStringConverter(final Map<Class<?>, ValueToStringConverter> converters, final ValueToStringConverter fallback) {
        this.converters = converters;
        this.fallback = fallback;
    }

    @Override
    public String convert2(final Object value) {
        for (final Map.Entry<Class<?>, ValueToStringConverter> entry : converters.entrySet()) {
            if (entry.getKey().isInstance(value)) {
                return entry.getValue().convert(value);
            }
        }
        return fallback.convert(value);
    }
}
