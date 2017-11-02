package biz.gabrys.maven.plugin.util.parameter.sanitizer;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Test;

public final class SimpleSanitizerTest {

    @Test
    public void sanitize2() {
        final Object sanitizedValue = mock(Object.class);
        final SimpleSanitizer sanitizer = new SimpleSanitizer(true, sanitizedValue);

        final Object value = mock(Object.class);
        final Object result = sanitizer.sanitize2(value);

        assertSame(sanitizedValue, result);
        verifyZeroInteractions(sanitizedValue, value);
    }
}
