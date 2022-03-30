package com.sportsdirect.lv.test;

import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.url;
import static com.sportsdirect.lv.utils.ConfigurationProvider.getHomePageUrl;

public class TestHelper {

    @Step("Opening home page with path: {path}")
    protected void openPage(String path) {
        String pathUrl = path;
        if (!path.startsWith("/")) pathUrl = "/" + path;
        open(getHomePageUrl() + pathUrl);
    }

    @Step("Opening home page")
    protected void openPage() {
        openPage("");
    }

    @Step("Checking url, should be {expectedUrl}")
    protected void checkUrl(final String expectedUrl) {
        webdriver().shouldHave(url(expectedUrl), Duration.ofSeconds(5));
    }
}
