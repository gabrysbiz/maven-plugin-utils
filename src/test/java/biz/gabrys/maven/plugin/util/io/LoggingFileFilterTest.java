package biz.gabrys.maven.plugin.util.io;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.File;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.maven.plugin.logging.Log;
import org.junit.Test;

public final class LoggingFileFilterTest {

    @Test
    public void accept_loggerIsDisabled_onlyReturnValue() {
        final File file = mock(File.class);
        final IOFileFilter fileFilter = mock(IOFileFilter.class);
        when(fileFilter.accept(file)).thenReturn(true);
        final Log logger = mock(Log.class);
        when(logger.isDebugEnabled()).thenReturn(false);
        final LoggingFileFilter filter = spy(new LoggingFileFilter(fileFilter, logger));

        assertTrue(filter.accept(file));

        verify(filter).accept(file);
        verify(fileFilter).accept(file);
        verify(logger).isDebugEnabled();
        verifyNoMoreInteractions(filter, logger);
        verifyZeroInteractions(file);
    }

    @Test
    public void accept_loggerIsEnabled_logInfo() {
        final File file = mock(File.class);
        when(file.isFile()).thenReturn(true);
        when(file.getAbsolutePath()).thenReturn("file");
        final IOFileFilter fileFilter = mock(IOFileFilter.class);
        when(fileFilter.accept(file)).thenReturn(true);
        final Log logger = mock(Log.class);
        when(logger.isDebugEnabled()).thenReturn(true);
        final LoggingFileFilter filter = spy(new LoggingFileFilter(fileFilter, logger));

        assertTrue(filter.accept(file));

        verify(filter).accept(file);
        verify(fileFilter).accept(file);
        verify(logger).isDebugEnabled();
        verify(file).isFile();
        verify(file).getAbsolutePath();
        verify(logger).debug(anyString());
        verifyNoMoreInteractions(filter, logger);
    }

    @Test
    public void acceptWithTwoParameters_loggerIsDisabled_onlyReturnValue() {
        final File dir = new File("dir");
        final String name = "name";
        final IOFileFilter fileFilter = mock(IOFileFilter.class);
        when(fileFilter.accept(dir, name)).thenReturn(true);
        final Log logger = mock(Log.class);
        when(logger.isDebugEnabled()).thenReturn(false);
        final LoggingFileFilter filter = spy(new LoggingFileFilter(fileFilter, logger));

        assertTrue(filter.accept(dir, name));

        verify(filter).accept(dir, name);
        verify(fileFilter).accept(dir, name);
        verify(logger).isDebugEnabled();
        verifyNoMoreInteractions(filter, logger);
    }

    @Test
    public void acceptWithTwoParameters_loggerIsEnabled_logInfo() {
        final File dir = new File("dir");
        final String name = "name";
        final IOFileFilter fileFilter = mock(IOFileFilter.class);
        when(fileFilter.accept(dir, name)).thenReturn(true);
        final Log logger = mock(Log.class);
        when(logger.isDebugEnabled()).thenReturn(true);
        final LoggingFileFilter filter = spy(new LoggingFileFilter(fileFilter, logger));

        assertTrue(filter.accept(dir, name));

        verify(filter).accept(dir, name);
        verify(fileFilter).accept(dir, name);
        verify(logger, times(2)).isDebugEnabled();
    }
}
