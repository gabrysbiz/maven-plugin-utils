package biz.gabrys.maven.plugin.util.io;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public final class RegexFileFilterTest {

    @Test
    public void accept_fileNotInRootAndIncluded_notAccepted() throws IOException {
        final File root = Mockito.mock(File.class);
        Mockito.when(root.getCanonicalPath()).thenReturn("/root/");

        final String[] includes = new String[] { "^.*$" };
        final String[] excludes = new String[0];
        final RegexFileFilter filter = new RegexFileFilter(root, includes, excludes);

        final File file = Mockito.mock(File.class);
        Mockito.when(file.getCanonicalPath()).thenReturn("/not-root/file");
        Assert.assertFalse("The source shouldn't be accepted.", filter.accept(file));
    }

    @Test
    public void accept_fileInRootAndPatternsAreEmpty_notAccepted() throws IOException {
        final File root = Mockito.mock(File.class);
        Mockito.when(root.getCanonicalPath()).thenReturn("/root/");

        final String[] includes = new String[0];
        final String[] excludes = new String[0];
        final RegexFileFilter filter = new RegexFileFilter(root, includes, excludes);

        final File file = Mockito.mock(File.class);
        Mockito.when(file.getCanonicalPath()).thenReturn("/root/src/file");
        Assert.assertFalse("The source shouldn't be accepted.", filter.accept(file));
    }

    @Test
    public void accept_fileInRootAndIncluded_accepted() throws IOException {
        final File root = Mockito.mock(File.class);
        Mockito.when(root.getCanonicalPath()).thenReturn("/root");

        final String[] includes = new String[] { "^.*/file$" };
        final String[] excludes = new String[0];
        final RegexFileFilter filter = new RegexFileFilter(root, includes, excludes);

        final File source = Mockito.mock(File.class);
        Mockito.when(source.getCanonicalPath()).thenReturn("/root/src/file");
        Assert.assertTrue("The source should be accepted.", filter.accept(source));
    }

    @Test
    public void accept_fileInRootAndExcluded_notAccepted() throws IOException {
        final File root = Mockito.mock(File.class);
        Mockito.when(root.getCanonicalPath()).thenReturn("/root");

        final String[] includes = new String[0];
        final String[] excludes = new String[] { "^.*/file$" };
        final RegexFileFilter filter = new RegexFileFilter(root, includes, excludes);

        final File source = Mockito.mock(File.class);
        Mockito.when(source.getCanonicalPath()).thenReturn("/root/src/file");
        Assert.assertFalse("The source shouldn't be accepted.", filter.accept(source));
    }

    @Test
    public void accept_fileInRootAndIncludedAndExcluded_notAccepted() throws IOException {
        final File root = Mockito.mock(File.class);
        Mockito.when(root.getCanonicalPath()).thenReturn("/root");

        final String[] includes = new String[] { "^.*/file$" };
        final String[] excludes = new String[] { "^.*/file$" };
        final RegexFileFilter filter = new RegexFileFilter(root, includes, excludes);

        final File source = Mockito.mock(File.class);
        Mockito.when(source.getCanonicalPath()).thenReturn("/root/src/file");
        Assert.assertFalse("The source shouldn't be accepted.", filter.accept(source));
    }

    @Test
    public void accept_fileInRootAndIncludedOnWindows_accepted() throws IOException {
        final File root = Mockito.mock(File.class);
        Mockito.when(root.getCanonicalPath()).thenReturn("C:\\root");

        final String[] includes = new String[] { "^.*/file$" };
        final String[] excludes = new String[0];
        final RegexFileFilter filter = new RegexFileFilter(root, includes, excludes);

        final File source = Mockito.mock(File.class);
        Mockito.when(source.getCanonicalPath()).thenReturn("C:\\root\\src\\file");
        Assert.assertTrue("The source should be accepted.", filter.accept(source));
    }
}
