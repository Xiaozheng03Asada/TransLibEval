package com.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.tools.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProjectIntegrator {

    private static final String SOURCE_CODE_DIRECTORY = "";
    private static final String TEST_CODE_DIRECTORY = "";
    private static final String TARGET_PACKAGE_DECLARATION = "";
    private static final String COMPILE_ERRORS_JSON_OUTPUT_PATH = "";

    public static void main(String[] args) {
        System.out.println("--- Starting Integrated Java Project Processor ---");

        System.out.println("\n--- Executing Step 1: AddPackageName ---");
        AddPackageName.execute(SOURCE_CODE_DIRECTORY, TARGET_PACKAGE_DECLARATION);
        System.out.println("--- Step 1 Completed ---");

        System.out.println("\n--- Executing Step 2: RenameClassToFileName ---");
        RenameClassToFileName.execute(SOURCE_CODE_DIRECTORY);
        System.out.println("--- Step 2 Completed ---");

        System.out.println("\n--- Executing Step 3: CheckJavaFiles ---");
        CheckJavaFiles.execute(SOURCE_CODE_DIRECTORY, TEST_CODE_DIRECTORY, COMPILE_ERRORS_JSON_OUTPUT_PATH);
        System.out.println("--- Step 3 Completed ---");

        System.out.println("\n--- Integrated Java Project Processor Finished Successfully ---");
    }

    private static class AddPackageName {
        public static void execute(String targetDirectoryPath, String packageNameDeclaration) {
            try (Stream<Path> paths = Files.walk(Paths.get(targetDirectoryPath))) {
                paths.filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".java"))
                        .forEach(filePath -> processJavaFile(filePath, packageNameDeclaration));
            } catch (IOException e) {
                System.err.println("Error in AddPackageName: " + e.getMessage());
                e.printStackTrace();
            }
        }

        private static void processJavaFile(Path filePath, String packageNameDeclaration) {
            try {
                File file = filePath.toFile();
                StringBuilder contentBuilder = new StringBuilder();

                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    boolean hasPackage = false;
                    while ((line = reader.readLine()) != null) {
                        if (line.trim().startsWith("package ")) {
                            hasPackage = true;
                        }
                        contentBuilder.append(line).append(System.lineSeparator());
                    }
                    if (hasPackage) {
                        System.out.println("Package already exists in: " + file.getName());
                        return;
                    }
                }

                String updatedContent = packageNameDeclaration + System.lineSeparator() + System.lineSeparator() + contentBuilder.toString();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write(updatedContent);
                    System.out.println("Added package to: " + file.getName());
                }
            } catch (IOException e) {
                System.err.println("Error processing file in AddPackageName: " + filePath);
                e.printStackTrace();
            }
        }
    }

    private static class RenameClassToFileName {
        public static void execute(String directoryPath) {
            File directory = new File(directoryPath);
            if (!directory.exists() || !directory.isDirectory()) {
                System.err.println("Error in RenameClassToFileName: Directory not found: " + directoryPath);
                return;
            }

            File[] javaFiles = directory.listFiles((dir, name) -> name.endsWith(".java"));
            if (javaFiles == null || javaFiles.length == 0) {
                System.out.println("No Java files found for RenameClassToFileName.");
                return;
            }

            for (File javaFile : javaFiles) {
                try {
                    String fileName = javaFile.getName().replace(".java", "");
                    modifyClassFile(javaFile, fileName);
                } catch (IOException e) {
                    System.err.println("Error processing file in RenameClassToFileName: " + javaFile.getPath());
                    e.printStackTrace();
                }
            }
        }

        private static void modifyClassFile(File javaFile, String fileName) throws IOException {
            String content = new String(Files.readAllBytes(javaFile.toPath()), StandardCharsets.UTF_8);
            Pattern pattern = Pattern.compile("\\bpublic\\s+class\\s+(\\w+)\\b");
            Matcher matcher = pattern.matcher(content);

            if (matcher.find()) {
                String oldClassName = matcher.group(1);
                if (oldClassName.equals(fileName)) {
                    System.out.println("Class name already matches file name: " + fileName);
                    return;
                }
                String newContent = content.replaceAll("\\b" + Pattern.quote(oldClassName) + "\\b", fileName);
                Files.write(javaFile.toPath(), newContent.getBytes(StandardCharsets.UTF_8));
                System.out.println("Renamed class " + oldClassName + " to " + fileName + " in file " + javaFile.getPath());
            } else {
                System.out.println("No public class declaration found in file " + javaFile.getPath());
            }
        }
    }

    private static class CheckJavaFiles {
        public static void execute(String folderPath, String testFolderPath, String outputJsonPath) {
            Map<String, List<String>> errorReports = new HashMap<>();
            List<File> filesToDelete = new ArrayList<>();
            List<File> testFilesToDelete = new ArrayList<>();

            try {
                List<File> javaFiles = Files.walk(Paths.get(folderPath))
                        .filter(path -> path.toString().endsWith(".java"))
                        .map(Path::toFile)
                        .collect(Collectors.toList());

                if (javaFiles.isEmpty()) {
                    System.out.println("No Java files found for compilation check.");
                }

                for (File javaFile : javaFiles) {
                    if (checkAndReportCompileError(javaFile, errorReports)) {
                        filesToDelete.add(javaFile);
                        File testFile = findTestFile(javaFile, testFolderPath);
                        if (testFile != null) {
                            testFilesToDelete.add(testFile);
                        }
                    }
                }

                writeErrorsToJson(errorReports, outputJsonPath);
                deleteFiles(filesToDelete, "Source file");
                deleteFiles(testFilesToDelete, "Test file");

            } catch (IOException e) {
                System.err.println("Error in CheckJavaFiles: " + e.getMessage());
                e.printStackTrace();
            }
        }

        private static boolean checkAndReportCompileError(File javaFile, Map<String, List<String>> errorReports) {
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            if (compiler == null) {
                System.err.println("No Java compiler available. Are you running this program with a JDK?");
                return false;
            }

            boolean hasErrors = false;
            try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null)) {
                DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
                Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(Collections.singletonList(javaFile));
                JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);

                if (!task.call()) {
                    hasErrors = true;
                    List<String> fileErrors = errorReports.computeIfAbsent(javaFile.getPath(), k -> new ArrayList<>());
                    for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                        String errorMessage = String.format("Line %d: %s", diagnostic.getLineNumber(), diagnostic.getMessage(null));
                        fileErrors.add(errorMessage);
                        System.out.println("Compile Error in " + javaFile.getName() + ": " + errorMessage);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error processing file in CheckJavaFiles: " + javaFile.getPath());
                e.printStackTrace();
            }
            return hasErrors;
        }

        private static File findTestFile(File functionFile, String testFolderPath) {
            String functionFileName = functionFile.getName();
            if (functionFileName.startsWith("function_")) {
                String testFileName = functionFileName.replace("function_", "test_");
                File testFile = Paths.get(testFolderPath, testFileName).toFile();
                return testFile.exists() ? testFile : null;
            }
            return null;
        }

        private static void deleteFiles(List<File> files, String fileType) {
            if (files.isEmpty()) {
                System.out.println("No " + fileType + "s to delete.");
                return;
            }
            for (File file : files) {
                try {
                    if (file.delete()) {
                        System.out.println("Deleted " + fileType + ": " + file.getPath());
                    } else {
                        System.err.println("Failed to delete " + fileType + ": " + file.getPath());
                    }
                } catch (Exception e) {
                    System.err.println("Error deleting " + fileType + ": " + file.getPath() + " - " + e.getMessage());
                }
            }
        }

        private static void writeErrorsToJson(Map<String, List<String>> errorReports, String outputJsonPath) {
            if (errorReports.isEmpty()) {
                System.out.println("No compile errors to report. Skipping JSON output.");
                return;
            }
            try (Writer writer = new FileWriter(outputJsonPath)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(errorReports, writer);
                System.out.println("Error report written to: " + outputJsonPath);
            } catch (IOException e) {
                System.err.println("Error writing JSON file in CheckJavaFiles: " + e.getMessage());
            }
        }
    }
}