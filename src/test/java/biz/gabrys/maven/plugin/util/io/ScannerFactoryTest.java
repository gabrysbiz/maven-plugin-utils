package biz.gabrys.maven.plugin.util.io;

import java.util.HashMap;
import java.util.Map;

import org.apache.maven.plugin.logging.Log;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public final class ScannerFactoryTest {

    @Test
    public void create_checkAllTypes_supportsAll() {
        final ScannerFactory factory = new ScannerFactory();

        final Log logger = Mockito.mock(Log.class);
        final Map<Class<?>, ScannerPatternFormat> scannersTypes = new HashMap<Class<?>, ScannerPatternFormat>();
        for (final ScannerPatternFormat patternFormat : ScannerPatternFormat.values()) {
            final FileScanner scanner = factory.create(patternFormat, logger);
            Assert.assertNotNull(String.format("File scanner instance for %s pattern format.", patternFormat.name()), scanner);

            final Class<?> clazz = scanner.getClass();
            if (scannersTypes.containsKey(clazz)) {
                Assert.fail(String.format("Factory returns the same scanner type (%s) for diffirent pattern formats: %s and %s.",
                        clazz.getName(), scannersTypes.get(clazz).name(), patternFormat.name()));
            }
            scannersTypes.put(clazz, patternFormat);
        }
    }

    @Test
    public void create_loggerIsNull_throwsException() {
        final ScannerFactory factory = new ScannerFactory();
        for (final ScannerPatternFormat patternFormat : ScannerPatternFormat.values()) {
            try {
                factory.create(patternFormat, null);
                Assert.fail("Should throw exception! Pattern format: " + patternFormat.name());
            } catch (final IllegalArgumentException e) {
                // ok
            }
        }
    }

    @Test
    public void create_antType_returnsAntFileScanner() {
        makeTypeTest(ScannerPatternFormat.ANT, AntFileScanner.class);
    }

    @Test
    public void create_regexType_returnsRegexFileScanner() {
        makeTypeTest(ScannerPatternFormat.REGEX, RegexFileScanner.class);
    }

    @Test
    public void create_nullType_returnsRegexFileScanner() {
        makeTypeTest(null, RegexFileScanner.class);
    }

    private static void makeTypeTest(final ScannerPatternFormat patternFormat, final Class<?> expectedClass) {
        final ScannerFactory factory = new ScannerFactory();

        final Log logger = Mockito.mock(Log.class);
        final FileScanner scanner = factory.create(patternFormat, logger);

        Assert.assertNotNull("File scanner instance.", scanner);
        Assert.assertEquals("File scanner class.", expectedClass, scanner.getClass());
        Mockito.verifyZeroInteractions(logger);
    }
}
