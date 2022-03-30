package com.sportsdirect.lv.test.scenarious;

import com.sportsdirect.lv.Setup;
import com.sportsdirect.lv.objects.components.TopBarComponent;
import com.sportsdirect.lv.objects.pages.BagPage;
import com.sportsdirect.lv.objects.pages.ProductPage;
import com.sportsdirect.lv.objects.pages.SearchResultsPage;
import com.sportsdirect.lv.test.TestHelper;
import com.sportsdirect.lv.utils.guice.GuiceExtension;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;
import java.math.BigInteger;

import static com.codeborne.selenide.Condition.exactText;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(GuiceExtension.class)
@ExtendWith(Setup.class)
@DisplayName("Bag tests")
@Severity(SeverityLevel.CRITICAL)
@Tags(value = {@Tag("all"), @Tag("bag")})
class BagTest extends TestHelper {
    @Inject private TopBarComponent topBar;
    @Inject private SearchResultsPage searchResultsPage;
    @Inject private ProductPage productPage;
    @Inject private BagPage bagPage;

    @Test
    @DisplayName("Adding product to the bag")
    @Description("Check that correct product added to the bag")
    void addItemToBagTest() {
        openPage();
        topBar.searchFor("shoes");
        searchResultsPage.clickFirstProduct();

        final BigInteger productPrice = productPage.getProductPrice();
        final String productName = productPage.getProductName();
        productPage.pickFirstAvailableSize().addItemToBag();

        topBar.openBag();
        final BigInteger bagProductTotalPrice = bagPage.getProductTotalPrice();
        final String bagProductName = bagPage.getProductName();

        assertAll(
                () -> assertThat(productPrice).isEqualTo(bagProductTotalPrice),
                () -> assertThat(productName).isEqualTo(bagProductName));
    }

    @Test
    @DisplayName("Adding product to the bag")
    @Description("Check that customer can't continue w/o picking size")
    void addItemToBagWihthoutSizeTest() {
        openPage();
        topBar.searchFor("shoes");

        searchResultsPage.clickFirstProduct();
        productPage.addItemToBag();

        productPage.getProductSizeWarning().shouldBe(exactText("Please select a size"));
    }
}
