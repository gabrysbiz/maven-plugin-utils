package biz.gabrys.maven.plugin.util.io;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public final class RegexFileFilterTest {

    @Test
    public void matches_patternsAreEmpty_returnsFalse() {
        final boolean matched = RegexFileFilter.matches("name", new String[0]);
        assertThat(matched).isFalse();
    }

    @Test
    public void matches_nameMatchedSecondPattern_returnsTrue() {
        final String[] patterns = new String[] { "^not-matched$", "^name$" };
        final boolean matched = RegexFileFilter.matches("name", patterns);
        assertThat(matched).isTrue();
    }

    @Test(expected = RegexFileFilterException.class)
    public void accept_baseDirectoryIsIncorrect_throwsException() throws IOException {
        final File base = mock(File.class);
        when(base.getCanonicalPath()).thenThrow(new IOException());
        new RegexFileFilter(base, new String[0], new String[0]);
    }

    @Test
    public void accept_oneParameter_executeIsAcceptable() {
        final File base = new File("base");
        final RegexFileFilter filter = spy(new RegexFileFilter(base, new String[0], new String[0]));
        final File file = new File("dir/name");

        filter.accept(file);

        verify(filter).accept(file);
        verify(filter).isAcceptable(file);
        verifyNoMoreInteractions(filter);
    }

    @Test
    public void accept_twoParameters_executeIsAcceptable() {
        final File base = new File("base");
        final RegexFileFilter filter = spy(new RegexFileFilter(base, new String[0], new String[0]));
        final File directory = new File("dir");

        filter.accept(directory, "name");

        verify(filter).accept(directory, "name");
        verify(filter).isAcceptable(new File(directory, "name"));
        verifyNoMoreInteractions(filter);
    }

    @Test(expected = RegexFileFilterException.class)
    public void isAcceptable_fileIsIncorrect_throwsException() throws IOException {
        final File base = mock(File.class);
        when(base.getCanonicalPath()).thenReturn("/base/");

        final RegexFileFilter filter = new RegexFileFilter(base, new String[0], new String[0]);

        final File file = mock(File.class);
        when(file.getCanonicalPath()).thenThrow(new IOException());
        filter.isAcceptable(file);
    }

    @Test
    public void isAcceptable_fileNotInBaseDirectoryAndIncluded_notAccepted() throws IOException {
        final File base = mock(File.class);
        when(base.getCanonicalPath()).thenReturn("/base/");

        final String[] includes = new String[] { "^.*$" };
        final String[] excludes = new String[0];
        final RegexFileFilter filter = new RegexFileFilter(base, includes, excludes);

        final File file = mock(File.class);
        when(file.getCanonicalPath()).thenReturn("/not-base/file");
        assertThat(filter.isAcceptable(file)).isFalse();
    }

    @Test
    public void isAcceptable_fileInBaseDirectoryAndPatternsAreEmpty_notAccepted() throws IOException {
        final File base = mock(File.class);
        when(base.getCanonicalPath()).thenReturn("/base/");

        final String[] includes = new String[0];
        final String[] excludes = new String[0];
        final RegexFileFilter filter = new RegexFileFilter(base, includes, excludes);

        final File file = mock(File.class);
        when(file.getCanonicalPath()).thenReturn("/base/src/file");
        assertThat(filter.isAcceptable(file)).isFalse();
    }

    @Test
    public void isAcceptable_fileInBaseDirectoryAndIncluded_accepted() throws IOException {
        final File base = mock(File.class);
        when(base.getCanonicalPath()).thenReturn("/base");

        final String[] includes = new String[] { "^.*/file$" };
        final String[] excludes = new String[0];
        final RegexFileFilter filter = new RegexFileFilter(base, includes, excludes);

        final File file = mock(File.class);
        when(file.getCanonicalPath()).thenReturn("/base/src/file");
        assertThat(filter.isAcceptable(file)).isTrue();
    }

    @Test
    public void isAcceptable_fileInBaseDirectoryAndExcluded_notAccepted() throws IOException {
        final File base = mock(File.class);
        when(base.getCanonicalPath()).thenReturn("/base");

        final String[] includes = new String[0];
        final String[] excludes = new String[] { "^.*/file$" };
        final RegexFileFilter filter = new RegexFileFilter(base, includes, excludes);

        final File file = mock(File.class);
        when(file.getCanonicalPath()).thenReturn("/base/src/file");
        assertThat(filter.isAcceptable(file)).isFalse();
    }

    @Test
    public void isAcceptable_fileInBaseDirectoryAndIncludedAndExcluded_notAccepted() throws IOException {
        final File base = mock(File.class);
        when(base.getCanonicalPath()).thenReturn("/base");

        final String[] includes = new String[] { "^.*/file$" };
        final String[] excludes = new String[] { "^.*/file$" };
        final RegexFileFilter filter = new RegexFileFilter(base, includes, excludes);

        final File file = mock(File.class);
        when(file.getCanonicalPath()).thenReturn("/base/src/file");
        assertThat(filter.isAcceptable(file)).isFalse();
    }

    @Test
    public void isAcceptable_fileInBaseDirectoryAndIncludedOnWindows_accepted() throws IOException {
        final File base = mock(File.class);
        when(base.getCanonicalPath()).thenReturn("C:\\base");

        final String[] includes = new String[] { "^.*/file$" };
        final String[] excludes = new String[0];
        final RegexFileFilter filter = new RegexFileFilter(base, includes, excludes);

        final File file = mock(File.class);
        when(file.getCanonicalPath()).thenReturn("C:\\base\\src\\file");
        assertThat(filter.isAcceptable(file)).isTrue();
    }
}
