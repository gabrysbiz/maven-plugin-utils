package biz.gabrys.maven.plugin.util.io;

import java.io.File;

import org.apache.maven.plugin.logging.Log;
import org.junit.Test;
import org.mockito.Mockito;

public final class LoggerUtilsTest {

    @Test
    public void debugInclusion_debugDisabledAndFileIncluded_doNothing() {
        final Log logger = Mockito.mock(Log.class);
        Mockito.when(logger.isDebugEnabled()).thenReturn(false);

        final File file = Mockito.mock(File.class);

        LoggerUtils.debugInclusion(logger, file, true);

        Mockito.verify(logger).isDebugEnabled();
        Mockito.verifyNoMoreInteractions(logger);
        Mockito.verifyZeroInteractions(file);
    }

    @Test
    public void debugInclusion_debugDisabledAndFileExcluded_doNothing() {
        final Log logger = Mockito.mock(Log.class);
        Mockito.when(logger.isDebugEnabled()).thenReturn(false);

        final File file = Mockito.mock(File.class);

        LoggerUtils.debugInclusion(logger, file, false);

        Mockito.verify(logger).isDebugEnabled();
        Mockito.verifyNoMoreInteractions(logger);
        Mockito.verifyZeroInteractions(file);
    }

    @Test
    public void debugInclusion_debugEnabledAndFileIsNotNormalAndIncluded_doNothing() {
        final Log logger = Mockito.mock(Log.class);
        Mockito.when(logger.isDebugEnabled()).thenReturn(true);

        final File file = Mockito.mock(File.class);
        Mockito.when(file.isFile()).thenReturn(false);

        LoggerUtils.debugInclusion(logger, file, true);

        Mockito.verify(logger).isDebugEnabled();
        Mockito.verify(file).isFile();
        Mockito.verifyNoMoreInteractions(logger, file);
    }

    @Test
    public void debugInclusion_debugEnabledAndFileIsNotNormalAndExcluded_doNothing() {
        final Log logger = Mockito.mock(Log.class);
        Mockito.when(logger.isDebugEnabled()).thenReturn(true);

        final File file = Mockito.mock(File.class);
        Mockito.when(file.isFile()).thenReturn(false);

        LoggerUtils.debugInclusion(logger, file, false);

        Mockito.verify(logger).isDebugEnabled();
        Mockito.verify(file).isFile();
        Mockito.verifyNoMoreInteractions(logger, file);
    }

    @Test
    public void debugInclusion_debugEnabledAndFileIsNormalAndIncluded_logMessage() {
        final Log logger = Mockito.mock(Log.class);
        Mockito.when(logger.isDebugEnabled()).thenReturn(true);

        final File file = Mockito.mock(File.class);
        Mockito.when(file.isFile()).thenReturn(true);
        Mockito.when(file.getAbsolutePath()).thenReturn("filePath");

        LoggerUtils.debugInclusion(logger, file, true);

        Mockito.verify(logger).isDebugEnabled();
        Mockito.verify(file).isFile();
        Mockito.verify(file).getAbsolutePath();
        Mockito.verify(logger).debug("Include filePath");
        Mockito.verifyNoMoreInteractions(logger, file);
    }

    @Test
    public void debugInclusion_debugEnabledAndFileIsNormalAndExcluded_logMessage() {
        final Log logger = Mockito.mock(Log.class);
        Mockito.when(logger.isDebugEnabled()).thenReturn(true);

        final File file = Mockito.mock(File.class);
        Mockito.when(file.isFile()).thenReturn(true);
        Mockito.when(file.getAbsolutePath()).thenReturn("filePath");

        LoggerUtils.debugInclusion(logger, file, false);

        Mockito.verify(logger).isDebugEnabled();
        Mockito.verify(file).isFile();
        Mockito.verify(file).getAbsolutePath();
        Mockito.verify(logger).debug("Exclude filePath");
        Mockito.verifyNoMoreInteractions(logger, file);
    }
}
