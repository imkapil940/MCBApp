package com.crestech.common.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import com.crestech.appium.utils.CommonAppiumTest;

/**
 * Android Alert Handling Utility
 * Author: Shafkat Ali, Divya Devi
 * Updated by: Kapil Sharma (for compilation fixes and consistency)
 */
public class AndroidAlert {

	private AppiumDriver driver;
    private CommonAppiumTest commonAppTest;
    private GestureUtils gestUtils;
    private HandleException obj_handleexception;
    private HandleException handleException;
    private WaitUtils waitUtils;

    public AndroidAlert(AppiumDriver driver2) {
    	this.driver = driver2;
        this.commonAppTest = new CommonAppiumTest(driver2);
        this.gestUtils = new GestureUtils(driver2);
        this.obj_handleexception = new HandleException(driver2);  // ✅ Pass driver
        this.handleException = new HandleException(driver2);
        this.waitUtils = new WaitUtils(driver2);
    }
    @Step("Handle Android alert or toast")
    public void handleAlert() throws Exception {
        try {
            @SuppressWarnings("rawtypes")
            CommonAlertElements obj = new CommonAlertElements(driver);
            List<WebElement> elementList = obj.getToastMessageElementList();

            if (elementList != null && !elementList.isEmpty()) {
                WebElement element = (WebElement) elementList.get(0);
                String toastMessage = element.getText();
                System.out.println("Toast Message: " + toastMessage);
                // handle based on toast text or any other condition
            } else {
                System.out.println("No toast message found.");
            }

        } catch (Exception e) {
            handleException.handleException(e, "Failed to handle Android alert");
            throw e;
        }
    }

    public String ToastMessage() throws Exception {
        @SuppressWarnings("rawtypes")
        CommonAlertElements obj = new CommonAlertElements(driver);
        WebElement element = obj.getToastMessageElement();
        return element.getAttribute("name");
    }

    public List<String> MultiToastMessage() throws Exception {
        @SuppressWarnings("rawtypes")
        CommonAlertElements obj = new CommonAlertElements(driver);
        List<WebElement> elementList = obj.getToastMessageElementList();

        List<String> toastMessages = new ArrayList<>();
        for (WebElement e : elementList) {
            toastMessages.add(e.getAttribute("name"));
        }
        return toastMessages;
    }

    public void PermissionAlertToggleList(int index) throws Exception {
        @SuppressWarnings("rawtypes")
        CommonAlertElements obj = new CommonAlertElements(driver);
        List<WebElement> elementList = obj.toggleList();
        commonAppTest.clickOnElement(elementList.get(index));
    }

    public void AlertHandlingWithButtonTiltleMessage(
            WebElement button, String expectedMessage, String expectedTitle) throws Exception {

        @SuppressWarnings("rawtypes")
        CommonAlertElements obj = new CommonAlertElements(driver);
        WebElement titleElement = obj.getalertTitle();
        WebElement messageElement = obj.getalertMessage();
        String actualTitle = commonAppTest.getTexOfElement(titleElement);
        String actualMessage = commonAppTest.getTexOfElement(messageElement);

        if (actualTitle.equalsIgnoreCase(expectedTitle) &&
            actualMessage.equalsIgnoreCase(expectedMessage)) {
            commonAppTest.clickOnElement(button);
        }

        Assert.assertEquals(actualTitle, expectedTitle, "Title Not matching");
        Assert.assertEquals(actualMessage, expectedMessage, "Alert Message Not matching");
    }

    public void AlertHandlingWithButtonMessage(
            WebElement button, String expectedMessage, WebElement messageElement) throws Exception {
        try {
            String actualMessage = commonAppTest.getTexOfElement(messageElement);
            Assert.assertEquals(actualMessage, expectedMessage, "Message Not matching");

            if (actualMessage.equalsIgnoreCase(expectedMessage)) {
                commonAppTest.clickOnElement(button);
            }
        } catch (HandleException e) {
            obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION", "Failed in Alert Handling", e);
        } catch (Exception e) {
            obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", "Failed in Alert Handling", e);
        }
    }

    @Step("Quitting the application for relaunching")
    public void relanchAlertWithButtonTiltleMessage(
            WebElement button, String expectedMessage, String expectedTitle) throws Exception {

        @SuppressWarnings("rawtypes")
        CommonAlertElements obj = new CommonAlertElements(driver);
        WebElement titleElement = obj.getTitle();
        WebElement messageElement = obj.getMessage();
        String actualTitle = commonAppTest.getTexOfElement(titleElement);
        String actualMessage = commonAppTest.getTexOfElement(messageElement);

        if (actualTitle.equalsIgnoreCase(expectedTitle) &&
            actualMessage.equalsIgnoreCase(expectedMessage)) {
            commonAppTest.clickOnElement(button);
        }

        Assert.assertEquals(actualTitle, expectedTitle, "Title Not matching");
        Assert.assertEquals(actualMessage, expectedMessage, "Alert Message Not matching");
    }

