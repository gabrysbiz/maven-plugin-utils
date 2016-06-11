package biz.gabrys.maven.plugin.util.parameter.converter;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class AbstractValueToStringConverterTest {

    @Test
    public void convert_notNullConverterAndNullObject_returnsString() {
        final NotNullConverter converter = Mockito.spy(new NotNullConverter());
        final String converted = converter.convert(null);
        Assert.assertEquals("Converted value.", AbstractValueToStringConverter.NULL_STRING, converted);
        Mockito.verify(converter).convert(null);
        Mockito.verifyNoMoreInteractions(converter);
    }

    @Test
    public void convert_nullConverterAndNullObject_returnsString() {
        final NullConverter converter = Mockito.spy(new NullConverter());
        final String converted = converter.convert(null);
        Assert.assertEquals("Converted value.", AbstractValueToStringConverter.NULL_STRING, converted);
        Mockito.verify(converter).convert(null);
        Mockito.verifyNoMoreInteractions(converter);
    }

    @Test
    public void convert_notNullConverterAndNotNullObject_returnsString() {
        final NotNullConverter converter = Mockito.spy(new NotNullConverter());
        final Object value = new Object();
        final String converted = converter.convert(value);
        Assert.assertEquals("Converted value.", NotNullConverter.RETURNED_VALUE, converted);
        Mockito.verify(converter).convert(value);
        Mockito.verify(converter).convert2(value);
        Mockito.verifyNoMoreInteractions(converter);
    }

    @Test
    public void convert_nullConverterAndNotNullObject_returnsString() {
        final NullConverter converter = Mockito.spy(new NullConverter());
        final Object value = new Object();
        final String converted = converter.convert(value);
        Assert.assertEquals("Converted value.", AbstractValueToStringConverter.NULL_STRING, converted);
        Mockito.verify(converter).convert(value);
        Mockito.verify(converter).convert2(value);
        Mockito.verifyNoMoreInteractions(converter);
    }

    private static class NotNullConverter extends AbstractValueToStringConverter {

        private static final String RETURNED_VALUE = "not null";

        @Override
        protected String convert2(final Object value) {
            return RETURNED_VALUE;
        }
    }

    private static class NullConverter extends AbstractValueToStringConverter {

        @Override
        protected String convert2(final Object value) {
            return null;
        }
    }
}
