package biz.gabrys.maven.plugin.util.io;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public final class ScannerPatternFormatTest {

    @Test
    public void toPattern_correctName_returnsPattern() {
        for (final ScannerPatternFormat value : ScannerPatternFormat.values()) {
            ScannerPatternFormat format = ScannerPatternFormat.toPattern(value.name());
            assertThat(format).isEqualTo(value);

            format = ScannerPatternFormat.toPattern(value.name().toLowerCase());
            assertThat(format).isEqualTo(value);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void toPattern_inCorrectName_returnsPattern() {
        ScannerPatternFormat.toPattern("non existent name");
    }
}
