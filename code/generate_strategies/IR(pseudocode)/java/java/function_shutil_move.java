package com.example;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileMover {
    public String perform_task(String task, String args) throws IOException {
        switch (task) {
            case "move": {
                String[] paths = args.split(",", 2);
                File src = new File(paths[0]);
                File dest = new File(paths[1]);

                if (!src.exists()) {
                    throw new IOException("Source path '" + src.getPath() + "' does not exist");
                }

                if (!dest.exists()) {
                    dest.mkdirs();
                }

                if (src.getAbsolutePath().equals(dest.getAbsolutePath())) {
                    return src.getPath();
                }

                
                File destFile;
                if (dest.isDirectory()) {
                    destFile = new File(dest, src.getName());
                    
                    if (destFile.exists()) {
                        if (destFile.isDirectory()) {
                            FileUtils.deleteDirectory(destFile);
                        } else {
                            destFile.delete();
                        }
                    }
                } else {
                    destFile = dest;
                }

                if (src.isDirectory()) {
                    FileUtils.moveDirectory(src, destFile);
                } else {
                    FileUtils.moveFile(src, destFile);
                }
                return destFile.getPath();
            }
            case "create_file": {
                String[] parts = args.split(",", 2);
                File file = new File(parts[0]);
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(parts[1]);
                }
                return null;
            }
            case "create_directory": {
                File dir = new File(args);
                dir.mkdirs();
                return null;
            }
            case "path_exists": {
                File path = new File(args);
                
                return path.exists() ? "True" : "False";
            }
            case "remove_path": {
                File path = new File(args);
                if (path.isDirectory()) {
                    FileUtils.deleteDirectory(path);
                } else {
                    if (!path.delete()) {
                        throw new IOException("Failed to delete file: " + path);
                    }
                }
                return null;
            }
            default:
                throw new IllegalArgumentException("Unknown task '" + task + "' specified.");
        }
    }
}