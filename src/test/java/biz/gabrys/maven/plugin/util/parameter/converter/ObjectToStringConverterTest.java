package biz.gabrys.maven.plugin.util.parameter.converter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class ObjectToStringConverterTest {

    @Test
    public void convert_nullObject_returnsString() {
        final ObjectToStringConverter converter = new ObjectToStringConverter();
        final String converted = converter.convert(null);
        assertEquals("null", converted);
    }

    @Test
    public void convert_notNullObject_returnsString() {
        final ObjectToStringConverter converter = new ObjectToStringConverter();
        final Integer value = 5;

        final String converted = converter.convert(value);

        assertEquals(value.toString(), converted);
    }
}
