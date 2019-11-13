package com.google.po;

import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.google.utils.SeleniumUtils.defaultWait;

/**
 * Class represents page with search results
 */
public class ResultsPage extends MainPage {
    private final static Logger LOGGER = LoggerFactory.getLogger(ResultsPage.class);

    ResultsPage(WebDriver driver) {
        super(driver);
    }

    private By resultsList = By.cssSelector(".rc > .r > a > div > cite");
    private By resultsStats = By.id("resultStats");
    private By resultPageNumber = By.cssSelector("td.cur");
    private By nextButton = By.id("pnnext");

    /**
     * Method that returns all searched urls
     *
     * @return map of URLs with their placement number on the results list
     */
    private Map<Integer, String> getResultsElements() {
        defaultWait(driver).until(ExpectedConditions.visibilityOf(driver.findElement(resultsStats)));
        defaultWait(driver).until(ExpectedConditions.numberOfElementsToBeMoreThan(resultsList, 0));
        List<WebElement> resultList = driver.findElements(resultsList);
        Map<Integer, String> mapOfResults = new HashMap<>();

        for (int i = 0; i < resultList.size(); i++) {
            mapOfResults.put(i, resultList.get(i).getText());
        }

        LOGGER.info("Results list: " + mapOfResults.values());

        return mapOfResults;
    }

    /**
     * Method that clicks on Next button
     *
     * @return ResultPage
     */
    private ResultsPage goToNextResultsPage() {
        defaultWait(driver).until(ExpectedConditions.visibilityOf(driver.findElement(nextButton)));
        driver.findElement(nextButton).click();

        return this;
    }

    /**
     * Method that returns number of result the page that is currently opened
     *
     * @return page number
     */
    private Integer getPageNumber() {
        defaultWait(driver).until(ExpectedConditions.visibilityOf(driver.findElement(resultPageNumber)));

        return Integer.valueOf(driver.findElement(resultPageNumber).getText());
    }

    /**
     * Method that returns result page number where given item was found
     * It will search till result will match, it is set to search for 5 minutes
     *
     * @param expectedUrl
     * @return number of the page
     */
    public Optional<Integer> getResultHitPageNumber(String expectedUrl) {
        List<Map.Entry<Integer, String>> filteredResults;
        List<Integer> resultNumber = new ArrayList<>();
        Integer pageNumber = null;

        for (long stop = System.nanoTime() + TimeUnit.MINUTES.toNanos(5); stop > System.nanoTime(); ) {
            Map<Integer, String> resultList = getResultsElements();

            filteredResults = resultList.entrySet().stream()
                    .filter(item -> item.getValue().contains(expectedUrl))
                    .collect(Collectors.toList());

            if (filteredResults.size() == 0) {
                LOGGER.info("URL is not displayed on this page.");
                try {
                    goToNextResultsPage();
                } catch (NotFoundException e) {
                    LOGGER.info("This was the last available result");
                    break;
                }

            } else {
                LOGGER.info("Filtered Results: " + filteredResults);
                for (int i = 0; i < filteredResults.size(); i++) {
                    resultNumber.add(filteredResults.get(i).getKey() + 1);
                }
                pageNumber = getPageNumber();
                LOGGER.info("Result found on the <" + resultNumber + "> positions at the <" + pageNumber + "> results page.");
                break;
            }
        }

        return Optional.ofNullable(pageNumber);
    }
}