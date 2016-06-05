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
