/*
 * Maven Plugin Utils
 * http://maven-project-utils.projects.gabrys.biz/
 *
 * Copyright (c) 2015 Adam Gabryś
 *
 * This file is licensed under the BSD 3-Clause (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain:
 * - a copy of the License at project page
 * - a template of the License at https://opensource.org/licenses/BSD-3-Clause
 */
package biz.gabrys.maven.plugin.util.io;

import java.io.File;

/**
 * <p>
 * Responsible for creating destination file (logical) in output directory based on source directory, source file and
 * output file name pattern.
 * </p>
 * <p>
 * Example:
 * </p>
 * 
 * <pre>
 * public class ExampleMojo extends AbstractMojo {
 *
 *     protected File sourceDirectory;
 *     protected File outputDirectory;
 *     protected String[] outputFileNamePattern = {@link #FILE_NAME_PARAMETER DestinationFileCreator.FILE_NAME_PARAMETER} + ".output";
 *
 *     public void execute() {
 *         // logic
 *         File source = ...
 *
 *         // process file data
 *         String content = process(source); 
 *
 *         File output = creatFile(source);
 *         saveFile(output, content);
 *     }
 * 
 *     private File createFile(File source) {
 *        DestinationFileCreator fileCreator = new DestinationFileCreator(sourceDirectory, outputDirectory, outputFileNamePattern);
 *        File output = fileCreator.create(source);
 *        return output;
 *    }
 *
 *    ...
 * }
 * </pre>
 * 
 * @since 1.0
 */
public class DestinationFileCreator {

    /**
     * The pattern in output file name which will be replaced by source file name without extension.
     * @since 1.0
     */
    public static final String FILE_NAME_PARAMETER = "{fileName}";

    private final File sourceDirectory;
    private final File outputDirectory;

    private String fileNamePattern;

    /**
     * Constructs a new instance.
     * @param sourceDirectory the source directory.
     * @param outputDirectory the output directory.
     * @since 1.0
     */
    public DestinationFileCreator(final File sourceDirectory, final File outputDirectory) {
        this.sourceDirectory = sourceDirectory;
        this.outputDirectory = outputDirectory;
    }

    /**
     * Constructs a new instance.
     * @param sourceDirectory the source directory.
     * @param outputDirectory the output directory.
     * @param fileNamePattern the output file name pattern.
     * @since 1.0
     */
    public DestinationFileCreator(final File sourceDirectory, final File outputDirectory, final String fileNamePattern) {
        this.sourceDirectory = sourceDirectory;
        this.outputDirectory = outputDirectory;
        this.fileNamePattern = fileNamePattern;
    }

    /**
     * Sets an output file name pattern.
     * @param fileNamePattern the output file name pattern.
     * @since 1.0
     */
    public void setFileNamePattern(final String fileNamePattern) {
        this.fileNamePattern = fileNamePattern;
    }

    /**
     * Creates a destination file in the output directory. This method does not create file on a hard drive.
     * @param source the source file.
     * @return the destination file.
     * @since 1.0
     */
    public File create(final File source) {
        String fileName = source.getName();
        final int index = fileName.lastIndexOf('.');
        if (index > 0) {
            fileName = fileName.substring(0, index);
        }
        fileName = fileNamePattern.replace(FILE_NAME_PARAMETER, fileName);

        final String parentPath = source.getParentFile().getAbsolutePath();
        final String path = parentPath.substring(sourceDirectory.getAbsolutePath().length());
        final File directory = new File(outputDirectory, path);

        return new File(directory, fileName);
    }
}
