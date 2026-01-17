package com.example;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

public class DirectoryHandler {
    public String handle_directory_operations(String srcDir, String destDir, String operation) throws IOException {
        class DirectoryHandler {
            public String handle_directory_operations(String srcDir, String destDir, String operation) throws IOException {
                if ("copy".equals(operation)) {
                    File destDirFile = new File(destDir);
                    if (destDirFile.exists()) {
                        throw new IllegalArgumentException("The destination directory '" + destDir + "' already exists.");
                    }
                    FileUtils.copyDirectory(new File(srcDir), destDirFile);
                    return "Directory copied from " + srcDir + " to " + destDir + ".";
                } else {
                    throw new IllegalArgumentException("Unsupported operation. Only 'copy' is supported.");
                }
            }
        }

        return new DirectoryHandler().handle_directory_operations(srcDir, destDir, operation);
    }
}