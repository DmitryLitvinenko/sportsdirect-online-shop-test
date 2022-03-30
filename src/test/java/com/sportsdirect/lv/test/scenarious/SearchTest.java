package com.sportsdirect.lv.test.scenarious;

import com.sportsdirect.lv.Setup;
import com.sportsdirect.lv.objects.components.TopBarComponent;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.inject.Inject;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.sportsdirect.lv.utils.ConfigurationProvider.*;
import static com.sportsdirect.lv.utils.ConfigurationProvider.getMensCategoryPath;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(GuiceExtension.class)
@ExtendWith(Setup.class)
@DisplayName("Search tests")
@Severity(SeverityLevel.CRITICAL)
@Tags(value = {@Tag("all"), @Tag("search")})
class SearchTest extends TestHelper {
    @Inject private TopBarComponent topBar;
    @Inject private SearchResultsPage searchResultsPage;
    @Inject private ProductPage productPage;

    @Test
    @DisplayName("Search not existing products")
    @Description("After search, should appears OOPS due to not found products")
    void searchNotExistingProductTest() {
        openPage();
        topBar.searchFor("non existing product");
        searchResultsPage.checkErrorMessage("Ooops..");
    }

    @Test
    @DisplayName("Search for specific products")
    @Description("Searching nike shoes, should appear list of products")
    void searchForSpecificProductsTest() {
        openPage(getMensCategoryPath());
        topBar.searchFor("Nike Shoes");
        searchResultsPage.getProducts().shouldBe(sizeGreaterThan(0));
    }

    @Test
    @DisplayName("Search for specific product")
    @Description("Searching specific product, should appear at the product page")
    void searchForSpecificProductTest() {
        final String productName = "Regatta Pulton Jkt Ld23";
        openPage(getMensCategoryPath());
        topBar.searchFor(productName);
        assertThat(productPage.getProductName()).isEqualTo(productName);
    }

    @ParameterizedTest(name = "Checking redirection (after searching: {1}) to category: {0}")
    @MethodSource("searchArgs")
    @DisplayName("Search categories")
    @Description("In case of correct category search, user should be redirected to the correct page")
    void searchCategoriesTest(final String path, final String searchInput) {
        openPage();
        topBar.searchFor(searchInput);
        checkUrl(getHomePageUrl() + path);
    }

    private static Stream<Arguments> searchArgs() {
        return Stream.of(
                Arguments.of(getKidsCategoryPath(), "kids"),
                Arguments.of(getUscCategoryPath(), "usc"),
                Arguments.of(getMensCategoryPath(), "mens"),
                Arguments.of(getWomensCategoryPath(), "womens"),
                Arguments.of(getOutletCategoryPath(), "outlet")); //this test will fail, maybe bug or feature, however good example for allure report
    }

}
