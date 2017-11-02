package biz.gabrys.maven.plugin.util.parameter.converter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;

public class AbstractValueToStringConverterTest {

    @Test
    public void convert_notNullConverterAndNullObject_returnsString() {
        final NotNullConverter converter = spy(new NotNullConverter());

        final String converted = converter.convert(null);

        assertEquals(AbstractValueToStringConverter.NULL_STRING, converted);
        verify(converter).convert(null);
        verifyNoMoreInteractions(converter);
    }

    @Test
    public void convert_nullConverterAndNullObject_returnsString() {
        final NullConverter converter = spy(new NullConverter());

        final String converted = converter.convert(null);

        assertEquals(AbstractValueToStringConverter.NULL_STRING, converted);
        verify(converter).convert(null);
        verifyNoMoreInteractions(converter);
    }

    @Test
    public void convert_notNullConverterAndNotNullObject_returnsString() {
        final NotNullConverter converter = spy(new NotNullConverter());
        final Object value = new Object();

        final String converted = converter.convert(value);

        assertEquals(NotNullConverter.RETURNED_VALUE, converted);
        verify(converter).convert(value);
        verify(converter).convert2(value);
        verifyNoMoreInteractions(converter);
    }

    @Test
    public void convert_nullConverterAndNotNullObject_returnsString() {
        final NullConverter converter = spy(new NullConverter());
        final Object value = new Object();

        final String converted = converter.convert(value);

        assertEquals(AbstractValueToStringConverter.NULL_STRING, converted);
        verify(converter).convert(value);
        verify(converter).convert2(value);
        verifyNoMoreInteractions(converter);
    }

    public static class NotNullConverter extends AbstractValueToStringConverter {

        private static final String RETURNED_VALUE = "not null";

        @Override
        protected String convert2(final Object value) {
            return RETURNED_VALUE;
        }
    }

    public static class NullConverter extends AbstractValueToStringConverter {

        @Override
        protected String convert2(final Object value) {
            return null;
        }
    }
}
