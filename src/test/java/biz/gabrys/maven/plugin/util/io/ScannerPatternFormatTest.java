package biz.gabrys.maven.plugin.util.io;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class ScannerPatternFormatTest {

    @Test
    public void toPattern_correctName_returnsPattern() {
        for (final ScannerPatternFormat value : ScannerPatternFormat.values()) {
            ScannerPatternFormat format = ScannerPatternFormat.toPattern(value.name());
            assertEquals("UPPERCASE name", value, format);

            format = ScannerPatternFormat.toPattern(value.name().toLowerCase());
            assertEquals("lowercase name", value, format);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void toPattern_inCorrectName_returnsPattern() {
        ScannerPatternFormat.toPattern("non existent name");
    }
}
