package biz.gabrys.maven.plugin.util.parameter.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public final class ArrayToStringConverterTest {

    @Test
    public void convert_nullObject_returnsString() {
        final ArrayToStringConverter converter = new ArrayToStringConverter();
        final String converted = converter.convert(null);
        assertThat(converted).isEqualTo("null");
    }

    @Test
    public void convert_notNullObject_returnsString() {
        final ArrayToStringConverter converter = new ArrayToStringConverter();
        final String converted = converter.convert(new String[] { "one", "two", "three" });
        assertThat(converted).isEqualTo("[one, two, three]");
    }
}
