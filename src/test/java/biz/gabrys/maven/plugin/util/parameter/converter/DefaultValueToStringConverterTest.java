package biz.gabrys.maven.plugin.util.parameter.converter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public final class DefaultValueToStringConverterTest {

    @Test
    public void convert_convertersMapIsEmpty_executesFallback() {
        final Map<Class<?>, ValueToStringConverter> converters = Collections.emptyMap();
        final ValueToStringConverter fallback = Mockito.mock(ValueToStringConverter.class);
        final DefaultValueToStringConverter converter = new DefaultValueToStringConverter(converters, fallback);

        final Object value = Mockito.mock(Object.class);
        final String expectedConverted = "object";
        Mockito.when(fallback.convert(value)).thenReturn(expectedConverted);

        final String converted = converter.convert(value);
        Assert.assertEquals("Converted value.", expectedConverted, converted);
    }

    @Test
    public void convert_convertersMapDoesNotContainConverterWhichSupportsValueType_executesFallback() {
        final Map<Class<?>, ValueToStringConverter> converters = new HashMap<Class<?>, ValueToStringConverter>();
        final ValueToStringConverter integerConverter = Mockito.mock(ValueToStringConverter.class);
        converters.put(Integer.class, integerConverter);
        final ValueToStringConverter booleanConverter = Mockito.mock(ValueToStringConverter.class);
        converters.put(Boolean.class, booleanConverter);
        final ValueToStringConverter fallback = Mockito.mock(ValueToStringConverter.class);
        final DefaultValueToStringConverter converter = new DefaultValueToStringConverter(converters, fallback);

        final String value = "value";
        final String expectedConverted = value;
        Mockito.when(fallback.convert(value)).thenReturn(expectedConverted);

        final String converted = converter.convert(value);
        Assert.assertEquals("Converted value.", expectedConverted, converted);
        Mockito.verify(fallback).convert(value);
        Mockito.verifyZeroInteractions(integerConverter, booleanConverter);
    }

    @Test
    public void convert_convertersMapContainsConverterWhichSupportsValueType_executesConverterWhichSupportsValueType() {
        final Map<Class<?>, ValueToStringConverter> converters = new HashMap<Class<?>, ValueToStringConverter>();
        final ValueToStringConverter integerConverter = Mockito.mock(ValueToStringConverter.class);
        converters.put(Integer.class, integerConverter);
        final ValueToStringConverter booleanConverter = Mockito.mock(ValueToStringConverter.class);
        converters.put(Boolean.class, booleanConverter);
        final ValueToStringConverter fallback = Mockito.mock(ValueToStringConverter.class);
        final DefaultValueToStringConverter converter = new DefaultValueToStringConverter(converters, fallback);

        final Boolean value = Boolean.TRUE;
        final String expectedConverted = value.toString();
        Mockito.when(booleanConverter.convert(value)).thenReturn(expectedConverted);

        final String converted = converter.convert(value);
        Assert.assertEquals("Converted value.", expectedConverted, converted);
        Mockito.verify(booleanConverter).convert(value);
        Mockito.verifyZeroInteractions(integerConverter, fallback);
    }
}
