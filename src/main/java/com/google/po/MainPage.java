package com.google.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.utils.SeleniumUtils.defaultWait;

/**
 * Main page represents main Google Search page
 */
public class MainPage {
    private final static Logger LOGGER = LoggerFactory.getLogger(MainPage.class);

    WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    private By searchBox = By.xpath("//input[@aria-label='Search']");
    private By searchButton = By.xpath("//input[@value='Google Search']");

    /**
     * Method that searches given item
     *
     * @param item
     * @return ResultsPage
     */
    public ResultsPage searchForItem(String item) {
        LOGGER.info("Searching item: " + item);
        defaultWait(driver).until(ExpectedConditions.visibilityOf(driver.findElement(searchBox)));
        driver.findElement(searchBox).sendKeys(item);

        defaultWait(driver).until(ExpectedConditions.visibilityOf(driver.findElement(searchButton)));
        driver.findElement(searchButton).click();

        return new ResultsPage(driver);
    }
}
