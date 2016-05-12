package biz.gabrys.maven.plugin.util.io;

import org.junit.Assert;
import org.junit.Test;

public final class ScannerPatternFormatTest {

    @Test
    public void toPattern_correctName_returnsPattern() {
        for (final ScannerPatternFormat value : ScannerPatternFormat.values()) {
            ScannerPatternFormat format = ScannerPatternFormat.toPattern(value.name());
            Assert.assertEquals("UPPERCASE name.", value, format);

            format = ScannerPatternFormat.toPattern(value.name().toLowerCase());
            Assert.assertEquals("lowercase name.", value, format);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void toPattern_inCorrectName_returnsPattern() {
        ScannerPatternFormat.toPattern("non existent name");
    }
}
