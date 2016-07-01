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
package biz.gabrys.maven.plugin.util.parameter.converter;

import java.util.Arrays;
import java.util.Collection;

/**
 * Responsible for converting collections to string representation.
 * @since 1.3.0
 */
public class CollectionToStringConverter extends AbstractValueToStringConverter {

    /**
     * Constructs a new instance.
     * @since 1.3.0
     */
    public CollectionToStringConverter() {
        // do nothing
    }

    @Override
    public String convert2(final Object value) {
        final Collection<?> collection = (Collection<?>) value;
        return Arrays.toString(collection.toArray());
    }
}
