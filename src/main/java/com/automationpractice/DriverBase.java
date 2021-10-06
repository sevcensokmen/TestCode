package com.automationpractice;

import com.automationpractice.pages.AbstractPage;
import com.automationpractice.utilities.Constant;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.automationpractice.config.DriverFactory;
import org.picocontainer.annotations.Inject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class DriverBase {

    //protected static final Logger log = (Logger) LogManager.getLogger(DriverBase.class);7
    public static Logger log = Logger.getLogger(DriverBase.class);
    private static final List<DriverFactory> webDriverThreadPool = Collections.synchronizedList(new ArrayList<>());
    private static ThreadLocal<DriverFactory> driverFactoryThread;

    protected static String automationPractice;
    protected static Properties configFile = new Properties();
    protected static FileInputStream fis;

    public static void instantiateDriverObject(String project) {
        driverFactoryThread = ThreadLocal.withInitial(() -> {
            DriverFactory driverFactory = new DriverFactory();
            webDriverThreadPool.add(driverFactory);
            return driverFactory;
        });
        configureLogging();
        readingConfig();
        automationPractice = configFile.getProperty("automationPracticeUrl");
    }

    public static void configureLogging() {
        String log4jConfigFile = System.getProperty("user.dir") + File.separator + "src/test/resources/properties" + File.separator
                + "log4j.properties";
        PropertyConfigurator.configure(log4jConfigFile);
    }

    public static void readingConfig() {
        try {
            fis = new FileInputStream(Constant.CONFIG_PROPERTIES_DIRECTORY);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            configFile.load(fis);
            log.info("Config properties file loaded");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static RemoteWebDriver getDriver() throws Exception {
        return driverFactoryThread.get().getDriver();
    }

    public static void clearCookies() {
        try {
            driverFactoryThread.get().getStoredDriver().manage().deleteAllCookies();
        } catch (Exception ignored) {
            log.warn("Unable to clear cookies, driver object is not viable...");
        }
    }

    public static void closeDriverObjects() {
        for (DriverFactory driverFactory : webDriverThreadPool) {
            driverFactory.quitDriver();
            log.info("Quit Driver");
        }
    }
}
