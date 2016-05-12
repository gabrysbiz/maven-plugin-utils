package biz.gabrys.maven.plugin.util.timer;

import org.junit.Assert;
import org.junit.Test;

public final class SystemTimerTest {

    @Test
    public void testTimerInteractions() throws InterruptedException {
        final Timer timer = new SystemTimer();

        Assert.assertNull("Time for not started timer should be equal to null", timer.getTime());

        timer.start();
        sleep();
        final Time time1 = timer.getTime();
        Assert.assertTrue("Time1 should be bigger than 0.", time1.toMilliseconds() > 0);

        sleep();
        final Time time2 = timer.getTime();
        Assert.assertTrue("Time2 should be bigger than 0.", time2.toMilliseconds() > 0);
        Assert.assertTrue("Time1 should be smaller than Time2.", time1.toMilliseconds() < time2.toMilliseconds());

        sleep();
        final Time time3 = timer.stop();
        Assert.assertTrue("Time2 should be smaller than Time3.", time2.toMilliseconds() < time3.toMilliseconds());

        Assert.assertTrue("getTime always return the same falue after stop.", time3 == timer.getTime());
        Assert.assertTrue("getTime always return the same falue after stop.", timer.getTime() == timer.getTime());

        sleep();
        Assert.assertTrue("Timer should return the same time when call stop() method multiple times.", timer.stop() == time3);
    }

    private void sleep() throws InterruptedException {
        Thread.sleep(5);
    }
}
