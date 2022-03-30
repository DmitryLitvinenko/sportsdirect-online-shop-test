package com.sportsdirect.lv.objects.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.math.BigInteger;

import static com.codeborne.selenide.Selenide.$;
import static com.sportsdirect.lv.helpers.PriceHelper.stringToBigInt;

public class BagPage {
    private final SelenideElement productTotalPrice = $("td.itemtotalprice > span.money").as("bag total price");
    private final SelenideElement productName = $("#dhypProductLink").as("product name");

    @Step("Get product price from the product page")
    public BigInteger getProductTotalPrice() {
        return stringToBigInt(productTotalPrice.getText());
    }

    @Step("Get product name from the produt page")
    public String getProductName() {
        return productName.getText();
    }
}
