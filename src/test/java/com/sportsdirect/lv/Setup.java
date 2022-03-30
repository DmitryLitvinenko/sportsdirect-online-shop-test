package com.sportsdirect.lv;

import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.google.common.io.Files;
import io.qameta.allure.Allure;
import lombok.extern.java.Log;
import org.junit.jupiter.api.extension.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Log
public class Setup implements TestWatcher, AfterAllCallback, BeforeEachCallback, AfterEachCallback {
    private final List<TestResults> testResults = new ArrayList<>();

    private enum TestResults {
        SUCCESSFUL, ABORTED, FAILED, DISABLED
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        if (extensionContext.getExecutionException().isPresent()) takeScreenshot();
        WebDriverRunner.getWebDriver().quit();
        log.info("driver closed");
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        log.info("starting test: " + extensionContext.getDisplayName());
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        Map<TestResults, Long> summary = testResults.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        log.info("test name: " + extensionContext.getDisplayName() + "\n Summary: " + summary.toString());
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        log.info("test name: " + context.getDisplayName() + " with reason: " +
                reason.orElse("No reason"));
        testResults.add(TestResults.DISABLED);
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        log.info("TEST SUCCESSFUL");
        testResults.add(TestResults.SUCCESSFUL);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        log.info("TEST ABORTED");
        testResults.add(TestResults.ABORTED);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        log.warning("TEST: " + context.getTestMethod() + " - FAILED");
        testResults.add(TestResults.FAILED);
    }

    private void takeScreenshot() {
        try {
            String finalName = format("%s-%s", "failedScenario", UUID.randomUUID());
            Selenide.screenshot(finalName);
            File lastScreenshot = Screenshots.getLastScreenshot();
            Allure.addAttachment(finalName, new ByteArrayInputStream(Files.toByteArray(lastScreenshot)));
        } catch (IOException e) {
            log.warning("Failed to attach screenshot to Allure");
        }
    }
}
