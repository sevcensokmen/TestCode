package com.automationpractice.utilities;

import java.io.File;
import java.io.IOException;

public class Constant {

    public static String PATH;
    public static String UPLOAD_TEST_FILE;

    static {
        try {
            PATH = new File(".").getCanonicalPath();
            String operatingSystem = System.getProperty("os.name").toUpperCase();
            if(operatingSystem.contains("WINDOWS")){
                UPLOAD_TEST_FILE = PATH + "\\src\\test\\resources\\data\\";
            }else{
                UPLOAD_TEST_FILE = PATH + "/src/test/resources/data/";
            }
        } catch (IOException e) {
            // Should not be here at all
            e.printStackTrace();
        }
    }

    //region 'Contact Data'
    public final static String CONFIG_PROPERTIES_DIRECTORY = PATH
            + "/src/test/resources/properties/config.properties";

    public final static String GECKO_DRIVER_DIRECTORY_LINUX = PATH
            + "/src/test/resources/executables/geckodriver";

    public final static String CHROME_DRIVER_DIRECTORY_LINUX = PATH
            + "/src/test/resources/executables/chromedriver";

    public final static String GECKO_DRIVER_DIRECTORY_WINDOWS = PATH
            + "\\src\\test\\resources\\executables\\geckodriver.exe";

    public final static String CHROME_DRIVER_DIRECTORY_WINDOWS = PATH
            + "\\src\\test\\resources\\executables\\chromedriver.exe";

    public final static String LOG4J_CONFIG_DIRECTORY = PATH
            + "\\src\\test\\resources\\properties\\log4j.properties";

    public final static String EXTENDS_FILE_DIRECTORY = PATH 
            + "/reports/";
    //endregion+

}
