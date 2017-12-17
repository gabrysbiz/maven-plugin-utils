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
        final TimeSpan timeSpan1 = timer.getTime();
        assertThat(timeSpan1.toMilliseconds()).isGreaterThan(0L);

        sleep();
        final TimeSpan timeSpan2 = timer.getTime();
        assertThat(timeSpan2.toMilliseconds()).isGreaterThan(0L);
        assertThat(timeSpan1.toMilliseconds()).isLessThan(timeSpan2.toMilliseconds());

        sleep();
        final TimeSpan timeSpan3 = timer.stop();
        assertThat(timeSpan2.toMilliseconds()).isLessThan(timeSpan3.toMilliseconds());

        // getTime always return the same value after stop
        assertThat(timer.getTime()).isSameAs(timeSpan3);
        assertThat(timer.getTime()).isSameAs(timer.getTime());

        sleep();
        assertThat(timeSpan3).isSameAs(timer.stop());
    }

    private void sleep() throws InterruptedException {
        Thread.sleep(5);
    }
}
