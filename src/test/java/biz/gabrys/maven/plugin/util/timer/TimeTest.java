package biz.gabrys.maven.plugin.util.timer;

import org.junit.Assert;
import org.junit.Test;

public final class TimeTest {

    @Test
    public void testToString() {
        Assert.assertEquals("0 seconds.", "0.000 seconds", new Time(0L).toString());
        Assert.assertEquals("589 milliseconds.", "0.589 seconds", new Time(589L).toString());
        Assert.assertEquals("1 second.", "1.000 second", new Time(1000L).toString());
        Assert.assertEquals("57 seconds 42 milliseconds.", "57.042 seconds", new Time(57042L).toString());
        Assert.assertEquals("1 minute.", "1 minute 0.000 seconds", new Time(60000L).toString());
        Assert.assertEquals("1 minute 1 second.", "1 minute 1.000 second", new Time(61000L).toString());
        Assert.assertEquals("12 minutes 28 seconds 321 milliseconds.", "12 minutes 28.321 seconds", new Time(748321L).toString());
        Assert.assertEquals("1 hour.", "1 hour 0 minutes 0.000 seconds", new Time(3600000L).toString());
        Assert.assertEquals("5 hours.", "5 hours 0 minutes 0.000 seconds", new Time(18000000L).toString());
        Assert.assertEquals("5 hours 1 minute 1 second.", "5 hours 1 minute 1.000 second", new Time(18061000L).toString());
        Assert.assertEquals("5 hours 9 minutes 4 seconds 123 milliseconds.", "5 hours 9 minutes 4.123 seconds",
                new Time(18544123L).toString());
    }
}
