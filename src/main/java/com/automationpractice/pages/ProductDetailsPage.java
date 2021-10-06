package com.automationpractice.pages;

import com.automationpractice.DriverBase;
import com.automationpractice.model.Product;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import com.automationpractice.model.Cart;
import com.automationpractice.utilities.RandomValues;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductDetailsPage extends AbstractPage {
    private ProductDetailsElements webElements;
    private Product product;
    public static Cart cart;
    private final RemoteWebDriver driver;

    public ProductDetailsPage() throws Exception {
        driver = DriverBase.getDriver();
    }

    //region Web Elements
    private By NameLabel() { return By.cssSelector(webElements.NAME_LABEL); }
    private By SkuLabel() { return By.cssSelector(webElements.SKU_LABEL); }
    private By CurrentPriceLabel() { return By.cssSelector(webElements.CURRENT_PRICE_LABEL); }
    private By ReductionLabel() { return By.cssSelector(webElements.REDUCTION_LABEL); }
    private By OldPriceLabel() { return By.cssSelector(webElements.OLD_PRICE_LABEL); }
    private By QuantityInput() { return By.cssSelector(webElements.QUANTITY_INPUT); }
    private By SizeCombo() { return By.cssSelector(webElements.SIZE_COMBO); }
    private By SelectedSize() { return By.cssSelector(webElements.SELECTED_SIZE); }
    private By ColorList() { return By.cssSelector(webElements.COLOR_LIST); }
    private By ColorOptions() { return By.cssSelector(webElements.COLOR_OPTIONS); }
    private By ColorPicked() { return By.cssSelector(webElements.COLOR_PICKED); }
    private By ProductImage() { return By.cssSelector(webElements.PRODUCT_IMAGE); }
    private By AddToCartButton() { return By.cssSelector(webElements.ADD_TO_CART_BUTTON); }
    private By PricesBox() { return By.cssSelector(webElements.PRICES_BOX); }
    //endregion

    //region Actions
    public ProductDetailsPage enterQuantity(){
        fillString(QuantityInput(), String.valueOf(RandomValues.randomNumber(1, 5)));
        return this;
    }
    public ProductDetailsPage chooseSize() {
        scrollIntoView(SizeCombo());
        selectRandOption(SizeCombo(), false);
        return this;
    }
    public ProductDetailsPage chooseColor(){
        waitForElement(ColorList());
        List<WebElement> colors = driver.findElement(ColorList()).findElements(ColorOptions());
        int index = RandomValues.randomNumber(0, colors.size() - 1);
        click(colors.get(index));
        waitForAttributeToBe(colors.get(index), "class", "selected");
        return this;
    }
    public ProductDetailsPage saveProductInfo(){
        int reduction = 0;
        float oldPrice = 0;
        String currentPrice = driver.findElement(CurrentPriceLabel()).getText().replace("$", "");

        if(isDisplayed(ReductionLabel())){
            reduction = Integer.parseInt(driver.findElement(ReductionLabel()).getText().substring(1)
                    .replace("%", ""));
            oldPrice = Float.parseFloat(driver.findElement(OldPriceLabel()).getText().replace("$", ""));
            validateReductionOnPrice(reduction, oldPrice, currentPrice);
        }

        product = new Product(
                driver.findElement(NameLabel()).getText(),
                driver.findElement(SkuLabel()).getText(),
                Float.parseFloat(currentPrice),
                reduction,
                oldPrice,
                Integer.parseInt(driver.findElement(QuantityInput()).getAttribute("value")),
                driver.findElement(SelectedSize()).getText().charAt(0),
                driver.findElement(ColorList()).findElement(ColorPicked()).getAttribute("name"),
                driver.findElement(ProductImage()).getAttribute("src"),
                0f
        );
        product.setTotalPrice(product.getQuantity() * product.getCurrentPrice());

        return this;
    }
    public ProductDetailsPage addToCart(){
        updateCart();
        click(AddToCartButton());
        return this;
    }
    //endregion

    //region Helpers
    private void validateReductionOnPrice(int reduction, float oldPrice, String currentPrice){
        float expected = oldPrice - (oldPrice * reduction / 100);
        assertEquals(PricesBox(), String.format("%.02f", expected).replaceAll(",","."),
                currentPrice.replaceAll(",","."));
    }
    private void updateCart(){
        List<Product> products;
        if(cart == null){
            products = new ArrayList<>();
            cart = new Cart(products);
        }else{
            products = cart.getProducts();
        }
        cart.setCartTotal(cart.getCartTotal() + product.getTotalPrice());
        products.add(product);
        cart.setProducts(products);
    }
    //endregion
}