package biz.gabrys.maven.plugin.util.io;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public final class RegexFileFilterTest {

    @Test
    public void matches_patternsAreEmpty_returnsFalse() {
        final boolean matched = RegexFileFilter.matches("name", new String[0]);
        Assert.assertFalse("Should return false when patterns store zero elements.", matched);
    }

    @Test
    public void matches_nameMatchedSecondPattern_returnsTrue() {
        final String[] patterns = new String[] { "^not-matched$", "^name$" };
        final boolean matched = RegexFileFilter.matches("name", patterns);
        Assert.assertTrue("Should return true when name matched at least one pattern.", matched);
    }

    @Test(expected = RegexFileFilterException.class)
    public void accept_baseDirectoryIsIncorrect_throwsException() throws IOException {
        final File base = Mockito.mock(File.class);
        Mockito.when(base.getCanonicalPath()).thenThrow(new IOException());
        new RegexFileFilter(base, new String[0], new String[0]);
    }

    @Test
    public void accept_oneParameter_executeIsAcceptable() {
        final File base = new File("base");
        final RegexFileFilter filter = Mockito.spy(new RegexFileFilter(base, new String[0], new String[0]));
        final File file = new File("dir/name");
        filter.accept(file);
        Mockito.verify(filter).accept(file);
        Mockito.verify(filter).isAcceptable(file);
        Mockito.verifyNoMoreInteractions(filter);
    }

    @Test
    public void accept_twoParameters_executeIsAcceptable() {
        final File base = new File("base");
        final RegexFileFilter filter = Mockito.spy(new RegexFileFilter(base, new String[0], new String[0]));
        final File directory = new File("dir");
        filter.accept(directory, "name");
        Mockito.verify(filter).accept(directory, "name");
        Mockito.verify(filter).isAcceptable(new File(directory, "name"));
        Mockito.verifyNoMoreInteractions(filter);
    }

    @Test(expected = RegexFileFilterException.class)
    public void isAcceptable_fileIsIncorrect_throwsException() throws IOException {
        final File base = Mockito.mock(File.class);
        Mockito.when(base.getCanonicalPath()).thenReturn("/base/");

        final RegexFileFilter filter = new RegexFileFilter(base, new String[0], new String[0]);

        final File file = Mockito.mock(File.class);
        Mockito.when(file.getCanonicalPath()).thenThrow(new IOException());
        filter.isAcceptable(file);
    }

    @Test
    public void isAcceptable_fileNotInBaseDirectoryAndIncluded_notAccepted() throws IOException {
        final File base = Mockito.mock(File.class);
        Mockito.when(base.getCanonicalPath()).thenReturn("/base/");

        final String[] includes = new String[] { "^.*$" };
        final String[] excludes = new String[0];
        final RegexFileFilter filter = new RegexFileFilter(base, includes, excludes);

        final File file = Mockito.mock(File.class);
        Mockito.when(file.getCanonicalPath()).thenReturn("/not-base/file");
        Assert.assertFalse("The source shouldn't be accepted", filter.isAcceptable(file));
    }

    @Test
    public void isAcceptable_fileInBaseDirectoryAndPatternsAreEmpty_notAccepted() throws IOException {
        final File base = Mockito.mock(File.class);
        Mockito.when(base.getCanonicalPath()).thenReturn("/base/");

        final String[] includes = new String[0];
        final String[] excludes = new String[0];
        final RegexFileFilter filter = new RegexFileFilter(base, includes, excludes);

        final File file = Mockito.mock(File.class);
        Mockito.when(file.getCanonicalPath()).thenReturn("/base/src/file");
        Assert.assertFalse("The source shouldn't be accepted.", filter.isAcceptable(file));
    }

    @Test
    public void isAcceptable_fileInBaseDirectoryAndIncluded_accepted() throws IOException {
        final File base = Mockito.mock(File.class);
        Mockito.when(base.getCanonicalPath()).thenReturn("/base");

        final String[] includes = new String[] { "^.*/file$" };
        final String[] excludes = new String[0];
        final RegexFileFilter filter = new RegexFileFilter(base, includes, excludes);

        final File source = Mockito.mock(File.class);
        Mockito.when(source.getCanonicalPath()).thenReturn("/base/src/file");
        Assert.assertTrue("The source should be accepted.", filter.isAcceptable(source));
    }

    @Test
    public void isAcceptable_fileInBaseDirectoryAndExcluded_notAccepted() throws IOException {
        final File base = Mockito.mock(File.class);
        Mockito.when(base.getCanonicalPath()).thenReturn("/base");

        final String[] includes = new String[0];
        final String[] excludes = new String[] { "^.*/file$" };
        final RegexFileFilter filter = new RegexFileFilter(base, includes, excludes);

        final File source = Mockito.mock(File.class);
        Mockito.when(source.getCanonicalPath()).thenReturn("/base/src/file");
        Assert.assertFalse("The source shouldn't be accepted.", filter.isAcceptable(source));
    }

    @Test
    public void isAcceptable_fileInBaseDirectoryAndIncludedAndExcluded_notAccepted() throws IOException {
        final File base = Mockito.mock(File.class);
        Mockito.when(base.getCanonicalPath()).thenReturn("/base");

        final String[] includes = new String[] { "^.*/file$" };
        final String[] excludes = new String[] { "^.*/file$" };
        final RegexFileFilter filter = new RegexFileFilter(base, includes, excludes);

        final File source = Mockito.mock(File.class);
        Mockito.when(source.getCanonicalPath()).thenReturn("/base/src/file");
        Assert.assertFalse("The source shouldn't be accepted.", filter.isAcceptable(source));
    }

    @Test
    public void isAcceptable_fileInBaseDirectoryAndIncludedOnWindows_accepted() throws IOException {
        final File base = Mockito.mock(File.class);
        Mockito.when(base.getCanonicalPath()).thenReturn("C:\\base");

        final String[] includes = new String[] { "^.*/file$" };
        final String[] excludes = new String[0];
        final RegexFileFilter filter = new RegexFileFilter(base, includes, excludes);

        final File source = Mockito.mock(File.class);
        Mockito.when(source.getCanonicalPath()).thenReturn("C:\\base\\src\\file");
        Assert.assertTrue("The source should be accepted.", filter.isAcceptable(source));
    }
}
