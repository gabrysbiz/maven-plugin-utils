package biz.gabrys.maven.plugin.util.parameter.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;

public final class CollectionToStringConverterTest {

    @Test
    public void convert_nullObject_returnsString() {
        final CollectionToStringConverter converter = new CollectionToStringConverter();
        final String converted = converter.convert(null);
        assertThat(converted).isEqualTo("null");
    }

    @Test
    public void convert_notNullObject_returnsString() {
        final CollectionToStringConverter converter = new CollectionToStringConverter();
        final String converted = converter.convert(Arrays.asList(10, 20, 30));
        assertThat(converted).isEqualTo("[10, 20, 30]");
    }
}
