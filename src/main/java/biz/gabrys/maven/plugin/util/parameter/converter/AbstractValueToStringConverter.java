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

public abstract class AbstractValueToStringConverter implements ValueToStringConverter {

    protected AbstractValueToStringConverter() {
        // do nothing
    }

    public String convert(final Object value) {
        if (value == null) {
            return "null";
        }
        return convert2(value);
    }

    protected abstract String convert2(final Object value);
}
