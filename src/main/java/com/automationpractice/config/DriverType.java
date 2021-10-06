package com.automationpractice.config;

import com.automationpractice.utilities.Constant;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;


import java.util.HashMap;

public enum DriverType implements DriverSetup {

    FIREFOX {
        public RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities,String operatingSystem) {
            String geckoDriverDirectory;
            FirefoxOptions options = new FirefoxOptions();
            if (operatingSystem.contains("WINDOWS")) {
                //options.setHeadless(HEADLESS);
                geckoDriverDirectory = Constant.GECKO_DRIVER_DIRECTORY_WINDOWS;
            } else {
                options.addArguments("--headless");
                geckoDriverDirectory = Constant.GECKO_DRIVER_DIRECTORY_LINUX;
            }
            System.setProperty("webdriver.gecko.driver", geckoDriverDirectory);
            return new FirefoxDriver(options);
        }
    },
    CHROME {
        public RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities,String operatingSystem) {
            String chromeDriverDirectory;
            HashMap<String, Object> chromePreferences = new HashMap<>();
            chromePreferences.put("profile.password_manager_enabled", false);

            ChromeOptions options = new ChromeOptions();
            options.merge(capabilities);
            //options.setHeadless(HEADLESS);
            options.addArguments("--no-default-browser-check");
            options.setExperimentalOption("prefs", chromePreferences);

            if (operatingSystem.contains("WINDOWS")) {
                chromeDriverDirectory = Constant.CHROME_DRIVER_DIRECTORY_WINDOWS;
            } else {
                chromeDriverDirectory = Constant.CHROME_DRIVER_DIRECTORY_LINUX;
            }

            System.setProperty("webdriver.chrome.driver", chromeDriverDirectory);
            return new ChromeDriver(options);
        }
    },
    IE {
        public RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities,String operatingSystem) {
            InternetExplorerOptions options = new InternetExplorerOptions();
            options.merge(capabilities);
            options.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
            options.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
            options.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);

            return new InternetExplorerDriver(options);
        }

        @Override
        public String toString() {
            return "internet explorer";
        }
    },
    EDGE {
        public RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities,String operatingSystem) {
            EdgeOptions options = new EdgeOptions();
            options.merge(capabilities);

            return new EdgeDriver(options);
        }
    },
    SAFARI {
        public RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities,String operatingSystem) {
            SafariOptions options = new SafariOptions();
            options.merge(capabilities);

            return new SafariDriver(options);
        }
    },
    OPERA {
        public RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities,String operatingSystem) {
            OperaOptions options = new OperaOptions();
            options.merge(capabilities);

            return new OperaDriver(options);
        }
    },
    BRAVE {
        public RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities,String operatingSystem) {
            HashMap<String, Object> chromePreferences = new HashMap<>();
            chromePreferences.put("profile.password_manager_enabled", false);

            ChromeOptions options = new ChromeOptions();
            options.setBinary(getBraveBinaryLocation());
            options.merge(capabilities);
            options.setHeadless(HEADLESS);
            options.addArguments("--no-default-browser-check");

            return new ChromeDriver(options);
        }
    };

    String getBraveBinaryLocation() {
        String defaultBraveLocation = "C:\\Program Files (x86)\\BraveSoftware\\Brave-Browser\\Application\\brave.exe";
        String currentOperatingSystemName = System.getProperties().getProperty("os.name");
        if (currentOperatingSystemName.toLowerCase().contains("mac")) {
            defaultBraveLocation = "/Applications/Brave Browser.app/Contents/MacOS/Brave Browser";
        }
        if (currentOperatingSystemName.toLowerCase().contains("linux")) {
            defaultBraveLocation = "/usr/bin/brave-browser";
        }

        return System.getProperty("braveBinaryLocation", defaultBraveLocation);
    }

    public final static boolean HEADLESS = Boolean.getBoolean("headless");

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

}
