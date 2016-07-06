package biz.gabrys.maven.plugin.util.parameter.sanitizer;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public final class AlwaysValidSanitizerTest {

    @Test
    public void isValid_valueIsNull_returnsTrue() {
        final AlwaysValidSanitizer sanitizer = new AlwaysValidSanitizer();
        final boolean valid = sanitizer.isValid(null);
        Assert.assertTrue("Returned value.", valid);
    }

    @Test
    public void isValid_valueIsNotNull_returnsTrue() {
        final AlwaysValidSanitizer sanitizer = new AlwaysValidSanitizer();

        final Object value = Mockito.mock(Object.class);
        final boolean valid = sanitizer.isValid(value);

        Assert.assertTrue("Returned value.", valid);
        Mockito.verifyZeroInteractions(value);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void sanitize_valueIsNull_throwsException() {
        final AlwaysValidSanitizer sanitizer = new AlwaysValidSanitizer();
        sanitizer.sanitize(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void sanitize_valueINotNull_throwsException() {
        final AlwaysValidSanitizer sanitizer = new AlwaysValidSanitizer();
        final Object value = Mockito.mock(Object.class);
        sanitizer.sanitize(value);
    }
}
