package biz.gabrys.maven.plugin.util.timer;

import org.junit.Assert;
import org.junit.Test;

public final class TimeTest {

    @Test
    public void equals() {
        final long milliseconds = 100L;
        final Time time = new Time(milliseconds);
        Assert.assertTrue("Other is the same instance.", time.equals(time));
        Assert.assertFalse("Other is null.", time.equals(null));
        Assert.assertFalse("Other has different type.", time.equals(new Object()));
        Assert.assertTrue("Other has the same time.", time.equals(new Time(milliseconds)));
        Assert.assertFalse("Other has different time.", time.equals(new Time(milliseconds + 1)));
    }

    @Test
    public void testToString() {
        Assert.assertEquals("0 seconds.", "0 seconds", new Time(0L).toString());
        Assert.assertEquals("589 milliseconds.", "589 milliseconds", new Time(589L).toString());
        Assert.assertEquals("1 second.", "1 second", new Time(1000L).toString());
        Assert.assertEquals("57 seconds 42 milliseconds.", "57 seconds 42 milliseconds", new Time(57042L).toString());
        Assert.assertEquals("1 minute.", "1 minute", new Time(60000L).toString());
        Assert.assertEquals("2 minutes 1 millisecond.", "2 minutes 1 millisecond", new Time(120001L).toString());
        Assert.assertEquals("7 minutes 1 second.", "7 minutes 1 second", new Time(421000L).toString());
        Assert.assertEquals("12 minutes 28 seconds 321 milliseconds.", "12 minutes 28 seconds 321 milliseconds",
                new Time(748321L).toString());
        Assert.assertEquals("1 hour.", "1 hour", new Time(3600000L).toString());
        Assert.assertEquals("5 hours.", "5 hours", new Time(18000000L).toString());
        Assert.assertEquals("5 hours 1 second.", "5 hours 1 second", new Time(18001000L).toString());
        Assert.assertEquals("5 hours 1 minute 54 seconds 132 milliseconds.", "5 hours 1 minute 54 seconds 132 milliseconds",
                new Time(18114132L).toString());
        Assert.assertEquals("5 hours 9 minutes 4 seconds 123 milliseconds.", "5 hours 9 minutes 4 seconds 123 milliseconds",
                new Time(18544123L).toString());
    }
}
