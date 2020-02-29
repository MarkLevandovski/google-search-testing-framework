package com.google.steps;

import com.google.po.MainPage;
import com.google.po.ResultsPage;
import com.google.utils.BrowserFactory;
import com.google.utils.SeleniumUtils;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.logging.LogEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class GoogleSteps {
    private final static Logger LOGGER = LoggerFactory.getLogger(GoogleSteps.class);

    private WebDriver driver;
    private MainPage mainPage;
    private ResultsPage resultsPage;

    @Before
    public void setUp() {
        driver = BrowserFactory.getBrowser("Chrome");
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                String testName = scenario.getName();
                scenario.embed(screenshot, "image/png");
                scenario.write(testName);
            } catch (WebDriverException wde) {
                LOGGER.error(wde.getMessage());
            } catch (ClassCastException cce) {
                cce.printStackTrace();
            }
        }

        driver.quit();
    }

    @Given("^I open Google search page$")
    public void iOpenGoogle() {
        LOGGER.info("Opening Google Search");
        driver.get("http://google.co.uk");
        mainPage = new MainPage(driver);
        Assert.assertEquals("Google", driver.getTitle());
        Assert.assertTrue(driver.findElement(By.xpath("//input[@aria-label='Search']")).isDisplayed());
        List<LogEntry> request = SeleniumUtils.getBrowserLogs(driver, "POST");
        Assert.assertNotNull(request.stream().filter(item -> item.getMessage().contains("requestWillBeSent")).findFirst());
    }

    @When("^I search for (.*)$")
    public void iSearchFor(String searchItem) {
        resultsPage = mainPage.searchForItem(searchItem);
    }

    @Then("(.*) should appear on the first page$")
    public void shouldBeOnTheFirstPage(String expectedUrl) {
        Optional<Integer> pageNumber = resultsPage.getResultHitPageNumber(expectedUrl);
        Assert.assertTrue(pageNumber.isPresent());
        Assert.assertEquals(1, pageNumber.get().intValue());
    }
}