package biz.gabrys.maven.plugin.util.io;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.File;

import org.apache.maven.plugin.logging.Log;
import org.junit.Test;

public final class LoggerUtilsTest {

    @Test
    public void debugInclusion_debugDisabledAndFileIncluded_doNothing() {
        final Log logger = mock(Log.class);
        when(logger.isDebugEnabled()).thenReturn(false);

        final File file = mock(File.class);

        LoggerUtils.debugInclusion(logger, file, true);

        verify(logger).isDebugEnabled();
        verifyNoMoreInteractions(logger);
        verifyZeroInteractions(file);
    }

    @Test
    public void debugInclusion_debugDisabledAndFileExcluded_doNothing() {
        final Log logger = mock(Log.class);
        when(logger.isDebugEnabled()).thenReturn(false);

        final File file = mock(File.class);

        LoggerUtils.debugInclusion(logger, file, false);

        verify(logger).isDebugEnabled();
        verifyNoMoreInteractions(logger);
        verifyZeroInteractions(file);
    }

    @Test
    public void debugInclusion_debugEnabledAndFileIsNotNormalAndIncluded_doNothing() {
        final Log logger = mock(Log.class);
        when(logger.isDebugEnabled()).thenReturn(true);

        final File file = mock(File.class);
        when(file.isFile()).thenReturn(false);

        LoggerUtils.debugInclusion(logger, file, true);

        verify(logger).isDebugEnabled();
        verify(file).isFile();
        verifyNoMoreInteractions(logger, file);
    }

    @Test
    public void debugInclusion_debugEnabledAndFileIsNotNormalAndExcluded_doNothing() {
        final Log logger = mock(Log.class);
        when(logger.isDebugEnabled()).thenReturn(true);

        final File file = mock(File.class);
        when(file.isFile()).thenReturn(false);

        LoggerUtils.debugInclusion(logger, file, false);

        verify(logger).isDebugEnabled();
        verify(file).isFile();
        verifyNoMoreInteractions(logger, file);
    }

    @Test
    public void debugInclusion_debugEnabledAndFileIsNormalAndIncluded_logMessage() {
        final Log logger = mock(Log.class);
        when(logger.isDebugEnabled()).thenReturn(true);

        final File file = mock(File.class);
        when(file.isFile()).thenReturn(true);
        when(file.getAbsolutePath()).thenReturn("filePath");

        LoggerUtils.debugInclusion(logger, file, true);

        verify(logger).isDebugEnabled();
        verify(file).isFile();
        verify(file).getAbsolutePath();
        verify(logger).debug("Include filePath");
        verifyNoMoreInteractions(logger, file);
    }

    @Test
    public void debugInclusion_debugEnabledAndFileIsNormalAndExcluded_logMessage() {
        final Log logger = mock(Log.class);
        when(logger.isDebugEnabled()).thenReturn(true);

        final File file = mock(File.class);
        when(file.isFile()).thenReturn(true);
        when(file.getAbsolutePath()).thenReturn("filePath");

        LoggerUtils.debugInclusion(logger, file, false);

        verify(logger).isDebugEnabled();
        verify(file).isFile();
        verify(file).getAbsolutePath();
        verify(logger).debug("Exclude filePath");
        verifyNoMoreInteractions(logger, file);
    }
}
