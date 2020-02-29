package com.google.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class SeleniumUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(SeleniumUtils.class);

    /**
     * Method that waits for specified time with polling
     *
     * @param driver
     * @return wait
     */
    public static Wait<WebDriver> defaultWait(WebDriver driver) {

        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class)
                .ignoring(IndexOutOfBoundsException.class);
    }

    public static List<LogEntry> getBrowserLogs(WebDriver driver, String messageText) {
        List<LogEntry> entries = driver.manage().logs().get(LogType.PERFORMANCE).getAll();
        LOGGER.info(entries.size() + " " + LogType.PERFORMANCE + " log entries found");

        List<LogEntry> requestWillBeSentExtraInfo = entries.stream().filter(item -> item.getMessage().contains(messageText))
                .collect(Collectors.toList());
        for (LogEntry entry : requestWillBeSentExtraInfo) {
            LOGGER.info(entry.getMessage());
        }

        return requestWillBeSentExtraInfo;
    }
}
