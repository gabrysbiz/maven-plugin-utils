package biz.gabrys.maven.plugin.util.parameter.sanitizer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.Test;

public final class AlwaysValidSanitizerTest {

    @Test
    public void isValid_valueIsNull_returnsTrue() {
        final AlwaysValidSanitizer sanitizer = new AlwaysValidSanitizer();
        final boolean valid = sanitizer.isValid(null);
        assertThat(valid).isTrue();
    }

    @Test
    public void isValid_valueIsNotNull_returnsTrue() {
        final AlwaysValidSanitizer sanitizer = new AlwaysValidSanitizer();
        final Object value = mock(Object.class);

        final boolean valid = sanitizer.isValid(value);

        assertThat(valid).isTrue();
        verifyNoInteractions(value);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void sanitize_valueIsNull_throwsException() {
        final AlwaysValidSanitizer sanitizer = new AlwaysValidSanitizer();
        sanitizer.sanitize(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void sanitize_valueINotNull_throwsException() {
        final AlwaysValidSanitizer sanitizer = new AlwaysValidSanitizer();
        final Object value = mock(Object.class);
        sanitizer.sanitize(value);
    }
}
