package biz.gabrys.maven.plugin.util.io;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public final class AntFileScannerTest {

    @Test
    public void convertToFiles_pathsAreEmpty_returnsEmptyCollection() {
        final AntFileScanner scanner = Mockito.spy(new AntFileScanner());

        final File directory = new File("/basedir");
        final String[] paths = new String[0];

        final List<File> files = scanner.convertToFiles(directory, paths);
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
        Assert.assertEquals("Collection should contain 3 files.", 3, files.size());

        Assert.assertEquals("First file path.", new File("/root/file1"), files.get(0));
        Assert.assertEquals("Second file path.", new File("/root/dir1/file2"), files.get(1));
        Assert.assertEquals("Third file path.", new File("/root/dir2/file3"), files.get(2));

        Mockito.verify(scanner).convertToFiles(directory, paths);
        Mockito.verifyNoMoreInteractions(scanner);
    }
}
