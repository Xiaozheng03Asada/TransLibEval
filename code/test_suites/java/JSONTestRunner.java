package tool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

public class JSONTestRunner {
    private final List<TestResult> results = new ArrayList<>();
    private final Map<String, ErrorInfo> errorTypeStats = new HashMap<>();

    private static class TestResult {
        String file;
        int totalTests;
        int passedTests;
        int failedTests;
        List<FailureDetail> failures;
        boolean timedOut = false;
        String timeoutMessage;

        TestResult(String file, int totalTests, int passedTests, int failedTests, List<FailureDetail> failures) {
            this.file = file;
            this.totalTests = totalTests;
            this.passedTests = passedTests;
            this.failedTests = failedTests;
            this.failures = failures;
        }

        TestResult(String file, int totalTests, String timeoutMessage) {
            this.file = file;
            this.totalTests = totalTests;
            this.passedTests = 0;
            this.failedTests = totalTests;
            this.failures = new ArrayList<>();
            this.failures.add(new FailureDetail("ALL_TESTS", timeoutMessage));
            this.timedOut = true;
            this.timeoutMessage = timeoutMessage;
        }
    }

    private static class FailureDetail {
        String testName;
        String reason;

        FailureDetail(String testName, String reason) {
            this.testName = testName;
            this.reason = reason;
        }
    }

    private static class ErrorInfo {
        int count;
        Set<String> files;

        ErrorInfo() {
            this.count = 0;
            this.files = new HashSet<>();
        }

        void addOccurrence(String filePath) {
            String fileName = new File(filePath).getName();
            this.count++;
            this.files.add(fileName);
        }
    }

    public void runTestFile(String filePath) {
        if (!containsJUnitTest(filePath)) {
            System.out.println("No tests found in file: " + filePath + ". Skipping...");
            return;
        }

        String className = getClassNameFromPath(filePath);
        String fullyQualifiedName = "com.example." + className;
        ExecutorService executor = Executors.newSingleThreadExecutor();

        try {
            Future<MyTestExecutionListener> future = executor.submit(() -> {
                File manifestFile = new File("test_manifest.mf");
                Manifest manifest = new Manifest();
                Attributes attributes = manifest.getMainAttributes();
                attributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");
                attributes.putValue("Main-Class", fullyQualifiedName);

                try (FileOutputStream fos = new FileOutputStream(manifestFile)) {
                    manifest.write(fos);
                }

                LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                        .selectors(selectClass(fullyQualifiedName))
                        .build();
                MyTestExecutionListener listener = new MyTestExecutionListener();

                Launcher launcher = LauncherFactory.create();
                launcher.registerTestExecutionListeners(listener);

                try {
                    launcher.execute(request);
                } catch (org.junit.platform.commons.JUnitException e) {
                    System.out.println("Test class not found or failed to load: " + fullyQualifiedName);
                }

                return listener;
            });

            try {
                MyTestExecutionListener listener = future.get(2, TimeUnit.MINUTES);
                printTestResults(filePath, listener, false, null);
                updateErrorStats(filePath, listener.getFailureDetails());
                results.add(new TestResult(
                        new File(filePath).getName(),
                        listener.getTotalTests(),
                        listener.getPassedTests(),
                        listener.getFailedTests(),
                        listener.getFailureDetails()
                ));
            } catch (TimeoutException e) {
                String timeoutMessage = "Test execution timed out after 2 minutes";
                System.out.println("Test in file " + filePath + " timed out after 2 minutes. Skipping...");
                future.cancel(true);

                printTestResults(filePath, new MyTestExecutionListener(), true, timeoutMessage);
                errorTypeStats.computeIfAbsent("TEST_TIMEOUT", k -> new ErrorInfo()).addOccurrence(filePath);
                results.add(new TestResult(
                        new File(filePath).getName(),
                        1,
                        timeoutMessage
                ));
            } catch (Exception e) {
                System.out.println("Error executing tests in file " + filePath + ": " + e.getMessage());
                future.cancel(true);
            }
        } finally {
            executor.shutdownNow();
        }
    }

