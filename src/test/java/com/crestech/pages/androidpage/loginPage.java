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

public class loginPage extends CommonAppiumTest {

	private static final Logger log = Logger.getLogger(loginPage.class.getName());
	private AppiumDriver driver;
    private HandleException obj_handleexception;
    private GestureUtils gestutils;

    public loginPage(AppiumDriver driver) throws Exception {
        super(driver);
        this.driver = driver;
        obj_handleexception = new HandleException(null, null);
        this.gestutils = new GestureUtils(driver);
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
    public void enterUsername(String user) {
        username.click();
        username.sendKeys(user);
    }

    @Step("Enter password: {0}")
    public void enterPassword(String pass) {
        password.click();
        password.sendKeys(pass);
    }

    @Step("Click Continue button")
    public void clickContinue() {
        continueButton.click();
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
