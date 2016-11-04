package biz.gabrys.maven.plugin.util.classpath;

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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public final class ContextClassLoaderExtenderTest {

    private MavenProject project;
    private Log logger;

    @Before
    public void setup() {
        project = Mockito.mock(MavenProject.class);
        logger = Mockito.mock(Log.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addDependencies_arrayIsNull_throwException() {
        final ContextClassLoaderExtender extender = Mockito.spy(new ContextClassLoaderExtender(project, logger));

        extender.addDependencies((String[]) null);
    }

    @Test
    public void addDependencies_arrayIsNotNull_executeAddDependencies() {
        final ContextClassLoaderExtender extender = Mockito.spy(new ContextClassLoaderExtender(project, logger));

        final String[] types = new String[] { "jar", "duplicated", "duplicated", "war" };
        Mockito.doNothing().when(extender).addDependencies(Matchers.anySetOf(String.class));

        extender.addDependencies(types);

        Mockito.verify(extender).addDependencies(types);
        Mockito.verify(extender).addDependencies(Matchers.anySetOf(String.class));
        Mockito.verifyNoMoreInteractions(extender);
        Mockito.verifyZeroInteractions(logger);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addDependencies_collectionIsNull_throwException() {
        final ContextClassLoaderExtender extender = Mockito.spy(new ContextClassLoaderExtender(project, logger));

        extender.addDependencies((Collection<String>) null);
    }

    @Test
    public void addDependencies_collectionIsNotNull_executeInternalLogic() {
        final ContextClassLoaderExtender extender = Mockito.spy(new ContextClassLoaderExtender(project, logger));

        final Set<Artifact> artifacts = new HashSet<Artifact>();
        Mockito.when(project.getArtifacts()).thenReturn(artifacts);

        final Collection<String> types = Arrays.asList("jar", "war");
        final List<Artifact> filtered = new ArrayList<Artifact>();
        Mockito.doReturn(filtered).when(extender).filterArtifacts(artifacts, types);

        final List<URL> urls = new ArrayList<URL>();
        Mockito.doReturn(urls).when(extender).resolveArtifactsUrls(filtered);

        extender.addDependencies(types);

        Mockito.verify(extender).addDependencies(types);
        Mockito.verify(extender).filterArtifacts(artifacts, types);
        Mockito.verify(extender).resolveArtifactsUrls(filtered);
        Mockito.verify(extender).addToContextClassLoader(urls);
        Mockito.verifyNoMoreInteractions(extender);
        Mockito.verifyZeroInteractions(logger);
    }

    @Test
    public void filterArtifacts_threeArtifactsAndOneShouldBeIgnored_returnTwoArtifacts() {
        final ContextClassLoaderExtender extender = Mockito.spy(new ContextClassLoaderExtender(project, logger));

        final Artifact artifact1 = Mockito.mock(Artifact.class);
        Mockito.doReturn("artifact1").when(extender).createDisplayText(artifact1);
        Mockito.when(artifact1.getType()).thenReturn("include");
        final Artifact artifact2 = Mockito.mock(Artifact.class);
        Mockito.doReturn("artifact2").when(extender).createDisplayText(artifact2);
        Mockito.when(artifact2.getType()).thenReturn("exclude");
        final Artifact artifact3 = Mockito.mock(Artifact.class);
        Mockito.doReturn("artifact3").when(extender).createDisplayText(artifact3);
        Mockito.when(artifact3.getType()).thenReturn("include");

        final List<Artifact> artifacts = Arrays.asList(artifact1, artifact2, artifact3);
        final List<String> types = Arrays.asList("include");

        Mockito.when(logger.isDebugEnabled()).thenReturn(Boolean.TRUE);

        final List<Artifact> filtered = extender.filterArtifacts(artifacts, types);
        Assert.assertNotNull("Filtered collection instance.", filtered);
        Assert.assertEquals("Filtered collection size.", 2, filtered.size());
        Assert.assertTrue("Filtered collection contains artifact1.", filtered.contains(artifact1));
        Assert.assertTrue("Filtered collection contains artifact3.", filtered.contains(artifact3));

        Mockito.verify(extender).filterArtifacts(artifacts, types);
        Mockito.verify(extender).createDisplayText(artifact1);
        Mockito.verify(logger, Mockito.times(3)).isDebugEnabled();
        Mockito.verify(logger).debug("Include artifact1");
        Mockito.verify(extender).createDisplayText(artifact2);
        Mockito.verify(logger).debug("Exclude artifact2");
        Mockito.verify(extender).createDisplayText(artifact3);
        Mockito.verify(logger).debug("Include artifact3");
        Mockito.verifyNoMoreInteractions(extender, logger);
    }

    @Test
    public void createDisplayText() {
        final ContextClassLoaderExtender extender = Mockito.spy(new ContextClassLoaderExtender(project, logger));

        final Artifact artifact = Mockito.mock(Artifact.class);
        Mockito.when(artifact.getGroupId()).thenReturn("groupId");
        Mockito.when(artifact.getArtifactId()).thenReturn("artifactId");
        Mockito.when(artifact.getVersion()).thenReturn("1.0");
        Mockito.when(artifact.getType()).thenReturn("jar");
        Mockito.when(artifact.getScope()).thenReturn("compile");

        final String text = extender.createDisplayText(artifact);
        Assert.assertEquals("Text representation.", "groupId:artifactId-1.0.jar (compile)", text);

        Mockito.verify(extender).createDisplayText(artifact);
        Mockito.verifyNoMoreInteractions(extender);
        Mockito.verifyZeroInteractions(logger);
    }
}
