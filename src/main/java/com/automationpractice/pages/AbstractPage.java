package com.automationpractice.pages;

import com.automationpractice.DriverBase;
import com.automationpractice.utilities.RandomValues;
import com.lazerycode.selenium.util.Query;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;
import java.util.List;
import static com.lazerycode.selenium.util.AssignDriver.initQueryObjects;

public abstract class AbstractPage {

    private static final int TIMEOUT_IN_SECONDS = 15;
    private final WebDriverWait wait;
    private final RemoteWebDriver driver;
    private final FluentWait<WebDriver> wait3;

    public AbstractPage() throws Exception {
        this.driver = DriverBase.getDriver();
        initQueryObjects(this, this.driver);
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(15), Duration.ofMillis(100));
        wait3 = new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofMillis(100))
                .ignoring(Exception.class);
    }

    protected void enterTextInto(final WebElement input, final String valueToEnter) {
        input.clear();
        input.sendKeys(valueToEnter);
    }

    protected void enterTextIntoWithJavascript(final Query input, final String valueToEnter) {
        wait.until(ExpectedConditions.presenceOfElementLocated(input.by()));
        Actions action = new Actions(driver);
        action.sendKeys(String.valueOf("p")).perform();
    }

    //  protected vod clear()
    protected void enterTextInto(final Query input, final String valueToEnter) {
        //wait.until(ExpectedConditions.presenceOfElementLocated(input.by()));
        input.findWebElement().clear();
        input.findWebElement().sendKeys(valueToEnter);
    }

   /* protected boolean isElementVisible(final String text, final String className) {
       // pause(2000);
        return new ConditionalWait(driver).isElementVisible(text, className);
    }

    protected boolean isElementVisibleId(final String text, final String id) {
       // pause(2000);
        return new ConditionalWait(driver).isElementVisibleId(text, id);
    }

    protected boolean isElementVisible(By by) {
       // pause(2000);
        return new ConditionalWait(driver).isElementVisible(by);
    }
*/
    public void waitForElement(By locator){
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public void waitForElement(WebElement element){
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForAttributeToBe(WebElement element, String attribute, String expected) {
        wait.until(ExpectedConditions.attributeContains(element, attribute, expected));
    }

    public void click(By locator) {
        waitForElement(locator);
        highlightElement(locator);
        driver.findElement(locator).click();
    }

    public void click(WebElement element){
        waitForElement(element);
        highlightElement(element);
        element.click();
    }

    public void jsClick(By locator){
        WebElement element = driver.findElement(locator);
        highlightElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void fillString(By locator, String value){
        waitForElement(locator);
        highlightElement(locator);
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(value);
    }

    public void selectRandOption(By selectLocator, boolean ignoreFirst) {
        int start = ignoreFirst ? 1 : 0;
        WebElement element = driver.findElement(selectLocator);
        highlightElement(element);
        Select select = new Select(element);
        List<WebElement> options = select.getOptions();
        select.selectByIndex(RandomValues.randomNumber(start, options.size() - 1));
    }

    public boolean isDisplayed(By locator){
        return driver.findElement(locator).isDisplayed();
    }

    public boolean isPresent(By locator){
        try{
            driver.findElement(locator);
            return true;
        }catch(NoSuchElementException e){
            return false;
        }
    }

    public void scrollIntoView(By locator) {
        WebElement element = driver.findElement(locator);
        highlightElement(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void highlightElement(By locator){
        WebElement element = driver.findElement(locator);
        highlightElement(element);
    }

    public void highlightElement(WebElement element){
        ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid red'", element);
    }

    public void assertEquals(By locator, String actual, String expected){
        WebElement element = driver.findElement(locator);
        try{
            Assert.assertEquals(actual, expected);
        }catch(AssertionError e){
            scrollIntoView(locator);
            highlightElement(element);
            throw e;
        }
    }

    public void assertTrue(By elementToHighlight, boolean condition){
        WebElement element = driver.findElement(elementToHighlight);
        try{
            Assert.assertTrue(condition);
        }catch(AssertionError e){
            scrollIntoView(elementToHighlight);
            highlightElement(element);
            throw e;
        }
    }

}