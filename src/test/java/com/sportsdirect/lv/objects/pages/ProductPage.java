package com.sportsdirect.lv.objects.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Getter;

import java.math.BigInteger;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.sportsdirect.lv.helpers.PriceHelper.stringToBigInt;

public class ProductPage {
    @Getter private final SelenideElement addToBagBtn = $("#aAddToBag").as("add to bag button");
    @Getter private final SelenideElement productSizeWarning = $(".popover-content").as("product size warning");
    @Getter private final ElementsCollection productSize = $$("#liItem").as("product size");
    private final SelenideElement productPrice = $("#lblSellingPrice").as("product price");
    private final SelenideElement productName = $("#lblProductName").as("product name");
    private final SelenideElement productBrand = $("#lblProductBrand").as("product brand");


    @Step("Pick first available product size")
    public ProductPage pickFirstAvailableSize() {
        for (SelenideElement size : productSize) {
            if (size.getAttribute("title").startsWith("Click to select")) {
                size.scrollIntoView("{block:'center'}").click();
                return this;
            }
        }
        throw new RuntimeException("There are no available sizes");
    }

    @Step("Get product price from the product page")
    public BigInteger getProductPrice() {
        return stringToBigInt(productPrice.getText());
    }

    @Step("Get product name from the produt page")
    public String getProductName() {
        String productBrandName = productBrand.shouldBe(visible).getText().toLowerCase();
        String productNaming = productName.shouldBe(visible).getText();
        return productBrandName.substring(0,1).toUpperCase() + productBrandName.substring(1) + " " + productNaming;
    }

    @Step("Add product to the bag")
    public void addItemToBag() {
        addToBagBtn.scrollIntoView("{block:'center'}").shouldBe(visible).click();
        sleep(1000); //sometimes sports direct respond very slow
    }

}
