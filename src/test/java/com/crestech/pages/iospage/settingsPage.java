package com.crestech.pages.iospage;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;

import com.crestech.annotation.values.ElementDescription;
import com.crestech.appium.utils.CommonAppiumTest;
import com.crestech.common.utilities.Asserts;
import com.crestech.common.utilities.GestureUtils;
import com.crestech.common.utilities.HandleException;
import com.crestech.pages.androidpage.launchPage;

import org.openqa.selenium.WebElement;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.qameta.allure.Step;

/**
 * @author Divya
 *
 */
public class settingsPage extends CommonAppiumTest {

	@SuppressWarnings("rawtypes")
	public AppiumDriver driver = null;
	HandleException obj_handleexception = null;

	@SuppressWarnings("rawtypes")
	public settingsPage(AppiumDriver driver) throws Exception {
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
	private By SignOutButtonLocators = By.xpath("//XCUIElementTypeStaticText[@name='Sign Out']");
	private By YesButtonLocators = By.xpath("//XCUIElementTypeButton[@name='Yes']");
	private By BackButtonLocator = By.xpath("//XCUIElementTypeButton[@name='Back']");
	
	public WebElement getBackButton() {
		return driver.findElement(BackButtonLocator);
	}
	
	public WebElement GetSignOutBtn() {
		return driver.findElement(SignOutButtonLocators);
	}
	
	public WebElement GetYes() {
		return driver.findElement(YesButtonLocators);
	}
	
	@Step("Click On Back Button.")
	public void ClickOnBackButton() throws Exception {
		try {
			WebElement BackBtn = getBackButton();
			com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
			clickOnElement(BackBtn);
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION", " Failed to Click On Back Button  ",
					e);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Click On Back Button  ", e);
		}
	}
	
	@Step("Click On Yes Button.")
	public void ClickOnYesButton() throws Exception {
		try {
			WebElement YesBtn = GetYes();
			com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
			clickOnElement(YesBtn);
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION", " Failed to Click On Yes Button  ",
					e);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Click On Yes Button  ", e);
		}
	}

	@Step("Click On Sign Out Button.")
	public void ClickOnSignOutButton() throws Exception {
		try {
			WebElement SignOutBtn = GetSignOutBtn();
			com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
			clickOnElement(SignOutBtn);
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION", " Failed to Click On Sign Out Button  ",
					e);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Click On Sign Out Button  ", e);
		}
	}

}
