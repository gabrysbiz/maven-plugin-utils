package biz.gabrys.maven.plugin.util.timer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public final class SystemTimerTest {

    @Test
    public void testTimerInteractions() throws InterruptedException {
        final Timer timer = new SystemTimer();

        assertThat(timer.getTime()).isNull();

        timer.start();
        sleep();
        final Time time1 = timer.getTime();
        assertThat(time1.toMilliseconds()).isGreaterThan(0L);

        sleep();
        final Time time2 = timer.getTime();
        assertThat(time2.toMilliseconds()).isGreaterThan(0L);
        assertThat(time1.toMilliseconds()).isLessThan(time2.toMilliseconds());

        sleep();
        final Time time3 = timer.stop();
        assertThat(time2.toMilliseconds()).isLessThan(time3.toMilliseconds());

        // getTime always return the same value after stop
        assertThat(timer.getTime()).isSameAs(time3);
        assertThat(timer.getTime()).isSameAs(timer.getTime());

        sleep();
        assertThat(time3).isSameAs(timer.stop());
    }

    private void sleep() throws InterruptedException {
        Thread.sleep(5);
    }
}
