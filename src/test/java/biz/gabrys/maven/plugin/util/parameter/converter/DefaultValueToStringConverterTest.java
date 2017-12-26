package biz.gabrys.maven.plugin.util.parameter.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public final class DefaultValueToStringConverterTest {

    @Test
    public void convert_convertersMapIsEmpty_executesFallback() {
        final Map<Class<?>, ValueToStringConverter> converters = Collections.emptyMap();
        final ValueToStringConverter fallback = mock(ValueToStringConverter.class);
        final DefaultValueToStringConverter converter = new DefaultValueToStringConverter(converters, fallback);

        final Object value = mock(Object.class);
        final String expectedConverted = "object";
        when(fallback.convert(value)).thenReturn(expectedConverted);

        final String converted = converter.convert(value);

        assertThat(converted).isEqualTo(expectedConverted);
    }

    @Test
    public void convert_convertersMapDoesNotContainConverterWhichSupportsValueType_executesFallback() {
        final Map<Class<?>, ValueToStringConverter> converters = new HashMap<Class<?>, ValueToStringConverter>();
        final ValueToStringConverter integerConverter = mock(ValueToStringConverter.class);
        converters.put(Integer.class, integerConverter);
        final ValueToStringConverter booleanConverter = mock(ValueToStringConverter.class);
        converters.put(Boolean.class, booleanConverter);
        final ValueToStringConverter fallback = mock(ValueToStringConverter.class);
        final DefaultValueToStringConverter converter = new DefaultValueToStringConverter(converters, fallback);

        final String value = "value";
        final String expectedConverted = value;
        when(fallback.convert(value)).thenReturn(expectedConverted);

        final String converted = converter.convert(value);

        assertThat(converted).isEqualTo(expectedConverted);
        verify(fallback).convert(value);
        verifyZeroInteractions(integerConverter, booleanConverter);
    }

    @Test
    public void convert_convertersMapContainsConverterWhichSupportsValueType_executesConverterWhichSupportsValueType() {
        final Map<Class<?>, ValueToStringConverter> converters = new HashMap<Class<?>, ValueToStringConverter>();
        final ValueToStringConverter integerConverter = mock(ValueToStringConverter.class);
        converters.put(Integer.class, integerConverter);
        final ValueToStringConverter booleanConverter = mock(ValueToStringConverter.class);
        converters.put(Boolean.class, booleanConverter);
        final ValueToStringConverter fallback = mock(ValueToStringConverter.class);
        final DefaultValueToStringConverter converter = new DefaultValueToStringConverter(converters, fallback);

        final Boolean value = Boolean.TRUE;
        final String expectedConverted = value.toString();
        when(booleanConverter.convert(value)).thenReturn(expectedConverted);

        final String converted = converter.convert(value);

        assertThat(converted).isEqualTo(expectedConverted);
        verify(booleanConverter).convert(value);
        verifyZeroInteractions(integerConverter, fallback);
    }
}
