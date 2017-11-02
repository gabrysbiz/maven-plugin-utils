package biz.gabrys.maven.plugin.util.parameter.sanitizer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Test;

public final class AbstractSimpleSanitizerTest {

    @Test
    public void isValid_conditionIsTrue_returnsTrue() {
        final SimpleSanitizerImpl sanitizer = new SimpleSanitizerImpl(true);
        assertTrue("Returned value (null parameter)", sanitizer.isValid(null));

        final Object value = mock(Object.class);
        assertTrue("Returned value (not null parameter)", sanitizer.isValid(value));
        verifyZeroInteractions(value);
    }

    @Test
    public void isValid_conditionIsFalse_returnsFalse() {
        final SimpleSanitizerImpl sanitizer = new SimpleSanitizerImpl(false);
        assertFalse("Returned value (null parameter)", sanitizer.isValid(null));

        final Object value = mock(Object.class);
        assertFalse("Returned value (not null parameter)", sanitizer.isValid(value));
        verifyZeroInteractions(value);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void sanitize_conditionIsTrue_throwsException() {
        final SimpleSanitizerImpl sanitizer = new SimpleSanitizerImpl(true);
        sanitizer.sanitize(null);
    }

    @Test
    public void sanitize_conditionIsFalse_executesSanitize2() {
        final SimpleSanitizerImpl sanitizer = spy(new SimpleSanitizerImpl(false));
        final Object value = mock(Object.class);

        final Object sanitizedValue = sanitizer.sanitize(value);

        assertSame(value, sanitizedValue);
        verify(sanitizer).sanitize(value);
        verify(sanitizer).sanitize2(value);
        verifyNoMoreInteractions(sanitizer);
        verifyZeroInteractions(value);
    }

    public static class SimpleSanitizerImpl extends AbstractSimpleSanitizer {

        protected SimpleSanitizerImpl(final boolean valid) {
            super(valid);
        }

        @Override
        protected Object sanitize2(final Object value) {
            return value;
        }
    }
}
