package biz.gabrys.maven.plugin.util.parameter.converter;

import java.util.Arrays;

public class ArrayToStringConverter implements ValueToStringConverter {

    public String convert(final Object value) {
        return Arrays.toString((Object[]) value);
    }
}
