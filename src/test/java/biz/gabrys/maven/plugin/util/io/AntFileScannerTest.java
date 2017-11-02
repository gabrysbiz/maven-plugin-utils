package biz.gabrys.maven.plugin.util.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.util.DirectoryScanner;
import org.junit.Test;

public final class AntFileScannerTest {

    @Test
    public void getFiles() {
        final AntFileScanner scanner = spy(new AntFileScanner());

        final DirectoryScanner directoryScanner = mock(DirectoryScanner.class);
        doReturn(directoryScanner).when(scanner).createDirectoryScanner();

        final File directory = mock(File.class);
        final String[] paths = new String[] { "path1", "path2" };

        when(directoryScanner.getIncludedFiles()).thenReturn(paths);

        @SuppressWarnings("unchecked")
        final List<File> convertedFiles = mock(List.class);
        doReturn(convertedFiles).when(scanner).convertToFiles(directory, paths);

        final String[] includes = new String[] { "include1", "include2" };
        final String[] excludes = new String[] { "exclude1", "exclude2" };
        final Collection<File> files = scanner.getFiles(directory, includes, excludes);

        assertEquals(convertedFiles, files);

        verify(scanner).getFiles(directory, includes, excludes);
        verify(scanner).createDirectoryScanner();
        verify(directoryScanner).setBasedir(directory);
        verify(directoryScanner).setIncludes(includes);
        verify(directoryScanner).setExcludes(excludes);
        verify(directoryScanner).scan();
        verify(directoryScanner).getIncludedFiles();
        verify(scanner).convertToFiles(directory, paths);
        verifyNoMoreInteractions(scanner, directoryScanner);
        verifyZeroInteractions(directory, convertedFiles);
    }

    @Test
    public void createDirectoryScanner_loggerIsNull_returnsDirectoryScanner() {
        final AntFileScanner scanner = spy(new AntFileScanner());

        final DirectoryScanner directoryScanner = scanner.createDirectoryScanner();
        assertNotNull("Directory scanner instance should not be equal to null", directoryScanner);
        assertEquals("Directory scanner class", DirectoryScanner.class, directoryScanner.getClass());

        verify(scanner).createDirectoryScanner();
        verifyNoMoreInteractions(scanner);
    }

    @Test
    public void createDirectoryScanner_loggerIsNotInDebugMode_returnsDirectoryScanner() {
        final Log logger = mock(Log.class);
        final AntFileScanner scanner = spy(new AntFileScanner(logger));

        when(logger.isDebugEnabled()).thenReturn(Boolean.FALSE);

        final DirectoryScanner directoryScanner = scanner.createDirectoryScanner();
        assertNotNull("Directory scanner instance should not be equal to null", directoryScanner);
        assertEquals("Directory scanner class", DirectoryScanner.class, directoryScanner.getClass());

        verify(scanner).createDirectoryScanner();
        verify(logger).isDebugEnabled();
        verifyNoMoreInteractions(logger);
        verifyNoMoreInteractions(scanner);
    }

    @Test
    public void createDirectoryScanner_loggerIsInDebugMode_returnsLoggingDirectoryScanner() {
        final Log logger = mock(Log.class);
        final AntFileScanner scanner = spy(new AntFileScanner(logger));

        when(logger.isDebugEnabled()).thenReturn(Boolean.TRUE);

        final DirectoryScanner directoryScanner = scanner.createDirectoryScanner();
        assertNotNull("Directory scanner instance should not be equal to null", directoryScanner);
        assertEquals("Directory scanner class", LoggingDirectoryScanner.class, directoryScanner.getClass());

        verify(scanner).createDirectoryScanner();
        verify(logger).isDebugEnabled();
        verifyNoMoreInteractions(logger);
        verifyNoMoreInteractions(scanner);
    }

    @Test
    public void convertToFiles_pathsAreEmpty_returnsEmptyCollection() {
        final AntFileScanner scanner = spy(new AntFileScanner());

        final File directory = new File("/basedir");
        final String[] paths = new String[0];

        final List<File> files = scanner.convertToFiles(directory, paths);
        assertNotNull("Collection instance should not be equal to null", files);
        assertEquals("Collection should contain 0 files", 0, files.size());

        verify(scanner).convertToFiles(directory, paths);
        verifyNoMoreInteractions(scanner);
    }

    @Test
    public void convertToFiles_threePaths_returnsCollectionWithThreeFiles() {
        final AntFileScanner scanner = spy(new AntFileScanner());

        final File directory = new File("/root");
        final String[] paths = new String[] { "file1", "dir1/file2", "dir2/file3" };

        final List<File> files = scanner.convertToFiles(directory, paths);
        assertNotNull("Collection instance should not be equal to null", files);
        assertEquals("Collection should contain 3 files", 3, files.size());

        assertEquals("First file path", new File("/root/file1"), files.get(0));
        assertEquals("Second file path", new File("/root/dir1/file2"), files.get(1));
        assertEquals("Third file path", new File("/root/dir2/file3"), files.get(2));

        verify(scanner).convertToFiles(directory, paths);
        verifyNoMoreInteractions(scanner);
    }
}
