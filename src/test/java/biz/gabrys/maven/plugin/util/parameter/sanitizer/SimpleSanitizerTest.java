package biz.gabrys.maven.plugin.util.parameter.sanitizer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.Test;

public final class SimpleSanitizerTest {

    @Test
    public void sanitize2() {
        final Object sanitizedValue = mock(Object.class);
        final SimpleSanitizer sanitizer = new SimpleSanitizer(true, sanitizedValue);

        final Object value = mock(Object.class);
        final Object result = sanitizer.sanitize2(value);

        assertThat(result).isSameAs(sanitizedValue);
        verifyNoInteractions(sanitizedValue, value);
    }
}
