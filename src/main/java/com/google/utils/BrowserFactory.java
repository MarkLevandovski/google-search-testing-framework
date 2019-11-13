package com.google.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
        }

        return driver;
    }
}
