package com.automationpractice.pages;

import com.automationpractice.DriverBase;
import com.automationpractice.model.Cart;
import com.automationpractice.model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartSummaryPage extends AbstractPage {
    private CartSummaryElements webElements;

    private final WebDriverWait wait;
    private final RemoteWebDriver driver;

    public CartSummaryPage() throws Exception {
        driver = DriverBase.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15), Duration.ofMillis(100));
        webElements = new CartSummaryElements();
    }

    //region Web Elements
    private By ProductImage() { return By.cssSelector(webElements.PRODUCT_IMAGE); }
    private By NameLabel() { return By.cssSelector(webElements.NAME_LABEL); }
    private By SkuLabel() { return By.cssSelector(webElements.SKU_LABEL); }
    private By ColorAndSizeLabel() { return By.cssSelector(webElements.COLOR_AND_SIZE_LABEL); }
    private By QuantityLabel() { return By.cssSelector(webElements.QUANTITY_LABEL); }
    private By QuantityInput() { return By.cssSelector(webElements.QUANTITY_LABEL + " " + webElements.QUANTITY_INPUT); }
    private By ItemTotalPriceLabel() { return By.cssSelector(webElements.ITEM_TOTAL_PRICE_LABEL); }
    private By TotalPriceProductsLabel() { return By.cssSelector(webElements.TOTAL_PRICE_PRODUCTS_LABEL); }
    private By TotalShippingLabel() { return By.cssSelector(webElements.TOTAL_SHIPPING_LABEL); }
    private By TotalPriceWithoutTaxLabel() { return By.cssSelector(webElements.TOTAL_PRICE_WITHOUT_TAX_LABEL); }
    private By TotalTaxLabel() { return By.cssSelector(webElements.TOTAL_TAX_LABEL); }
    private By TotalFinalPrice() { return By.cssSelector(webElements.TOTAL_FINAL_PRICE); }
    private By ProceedToCheckoutButton() { return By.cssSelector(webElements.PROCEED_TO_CHECKOUT_BUTTON); }
    private By ProductInfoBox() { return By.cssSelector(webElements.PRODUCT_INFO_BOX); }
    //endregion

    //region Actions
    public CartSummaryPage validateProductInformation(Cart cart){
        waitForElement(ProductImage());
        for(int i = 0; i < cart.getProducts().size(); i++){
            String[] colorSize = driver.findElements(ColorAndSizeLabel()).get(i).getText().split(", ");
            assertTrue(ProductInfoBox(), cartContainsAddedItems(cart.getProducts(),
                    new Product(
                            driver.findElements(NameLabel()).get(i).getText(),
                            driver.findElements(SkuLabel()).get(i).getText().split(": ")[1],
                            isPresent(QuantityInput()) ?
                                    Integer.parseInt(driver.findElements(QuantityInput()).get(i).getAttribute("value"))
                                    : Integer.parseInt(driver.findElements(QuantityLabel()).get(i).getText()),
                            colorSize[1].split(": ")[1].charAt(0),
                            colorSize[0].split(": ")[1],
                            driver.findElements(ProductImage()).get(i).getAttribute("src"),
                            Float.parseFloat(driver.findElements(ItemTotalPriceLabel()).get(i).getText()
                                    .replace("$",""))
                    )
            ));
        }
        return this;
    }
    public CartSummaryPage validateCartInformationWithTax(Cart cart){
        float totalWithoutTax = cart.getShipping() + cart.getCartTotal();
        float tax = Float.parseFloat(driver.findElement(TotalTaxLabel()).getText().replace("$",""));

        validateShippingAndProductsTotal(cart);

        assertEquals(TotalPriceWithoutTaxLabel(), driver.findElement(TotalPriceWithoutTaxLabel()).getText()
                .replace("$",""), String.format("%.02f", totalWithoutTax)
                .replaceAll(",","."));
        assertEquals(TotalFinalPrice(), driver.findElement(TotalFinalPrice()).getText()
                .replace("$",""), String.format("%.02f", totalWithoutTax + tax)
                .replaceAll(",","."));

        return this;
    }
    public CartSummaryPage validateCartInformationWithoutTax(Cart cart){
        validateShippingAndProductsTotal(cart);
        assertEquals(TotalFinalPrice(), driver.findElement(TotalFinalPrice()).getText()
                .replace("$",""), String.format("%.02f", cart.getCartTotal() + cart.getShipping())
                .replaceAll(",","."));
        return this;
    }
    public LoginPage clickProceedToCheckoutButton() throws Exception {
        click(ProceedToCheckoutButton());
        return new LoginPage();
    }
    //endregion

    //region Helpers
    private boolean cartContainsAddedItems(List<Product> addedProducts, Product cartProduct){
        return addedProducts.stream().anyMatch(o ->
                o.getImageURL().replace("large", "small").equals(cartProduct.getImageURL()) &&
                        o.getName().toUpperCase().equals(cartProduct.getName().toUpperCase()) &&
                        o.getSku().equals(cartProduct.getSku()) && o.getColor().equals(cartProduct.getColor()) &&
                        o.getSize() == cartProduct.getSize() && o.getQuantity() == cartProduct.getQuantity() &&
                        o.getTotalPrice() == cartProduct.getTotalPrice());
    }
    private void validateShippingAndProductsTotal(Cart cart){
        assertEquals(TotalPriceProductsLabel(), driver.findElement(TotalPriceProductsLabel()).getText()
                .replace("$",""), String.format("%.02f", cart.getCartTotal())
                .replaceAll(",","."));
        assertEquals(TotalShippingLabel(), driver.findElement(TotalShippingLabel()).getText()
                .replace("$",""), String.format("%.02f", cart.getShipping())
                .replaceAll(",","."));
    }
    //endregion
}