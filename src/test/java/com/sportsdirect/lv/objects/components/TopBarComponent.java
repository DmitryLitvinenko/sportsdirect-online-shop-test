package com.sportsdirect.lv.objects.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Getter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class TopBarComponent {
    @Getter public final SelenideElement search = $("#txtSearch").as("search input field");
    @Getter public final SelenideElement bag = $("#bagQuantityContainer").as("bag");
    @Getter public final ElementsCollection categories = $$x("//*[@class='lnkLevel2']").as("categories in top bar");

    @Step("Searching for {input}")
    public void searchFor(final String input) {
        search.shouldBe(visible).sendKeys(input);
        search.pressEnter();
    }

    @Step("Open bag")
    public void openBag() {
        bag.scrollIntoView("{block:'center'}").shouldBe(visible).click();
    }
}
