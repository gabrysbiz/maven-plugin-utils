package biz.gabrys.maven.plugin.util.parameter.sanitizer;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public final class AbstractSimpleSanitizerTest {

    @Test
    public void isValid_conditionIsTrue_returnsTrue() {
        final SimpleSanitizerImpl sanitizer = new SimpleSanitizerImpl(true);

        Assert.assertTrue("Returned value (null parameter).", sanitizer.isValid(null));

        final Object value = Mockito.mock(Object.class);
        Assert.assertTrue("Returned value (not null parameter).", sanitizer.isValid(value));
        Mockito.verifyZeroInteractions(value);
    }

    @Test
    public void isValid_conditionIsFalse_returnsFalse() {
        final SimpleSanitizerImpl sanitizer = new SimpleSanitizerImpl(false);

        Assert.assertFalse("Returned value (null parameter).", sanitizer.isValid(null));

        final Object value = Mockito.mock(Object.class);
        Assert.assertFalse("Returned value (not null parameter).", sanitizer.isValid(value));
        Mockito.verifyZeroInteractions(value);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void sanitize_conditionIsTrue_throwsException() {
        final SimpleSanitizerImpl sanitizer = new SimpleSanitizerImpl(true);
        sanitizer.sanitize(null);
    }

    @Test
    public void sanitize_conditionIsFalse_executesSanitize2() {
        final SimpleSanitizerImpl sanitizer = Mockito.spy(new SimpleSanitizerImpl(false));

        final Object value = Mockito.mock(Object.class);
        final Object sanitizedValue = sanitizer.sanitize(value);

        Assert.assertTrue("Sanitized value and returned value should be the same object.", value == sanitizedValue);

        Mockito.verify(sanitizer).sanitize(value);
        Mockito.verify(sanitizer).sanitize2(value);
        Mockito.verifyNoMoreInteractions(sanitizer);
        Mockito.verifyZeroInteractions(value);
    }

    private static class SimpleSanitizerImpl extends AbstractSimpleSanitizer {

        protected SimpleSanitizerImpl(final boolean valid) {
            super(valid);
        }

        @Override
        protected Object sanitize2(final Object value) {
            return value;
        }
    }
}
