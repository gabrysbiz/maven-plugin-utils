package biz.gabrys.maven.plugin.util.io;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.util.DirectoryScanner;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public final class AntFileScannerTest {

    @Test
    public void getFiles() {
        final AntFileScanner scanner = Mockito.spy(new AntFileScanner());

        final DirectoryScanner directoryScanner = Mockito.mock(DirectoryScanner.class);
        Mockito.doReturn(directoryScanner).when(scanner).createDirectoryScanner();

        final File directory = Mockito.mock(File.class);
        final String[] paths = new String[] { "path1", "path2" };

        Mockito.when(directoryScanner.getIncludedFiles()).thenReturn(paths);

        @SuppressWarnings("unchecked")
        final List<File> convertedFiles = Mockito.mock(List.class);
        Mockito.doReturn(convertedFiles).when(scanner).convertToFiles(directory, paths);

        final String[] includes = new String[] { "include1", "include2" };
        final String[] excludes = new String[] { "exclude1", "exclude2" };
        final Collection<File> files = scanner.getFiles(directory, includes, excludes);

        Assert.assertEquals("Files collection.", convertedFiles, files);

        Mockito.verify(scanner).getFiles(directory, includes, excludes);
        Mockito.verify(scanner).createDirectoryScanner();
        Mockito.verify(directoryScanner).setBasedir(directory);
        Mockito.verify(directoryScanner).setIncludes(includes);
        Mockito.verify(directoryScanner).setExcludes(excludes);
        Mockito.verify(directoryScanner).scan();
        Mockito.verify(directoryScanner).getIncludedFiles();
        Mockito.verify(scanner).convertToFiles(directory, paths);
        Mockito.verifyNoMoreInteractions(scanner, directoryScanner);
        Mockito.verifyZeroInteractions(directory, convertedFiles);
    }

    @Test
    public void createDirectoryScanner_loggerIsNull_returnsDirectoryScanner() {
        final AntFileScanner scanner = Mockito.spy(new AntFileScanner());

        final DirectoryScanner directoryScanner = scanner.createDirectoryScanner();
        Assert.assertNotNull("Directory scanner instance.", directoryScanner);
        Assert.assertEquals("Directory scanner class.", DirectoryScanner.class, directoryScanner.getClass());

        Mockito.verify(scanner).createDirectoryScanner();
        Mockito.verifyNoMoreInteractions(scanner);
    }

    @Test
    public void createDirectoryScanner_loggerIsNotInDebugMode_returnsDirectoryScanner() {
        final Log logger = Mockito.mock(Log.class);
        final AntFileScanner scanner = Mockito.spy(new AntFileScanner(logger));

        Mockito.when(logger.isDebugEnabled()).thenReturn(Boolean.FALSE);

        final DirectoryScanner directoryScanner = scanner.createDirectoryScanner();
        Assert.assertNotNull("Directory scanner instance.", directoryScanner);
        Assert.assertEquals("Directory scanner class.", DirectoryScanner.class, directoryScanner.getClass());

        Mockito.verify(scanner).createDirectoryScanner();
        Mockito.verify(logger).isDebugEnabled();
        Mockito.verifyNoMoreInteractions(logger);
        Mockito.verifyNoMoreInteractions(scanner);
    }

    @Test
    public void createDirectoryScanner_loggerIsInDebugMode_returnsLoggingDirectoryScanner() {
        final Log logger = Mockito.mock(Log.class);
        final AntFileScanner scanner = Mockito.spy(new AntFileScanner(logger));

        Mockito.when(logger.isDebugEnabled()).thenReturn(Boolean.TRUE);

        final DirectoryScanner directoryScanner = scanner.createDirectoryScanner();
        Assert.assertNotNull("Directory scanner instance.", directoryScanner);
        Assert.assertEquals("Directory scanner class.", LoggingDirectoryScanner.class, directoryScanner.getClass());

        Mockito.verify(scanner).createDirectoryScanner();
        Mockito.verify(logger).isDebugEnabled();
        Mockito.verifyNoMoreInteractions(logger);
        Mockito.verifyNoMoreInteractions(scanner);
    }

    @Test
    public void convertToFiles_pathsAreEmpty_returnsEmptyCollection() {
        final AntFileScanner scanner = Mockito.spy(new AntFileScanner());

        final File directory = new File("/basedir");
        final String[] paths = new String[0];

        final List<File> files = scanner.convertToFiles(directory, paths);
        Assert.assertNotNull("Collection instance.", files);
        Assert.assertEquals("Collection should contain 0 files.", 0, files.size());

        Mockito.verify(scanner).convertToFiles(directory, paths);
        Mockito.verifyNoMoreInteractions(scanner);
    }

    @Test
    public void convertToFiles_threePaths_returnsCollectionWithThreeFiles() {
        final AntFileScanner scanner = Mockito.spy(new AntFileScanner());

        final File directory = new File("/root");
        final String[] paths = new String[] { "file1", "dir1/file2", "dir2/file3" };

        final List<File> files = scanner.convertToFiles(directory, paths);
        Assert.assertNotNull("Collection instance.", files);
        Assert.assertEquals("Collection should contain 3 files.", 3, files.size());

        Assert.assertEquals("First file path.", new File("/root/file1"), files.get(0));
        Assert.assertEquals("Second file path.", new File("/root/dir1/file2"), files.get(1));
        Assert.assertEquals("Third file path.", new File("/root/dir2/file3"), files.get(2));

        Mockito.verify(scanner).convertToFiles(directory, paths);
        Mockito.verifyNoMoreInteractions(scanner);
    }
}
