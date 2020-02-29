package com.google.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.logging.Level;

/**
 * Class that represents Browser Factory
 */
public class BrowserFactory {
    private final static Logger LOGGER = LoggerFactory.getLogger(BrowserFactory.class);

    /**
     * Method that initializes browser
     *
     * @param browserName
     * @return driver
     */
    public static WebDriver getBrowser(String browserName) {
        WebDriver driver = null;
        LOGGER.info("Opening browser: " + browserName);

        switch (browserName) {
            case "Chrome":
                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logPrefs = new LoggingPreferences();
                logPrefs.enable( LogType.PERFORMANCE, Level.ALL );
                options.setCapability( "goog:loggingPrefs", logPrefs );
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(options);
                break;
        }

        return driver;
    }
}
