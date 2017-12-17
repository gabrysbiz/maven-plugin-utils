package biz.gabrys.maven.plugin.util.parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.apache.maven.plugin.logging.Log;
import org.junit.Test;

import biz.gabrys.maven.plugin.util.parameter.ParametersLogBuilder.DebugInternalLoggerAdapter;

public final class DebugInternalLoggerAdapterTest {

    @Test
    public void isEnabled() {
        final Log logger = mock(Log.class);
        final DebugInternalLoggerAdapter adapter = new DebugInternalLoggerAdapter(logger);

        when(logger.isDebugEnabled()).thenReturn(Boolean.FALSE);
        assertThat(adapter.isEnabled()).isFalse();

        when(logger.isDebugEnabled()).thenReturn(Boolean.TRUE);
        assertThat(adapter.isEnabled()).isTrue();

        verify(logger, times(2)).isDebugEnabled();
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void log() {
        final Log logger = mock(Log.class);
        final DebugInternalLoggerAdapter adapter = new DebugInternalLoggerAdapter(logger);

        final String line = "line";
        adapter.log(line);

        verify(logger).debug(line);
        verifyNoMoreInteractions(logger);
    }
}
