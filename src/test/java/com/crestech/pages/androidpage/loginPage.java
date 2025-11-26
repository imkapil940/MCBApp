package com.crestech.pages.androidpage;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import java.util.logging.Logger; 
import com.crestech.appium.utils.CommonAppiumTest;
import com.crestech.common.utilities.HandleException;
import com.crestech.common.utilities.GestureUtils;
import com.crestech.common.utilities.WaitUtils;

public class loginPage extends CommonAppiumTest {

	private static final Logger log = Logger.getLogger(loginPage.class.getName());
	private AppiumDriver driver;
    private HandleException obj_handleexception;
    private GestureUtils gestutils;
    private WaitUtils waitUtils;

    public loginPage(AppiumDriver driver) throws Exception {
        super(driver);
        this.driver = driver;
        obj_handleexception = new HandleException(null, null);
        this.gestutils = new GestureUtils(driver);
        this.waitUtils = new WaitUtils(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    // -------------------- Locators --------------------

    @AndroidFindBy(xpath = "//*[@class='android.widget.EditText']")
    private WebElement username;

    @AndroidFindBy(xpath = "//android.widget.Button[@resource-id='eu.afse.omnia.mcb.curacao:id/login_button']")
    private WebElement continueButton;

    @AndroidFindBy(xpath = "//*[@text='Password']")
    private WebElement password;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text and contains(@resource-id, 'otp')]")
    private WebElement ecodeElement;

    @AndroidFindBy(xpath = "//*[@text='e-Code']")
    private WebElement ecode;

    @AndroidFindBy(xpath = "//android.widget.Button[@text='LOGIN']")
    private WebElement loginButton;

    @AndroidFindBy(xpath = "//*[@text='Register Device']")
    private WebElement registerText;

    @AndroidFindBy(xpath = "//*[@text='CONTINUE']")
    private WebElement registerContinue;

    @AndroidFindBy(xpath = "//*[@text='Allow']")
    private WebElement allowButton;

    @AndroidFindBy(xpath = "//*[@text='Enter PIN']")
    private WebElement enterPin;

    @AndroidFindBy(xpath = "//*[@text='Repeat PIN']")
    private WebElement repeatPin;

    @AndroidFindBy(xpath = "//*[@text='Confirm Transaction']")
    private WebElement confirmTransaction;

    // -------------------- Getter Methods --------------------
    public WebElement getUsernameField() { return username; }
    public WebElement getContinueButton() { return continueButton; }
    public WebElement getPasswordField() { return password; }
    public WebElement getEcodeElement() { return ecodeElement; }
    public WebElement getEcodeField() { return ecode; }
    public WebElement getLoginButton() { return loginButton; }
    public WebElement getRegisterText() { return registerText; }
    public WebElement getRegisterContinue() { return registerContinue; }
    public WebElement getAllowButton() { return allowButton; }
    public WebElement getEnterPin() { return enterPin; }
    public WebElement getRepeatPin() { return repeatPin; }
    public WebElement getConfirmTransaction() { return confirmTransaction; }

    // -------------------- Actions --------------------

    @Step("Enter username: {0}")
    public void enterUsername(String user) throws Exception {
        try {
            log.info("Waiting for username field to be visible...");
            waitUtils.fluentWaitForElement(username);
            log.info("Username field found, clicking and entering text...");
            username.click();
            Thread.sleep(1000); // Small delay after click
            username.clear(); // Clear any existing text
            username.sendKeys(user);
            log.info("✅ Username entered successfully: " + user);
            Thread.sleep(1000); // Small delay after entering text
        } catch (Exception e) {
            log.severe("Failed to enter username: " + e.getMessage());
            throw new Exception("Could not enter username: " + e.getMessage(), e);
        }
    }

    @Step("Enter password: {0}")
    public void enterPassword(String pass) throws Exception {
        try {
            log.info("Waiting for password field to be visible...");
            
            // Try to find password field with multiple strategies
            WebElement passwordField = null;
            int attempts = 0;
            int maxAttempts = 10;
            
            while (passwordField == null && attempts < maxAttempts) {
                try {
                    // First try the PageFactory element
                    waitUtils.fluentWaitForElement(password);
                    passwordField = password;
                    log.info("Password field found using PageFactory locator");
                    break;
                } catch (Exception e1) {
                    attempts++;
                    log.info("Attempt " + attempts + ": Password field not found yet, trying alternative locator...");
                    
                    // Try alternative locator directly
                    try {
                        passwordField = driver.findElement(By.xpath("//*[@text='Password' or contains(@text,'Password')]"));
                        if (passwordField != null && passwordField.isDisplayed()) {
                            log.info("Password field found using direct xpath locator");
                            break;
                        }
                    } catch (Exception e2) {
                        log.info("Alternative locator also failed, waiting and retrying...");
                    }
                    
                    Thread.sleep(1000); // Wait 1 second before retrying
                }
            }
            
            if (passwordField == null) {
                throw new Exception("Password field not found after " + maxAttempts + " attempts");
            }
            
            log.info("Password field found, clicking and entering text...");
            passwordField.click();
            Thread.sleep(1000); // Small delay after click
            passwordField.clear(); // Clear any existing text
            passwordField.sendKeys(pass);
            log.info("✅ Password entered successfully");
            Thread.sleep(1000); // Small delay after entering text
        } catch (Exception e) {
            log.severe("Failed to enter password: " + e.getMessage());
            throw new Exception("Could not enter password. Password field not found or not accessible: " + e.getMessage(), e);
        }
    }

    @Step("Click Continue button")
    public void clickContinue() throws Exception {
        try {
            log.info("Waiting for Continue button to be clickable...");
            waitUtils.waitForElementToBeClickable(continueButton);
            log.info("Continue button found, clicking...");
            continueButton.click();
            log.info("✅ Continue button clicked");
            // Wait for password screen to appear after clicking Continue
            log.info("Waiting for password screen to load (5 seconds)...");
            Thread.sleep(5000); // Give app more time to navigate to password screen
            
            // Verify we're on password screen by waiting for password field to appear
            int retryCount = 0;
            while (retryCount < 10) {
                try {
                    WebElement pwdField = driver.findElement(By.xpath("//*[@text='Password' or contains(@text,'Password')]"));
                    if (pwdField != null && pwdField.isDisplayed()) {
                        log.info("✅ Password screen loaded successfully");
                        break;
                    }
                } catch (Exception e) {
                    retryCount++;
                    if (retryCount >= 10) {
                        log.warning("Password field not visible yet after Continue click, but proceeding anyway...");
                    } else {
                        Thread.sleep(1000); // Wait 1 second before retrying
                    }
                }
            }
        } catch (Exception e) {
            log.severe("Failed to click Continue button: " + e.getMessage());
            throw new Exception("Could not click Continue button: " + e.getMessage(), e);
        }
    }

    @Step("Click Login button")
    public void clickLogin() {
        loginButton.click();
    }

    @Step("Fetch e-code from Entrust app")
    public String fetchEcodeFromEntrust() throws Exception {
        Activity entrustActivity = new Activity("com.entrust.identityGuard.mobile",
                "com.entrust.identityGuard.mobile.MainActivity");
        ((AndroidDriver) driver).startActivity(entrustActivity);

        Thread.sleep(5000);
        new Actions(driver).sendKeys("2024").perform();
        return ecodeElement.getText();
    }

    @Step("Switch back to MCB app")
    public void switchToMCBApp() {
        ((AndroidDriver) driver).activateApp("eu.afse.omnia.mcb.curacao");
    }

    @Step("Complete registration process")
    public void completeRegistration(String pin) throws InterruptedException {
        registerContinue.click();
        allowButton.click();
        enterPin.click();
        new Actions(driver).sendKeys(pin).perform();
        repeatPin.click();
        new Actions(driver).sendKeys(pin).perform();
        ((AndroidDriver) driver).pressKey(new io.appium.java_client.android.nativekey.KeyEvent(io.appium.java_client.android.nativekey.AndroidKey.ENTER));
    }
    
    public boolean isInvalidEcodePopupDisplayed() {
        try {
            WebElement popup = driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'invalid') or contains(@text,'incorrect')]"));
            return popup.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickPopupOk() {
        try {
            WebElement okButton = driver.findElement(By.xpath("//android.widget.Button[@text='OK' or @text='Ok']"));
            okButton.click();
            log.info("Clicked on OK button of invalid eCode popup");
        } catch (Exception e) {
            log.warning("OK button not found in popup: " + e.getMessage());
        }
    }

}
