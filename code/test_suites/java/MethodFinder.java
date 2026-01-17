package com.example;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class MethodFinder {
    private static final String FUNCTION_DIR = "";
    private static final String TEST_DIR = "";

    public static void main(String[] args) {
        try {
            System.out.println("==== Program execution started ====");

            String projectRoot = System.getProperty("user.dir");
            System.out.println("\n==== Directory Information ====");
            System.out.println("Project root: " + projectRoot);

            String fullFunctionDir = Paths.get(projectRoot, FUNCTION_DIR).toString();
            String fullTestDir = Paths.get(projectRoot, TEST_DIR).toString();

            System.out.println("Full path for source directory: " + fullFunctionDir);
            System.out.println("Full path for test directory: " + fullTestDir);

            System.out.println("\n==== Directory Validation ====");
            validateDirectory(fullFunctionDir, "Source directory");
            validateDirectory(fullTestDir, "Test directory");

            System.out.println("\n==== File Scanning ====");
            List<File> functionFiles = listJavaFiles(fullFunctionDir);
            List<File> testFiles = listJavaFiles(fullTestDir);

            System.out.println("\n==== File Statistics ====");
            System.out.println("Number of source files: " + functionFiles.size());
            System.out.println("Number of test files: " + testFiles.size());

            if (functionFiles.isEmpty() || testFiles.isEmpty()) {
                System.err.println("Warning: No Java files found. Please check if the directory paths are correct!");
                return;
            }

            System.out.println("\n==== Start processing files ====");
            for (File functionFile : functionFiles) {
                processFileWithDebug(functionFile, testFiles);
            }
        } catch (Exception e) {
            System.err.println("\n==== An error occurred ====");
            System.err.println("Error type: " + e.getClass().getName());
            System.err.println("Error message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void validateDirectory(String directory, String dirType) {
        Path dirPath = Paths.get(directory);
        System.out.println("Checking " + dirType + ": " + directory);
        if (Files.exists(dirPath)) {
            System.out.println("✓ " + dirType + " exists");
            if (Files.isDirectory(dirPath)) {
                System.out.println("✓ " + dirType + " is a directory");
            } else {
                System.err.println("✗ " + dirType + " is not a directory!");
            }
        } else {
            System.err.println("✗ " + dirType + " does not exist!");
        }
    }

    private static List<File> listJavaFiles(String directory) {
        try {
            System.out.println("Scanning directory: " + directory);
            Path dirPath = Paths.get(directory);
            List<File> files = Files.walk(dirPath)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .map(Path::toFile)
                    .collect(Collectors.toList());

            System.out.println("Java files found:");
            files.forEach(file -> System.out.println("- " + file.getName()));

            return files;
        } catch (Exception e) {
            System.err.println("Failed to scan directory: " + directory);
            System.err.println("Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static void processFileWithDebug(File functionFile, List<File> testFiles) {
        System.out.println("\n==== Processing file ====");
        System.out.println("Processing source class: " + functionFile.getName());

        try {
            String functionClassName = functionFile.getName().replace(".java", "");
            System.out.println("Source class base name: " + functionClassName);

            File testFile = findMatchingTestFile(testFiles, functionClassName);
            if (testFile == null) {
                System.err.println("Corresponding test class not found, skipping process");
                return;
            }

            System.out.println("\n==== Reading file content ====");
            String functionContent = readFileContent(functionFile);
            String testContent = readFileContent(testFile);

            if (functionContent == null || testContent == null) {
                System.err.println("Failed to read file content, skipping process");
                return;
            }

            System.out.println("\n==== Parsing source code ====");
            CompilationUnit functionCU = parseJavaFile(functionFile, functionContent);
            CompilationUnit testCU = parseJavaFile(testFile, testContent);

            if (functionCU == null || testCU == null) {
                System.err.println("Failed to parse source code, skipping process");
                return;
            }

            System.out.println("Setting up LexicalPreservingPrinter...");
            LexicalPreservingPrinter.setup(functionCU);

            System.out.println("\n==== Method Analysis ====");
            MethodFindingVisitor functionVisitor = new MethodFindingVisitor();
            functionCU.accept(functionVisitor, null);

            TestMethodVisitor testVisitor = new TestMethodVisitor();
            testCU.accept(testVisitor, null);

            if (testVisitor.testMethod != null) {
                System.out.println("Found test method abC123, starting to find the corresponding composite method");
                processMethodRenaming(functionFile, functionCU, functionVisitor.methods, testVisitor.testMethod);
            } else {
                System.err.println("Method abC123 not found in the test class!");
            }

        } catch (Exception e) {
            System.err.println("\n==== An error occurred while processing the file ====");
            System.err.println("File: " + functionFile.getName());
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String readFileContent(File file) {
        try {
            System.out.println("Reading file: " + file.getName());
            String content = Files.readString(file.toPath(), StandardCharsets.UTF_8);
            System.out.println("✓ File content read successfully (Length: " + content.length() + " characters)");
            return content;
        } catch (Exception e) {
            System.err.println("✗ Failed to read file: " + file.getName());
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

    private static CompilationUnit parseJavaFile(File file, String content) {
        try {
            System.out.println("Parsing Java file: " + file.getName());
            CompilationUnit cu = new JavaParser().parse(content).getResult().orElseThrow();
            System.out.println("✓ File parsed successfully");
            return cu;
        } catch (Exception e) {
            System.err.println("✗ Parsing failed: " + file.getName());
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

    private static File findMatchingTestFile(List<File> testFiles, String functionClassName) {
        String baseName = functionClassName.startsWith("function_") ?
                functionClassName.substring(9) : functionClassName;

        String expectedTestFileName = "test_" + baseName + ".java";
        System.out.println("Looking for test file: " + expectedTestFileName);

        return testFiles.stream()
                .filter(file -> file.getName().equals(expectedTestFileName))
                .findFirst()
                .orElse(null);
    }

    private static void processMethodRenaming(
            File functionFile,
            CompilationUnit functionCU,
            List<MethodInfo> methods,
            MethodInfo testMethod) {

        System.out.println("\n==== Method Renaming Process ====");

        MethodDeclaration mainMethod = findMainMethod(methods, testMethod);

        if (mainMethod == null) {
            System.err.println("No matching composite method found!");
            return;
        }

        String originalName = mainMethod.getNameAsString();
        System.out.println("Found composite method: " + originalName);

        try {
            System.out.println("Attempting to rename method...");
            mainMethod.setName("abC123");

            System.out.println("Generating modified code...");
            String modifiedCode = LexicalPreservingPrinter.print(functionCU);

            System.out.println("\n==== Code Modification Verification ====");
            System.out.println("Checking if the new method name exists in the modified code...");
            if (!modifiedCode.contains("abC123")) {
                System.err.println("Warning: New method name not found in the modified code!");
                System.out.println("Original method name: " + originalName);
                return;
            }

            System.out.println("Writing modified code to file...");
            Files.writeString(functionFile.toPath(), modifiedCode, StandardCharsets.UTF_8);

            System.out.println("Verifying file modification...");
            String newContent = Files.readString(functionFile.toPath(), StandardCharsets.UTF_8);
            if (newContent.contains("abC123")) {
                System.out.println("✓ Method renamed successfully!");
                System.out.println("Original method name: " + originalName);
                System.out.println("New method name: abC123");
            } else {
                System.err.println("✗ File modification verification failed!");
                System.err.println("Please check file write permissions and file system status");
            }

        } catch (Exception e) {
            System.err.println("\n==== An error occurred during the renaming process ====");
            System.err.println("Error type: " + e.getClass().getName());
            System.err.println("Error message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static class MethodInfo {
        private final MethodDeclaration method;
        private final int callCount;
        private final int parameterCount;

        public MethodInfo(MethodDeclaration method, int callCount) {
            this.method = method;
            this.callCount = callCount;
            this.parameterCount = method.getParameters().size();
        }

        public MethodDeclaration getMethod() {
            return method;
        }

        public int getCallCount() {
            return callCount;
        }

        public int getParameterCount() {
            return parameterCount;
        }
    }

    private static class MethodFindingVisitor extends VoidVisitorAdapter<Void> {
        private final List<MethodInfo> methods = new ArrayList<>();

        @Override
        public void visit(MethodDeclaration method, Void arg) {
            List<MethodCallExpr> calls = method.findAll(MethodCallExpr.class);
            Set<String> uniqueCalls = calls.stream()
                    .map(MethodCallExpr::getNameAsString)
                    .collect(Collectors.toSet());

            methods.add(new MethodInfo(method, uniqueCalls.size()));
            System.out.println(String.format(
                    "Found method: %s, Number of other methods called: %d",
                    method.getNameAsString(),
                    uniqueCalls.size()
            ));

            super.visit(method, arg);
        }
    }

    private static class TestMethodVisitor extends VoidVisitorAdapter<Void> {
        private MethodInfo testMethod;

        @Override
        public void visit(MethodDeclaration method, Void arg) {
            List<MethodCallExpr> calls = method.findAll(MethodCallExpr.class);

            Optional<MethodCallExpr> abC123Call = calls.stream()
                    .filter(call -> call.getNameAsString().equals("abC123"))
                    .findFirst();

            if (abC123Call.isPresent()) {
                MethodCallExpr callExpr = abC123Call.get();
                System.out.println("Found abC123 method call in test class, number of arguments: " + callExpr.getArguments().size());

                MethodDeclaration virtualMethod = new MethodDeclaration();
                virtualMethod.setName("abC123");

                callExpr.getArguments().forEach(argument -> {
                    virtualMethod.addParameter("String", "param");
                });

                testMethod = new MethodInfo(virtualMethod, 0);
            }
            super.visit(method, arg);
        }
    }

    private static MethodDeclaration findMainMethod(List<MethodInfo> methods, MethodInfo testMethod) {
        System.out.println("\n==== Finding Composite Method ====");

        if (testMethod == null || methods.isEmpty()) {
            System.err.println("Test method or list of source class methods is empty!");
            return null;
        }

        List<MethodInfo> publicMethods = methods.stream()
                .filter(method -> !method.getMethod().isPrivate())
                .collect(Collectors.toList());

        System.out.println("Number of public methods: " + publicMethods.size());

        System.out.println("\nAvailable public methods:");
        publicMethods.forEach(method -> {
            System.out.println(String.format(
                    "- Method: %-20s | Params: %-3d | Calls: %-3d",
                    method.getMethod().getNameAsString(),
                    method.getParameterCount(),
                    method.getCallCount()
            ));
        });

        Optional<MethodInfo> matchedMethod = publicMethods.stream()
                .filter(method -> method.getCallCount() > 0)
                .max(Comparator.comparingInt(MethodInfo::getCallCount));

        if (matchedMethod.isPresent()) {
            System.out.println("\nFound matching composite method: " + matchedMethod.get().getMethod().getNameAsString());
            return matchedMethod.get().getMethod();
        } else {
            System.err.println("\nNo matching composite method found!");
            return null;
        }
    }
}