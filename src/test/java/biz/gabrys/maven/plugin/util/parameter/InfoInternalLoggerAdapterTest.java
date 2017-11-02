package biz.gabrys.maven.plugin.util.parameter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.apache.maven.plugin.logging.Log;
import org.junit.Test;

import biz.gabrys.maven.plugin.util.parameter.ParametersLogBuilder.InfoInternalLoggerAdapter;

public final class InfoInternalLoggerAdapterTest {

    @Test
    public void isEnabled() {
        final Log logger = mock(Log.class);
        final InfoInternalLoggerAdapter adapter = new InfoInternalLoggerAdapter(logger);

        when(logger.isInfoEnabled()).thenReturn(Boolean.FALSE);
        assertFalse("Should return false when isInfoEnabled = false", adapter.isEnabled());

        when(logger.isInfoEnabled()).thenReturn(Boolean.TRUE);
        assertTrue("Should return true when isInfoEnabled = true", adapter.isEnabled());

        verify(logger, times(2)).isInfoEnabled();
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log() {
        final Log logger = mock(Log.class);
        final InfoInternalLoggerAdapter adapter = new InfoInternalLoggerAdapter(logger);

        final String line = "line";
        adapter.log(line);

        verify(logger).info(line);
        verifyNoMoreInteractions(logger);
    }
}
