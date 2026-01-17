package com.example;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class FileHandler {
    public String handle_file_operations(String content, String operation, String target_path) {
        class FileHandler {
            public String handleFileOps(String content, String operation, String target_path) throws IOException {
                if (content == null) content = "Hello, world!";
                if (operation == null) operation = "copy";

                switch (operation) {
                    case "copy": {
                        File tempFile = File.createTempFile(UUID.randomUUID().toString(), ".tmp");
                        FileUtils.writeStringToFile(tempFile, content, StandardCharsets.UTF_8);

                        File targetFile;
                        if (target_path == null) {
                            Path tempDir = Files.createTempDirectory("temp");
                            targetFile = new File(tempDir.toString(), tempFile.getName());
                        } else {
                            targetFile = new File(target_path);
                        }

                        FileUtils.copyFile(tempFile, targetFile);
                        // 返回源文件和目标文件路径，用分隔符"|"连接
                        return tempFile.getAbsolutePath() + "|" + targetFile.getAbsolutePath();
                    }
                    case "read": {
                        return FileUtils.readFileToString(new File(content), StandardCharsets.UTF_8);
                    }
                    case "exists": {
                        return String.valueOf(new File(content).exists());
                    }
                    case "remove": {
                        File file = new File(content);
                        if (file.exists()) {
                            file.delete();
                        }
                        return "removed";
                    }
                    default:
                        throw new IllegalArgumentException("Unsupported operation. Choose from 'copy', 'read', 'exists', 'remove'.");
                }
            }
        }

        try {
            return new FileHandler().handleFileOps(content, operation, target_path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}