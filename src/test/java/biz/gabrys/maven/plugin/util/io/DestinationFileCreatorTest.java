package biz.gabrys.maven.plugin.util.io;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

public final class DestinationFileCreatorTest {

    @Test
    public void create_fileWithExtension_createdOutputFile() {
        final File sourceDirectory = new File("/source");
        final File outputDirectory = new File("/output");

        final String outputFormat = "prefix-%s-suffix.fileExt";
        final String fileNamePattern = String.format(outputFormat, DestinationFileCreator.FILE_NAME_PARAMETER);

        final DestinationFileCreator creator = new DestinationFileCreator(sourceDirectory, outputDirectory);
        creator.setFileNamePattern(fileNamePattern);

        final File source = new File(sourceDirectory, "name.extension");
        final String expectedName = String.format(outputFormat, "name");

        final File output = creator.create(source);

        assertEquals("Output file name", expectedName, output.getName());
        assertEquals("Output file path", new File(outputDirectory, expectedName), output);
    }

    @Test
    public void create_fileWithoutExtension_createdOutputFile() {
        final File sourceDirectory = new File("/source");
        final File outputDirectory = new File("/output");

        final String outputFormat = "prefix-%s-suffix";
        final String fileNamePattern = String.format(outputFormat, DestinationFileCreator.FILE_NAME_PARAMETER);

        final DestinationFileCreator creator = new DestinationFileCreator(sourceDirectory, outputDirectory, fileNamePattern);

        final File source = new File(sourceDirectory, "name");
        final String expectedName = String.format(outputFormat, "name");

        final File output = creator.create(source);

        assertEquals("Output file name", expectedName, output.getName());
        assertEquals("Output file path", new File(outputDirectory, expectedName), output);
    }
}
