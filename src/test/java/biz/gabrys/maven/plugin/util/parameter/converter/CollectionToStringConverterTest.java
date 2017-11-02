package biz.gabrys.maven.plugin.util.parameter.converter;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

public final class CollectionToStringConverterTest {

    @Test
    public void convert_nullObject_returnsString() {
        final CollectionToStringConverter converter = new CollectionToStringConverter();
        final String converted = converter.convert(null);
        assertEquals("null", converted);
    }

    @Test
    public void convert_notNullObject_returnsString() {
        final CollectionToStringConverter converter = new CollectionToStringConverter();
        final Collection<Integer> value = Arrays.asList(10, 20, 30);

        final String converted = converter.convert(value);

        assertEquals("[10, 20, 30]", converted);
    }
}
