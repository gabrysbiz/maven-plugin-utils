package biz.gabrys.maven.plugin.util.timer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public final class TimeTest {

    @Test
    public void equals() {
        final long milliseconds = 100L;
        final Time time = new Time(milliseconds);

        assertThat(time.equals(time)).isTrue();
        assertThat(time.equals(null)).isFalse();
        assertThat(time.equals(new Object())).isFalse();
        assertThat(time.equals(new Time(milliseconds))).isTrue();
        assertThat(time.equals(new Time(milliseconds + 1))).isFalse();
    }

    @Test
    public void testToString() {
        assertThat(new Time(0L).toString()).isEqualTo("0 seconds");
        assertThat(new Time(589L).toString()).isEqualTo("589 milliseconds");
        assertThat(new Time(1000L).toString()).isEqualTo("1 second");
        assertThat(new Time(57042L).toString()).isEqualTo("57 seconds 42 milliseconds");
        assertThat(new Time(60000L).toString()).isEqualTo("1 minute");
        assertThat(new Time(120001L).toString()).isEqualTo("2 minutes 1 millisecond");
        assertThat(new Time(421000L).toString()).isEqualTo("7 minutes 1 second");
        assertThat(new Time(748321L).toString()).isEqualTo("12 minutes 28 seconds 321 milliseconds");
        assertThat(new Time(3600000L).toString()).isEqualTo("1 hour");
        assertThat(new Time(18000000L).toString()).isEqualTo("5 hours");
        assertThat(new Time(18001000L).toString()).isEqualTo("5 hours 1 second");
        assertThat(new Time(18114132L).toString()).isEqualTo("5 hours 1 minute 54 seconds 132 milliseconds");
        assertThat(new Time(18544123L).toString()).isEqualTo("5 hours 9 minutes 4 seconds 123 milliseconds");
    }
}
