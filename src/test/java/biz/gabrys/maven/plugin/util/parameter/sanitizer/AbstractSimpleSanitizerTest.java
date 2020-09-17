package biz.gabrys.maven.plugin.util.parameter.sanitizer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;

public final class AbstractSimpleSanitizerTest {

    @Test
    public void isValid_conditionIsTrue_returnsTrue() {
        final SimpleSanitizerImpl sanitizer = new SimpleSanitizerImpl(true);
        assertThat(sanitizer.isValid(null)).isTrue();

        final Object value = mock(Object.class);
        assertThat(sanitizer.isValid(value)).isTrue();
        verifyNoInteractions(value);
    }

    @Test
    public void isValid_conditionIsFalse_returnsFalse() {
        final SimpleSanitizerImpl sanitizer = new SimpleSanitizerImpl(false);
        assertThat(sanitizer.isValid(null)).isFalse();

        final Object value = mock(Object.class);
        assertThat(sanitizer.isValid(value)).isFalse();
        verifyNoInteractions(value);
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

        assertThat(sanitizedValue).isSameAs(value);
        verify(sanitizer).sanitize(value);
        verify(sanitizer).sanitize2(value);
        verifyNoMoreInteractions(sanitizer);
        verifyNoInteractions(value);
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
