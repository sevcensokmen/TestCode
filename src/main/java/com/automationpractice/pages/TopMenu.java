package com.automationpractice.pages;

import com.automationpractice.DriverBase;
import com.lazerycode.selenium.util.Query;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class TopMenu {

    RemoteWebDriver driver;
    private final WebDriverWait wait;

    public TopMenu() throws Exception {
        driver = DriverBase.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15), Duration.ofMillis(100));

    }

    public void gotoDress(String link) throws Exception {
        WebElement ele = getDressesLink();
        Actions action = new Actions(driver);
        action.moveToElement(ele).perform();
        getSummerDressesLink().click();
    }

    public WebElement getWomenLink() {
        return driver.findElement(By.xpath("//*[@id=\"block_top_menu\"]/ul/li[1]"));
    }

    public WebElement getDressesLink() {
        return driver.findElement(By.xpath("//*[@id=\"block_top_menu\"]/ul/li[2]"));
    }

    public WebElement getTShirtsLink() {
        return driver.findElement(By.xpath("//*[@id=\"block_top_menu\"]/ul/li[3]"));
    }

    public WebElement getDressesDropDown() {
        return driver.findElement(By.xpath("//*[@id=\"block_top_menu\"]/ul/li[2]/ul"));
    }

    public WebElement getDressMenuItem(String menuitem){
        return driver.findElement(By.xpath("//*[@id=\"block_top_menu\"]/ul/li[2]//a[contains(text(),"+menuitem+")]"));
    }
    public WebElement getCasualDressesLink() {
        return driver.findElement(By.xpath("//*[@id=\"block_top_menu\"]/ul/li[2]//a[contains(text(), \"Casual Dresses\")]"));
    }

    public WebElement getEveningDressesLink() {
        return driver.findElement(By.xpath("//*[@id=\"block_top_menu\"]/ul/li[2]//a[contains(text(), \"Evening Dresses\")]"));
    }

    public WebElement getSummerDressesLink() {
        return driver.findElement(By.xpath("//*[@id=\"block_top_menu\"]/ul/li[2]//a[contains(text(), \"Summer Dresses\")]"));
    }

    public WebElement getSummerDressesLink2() {
        return driver.findElement(By.xpath("//*[@id=\"block_top_menu\"]/ul/li[2]//li[3]"));
    }

}
