package biz.gabrys.maven.plugin.util.parameter.sanitizer;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Test;

import biz.gabrys.maven.plugin.util.parameter.sanitizer.LazySimpleSanitizer.ValueContainer;

public final class LazySimpleSanitizerTest {

    @Test
    public void sanitize2() {
        final Object sanitizedValue = mock(Object.class);
        final ValueContainer container = spy(new ValueContainerImpl(sanitizedValue));
        final LazySimpleSanitizer sanitizer = new LazySimpleSanitizer(true, container);

        final Object value = mock(Object.class);
        final Object result = sanitizer.sanitize2(value);

        assertSame(sanitizedValue, result);
        verify(container).getValue();
        verifyNoMoreInteractions(container);
        verifyZeroInteractions(sanitizedValue, value);
    }

    public static class ValueContainerImpl implements ValueContainer {

        private final Object value;

        public ValueContainerImpl(final Object value) {
            this.value = value;
        }

        public Object getValue() {
            return value;
        }
    }
}
