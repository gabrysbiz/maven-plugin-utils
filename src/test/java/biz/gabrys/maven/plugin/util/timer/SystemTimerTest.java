package biz.gabrys.maven.plugin.util.timer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class SystemTimerTest {

    @Test
    public void testTimerInteractions() throws InterruptedException {
        final Timer timer = new SystemTimer();

        assertNull("Time for not started timer should be equal to null", timer.getTime());

        timer.start();
        sleep();
        final Time time1 = timer.getTime();
        assertTrue("Time1 should be bigger than 0", time1.toMilliseconds() > 0);

        sleep();
        final Time time2 = timer.getTime();
        assertTrue("Time2 should be bigger than 0", time2.toMilliseconds() > 0);
        assertTrue("Time1 should be smaller than Time2", time1.toMilliseconds() < time2.toMilliseconds());

        sleep();
        final Time time3 = timer.stop();
        assertTrue("Time2 should be smaller than Time3", time2.toMilliseconds() < time3.toMilliseconds());

        assertSame("getTime always return the same falue after stop", time3, timer.getTime());
        assertSame("getTime always return the same falue after stop", timer.getTime(), timer.getTime());

        sleep();
        assertSame("Timer should return the same time when call stop() method multiple times", time3, timer.stop());
    }

    private void sleep() throws InterruptedException {
        Thread.sleep(5);
    }
}
