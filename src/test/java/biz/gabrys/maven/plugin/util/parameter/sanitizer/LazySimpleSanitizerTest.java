package biz.gabrys.maven.plugin.util.parameter.sanitizer;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugin.util.parameter.sanitizer.LazySimpleSanitizer.ValueContainer;

public final class LazySimpleSanitizerTest {

    @Test
    public void sanitize2() {
        final Object sanitizedValue = Mockito.mock(Object.class);
        final ValueContainer container = Mockito.spy(new ValueContainerImpl(sanitizedValue));
        final LazySimpleSanitizer sanitizer = new LazySimpleSanitizer(true, container);

        final Object value = Mockito.mock(Object.class);
        final Object result = sanitizer.sanitize2(value);

        Assert.assertTrue("Sanitized value and returned value should be the same object.", sanitizedValue == result);
        Mockito.verify(container).getValue();
        Mockito.verifyNoMoreInteractions(container);
        Mockito.verifyZeroInteractions(sanitizedValue, value);
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
