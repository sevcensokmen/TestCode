package com.automationpractice.pages;

import com.automationpractice.DriverBase;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage extends AbstractPage{

    private final RemoteWebDriver driver;
    private LoginElements webElements;

    public LoginPage() throws Exception {
        driver = DriverBase.getDriver();
        webElements = new LoginElements();
    }

    public void setUsername(String username) {
        fillString(By.id(webElements.EMAIL_ADDRESS_ID), username);
    }

    public void setPassword(String password) {
        fillString(By.id(webElements.PASSWORD_ID), password);
    }

    public void clickLoginSubmitButton() throws Exception {
        click(By.id(webElements.SUBMIT_BUTTON_ID));
    }
}
