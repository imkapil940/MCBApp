package com.crestech.pages.androidpage;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import com.crestech.annotation.values.ElementDescription;
import com.crestech.appium.utils.CommonAppiumTest;
import com.crestech.common.utilities.CommonTestData;
import com.crestech.common.utilities.HandleException;
import org.openqa.selenium.WebElement;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.qameta.allure.Step;

/**
 * @author Divya
 *
 */
public class launchPage extends CommonAppiumTest{
	
	@SuppressWarnings("rawtypes")
	public AppiumDriver driver = null;
	HandleException obj_handleexception = null;
	//preloginPage preloginpg=null;
	
	@SuppressWarnings("rawtypes")
	public launchPage(AppiumDriver driver) throws Exception {
		super(driver);
		try {
			this.driver = driver;
			obj_handleexception = new HandleException(null, null);
			//preloginpg =new preloginPage(driver);
			PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(5)), this);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	//object
	@ElementDescription(value = "Quit Button.")
	@AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.Button")
	private WebElement quitBtn;
	
	@ElementDescription(value = "PreLogin Button")
	@AndroidFindBy(xpath = "//android.widget.Button[@text='PRE LOGIN']")
	private WebElement PreLoginBtn;
	
	@ElementDescription(value = "More Button")
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='More']")
	private WebElement MoreButton;
	
	@ElementDescription(value = "LOG IN Button")
	@AndroidFindBy(xpath = "//android.widget.Button[@text='LOG IN']")
	private WebElement loginButton;

	@ElementDescription(value = "User ID EditTexT")
	@AndroidFindBy(xpath = "//android.widget.EditText[contains(@resource-id,'id/edit_user_id')]")
	private WebElement userIdEditText;
	
	
	@ElementDescription(value = "Log In Button prelogin page")
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Log In']")
	private WebElement LogInButton_preloginpage;
	
	@ElementDescription(value = "Error Messge Element")
	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='android:id/message']")
	private WebElement ErrorMessgeElement;
	
	@ElementDescription(value = "DigibankAlertHeaderElement")
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='digibank Alert']")
	private WebElement DigibankAlertHeaderElement;
	
	@ElementDescription(value = "DigitalTokenUnderMaintenanceMessage")
	@AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id,':id/login_kill_switch_error_message')]")
	private WebElement DigitalTokenUnderMaintenanceMessage;
	
	@ElementDescription(value = "DigitalTokenUnderMaintenanceMessageHeader")
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Digital token under maintenance']")
	private WebElement DigitalTokenUnderMaintenanceMessageHeader;

	@ElementDescription(value = "Progress Bar")
	@AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id,':id/progress_bar_message')]")
	private WebElement progress_bar;
	
	@ElementDescription(value = "Progress Bar")
	@AndroidFindBy(xpath = "//android.widget.ImageView[contains(@resource-id,':id/progress_bar')]")
	private WebElement progress_bar_imageview;
	
	@ElementDescription(value = "Authenticating Bar")
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Authenticating...']")
	private WebElement Authenticating_Bar;
	
	@ElementDescription(value = "AlertTitle")
	@AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id,':id/tv_alert_server_title')]")
	private WebElement AlertTitle;
	
	@ElementDescription(value = "AlertBodyMessage")
	@AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id,':id/tv_alert_body')]")
	private WebElement AlertBodyMessage;
	
	public WebElement AlertTitle(){
		return AlertTitle;
	}
	public WebElement AlertBodyMessage(){
		return AlertBodyMessage;
	}
	
	
	public WebElement DigitalTokenUnderMaintenanceMessage(){
		return DigitalTokenUnderMaintenanceMessage;
	}
	public WebElement DigitalTokenUnderMaintenanceMessageHeader(){
		return DigitalTokenUnderMaintenanceMessageHeader;
	}
	public WebElement Authenticating_Bar() { 
		return Authenticating_Bar;
	}
	
	public WebElement progress_bar_imageview() { 
		return progress_bar_imageview;
	}
	
	public WebElement progress_bar() { 
		return progress_bar;
	}
	
	public WebElement DigibankAlertHeaderElement() { 
		return DigibankAlertHeaderElement;
	}
	
	public WebElement ErrorMessgeElement() { 
		return ErrorMessgeElement;
	}
	
	public WebElement quitBtn() { 
		return quitBtn;
	}
	public WebElement loginButton() { 
		return loginButton;
	}

	

	
	
	@Step("Clicked on Pre-Login button")
	public void ClickOnPreloginButton() throws Exception {
		try {
			if(wait.waitForElementToBeClickable2(PreLoginBtn))
			{
				com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
			int count = 0;
			do {
				clickOnElement(PreLoginBtn);
				count++;
			} while (!isElementVisible2(MoreButton) && count < 3);
			}
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION", " Failed to Click On Prelogin Button ", e);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Click On Prelogin Button ", e);
		}
	}
	
	@Step("Clicked on Login button")
	public void clickOnLoginButton() throws Exception {
		try {
			if(wait.waitForElementToBeClickable2(loginButton))
			{
				com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
			int count = 0;
			do {
				clickOnElement(loginButton);
				count++;
			} while (!isElementVisible2(userIdEditText) && count < 3);
			}
			else if (wait.waitForElementToBeClickable2(LogInButton_preloginpage))
			{
				com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
				clickOnElement(LogInButton_preloginpage);
			}
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION", " Failed to Click On Login Button ", e);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Click On Login Button ", e);
		}
	}

}
