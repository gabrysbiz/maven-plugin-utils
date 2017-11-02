package biz.gabrys.maven.plugin.util.timer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class TimeTest {

    @Test
    public void equals() {
        final long milliseconds = 100L;
        final Time time = new Time(milliseconds);
        assertTrue("Other is the same instance", time.equals(time));
        assertFalse("Other is null", time.equals(null));
        assertFalse("Other has different type", time.equals(new Object()));
        assertTrue("Other has the same time", time.equals(new Time(milliseconds)));
        assertFalse("Other has different time", time.equals(new Time(milliseconds + 1)));
    }

    @Test
    public void testToString() {
        assertEquals("0 seconds", new Time(0L).toString());
        assertEquals("589 milliseconds", new Time(589L).toString());
        assertEquals("1 second", new Time(1000L).toString());
        assertEquals("57 seconds 42 milliseconds", new Time(57042L).toString());
        assertEquals("1 minute", new Time(60000L).toString());
        assertEquals("2 minutes 1 millisecond", new Time(120001L).toString());
        assertEquals("7 minutes 1 second", new Time(421000L).toString());
        assertEquals("12 minutes 28 seconds 321 milliseconds", new Time(748321L).toString());
        assertEquals("1 hour", new Time(3600000L).toString());
        assertEquals("5 hours", new Time(18000000L).toString());
        assertEquals("5 hours 1 second", new Time(18001000L).toString());
        assertEquals("5 hours 1 minute 54 seconds 132 milliseconds", new Time(18114132L).toString());
        assertEquals("5 hours 9 minutes 4 seconds 123 milliseconds", new Time(18544123L).toString());
    }
}
