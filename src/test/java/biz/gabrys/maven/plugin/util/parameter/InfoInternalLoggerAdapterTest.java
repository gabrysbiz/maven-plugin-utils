package biz.gabrys.maven.plugin.util.parameter;

import org.apache.maven.plugin.logging.Log;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugin.util.parameter.ParametersLogBuilder.InfoInternalLoggerAdapter;

public final class InfoInternalLoggerAdapterTest {

    @Test
    public void isEnabled() {
        final Log logger = Mockito.mock(Log.class);
        final InfoInternalLoggerAdapter adapter = new InfoInternalLoggerAdapter(logger);

        Mockito.when(logger.isInfoEnabled()).thenReturn(Boolean.FALSE);
        Assert.assertFalse("Should return false when isInfoEnabled = false.", adapter.isEnabled());

        Mockito.when(logger.isInfoEnabled()).thenReturn(Boolean.TRUE);
        Assert.assertTrue("Should return true when isInfoEnabled = true.", adapter.isEnabled());

        Mockito.verify(logger, Mockito.times(2)).isInfoEnabled();
        Mockito.verifyNoMoreInteractions(logger);
    }

    @Test
    public void log() {
        final Log logger = Mockito.mock(Log.class);
        final InfoInternalLoggerAdapter adapter = new InfoInternalLoggerAdapter(logger);

        final String line = "line";
        adapter.log(line);
        Mockito.verify(logger).info(line);
        Mockito.verifyNoMoreInteractions(logger);
    }
}
