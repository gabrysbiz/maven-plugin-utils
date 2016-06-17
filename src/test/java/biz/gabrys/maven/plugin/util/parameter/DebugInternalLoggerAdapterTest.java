package biz.gabrys.maven.plugin.util.parameter;

import org.apache.maven.plugin.logging.Log;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugin.util.parameter.ParametersLogBuilder.DebugInternalLoggerAdapter;

public final class DebugInternalLoggerAdapterTest {

    @Test
    public void isEnabled() {
        final Log logger = Mockito.mock(Log.class);
        final DebugInternalLoggerAdapter adapter = new DebugInternalLoggerAdapter(logger);

        Mockito.when(logger.isDebugEnabled()).thenReturn(Boolean.FALSE);
        Assert.assertFalse("Should return false when isDebugEnabled = false.", adapter.isEnabled());

        Mockito.when(logger.isDebugEnabled()).thenReturn(Boolean.TRUE);
        Assert.assertTrue("Should return true when isDebugEnabled = true.", adapter.isEnabled());

        Mockito.verify(logger, Mockito.times(2)).isDebugEnabled();
        Mockito.verifyNoMoreInteractions(logger);
    }

    @Test
    public void log() {
        final Log logger = Mockito.mock(Log.class);
        final DebugInternalLoggerAdapter adapter = new DebugInternalLoggerAdapter(logger);

        final String line = "line";
        adapter.log(line);
        Mockito.verify(logger).debug(line);
        Mockito.verifyNoMoreInteractions(logger);
    }
}
