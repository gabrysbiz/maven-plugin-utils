package biz.gabrys.maven.plugin.util.parameter.converter;

import org.junit.Assert;
import org.junit.Test;

public final class ArrayToStringConverterTest {

    @Test
    public void convert_nullObject_returnsString() {
        final ArrayToStringConverter converter = new ArrayToStringConverter();
        final String converted = converter.convert(null);
        Assert.assertEquals("Converted value.", "null", converted);
    }

    @Test
    public void convert_notNullObject_returnsString() {
        final ArrayToStringConverter converter = new ArrayToStringConverter();
        final String[] value = { "one", "two", "three" };
        final String converted = converter.convert(value);
        Assert.assertEquals("Converted value.", "[one, two, three]", converted);
    }
}
