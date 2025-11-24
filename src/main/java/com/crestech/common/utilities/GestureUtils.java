package com.crestech.common.utilities;

import io.appium.java_client.AppiumDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.HashMap;
import java.util.Map;

public class GestureUtils {

    private AppiumDriver driver;
    private WaitUtils waitUtils;
    private HandleException handleException;

    public GestureUtils(AppiumDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        this.handleException = new HandleException(driver);  // pass driver for better logging
    }
    /**
     * Performs tap on an element
     */
    public void tapElement(WebElement element) throws Exception {
        try {
            waitUtils.fluentWaitForElement(element);
            Map<String, Object> params = new HashMap<>();
            params.put("elementId", ((RemoteWebElement) element).getId());
            ((JavascriptExecutor) driver).executeScript("mobile: clickGesture", params);
        } catch (Exception e) {
            handleException.handleException(e, "Error while tapping element");
        }
    }

    /**
     * Performs long press gesture
     */
    public void longPress(WebElement element, int durationInSeconds) throws Exception {
        try {
            waitUtils.fluentWaitForElement(element);
            Map<String, Object> params = new HashMap<>();
            params.put("elementId", ((RemoteWebElement) element).getId());
            params.put("duration", durationInSeconds * 1000);
            ((JavascriptExecutor) driver).executeScript("mobile: longClickGesture", params);
        } catch (Exception e) {
            handleException.handleException(e, "Error while performing long press");
        }
    }

    /**
     * Swipe from one element to another
     */
    public void swipe(WebElement startElement, WebElement endElement) throws Exception {
        try {
            waitUtils.fluentWaitForElement(startElement);
            waitUtils.fluentWaitForElement(endElement);

            Map<String, Object> params = new HashMap<>();
            params.put("elementId", ((RemoteWebElement) startElement).getId());
            params.put("endElementId", ((RemoteWebElement) endElement).getId());
            ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", params);
        } catch (Exception e) {
            handleException.handleException(e, "Error while swiping");
        }
    }

    /**
     * Scroll to element until visible
     */
    public void scrollToElement(WebElement element) throws Exception {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("elementId", ((RemoteWebElement) element).getId());
            params.put("direction", "down");
            params.put("percent", 0.8);
            ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", params);
        } catch (Exception e) {
            handleException.handleException(e, "Error while scrolling to element");
        }
    }

    /**
     * Swipe by coordinates (generic)
     */
    public void swipeByCoordinates(int startX, int startY, int endX, int endY, int durationMillis) throws Exception {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("startX", startX);
            params.put("startY", startY);
            params.put("endX", endX);
            params.put("endY", endY);
            params.put("speed", durationMillis);
            ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", params);
        } catch (Exception e) {
            handleException.handleException(e, "Error while swiping by coordinates");
        }
    }

    /**
     * Scroll down to find an object by attribute and value
     * @param attributeType The attribute type to search for (e.g., "text", "content-desc", "resource-id")
     * @param value The value to match
     * @param options Additional options (can be null)
     * @throws Exception
     */
    public void scrollDOWNtoObject(String attributeType, String value, Object options) throws Exception {
        try {
            int maxScrolls = 10; // Maximum number of scroll attempts
            int scrollCount = 0;
            WebElement element = null;
            
            // Build the XPath based on attribute type
            String xpath;
            if ("text".equalsIgnoreCase(attributeType)) {
                xpath = "//*[@text='" + value + "']";
            } else if ("content-desc".equalsIgnoreCase(attributeType) || "contentDesc".equalsIgnoreCase(attributeType)) {
                xpath = "//*[@content-desc='" + value + "']";
            } else if ("resource-id".equalsIgnoreCase(attributeType) || "resourceId".equalsIgnoreCase(attributeType)) {
                xpath = "//*[@resource-id='" + value + "']";
            } else {
                xpath = "//*[@" + attributeType + "='" + value + "']";
            }
            
            // Get screen dimensions for scrolling
            org.openqa.selenium.Dimension windowSize = driver.manage().window().getSize();
            int centerX = windowSize.width / 2;
            int startY = (int) (windowSize.height * 0.7); // Start from 70% down the screen
            int endY = (int) (windowSize.height * 0.3); // Scroll to 30% of screen height
            
            // Try to find the element, scrolling if necessary
            while (scrollCount < maxScrolls) {
                try {
                    element = driver.findElement(By.xpath(xpath));
                    if (element != null && element.isDisplayed()) {
                        // Element found and visible, scroll to it to ensure it's in view
                        Map<String, Object> params = new HashMap<>();
                        params.put("elementId", ((RemoteWebElement) element).getId());
                        params.put("direction", "down");
                        params.put("percent", 0.5);
                        ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", params);
                        return;
                    }
                } catch (Exception e) {
                    // Element not found, scroll down and try again
                }
                
                // Scroll down using swipe gesture
                Map<String, Object> swipeParams = new HashMap<>();
                swipeParams.put("startX", centerX);
                swipeParams.put("startY", startY);
                swipeParams.put("endX", centerX);
                swipeParams.put("endY", endY);
                swipeParams.put("speed", 1000);
                ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", swipeParams);
                
                scrollCount++;
                Thread.sleep(500); // Small delay between scrolls
            }
            
            // If we reach here, element was not found after max scrolls
            throw new Exception("Element with " + attributeType + "='" + value + "' not found after scrolling");
        } catch (Exception e) {
            handleException.handleException(e, "Error while scrolling down to object with " + attributeType + "='" + value + "'");
        }
    }
}
