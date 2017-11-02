package biz.gabrys.maven.plugin.util.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.File;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.maven.plugin.logging.Log;
import org.junit.Test;

public final class RegexFileScannerTest {

    @Test
    public void createFileFilter_loggerIsNull_returnsDirectoryScanner() {
        final RegexFileScanner scanner = spy(new RegexFileScanner());

        final File directory = mock(File.class);
        final String[] includes = new String[] { "include1", "include2" };
        final String[] excludes = new String[] { "exclude1", "exclude2" };

        final IOFileFilter filter = scanner.createFileFilter(directory, includes, excludes);
        assertNotNull("Filter instance should not be equal to null", filter);
        assertEquals("Filter class", RegexFileFilter.class, filter.getClass());

        verify(scanner).createFileFilter(directory, includes, excludes);
        verifyNoMoreInteractions(scanner);
    }

    @Test
    public void createDirectoryScanner_loggerIsNotInDebugMode_returnsDirectoryScanner() {
        final Log logger = mock(Log.class);
        final RegexFileScanner scanner = spy(new RegexFileScanner(logger));

        final File directory = mock(File.class);
        final String[] includes = new String[] { "include1", "include2" };
        final String[] excludes = new String[] { "exclude1", "exclude2" };

        when(logger.isDebugEnabled()).thenReturn(Boolean.FALSE);

        final IOFileFilter filter = scanner.createFileFilter(directory, includes, excludes);
        assertNotNull("Filter instance should not be equal to null", filter);
        assertEquals("Filter class", RegexFileFilter.class, filter.getClass());

        verify(scanner).createFileFilter(directory, includes, excludes);
        verify(logger).isDebugEnabled();
        verifyNoMoreInteractions(logger, scanner);
    }

    @Test
    public void createDirectoryScanner_loggerIsInDebugMode_returnsLoggingDirectoryScanner() {
        final Log logger = mock(Log.class);
        final RegexFileScanner scanner = spy(new RegexFileScanner(logger));

        final File directory = mock(File.class);
        final String[] includes = new String[] { "include1", "include2" };
        final String[] excludes = new String[] { "exclude1", "exclude2" };

        when(logger.isDebugEnabled()).thenReturn(Boolean.TRUE);

        final IOFileFilter filter = scanner.createFileFilter(directory, includes, excludes);
        assertNotNull("Filter instance should not be equal to null", filter);
        assertEquals("Filter class", LoggingFileFilter.class, filter.getClass());

        verify(scanner).createFileFilter(directory, includes, excludes);
        verify(logger).isDebugEnabled();
        verifyNoMoreInteractions(logger, scanner);
    }
}
