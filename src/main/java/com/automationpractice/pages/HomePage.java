package com.automationpractice.pages;

import com.automationpractice.DriverBase;
import com.automationpractice.helpers.CustomExpectedConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class HomePage extends AbstractPage{

    private final WebDriverWait wait;
    private final RemoteWebDriver driver;
    private HomeElements webElements;
    private TopMenu topMenu;
    private boolean productSelected;

    public HomePage() throws Exception {
        driver = DriverBase.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15), Duration.ofMillis(100));
        topMenu = new TopMenu();
        this.webElements = new HomeElements();
    }

    public void goToDress(String menuitem) throws Exception {
        topMenu.gotoDress(menuitem);
    }

    public boolean checkProductList(){
        List<WebElement> products = driver.findElements(By.cssSelector(webElements.PRODUCT_LIST));
        int productListSize= products.size();
        if(productListSize==0)
            return false;
        else
            return true;
    }


    public ProductDetailsPage clickOneProductDetails(String productCategory) throws Exception {
        String productAvailableNow;
        String productName;
        productSelected = false;
        waitForElement(By.cssSelector(webElements.PRODUCT_LIST));
        List<WebElement> products = driver.findElements(By.cssSelector(webElements.PRODUCT_LIST));
        for (WebElement result : products) {
            productAvailableNow = result.findElement(By.className(webElements.PRODUCT_AVAILABLE_NAME)).getText();
            productName = result.findElement(By.className(webElements.PRODUCT_NAME_NAME)).getText();
            if (productAvailableNow.equals("In stock") && productName.contains(productCategory)) {
                click(result);
                productSelected = true;
                break;
            }
        }
        return new ProductDetailsPage();
    }

    public void clickSearchButton() throws Exception {
         click(driver.findElement(By.name(webElements.SEARCH_SUBMIT_NAME)));
    }

    public void goToLogOut(){

    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void waitForPageTitleToStartWith(String someText) {
        wait.until(CustomExpectedConditions.pageTitleStartsWith(someText));
    }

    public void goToHome(){

    }

    public void goToContactUs(){
    }

    public void goToSignIn(){
    }

    public void goToCart(){
    }

    public void setSearchValue(String searchValue) {
        enterTextInto(driver.findElement(By.cssSelector(webElements.SEARCH_TEXT)), searchValue);
    }
    public boolean isProductSelected() {
        return productSelected;
    }

}