    @Step("Accepting Alert Message")
    public void AlertHandlingWithButtonMessageContainsCase(
            WebElement button, String expectedMessage) throws Exception {

        @SuppressWarnings("rawtypes")
        CommonAlertElements obj = new CommonAlertElements(driver);
        WebElement messageElement = obj.getalertMessage();
        String actualMessage = commonAppTest.getTexOfElement(messageElement);

        if (actualMessage.toLowerCase().contains(expectedMessage.toLowerCase())) {
            commonAppTest.clickOnElement(button);
        }

        Assert.assertTrue(actualMessage.toLowerCase().contains(expectedMessage.toLowerCase()),
                "Alert Message does not contain expected string");
    }

    public void AlertHandlingWithButtonTiltle(WebElement button, String expectedTitle) throws Exception {
        @SuppressWarnings("rawtypes")
        CommonAlertElements obj = new CommonAlertElements(driver);
        WebElement titleElement = obj.getalertTitle();
        String actualTitle = commonAppTest.getTexOfElement(titleElement);

        if (actualTitle.equalsIgnoreCase(expectedTitle)) {
            commonAppTest.clickOnElement(button);
        }

        Assert.assertEquals(actualTitle, expectedTitle, "Title Not matching");
    }

    public void notRespondingAlertWithButtonTiltle(WebElement button, String expectedTitle) throws Exception {
        @SuppressWarnings("rawtypes")
        CommonAlertElements obj = new CommonAlertElements(driver);
        WebElement titleElement = obj.getNotRespondingTitle();
        String actualTitle = commonAppTest.getTexOfElement(titleElement);

        if (actualTitle.equalsIgnoreCase(expectedTitle)) {
            commonAppTest.clickOnElement(button);
        }

        Assert.assertEquals(actualTitle, expectedTitle, "Title Not matching");
    }

    public void permissionAlertWithButtonMessage(WebElement button, String expectedMessage) throws Exception {
        @SuppressWarnings("rawtypes")
        CommonAlertElements obj = new CommonAlertElements(driver);
        WebElement messageElement = obj.getPermissionMessage();
        String actualMessage = commonAppTest.getTexOfElement(messageElement);

        if (actualMessage.equalsIgnoreCase(expectedMessage)) {
            commonAppTest.clickOnElement(button);
        }

        Assert.assertEquals(actualMessage, expectedMessage, "Alert Message Not matching");
    }

    public String getAlertTitle(WebElement title) {
        return title.getText();
    }

    public String getAlertMessage(WebElement message) {
        return message.getText();
    }

    public void ClickOnButtonAlert(WebElement button) throws Exception {
        commonAppTest.clickOnElement(button);
    }

    public void getContextWebORNative() {
        try {
            // In Appium 8.x, getContextHandles() via executeScript
            @SuppressWarnings("rawtypes")
            Set<String> contextNames = (Set<String>) ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("mobile: getContexts");
            for (String contextName : contextNames) {
                System.out.println(contextName);
            }
        } catch (Exception e) {
            System.out.println("Error getting context handles: " + e.getMessage());
        }
    }

    public void switchContext(String appType) {
        try {
            // In Appium 8.x, context() via executeScript
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("mobile: setContext", Map.of("name", appType));
        } catch (Exception e) {
            System.out.println("Error switching context: " + e.getMessage());
        }
    }

    @Step("Handling First Popup After Login")
    public void handlingFirstPopupAfterLogin(String expectedMessage) throws Exception {
        @SuppressWarnings("rawtypes")
        CommonAlertElements obj = new CommonAlertElements(driver);
        WebElement headerElement = obj.headerMessage();
        String actualMessage = commonAppTest.getTexOfElement(headerElement);

        if (actualMessage.equalsIgnoreCase(expectedMessage)) {
            Dimension windowSize = driver.manage().window().getSize();
            int y = (int) (windowSize.getHeight() - 10);
            int x = (int) (windowSize.getWidth() / 2);
            WebElement swipeButtonElement = obj.swipeButton();
            // Use swipeByCoordinates with element's location
            org.openqa.selenium.Point elementLocation = swipeButtonElement.getLocation();
            int startX = elementLocation.getX();
            int startY = elementLocation.getY();
            gestUtils.swipeByCoordinates(startX, startY, x, y, 500);
        }

        Assert.assertEquals(actualMessage, expectedMessage, "Alert Message Not matching");
    }

    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean isIOSAlertPresent() {
        try {
            driver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='digibank Alert']"));
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean isIOSOfflineAlertPresent() {
        try {
            driver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='You seem to be offline']"));
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean isDigitalTokenMainAlertPresent() {
        try {
            driver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='Digital token under maintenance']"));
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
