package biz.gabrys.maven.plugin.util.timer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public final class TimeSpanTest {

    @Test
    public void equals() {
        final long milliseconds = 100L;
        final TimeSpan timeSpan = new TimeSpan(milliseconds);

        assertThat(timeSpan.equals(timeSpan)).isTrue();
        assertThat(timeSpan.equals(null)).isFalse();
        assertThat(timeSpan.equals(new Object())).isFalse();
        assertThat(timeSpan.equals(new TimeSpan(milliseconds))).isTrue();
        assertThat(timeSpan.equals(new TimeSpan(milliseconds + 1))).isFalse();
    }

    @Test
    public void testToString() {
        verifyToString(0L, "0 seconds");
        verifyToString(589L, "589 milliseconds");
        verifyToString(1000L, "1 second");
        verifyToString(57042L, "57 seconds 42 milliseconds");
        verifyToString(60000L, "1 minute");
        verifyToString(120001L, "2 minutes 1 millisecond");
        verifyToString(421000L, "7 minutes 1 second");
        verifyToString(748321L, "12 minutes 28 seconds 321 milliseconds");
        verifyToString(3600000L, "1 hour");
        verifyToString(18000000L, "5 hours");
        verifyToString(18001000L, "5 hours 1 second");
        verifyToString(18114132L, "5 hours 1 minute 54 seconds 132 milliseconds");
        verifyToString(18544123L, "5 hours 9 minutes 4 seconds 123 milliseconds");
    }

    private static void verifyToString(final long milliseconds, final String expected) {
        assertThat(new TimeSpan(milliseconds).toString()).isEqualTo(expected);
    }
}
