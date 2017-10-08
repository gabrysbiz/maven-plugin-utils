package biz.gabrys.maven.plugin.util.io;

import java.io.File;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.maven.plugin.logging.Log;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public final class LoggingFileFilterTest {

    @Test
    public void accept_loggerIsDisabled_onlyReturnValue() {
        final File file = Mockito.mock(File.class);

        final IOFileFilter fileFilter = Mockito.mock(IOFileFilter.class);
        Mockito.when(fileFilter.accept(file)).thenReturn(true);

        final Log logger = Mockito.mock(Log.class);
        Mockito.when(logger.isDebugEnabled()).thenReturn(false);

        final LoggingFileFilter filter = Mockito.spy(new LoggingFileFilter(fileFilter, logger));

        Assert.assertTrue("Filter should return true.", filter.accept(file));

        Mockito.verify(filter).accept(file);
        Mockito.verify(fileFilter).accept(file);
        Mockito.verify(logger).isDebugEnabled();
        Mockito.verifyNoMoreInteractions(filter, logger);
        Mockito.verifyZeroInteractions(file);
    }

    @Test
    public void accept_loggerIsEnabled_logInfo() {
        final File file = Mockito.mock(File.class);
        Mockito.when(file.isFile()).thenReturn(true);
        Mockito.when(file.getAbsolutePath()).thenReturn("file");

        final IOFileFilter fileFilter = Mockito.mock(IOFileFilter.class);
        Mockito.when(fileFilter.accept(file)).thenReturn(true);

        final Log logger = Mockito.mock(Log.class);
        Mockito.when(logger.isDebugEnabled()).thenReturn(true);

        final LoggingFileFilter filter = Mockito.spy(new LoggingFileFilter(fileFilter, logger));

        Assert.assertTrue("Filter should return true.", filter.accept(file));

        Mockito.verify(filter).accept(file);
        Mockito.verify(fileFilter).accept(file);
        Mockito.verify(logger).isDebugEnabled();
        Mockito.verify(file).isFile();
        Mockito.verify(file).getAbsolutePath();
        Mockito.verify(logger).debug(Matchers.anyString());
        Mockito.verifyNoMoreInteractions(filter, logger);
    }

    @Test
    public void acceptWithTwoParameters_loggerIsDisabled_onlyReturnValue() {
        final File dir = new File("dir");
        final String name = "name";

        final IOFileFilter fileFilter = Mockito.mock(IOFileFilter.class);
        Mockito.when(fileFilter.accept(dir, name)).thenReturn(true);

        final Log logger = Mockito.mock(Log.class);
        Mockito.when(logger.isDebugEnabled()).thenReturn(false);

        final LoggingFileFilter filter = Mockito.spy(new LoggingFileFilter(fileFilter, logger));

        Assert.assertTrue("Filter should return true.", filter.accept(dir, name));

        Mockito.verify(filter).accept(dir, name);
        Mockito.verify(fileFilter).accept(dir, name);
        Mockito.verify(logger).isDebugEnabled();
        Mockito.verifyNoMoreInteractions(filter, logger);
    }

    @Test
    public void acceptWithTwoParameters_loggerIsEnabled_logInfo() {
        final File dir = new File("dir");
        final String name = "name";

        final IOFileFilter fileFilter = Mockito.mock(IOFileFilter.class);
        Mockito.when(fileFilter.accept(dir, name)).thenReturn(true);

        final Log logger = Mockito.mock(Log.class);
        Mockito.when(logger.isDebugEnabled()).thenReturn(true);

        final LoggingFileFilter filter = Mockito.spy(new LoggingFileFilter(fileFilter, logger));

        Assert.assertTrue("Filter should return true.", filter.accept(dir, name));

        Mockito.verify(filter).accept(dir, name);
        Mockito.verify(fileFilter).accept(dir, name);
        Mockito.verify(logger, Mockito.times(2)).isDebugEnabled();
    }
}
