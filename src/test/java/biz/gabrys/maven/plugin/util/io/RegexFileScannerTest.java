package biz.gabrys.maven.plugin.util.io;

import java.io.File;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.maven.plugin.logging.Log;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public final class RegexFileScannerTest {

    @Test
    public void createFileFilter_loggerIsNull_returnsDirectoryScanner() {
        final RegexFileScanner scanner = Mockito.spy(new RegexFileScanner());

        final File directory = Mockito.mock(File.class);
        final String[] includes = new String[] { "include1", "include2" };
        final String[] excludes = new String[] { "exclude1", "exclude2" };

        final IOFileFilter filter = scanner.createFileFilter(directory, includes, excludes);
        Assert.assertNotNull("Filter instance.", filter);
        Assert.assertEquals("Filter class.", RegexFileFilter.class, filter.getClass());

        Mockito.verify(scanner).createFileFilter(directory, includes, excludes);
        Mockito.verifyNoMoreInteractions(scanner);
    }

    @Test
    public void createDirectoryScanner_loggerIsNotInDebugMode_returnsDirectoryScanner() {
        final Log logger = Mockito.mock(Log.class);
        final RegexFileScanner scanner = Mockito.spy(new RegexFileScanner(logger));

        final File directory = Mockito.mock(File.class);
        final String[] includes = new String[] { "include1", "include2" };
        final String[] excludes = new String[] { "exclude1", "exclude2" };

        Mockito.when(logger.isDebugEnabled()).thenReturn(Boolean.FALSE);

        final IOFileFilter filter = scanner.createFileFilter(directory, includes, excludes);
        Assert.assertNotNull("Filter instance.", filter);
        Assert.assertEquals("Filter class.", RegexFileFilter.class, filter.getClass());

        Mockito.verify(scanner).createFileFilter(directory, includes, excludes);
        Mockito.verify(logger).isDebugEnabled();
        Mockito.verifyNoMoreInteractions(logger);
        Mockito.verifyNoMoreInteractions(scanner);
    }

    @Test
    public void createDirectoryScanner_loggerIsInDebugMode_returnsLoggingDirectoryScanner() {
        final Log logger = Mockito.mock(Log.class);
        final RegexFileScanner scanner = Mockito.spy(new RegexFileScanner(logger));

        final File directory = Mockito.mock(File.class);
        final String[] includes = new String[] { "include1", "include2" };
        final String[] excludes = new String[] { "exclude1", "exclude2" };

        Mockito.when(logger.isDebugEnabled()).thenReturn(Boolean.TRUE);

        final IOFileFilter filter = scanner.createFileFilter(directory, includes, excludes);
        Assert.assertNotNull("Filter instance.", filter);
        Assert.assertEquals("Filter class.", LoggingFileFilter.class, filter.getClass());

        Mockito.verify(scanner).createFileFilter(directory, includes, excludes);
        Mockito.verify(logger).isDebugEnabled();
        Mockito.verifyNoMoreInteractions(logger);
        Mockito.verifyNoMoreInteractions(scanner);
    }

}
