package com.automationpractice.pages;


import com.automationpractice.DriverBase;
import com.automationpractice.model.Cart;
import com.automationpractice.model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ConfirmationCardPage extends AbstractPage {
    private ConfirmationCardElements webElements;
    private final WebDriverWait wait;
    private final RemoteWebDriver driver;

    public ConfirmationCardPage() throws Exception {
        driver = DriverBase.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15), Duration.ofMillis(100));
        this.webElements = new ConfirmationCardElements();
    }


    //region Web Elements
    private By ConfirmationMessage() { return By.cssSelector(webElements.CONFIRMATION_MESSAGE); }
    private By NameLabel() { return By.cssSelector(webElements.NAME_LABEL); }
    private By ColorAndSizeLabel() { return By.cssSelector(webElements.COLOR_AND_SIZE_LABEL); }
    private By QuantityLabel() { return By.cssSelector(webElements.QUANTITY_LABEL); }
    private By TotalCurrentPriceLabel() { return By.cssSelector(webElements.TOTAL_CURRENT_PRICE_LABEL); }
    private By ProductImage() { return By.cssSelector(webElements.PRODUCT_IMAGE); }
    private By CartTotalProductsPriceLabel() { return By.cssSelector(webElements.CART_TOTAL_PRODUCTS_PRICE_LABEL); }
    private By ShippingLabel() { return By.cssSelector(webElements.SHIPPING_LABEL); }
    private By CartTotalPriceLabel() { return By.cssSelector(webElements.CART_TOTAL_PRICE_LABEL); }
    private By ProceedToCheckoutButton() { return By.cssSelector(webElements.PROCEED_TO_CHECKOUT_BUTTON); }
    //endregion

    //region Actions
    public ConfirmationCardPage validateConfirmation(){
        waitForElement(ConfirmationMessage());
        assertEquals(ConfirmationMessage(), driver.findElement(ConfirmationMessage()).getText(),
                "Product successfully added to your shopping cart");
        return this;
    }
    public ConfirmationCardPage validateProductInformation(Product product){
        String[] colorSize = driver.findElement(ColorAndSizeLabel()).getText().split(", ");

        assertEquals(NameLabel(), driver.findElement(NameLabel()).getText().toUpperCase(),
                product.getName().toUpperCase());
        assertEquals(ColorAndSizeLabel(), colorSize[0], product.getColor());
        assertEquals(ColorAndSizeLabel(), colorSize[1], String.valueOf(product.getSize()));
        assertEquals(QuantityLabel(), driver.findElement(QuantityLabel()).getText(),
                String.valueOf(product.getQuantity()));
        assertEquals(ProductImage(), driver.findElement(ProductImage()).getAttribute("src"),
                product.getImageURL().replace("large", "home"));
        assertEquals(TotalCurrentPriceLabel(), driver.findElement(TotalCurrentPriceLabel()).getText()
                .replace("$", ""), String.format("%.02f", product.getTotalPrice())
                .replaceAll(",","."));

        return this;
    }
    public ConfirmationCardPage validateCartInformation(Cart cart){
        cart.setShipping(Float.parseFloat(driver.findElement(ShippingLabel()).getText().replace("$",
                "")));
        assertEquals(CartTotalProductsPriceLabel(), driver.findElement(CartTotalProductsPriceLabel()).getText()
                .replace("$", ""), String.format("%.02f", cart.getCartTotal())
                .replaceAll(",","."));
        assertEquals(ShippingLabel(), driver.findElement(ShippingLabel()).getText().replace("$", ""),
                String.format("%.02f", cart.getShipping()).replaceAll(",","."));
        assertEquals(CartTotalPriceLabel(), driver.findElement(CartTotalPriceLabel()).getText()
                .replace("$", ""), String.format("%.02f", cart.getCartTotal() + cart.getShipping())
                .replaceAll(",","."));

        return this;
    }
    public CartSummaryPage clickProceedToCheckoutButton() throws Exception {
        click(ProceedToCheckoutButton());
        return new CartSummaryPage();
    }
    //endregion
}