package biz.gabrys.maven.plugin.util.classpath;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

public final class ContextClassLoaderExtenderTest {

    private MavenProject project;
    private Log logger;

    @Before
    public void setup() {
        project = mock(MavenProject.class);
        logger = mock(Log.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addDependencies_arrayIsNull_throwException() {
        final ContextClassLoaderExtender extender = spy(new ContextClassLoaderExtender(project, logger));

        extender.addDependencies((String[]) null);
    }

    @Test
    public void addDependencies_arrayIsNotNull_executeAddDependencies() {
        final ContextClassLoaderExtender extender = spy(new ContextClassLoaderExtender(project, logger));

        final String[] types = new String[] { "jar", "duplicated", "duplicated", "war" };
        doNothing().when(extender).addDependencies(ArgumentMatchers.<String>anySet());

        extender.addDependencies(types);

        verify(extender).addDependencies(types);
        verify(extender).addDependencies(ArgumentMatchers.<String>anySet());
        verifyNoMoreInteractions(extender);
        verifyNoInteractions(logger);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addDependencies_collectionIsNull_throwException() {
        final ContextClassLoaderExtender extender = spy(new ContextClassLoaderExtender(project, logger));

        extender.addDependencies((Collection<String>) null);
    }

    @Test
    public void addDependencies_collectionIsNotNull_executeInternalLogic() {
        final ContextClassLoaderExtender extender = spy(new ContextClassLoaderExtender(project, logger));

        final Set<Artifact> artifacts = new HashSet<Artifact>();
        when(project.getArtifacts()).thenReturn(artifacts);

        final Collection<String> types = Arrays.asList("jar", "war");
        final List<Artifact> filtered = new ArrayList<Artifact>();
        doReturn(filtered).when(extender).filterArtifacts(artifacts, types);

        final List<URL> urls = new ArrayList<URL>();
        doReturn(urls).when(extender).resolveArtifactsUrls(filtered);

        extender.addDependencies(types);

        verify(extender).addDependencies(types);
        verify(extender).filterArtifacts(artifacts, types);
        verify(extender).resolveArtifactsUrls(filtered);
        verify(extender).addToContextClassLoader(urls);
        verifyNoMoreInteractions(extender);
        verifyNoInteractions(logger);
    }

    @Test
    public void filterArtifacts_loggerEnabledAndThreeArtifactsAndOneShouldBeIgnored_logsDataAndReturnTwoArtifacts() {
        final ContextClassLoaderExtender extender = spy(new ContextClassLoaderExtender(project, logger));

        final Artifact artifact1 = mock(Artifact.class);
        doReturn("artifact1").when(extender).createDisplayText(artifact1);
        when(artifact1.getType()).thenReturn("include");
        final Artifact artifact2 = mock(Artifact.class);
        doReturn("artifact2").when(extender).createDisplayText(artifact2);
        when(artifact2.getType()).thenReturn("exclude");
        final Artifact artifact3 = mock(Artifact.class);
        doReturn("artifact3").when(extender).createDisplayText(artifact3);
        when(artifact3.getType()).thenReturn("include");

        final List<Artifact> artifacts = Arrays.asList(artifact1, artifact2, artifact3);
        final List<String> types = Arrays.asList("include");

        when(logger.isDebugEnabled()).thenReturn(Boolean.TRUE);

        final List<Artifact> filtered = extender.filterArtifacts(artifacts, types);
        assertThat(filtered).containsExactly(artifact1, artifact3);

        verify(extender).filterArtifacts(artifacts, types);
        verify(extender).createDisplayText(artifact1);
        verify(logger, times(3)).isDebugEnabled();
        verify(logger).debug("Include artifact1");
        verify(extender).createDisplayText(artifact2);
        verify(logger).debug("Exclude artifact2");
        verify(extender).createDisplayText(artifact3);
        verify(logger).debug("Include artifact3");
        verifyNoMoreInteractions(extender, logger);
    }

    @Test
    public void filterArtifacts_loggerDisabledAndThreeArtifactsAndOneShouldBeIgnored_logsDataAndReturnTwoArtifacts() {
        final ContextClassLoaderExtender extender = spy(new ContextClassLoaderExtender(project, logger));

        final Artifact artifact1 = mock(Artifact.class);
        doReturn("artifact1").when(extender).createDisplayText(artifact1);
        when(artifact1.getType()).thenReturn("include");
        final Artifact artifact2 = mock(Artifact.class);
        doReturn("artifact2").when(extender).createDisplayText(artifact2);
        when(artifact2.getType()).thenReturn("exclude");
        final Artifact artifact3 = mock(Artifact.class);
        doReturn("artifact3").when(extender).createDisplayText(artifact3);
        when(artifact3.getType()).thenReturn("include");

        final List<Artifact> artifacts = Arrays.asList(artifact1, artifact2, artifact3);
        final List<String> types = Arrays.asList("include");

        when(logger.isDebugEnabled()).thenReturn(Boolean.FALSE);

        final List<Artifact> filtered = extender.filterArtifacts(artifacts, types);
        assertThat(filtered).containsExactly(artifact1, artifact3);

        verify(extender).filterArtifacts(artifacts, types);
        verify(logger, times(3)).isDebugEnabled();
        verifyNoMoreInteractions(extender, logger);
    }

    @Test
    public void createDisplayText() {
        final ContextClassLoaderExtender extender = spy(new ContextClassLoaderExtender(project, logger));

        final Artifact artifact = mock(Artifact.class);
        when(artifact.getGroupId()).thenReturn("groupId");
        when(artifact.getArtifactId()).thenReturn("artifactId");
        when(artifact.getVersion()).thenReturn("1.0");
        when(artifact.getType()).thenReturn("jar");
        when(artifact.getScope()).thenReturn("compile");

        final String text = extender.createDisplayText(artifact);

        assertThat(text).isEqualTo("groupId:artifactId-1.0.jar (compile)");
        verify(extender).createDisplayText(artifact);
        verifyNoMoreInteractions(extender);
        verifyNoInteractions(logger);
    }
}
