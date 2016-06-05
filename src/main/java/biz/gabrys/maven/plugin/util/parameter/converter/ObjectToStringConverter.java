package biz.gabrys.maven.plugin.util.parameter.converter;

public class ObjectToStringConverter implements ValueToStringConverter {

    public String convert(final Object value) {
        return String.valueOf(value);
    }
}
