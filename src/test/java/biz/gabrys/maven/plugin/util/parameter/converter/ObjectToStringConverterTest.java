package biz.gabrys.maven.plugin.util.parameter.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public final class ObjectToStringConverterTest {

    @Test
    public void convert_nullObject_returnsString() {
        final ObjectToStringConverter converter = new ObjectToStringConverter();
        final String converted = converter.convert(null);
        assertThat(converted).isEqualTo("null");
    }

    @Test
    public void convert_notNullObject_returnsString() {
        final ObjectToStringConverter converter = new ObjectToStringConverter();
        final String converted = converter.convert(5);
        assertThat(converted).isEqualTo("5");
    }
}
