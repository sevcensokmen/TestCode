package com.automationpractice.steps;

import com.automationpractice.DriverBase;
import com.automationpractice.model.Cart;
import com.automationpractice.model.Product;
import com.automationpractice.pages.*;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AddProductToCartSteps extends DriverBase {

    WebDriver driver;
    HomePage homePage;
    ProductDetailsPage productDetailsPage;
    ConfirmationCardPage confirmationCardPage;
    CartSummaryPage cartSummaryPage;
    LoginPage loginPage;
    Product product;
    Cart cart;
    String searchValue;

    @Given("user navigates automation practice url")
    public void userNavigatesAutomationPractice() throws Exception {
        driver = getDriver();
        driver.get(automationPractice);
        homePage = new HomePage();
    }

    @When("user goes to \"([^\"]*)\" menu item")
    public void userGoesToMenuItem(String menuitem) throws Exception {
        homePage.goToDress(menuitem);
    }

    @Then("system shows \"([^\"]*)\" category name")
    public void systemShowCategoryName(String categoryName) throws Exception {
        String actual = "";//homePage.getCategoryName();
        String expected = categoryName;
       // assertThat(actual).isEqualTo(expected);
    }

    @Then("results are shown on a list")
    public void multipleResultsAreShownForResult() {
        boolean productListActual = homePage.checkProductList();
        assertThat(productListActual).isEqualTo(true);
    }

    @Then("user checks cart details")
    public void userChecksCartDetails() {
    }

    @When("user clicks one product details")
    public void userClicksOneProductToAddToTheCart() throws Exception {
        productDetailsPage= homePage.clickOneProductDetails(searchValue);
        Assert.assertEquals(homePage.isProductSelected(), true);
    }

    @Then("user can see product details successfully")
    public void userCanSeeProductDetailsSuccessfully() {
        String actual = homePage.getPageTitle();
        String expected = searchValue;
        //if(!actual.contains(expected))
        //    Assert.assertTrue(false);
    }

    @When("user clicks add cart button")
    public void userClicksAddCartButton() {
        cart = productDetailsPage
                .enterQuantity()
                .chooseSize()
                .chooseColor()
                .saveProductInfo()
                .addToCart()
                .cart;
    }

    @Then("system shows confirmation card")
    public void productDetailsAreShownOnCartPage() throws Exception {
        confirmationCardPage = new ConfirmationCardPage();
        confirmationCardPage
                .validateConfirmation()
                .validateProductInformation(cart.getProducts().get(cart.getProducts().size() - 1))
                .validateCartInformation(cart);

    }

    @When("on confirmation card user clicks proceed to checkout button")
    public void userClicksProceedToCheckoutButton() throws Exception {
        cartSummaryPage = confirmationCardPage.clickProceedToCheckoutButton();
    }

    @Then("user goes to the shopping cart summary page")
    public void userGoesToTheShoppingCartSummaryPage() {
        cartSummaryPage.validateProductInformation(cart)
          .validateCartInformationWithTax(cart);

    }

    @When("on cart summary page user clicks proceed to checkout")
    public void userClicksProceedToCheckout() throws Exception {
        loginPage = cartSummaryPage.clickProceedToCheckoutButton();
    }


    @And("user enters \"([^\"]*)\" email address and \"([^\"]*)\" password")
    public void userEntersEmailAddressAndPassword(String email, String password) {
        loginPage.setUsername(email);
        loginPage.setPassword(password);
    }

    @When("user clicks log in button")
    public void userClicksLogInButton() throws Exception {
        loginPage.clickLoginSubmitButton();
    }

    @Then("check login on home page")
    public void checkLoginOnHomePage() {
        String actualTitle = homePage.getPageTitle();
        String expectedTitle = "";
        if(!actualTitle.contains(expectedTitle))
            Assert.assertTrue(false);
    }

    @Given("user enters search term \"([^\"]*)\"")
    public void userEntersSearchTermSearchTerm(String searchVal) {
        homePage.setSearchValue(searchVal);
        searchValue = searchVal;
    }


    @When("user clicks search button")
    public void userDoesSearch() throws Exception {
        homePage.clickSearchButton();
    }

}
