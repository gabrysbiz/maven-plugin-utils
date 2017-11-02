package biz.gabrys.maven.plugin.util.parameter.converter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class ArrayToStringConverterTest {

    @Test
    public void convert_nullObject_returnsString() {
        final ArrayToStringConverter converter = new ArrayToStringConverter();
        final String converted = converter.convert(null);
        assertEquals("null", converted);
    }

    @Test
    public void convert_notNullObject_returnsString() {
        final ArrayToStringConverter converter = new ArrayToStringConverter();
        final String[] value = { "one", "two", "three" };

        final String converted = converter.convert(value);

        assertEquals("[one, two, three]", converted);
    }
}
