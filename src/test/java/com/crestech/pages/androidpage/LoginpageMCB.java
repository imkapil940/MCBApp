package com.crestech.pages.androidpage;

import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;

import com.crestech.appium.utils.CommonAppiumTest;
import com.crestech.common.utilities.CommonTestData;
import com.crestech.common.utilities.GestureUtils;
import com.crestech.common.utilities.HandleException;
import org.openqa.selenium.WebElement;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.qameta.allure.Step;

/**
 * @author KAPIL SHARMA
 *
 */
public class LoginpageMCB extends CommonAppiumTest {

	@SuppressWarnings("rawtypes")
	public AppiumDriver driver = null;
	HandleException obj_handleexception = null;
	GestureUtils gestutils = null;

	@SuppressWarnings("rawtypes")
	public LoginpageMCB(AppiumDriver driver) throws Exception {
		super(driver);
		try {
			this.driver = driver;
			obj_handleexception = new HandleException(null, null);
			gestutils = new GestureUtils(driver);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	// Locators
	private By phnNumberLocator = By.xpath("//android.widget.EditText");
	private By continueButton = By.xpath("//android.widget.Button");
	private By SubmitButton = By.xpath("//android.widget.Button[@resource-id='com.ixigo:id/btn_update_email_id']");
	private By emailField = By.xpath("//android.widget.EditText[@resource-id='com.ixigo:id/et_email_id']");
	private By skipButton = By.xpath("//android.widget.TextView[@text='Skip']");
	private By verifyButtonLocator = By.xpath("//android.widget.TextView[@text='Verify']");
// MCB Locators
	
	private By name=By.xpath("//*[@class=\"android.widget.EditText\"]");
	private By button=By.xpath("//android.widget.Button[@resource-id=\"eu.afse.omnia.mcb.curacao:id/login_button\"]");
	private By password=By.xpath("//*[@text=\"Password\"]");
	private By unlocktoken=By.xpath("//*[@text=\"Password\"]");
	private By ecodeElement = By.xpath("//android.widget.TextView[@text and contains(@resource-id, 'otp')]");
	private By ecode=By.xpath("//*[@text=\"e-Code\"]");
	private By login=By.xpath("//android.widget.Button[@text='LOGIN']");
	
	private By registertext=By.xpath("//*[@text=\"Register Device\"]");
	private By rcontinue=By.xpath("//*[@text=\"CONTINUE\"]");
	private By allow=By.xpath("//*[@text=\"Allow\"]");
	private By enterpin=By.xpath("//*[@text=\"Enter PIN\"]");
	private By repeatpin=By.xpath("//*[@text=\"Repeat PIN\"]");
	private By confirmtranscation=By.xpath("//*[@text=\"Confirm Transaction\"]");
	
	public WebElement registertext() {
		return driver.findElement(registertext);
	}	
	public WebElement rcontinue() {
		return driver.findElement(rcontinue);
	}
	public WebElement allow() {
		return driver.findElement(allow);
	}
	public WebElement enterpin() {
		return driver.findElement(enterpin);
	}
	public WebElement repeatpin() {
		return driver.findElement(repeatpin);
	}
	public WebElement confirmtranscation() {
		return driver.findElement(confirmtranscation);
	}
	
	public WebElement getName() {
			return driver.findElement(name);
		}
	
		public WebElement getButton() {
			return driver.findElement(button);
		}

		public WebElement getpassword() {
			return driver.findElement(password);
		}
		public WebElement unlocktoken() {
			return driver.findElement(unlocktoken);
		}
		public WebElement ecodeElement() {
			return driver.findElement(ecodeElement);
		}
		public WebElement ecode() {
			return driver.findElement(ecode);
		}
		public WebElement login() {
			return driver.findElement(login);
		}
		
	public WebElement getVerifyButton() {
		return driver.findElement(verifyButtonLocator);
	}

	public WebElement skipBtnLocator() {
		return driver.findElement(skipButton);
	}

	@Step("Enter OTP for Secure Pin Authentication.")
	public void EnterOTPAndVerify() throws Exception {
		try {
			System.out.println("Enter OTP in device manually and Press \"ENTER\" on console to continue...");
			Scanner scanner = new Scanner(System.in);
			scanner.nextLine();
			ClickOnVerifyButton();
		} catch (Exception e) {
			obj_handleexception.throwException("ENTER_PASSCODE_EXCEPTION", " Failed to enter OTP  ", e);
		}
	}

	@Step("Click On Verify Button.")
	public void ClickOnVerifyButton() throws Exception {
		try {
			WebElement verifyButton = getVerifyButton();
			// com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
			clickOnElement(verifyButton);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Click On Verify Button ", e);
		}
	}

	public WebElement getEmailField() {
		return driver.findElement(emailField);
	}

	public WebElement submitBtnLocator() {
		return driver.findElement(SubmitButton);
	}

	public WebElement getPhoneNumberField() {
		return driver.findElement(phnNumberLocator);
	}

	public WebElement continueBtnLocator() {
		return driver.findElement(continueButton);
	}
	
	


	@Step("Click On Continue Button.")
	public void ClickOnContinueButton() throws Exception {
		try {
			WebElement continueBtn = continueBtnLocator();
			// com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
			clickOnElement(continueBtn);
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION", " Failed to Click On continue Button ", e);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Click On continue Button  ", e);
		}
	}

	@Step("Enter Phone Number")
	public void EnterPhoneNumber(String mobNo) throws Exception {
		try {
			WebElement mobileNumberField = getPhoneNumberField();
			// com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
			mobileNumberField.click();
			mobileNumberField.sendKeys(mobNo);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Enter Phone Number ", e);
		}
	}

	@Step("Click On Submit Button.")
	public void ClickOnSubmitButton() throws Exception {
		try {
			WebElement submitBtn = submitBtnLocator();
			// com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
			clickOnElement(submitBtn);
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION", " Failed to Click On Submit Button ", e);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Click On Submit Button  ", e);
		}
	}

