package com.crestech.common.utilities;

import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.crestech.appium.utils.CommandPrompt;

import io.appium.java_client.AppiumDriver;

public class WaitUtils extends CommandPrompt {

    private AppiumDriver driver;
    private final int WAIT_TIME = 5;

    public WaitUtils(AppiumDriver driver) {
        this.driver = driver;
    }

    /**
     * Waits for the page to load completely.
     */
    public void waitForPageLoad() throws Exception {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            for (int i = 0; i < WAIT_TIME * 2; i++) {
                String state = js.executeScript("return document.readyState").toString();
                if ("complete".equals(state))
                    return;
                Thread.sleep(500);
            }
            throw new Exception("Page did not load completely within " + WAIT_TIME + " seconds");
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Waits for an element to become invisible.
     */
    public void waitForElementInvisibility(WebElement element) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME));
            wait.until(ExpectedConditions.invisibilityOf(element));
        } catch (Exception e) {
            throw e;
        }
    }
    /**
     * Reusable FluentWait for any ExpectedCondition
     */
    public void fluentWaitForCondition(ExpectedCondition<?> condition) {
        new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(WAIT_TIME))
            .pollingEvery(Duration.ofMillis(1000))
            .ignoring(NoSuchElementException.class)
            .ignoring(StaleElementReferenceException.class)
            .until(condition);
    }

    /**
     * Waits for a specific element to be visible using FluentWait
     */
    public void fluentWaitForElement(WebElement element) throws Exception {
        try {
            fluentWaitForCondition(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            throw e;
        }
    }
   


    /**
     * Waits for an element to be visible.
     */
    public void waitForElementVisibility(WebElement element) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME));
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Waits for an element to be clickable.
     */
    public void waitForElementToBeClickable(WebElement element) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME));
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Waits for an element to be clickable and returns boolean.
     */
    public boolean waitForElementToBeClickable2(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Sets page load timeout.
     */
    public void waitForPageLoadTimeout() throws Exception {
        try {
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(WAIT_TIME));
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Sets implicit wait for the driver.
     */
    public void implicitlyWait(int timeInSeconds) throws Exception {
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeInSeconds));
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * Sets implicit wait for the driver (capitalized version for compatibility).
     */
    public void ImplicitlyWait(int timeInSeconds) throws Exception {
        implicitlyWait(timeInSeconds);
    }

    /**
     * Waits for page to load within given time.
     */
    public void waitForPageLoadedWithTime(int timeInSeconds) {
        ExpectedCondition<Boolean> expectation = driver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState").toString().equals("complete");

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeInSeconds));
            wait.until(expectation);
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
    }

    /**
     * Waits until given text is present in element.
     */
    public void waitForTextToBePresent(WebElement element, String text) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME));
            wait.until(ExpectedConditions.textToBePresentInElement(element, text));
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Waits until title contains given text.
     */
    public void waitForGivenTitle(String title) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME));
            wait.until(ExpectedConditions.titleContains(title));
        } catch (Exception e) {
            throw e;
        }
    }
}
