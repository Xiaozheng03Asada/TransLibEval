package com.example;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.FileVisitResult;
import java.nio.file.attribute.BasicFileAttributes;

public class DirectoryDeleter {
    public String delete_directory_tree(String directoryPath) {
        class DirectoryDeleter {
            public String execute(String path) {
                File directory = new File(path);

                if (!directory.exists()) {
                    return String.format("Directory '%s' does not exist.", path);
                }

                try {
                    
                    Files.walkFileTree(directory.toPath(), new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            try {
                                
                                file.toFile().setWritable(true);
                                Files.delete(file);
                            } catch (IOException e) {
                                throw new IOException("Permission denied: " + file);
                            }
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                            try {
                                
                                dir.toFile().setWritable(true);
                                Files.delete(dir);
                            } catch (IOException e) {
                                throw new IOException("Failed to delete directory: " + dir);
                            }
                            return FileVisitResult.CONTINUE;
                        }
                    });
                    return String.format("Directory '%s' deleted successfully.", path);
                } catch (IOException e) {
                    return String.format("Failed to delete directory '%s': %s", path, e.getMessage());
                }
            }
        }

        return new DirectoryDeleter().execute(directoryPath);
    }
}