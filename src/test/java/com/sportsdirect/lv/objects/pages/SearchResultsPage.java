package com.sportsdirect.lv.objects.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Getter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class SearchResultsPage {
    @Getter private final SelenideElement searchErrorMessage = $(".searchHeadertitle")
            .as("search error message");
    @Getter private final ElementsCollection products = $$(".s-productthumbbox").as("item body");

    @Step("Checking error message in case of non existing item")
    public void checkErrorMessage(final String input) {
        getSearchErrorMessage().shouldHave(text(input));
    }

    @Step("Click first product at the search result page")
    public void clickFirstProduct() {
        products.get(0).shouldBe(visible).click();
    }
}