	@Step("Update Email")
	public void UpdateEmail(String Email) throws Exception {
		try {
			WebElement enterEmail = getEmailField();
			// com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
			enterTextInTextbox(enterEmail, Email);
			ClickOnSubmitButton();

		} catch (HandleException e) {
			obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION", " Failed to Update Email ", e);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Update Email ", e);
		}
	}

	@Step("Enter Login Details")
	public void EnterLoginDetails(String MobNumber, String Otp) throws Exception {
		try {
			EnterPhoneNumber(MobNumber);
			ClickOnContinueButton();
			EnterOTPAndVerify();
			try {
				if (isElementVisible2(driver.findElement(emailField))) {
					UpdateEmail(CommonTestData.EMAIL.getEnumValue());
				}
			} catch (Exception e) {
				System.out.println("no email updation");
			}

		} catch (HandleException e) {
			obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION", " Failed to Enter Login Details ", e);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Enter Login Details ", e);
		}
	}

	@Step("Click On Skip Button to Skip login functionality.")
	public void ClickOnSkipButton() throws Exception {
		try {

			try {
				wait.ImplicitlyWait(5);
				if (isElementVisible2(driver.findElement(skipButton))) {
					WebElement skipBtn = skipBtnLocator();
					clickOnElement(skipBtn);
				}
				wait.ImplicitlyWait(15);
			} catch (Exception e) {
				System.out.println("Skip button is not visible to skip Login Functionality");
			}

		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Click On Skip Button  ", e);
		}
	}

	
	@Step("Relaunching Ixigo application")
	public void relaunchingIxigo() throws Exception {
		try {
			relanchApplication(CommonTestData.IXIGO_APP_PACKAGE.getEnumValue(),
					CommonTestData.IXIGO_APPS_ACTIVITY.getEnumValue());
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("RELAUNCHING_IXIGO_EXCEPTION",
					" Failed to Relaunching IXIGO Application  ", e);
		} catch (Exception e) {
			obj_handleexception.throwException("RELAUNCHING_DBS_EXCEPTION",
					" Failed to Relaunching IXIGO Application  ", e);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Step("Login MCB")
	public void enterdata() throws Exception {
		try {

			try {
				wait.ImplicitlyWait(15);
				  // -----------------------------
		        // STEP 1: Login Screen
		        // -----------------------------
		        System.out.println("Starting login flow on MCB app...");
		 

		        WebElement name = getName();
				// com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
		        name.click();
		        wait.ImplicitlyWait(20);
		        //driver.hideKeyboard();
		        name.sendKeys("pcloudy");
		        System.out.println("Text Enter");
		       // name.setValue("Hello pCloudy!");
		        wait.ImplicitlyWait(20);
		        WebElement button = getButton();
				// com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
		        button.click();
		        wait.ImplicitlyWait(20);
		        WebElement password = getpassword();
				// com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
		        password.click();
		//        driver.getKeyboard().sendKeys("Test12345");
	        password.sendKeys("Test12345");
	        
		        wait.ImplicitlyWait(3);
		        System.out.println("Password Enter");
				wait.ImplicitlyWait(5);
//				  // -----------------------------
//		        // STEP 2: Switch to Entrust for e-code
//		        // -----------------------------
//		        System.out.println("Switching to Entrust Identity app...");
//		 
//		        // Launch Entrust Identity app
//		        Activity entrustActivity = new Activity(
//		                "com.entrust.identityGuard.mobile",
//		                "com.entrust.identityGuard.mobile.MainActivity");
//		        ((AndroidDriver) driver).startActivity(entrustActivity);
//		   
//		        Thread.sleep(5000);	
//		        // Enter Entrust PIN (2024)
//		        driver.getKeyboard().sendKeys("2024");
//		        
//		     // Fetch the e-code
//		        MobileElement ecodeElement = ecodeElement();
//		        String ecodetext = ecodeElement.getText();
//		 
//		        System.out.println("✅ E-code fetched: " + ecodetext);
//		        
//		     // -----------------------------
//		        // STEP 3: Return to MCB App
//		        // -----------------------------
//		        System.out.println("Switching back to MCB app...");
//		 
////		        Activity mcbActivity = new Activity(
////		                "eu.afse.omnia.mcb.curacao",
////		                "eu.afse.omnia.prototype.usecases.splash.SplashScreenActivity");
//		      
//		        
//		        ((AndroidDriver) driver).activateApp("eu.afse.omnia.mcb.curacao");
//
//		 
//		        Thread.sleep(3000);
//		 
//		        // Enter e-code
//		        MobileElement ecode =ecode();
//		        ecode.sendKeys(ecodetext);	        
//		        System.out.println("Ecode Enter.....");
//		     //   wait.ImplicitlyWait(5);
//		        MobileElement login =login();
//		        login.click();
//		        System.out.println("Login button Clicked");
//		        
//		        Thread.sleep(5000);
//		        
//		        MobileElement registertext =registertext();
//		        wait.waitForElementVisibility(registertext);
//		        String registerText = registertext.getText();
//		        System.out.println("Registration Device Screen Text: " + registerText);
//		        Assert.assertTrue(registerText.contains("Register"), "Registration failed — Welcome text not found!");
//		        System.out.println("Registration Device Page Open......");
//		        
//		        wait.ImplicitlyWait(3);
//		        
//		        MobileElement rcontinue =rcontinue();
//		        rcontinue.click();
//		        
//		        wait.ImplicitlyWait(10);
//		        
//		        MobileElement allow =allow();
//		        allow.click();
//		        
//		        wait.ImplicitlyWait(5);
//		        
//		        MobileElement enterpin =enterpin();
//		        enterpin.click();
//		        driver.getKeyboard().sendKeys("11111");
//		        Thread.sleep(2000);
//		        MobileElement repeatpin =repeatpin();
//		        repeatpin.click();
//		        driver.getKeyboard().sendKeys("11111");
//		        Thread.sleep(2000);
//		        ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.ENTER));
//		        
//		        wait.ImplicitlyWait(5);
//		        
//		        MobileElement confirmtranscation =registertext();
//		        wait.waitForElementVisibility(confirmtranscation);
//		        String confirmationText = confirmtranscation.getText();
//		        System.out.println("Confirm Transactione Screen Text: " + confirmationText);
//		        Assert.assertTrue(confirmationText.contains("Confirm"), "Transaction Failed — Welcome text not found!");
//		        System.out.println("Confirm Transactione Page Open......");
		        
		        
		        
		        
			} catch (Exception e) {
				System.out.println("Skip button is not visible to skip Login Functionality");
			}

		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Click On Skip Button  ", e);
		}
	}
	
	
}