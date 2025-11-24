package com.crestech.pages.iospage;

import java.time.Duration;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.crestech.annotation.values.ElementDescription;
import com.crestech.appium.utils.CommonAppiumTest;
import com.crestech.common.utilities.CommonTestData;
import com.crestech.common.utilities.HandleException;

import org.openqa.selenium.WebElement;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.qameta.allure.Step;

public class loginPage extends CommonAppiumTest {

	@SuppressWarnings("rawtypes")
	public AppiumDriver driver = null;
	HandleException obj_handleexception = null;

	@SuppressWarnings("rawtypes")
	public loginPage(AppiumDriver driver) throws Exception {
		super(driver);
		try {
			this.driver = driver;
			obj_handleexception = new HandleException(null, null);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	// Locators
	private By phnNumberLocator = By.xpath("//XCUIElementTypeTextField[@name='LoginMobileNumber']");
	private By continueButton = By.xpath("//XCUIElementTypeStaticText[@name='CONTINUE']");
	private By emailField = By.xpath("//XCUIElementTypeStaticText[@name='Email address']/following-sibling::XCUIElementTypeTextField");
	private By SubmitButton = By.xpath("//XCUIElementTypeButton[@name='SUBMIT']");
 
	public WebElement getEmailField() {
		return driver.findElement(emailField);
	}
	
	public WebElement submitBtnLocator() {
		return driver.findElement(SubmitButton);
	}
 

	public WebElement getPhoneNumberField() {
		return driver.findElement(phnNumberLocator);
	}

	public WebElement getContinueBtn() {
		return driver.findElement(continueButton);
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
			}
			catch (Exception e) {
				System.out.println("no email updation");
			}
 
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION", " Failed to Enter Login Details ", e);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Enter Login Details ", e);
		}
	}
	
	@Step("Enter OTP for Secure Pin Authentication.")
	public void EnterOTPAndVerify() throws Exception {
		try {
			   System.out.println("Enter OTP in device manually and Press \"ENTER\" on console to continue...");
	    	   Scanner scanner = new Scanner(System.in);
	    	   scanner.nextLine();
		} catch (Exception e) {
			obj_handleexception.throwException("ENTER_PASSCODE_EXCEPTION", " Failed to enter OTP  ", e);
		}
	}

	@Step("Click On Continue Button.")
	public void ClickOnContinueButton() throws Exception {
		try {
			WebElement continueBtn = getContinueBtn();
			com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
			clickOnElement(continueBtn);
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION", " Failed to Click On continue Button ", e);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Click On continue Button  ", e);
		}
	}
	
	@Step("Update Email")
	public void UpdateEmail(String Email) throws Exception {
		try {
			WebElement enterEmail = getEmailField();
			com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
		    enterTextInTextbox(enterEmail, Email);
		    ClickOnSubmitButton();        
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION", " Failed to Update Email ", e);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Update Email ", e);
		}
	}
	
	@Step("Enter Phone Number")
	public void EnterPhoneNumber(String mobNo) throws Exception {
		try {
			WebElement mobileNumberField = getPhoneNumberField();
			com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
			mobileNumberField.clear();
			mobileNumberField.sendKeys(mobNo);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Enter Phone Number ", e);
		}
	}
	
	@Step("Click On Submit Button.")
	public void ClickOnSubmitButton() throws Exception {
		try {
			WebElement submitBtn = submitBtnLocator();
			com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
			clickOnElement(submitBtn);
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION", " Failed to Click On Submit Button ",
					e);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Click On Submit Button  ", e);
		}
	}

}
