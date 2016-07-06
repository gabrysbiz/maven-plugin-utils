package biz.gabrys.maven.plugin.util.parameter.converter;

import org.junit.Assert;
import org.junit.Test;

public final class ObjectToStringConverterTest {

    @Test
    public void convert_nullObject_returnsString() {
        final ObjectToStringConverter converter = new ObjectToStringConverter();
        final String converted = converter.convert(null);
        Assert.assertEquals("Converted value.", "null", converted);
    }

    @Test
    public void convert_notNullObject_returnsString() {
        final ObjectToStringConverter converter = new ObjectToStringConverter();
        final Integer value = 5;
        final String converted = converter.convert(value);
        Assert.assertEquals("Converted value.", value.toString(), converted);
    }
}