    private void printTestResults(String filePath, MyTestExecutionListener listener, boolean timedOut, String timeoutMessage) {
        System.out.println("======================================");
        System.out.println("Running tests in: " + filePath);
        if (timedOut) {
            System.out.println("TEST TIMEOUT: " + timeoutMessage);
        } else {
            System.out.println("Total Tests Run: " + listener.getTotalTests());
            System.out.println("Total Tests Passed: " + listener.getPassedTests());
            System.out.println("Total Tests Failed: " + listener.getFailedTests());
            if (!listener.getFailureDetails().isEmpty()) {
                System.out.println("Failures:");
                for (FailureDetail failure : listener.getFailureDetails()) {
                    System.out.println("  Test: " + failure.testName);
                    System.out.println("  Reason: " + failure.reason);
                }
            }
        }
        System.out.println("======================================");
    }

    private void updateErrorStats(String filePath, List<FailureDetail> failures) {
        Set<String> uniqueErrorTypes = new HashSet<>();
        for (FailureDetail failure : failures) {
            uniqueErrorTypes.add(failure.reason);
        }
        for (String errorType : uniqueErrorTypes) {
            errorTypeStats.computeIfAbsent(errorType, k -> new ErrorInfo()).addOccurrence(filePath);
        }
    }

    private boolean containsJUnitTest(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                if (line.contains("@Test")) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file " + filePath + ": " + e.getMessage());
        }
        return false;
    }

    public void saveResults(String filename) {
        Map<String, Object> output = new HashMap<>();

        int totalTestsRun = results.stream()
                .mapToInt(result -> result.totalTests)
                .sum();

        int totalTestsPassed = results.stream()
                .mapToInt(result -> result.passedTests)
                .sum();

        long fullyPassedFiles = results.stream()
                .filter(result -> {
                    if (result.timedOut) {
                        return false;
                    }
                    return result.totalTests > 0 && result.failedTests == 0;
                })
                .count();

        output.put("totalTestsRun", totalTestsRun);
        output.put("totalTestsPassed", totalTestsPassed);
        output.put("fullyPassedFiles", fullyPassedFiles);
        output.put("errorTypeStats", errorTypeStats);
        output.put("results", results);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(output, writer);
        } catch (IOException e) {
            System.err.println("Error saving results to file: " + e.getMessage());
        }
    }

    private String getClassNameFromPath(String filePath) {
        String fileName = new File(filePath).getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex != -1) {
            fileName = fileName.substring(0, dotIndex);
        }
        return fileName;
    }

    public static void main(String[] args) {
        JSONTestRunner runner = new JSONTestRunner();
        String path = "";

        try {
            Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .filter(file -> file.getFileName().toString().startsWith("test_")
                            && file.getFileName().toString().endsWith(".java"))
                    .forEach(file -> runner.runTestFile(file.toString()));

            runner.saveResults("test_results.json");
        } catch (IOException e) {
            System.err.println("Error walking through files: " + e.getMessage());
        }
    }

    private static class MyTestExecutionListener implements TestExecutionListener {
        private final AtomicInteger totalTests = new AtomicInteger();
        private final AtomicInteger passedTests = new AtomicInteger();
        private final AtomicInteger failedTests = new AtomicInteger();
        private final List<FailureDetail> failureDetails = new ArrayList<>();

        @Override
        public void executionStarted(TestIdentifier testIdentifier) {
            if (testIdentifier.isTest()) {
                totalTests.incrementAndGet();
            }
        }

        @Override
        public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
            if (testIdentifier.isTest()) {
                if (testExecutionResult.getStatus() == TestExecutionResult.Status.SUCCESSFUL) {
                    passedTests.incrementAndGet();
                } else {
                    failedTests.incrementAndGet();
                    failureDetails.add(new FailureDetail(
                            testIdentifier.getDisplayName(),
                            testExecutionResult.getThrowable().map(Throwable::toString).orElse("Unknown error")
                    ));
                }
            }
        }

        public int getTotalTests() {
            return totalTests.get();
        }

        public int getPassedTests() {
            return passedTests.get();
        }

        public int getFailedTests() {
            return failedTests.get();
        }

        public List<FailureDetail> getFailureDetails() {
            return failureDetails;
        }
    }
}