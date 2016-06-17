package biz.gabrys.maven.plugin.util.parameter.sanitizer;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public final class SimpleSanitizerTest {

    @Test
    public void sanitize2() {
        final Object sanitizedValue = Mockito.mock(Object.class);
        final SimpleSanitizer sanitizer = new SimpleSanitizer(true, sanitizedValue);

        final Object value = Mockito.mock(Object.class);
        final Object result = sanitizer.sanitize2(value);

        Assert.assertTrue("Sanitized value and returned value should be the same object.", sanitizedValue == result);
        Mockito.verifyZeroInteractions(sanitizedValue, value);
    }
}
