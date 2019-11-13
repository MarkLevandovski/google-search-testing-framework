package com.google.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.NoSuchElementException;

public class SeleniumUtils {
    /**
     * Method that waits for specified time with polling
     *
     * @param driver
     * @return wait
     */
    public static Wait defaultWait(WebDriver driver) {

        return new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class)
                .ignoring(IndexOutOfBoundsException.class);
    }
}
