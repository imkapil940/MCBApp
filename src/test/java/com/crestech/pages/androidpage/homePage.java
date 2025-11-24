package com.crestech.pages.androidpage;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebElement;
import com.crestech.appium.utils.CommonAppiumTest;
import com.crestech.common.utilities.AndroidAlert;
import com.crestech.common.utilities.Asserts;
import com.crestech.common.utilities.GestureUtils;
import com.crestech.common.utilities.HandleException;
import org.openqa.selenium.WebElement;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;

/**
 * @author Divya
 *
 */
public class homePage extends CommonAppiumTest {

	@SuppressWarnings("rawtypes")
	public AppiumDriver driver = null;
	HandleException obj_handleexception = null;
	GestureUtils gestUtils = null;
	Asserts Assert = null;
	AndroidAlert androidAlert = null;

	@SuppressWarnings("rawtypes")
	public homePage(AppiumDriver driver) throws Exception {
		super(driver);
		try {
			this.driver = driver;
			obj_handleexception = new HandleException(null, null);
			gestUtils = new GestureUtils(driver);
			Assert = new Asserts();
			androidAlert = new AndroidAlert(driver);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	// Locators
	private By homeButtonLocator = By.xpath("//android.widget.TextView[@text='Home']");
	private By searchFlightsButtonLocator = By.xpath("//android.widget.TextView[@text='Search Flights']");
	private By RoundTripLocator = By
			.xpath("//android.widget.TextView[@text='Round Trip']");
	private By OneTravellerEconomyLocator = By.xpath("//android.widget.TextView[@text='1 Traveller • Economy']");
	private By PremiumEconomyClassLocator = By
			.xpath("//android.widget.RadioButton[@resource-id='com.ixigo:id/chip_premium_economy']");
	private By DoneButtonLocator = By.xpath("//android.widget.TextView[@text='Done']");
	private By dateLocator = By.id("com.ixigo:id/layout_flight_date");
	private By dayLocator = By.xpath("//android.widget.TextView[@resource-id='com.ixigo:id/tv_date']");
	private By calanderLocator = By.id("com.ixigo:id/calendar_grid");
	private By searchFromButtonLocator = By.xpath(
			"//android.view.ViewGroup[@resource-id='com.ixigo:id/originField']/android.view.View/android.view.View/android.widget.EditText");
	private By destinationButtonLocator = By.xpath("//android.view.ViewGroup[@resource-id='com.ixigo:id/destinationField']/android.view.View/android.widget.EditText");

	public WebElement getDoneButton() {
		return driver.findElement(DoneButtonLocator);
	}

	public WebElement getPremiumEconomyClass() {
		return driver.findElement(PremiumEconomyClassLocator);
	}

	public WebElement getHomeButton() {
		return driver.findElement(homeButtonLocator);
	}

	public WebElement getOneTravellerEconomy() {
		return driver.findElement(OneTravellerEconomyLocator);
	}

	public WebElement getRoundTripButton() {
		return driver.findElement(RoundTripLocator);
	}

	public WebElement getSearchFlightsButton() {
		return driver.findElement(searchFlightsButtonLocator);
	}

	public WebElement getDateButton() {
		return driver.findElement(dateLocator);
	}

	public WebElement getDayButton() {
		return driver.findElement(dayLocator);
	}

	public WebElement getcalander() {
		return driver.findElement(calanderLocator);
	}

	public WebElement getSearchFromButton() {
		return driver.findElement(searchFromButtonLocator);
	}
	
	public WebElement getToField() {
		return driver.findElement(destinationButtonLocator);
	}

	@Step("Select From Location.")
	public void SelectFromLocation(String FromLocation, String LocatorValue,String FullFromLocation) throws Exception {
		try {
			String FromLocationXpath = "//android.widget.TextView[@text='"+LocatorValue+"']";
			WebElement FromLocationElement = driver.findElement(By.xpath(FromLocationXpath));
			clickOnElement(FromLocationElement);
			
			WebElement FromInputField = getSearchFromButton();
			FromInputField.sendKeys(FromLocation);
			
			String FromLocationXpathInDropdown = "//android.widget.TextView[@text='"+FullFromLocation+"']";
			WebElement FullFromLocationElement = driver.findElement(By.xpath(FromLocationXpathInDropdown));
			//com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
			clickOnElement(FullFromLocationElement);
			
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION", " Failed to Select the From Location  ", e);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", "  Failed to Select the From Location   ", e);
		}
	}
	
	@Step("Search To Location.")
	public void SearchToLocation(String ToLocation, String FullToLocation, String scrnshotName) throws Exception {
		try {
			
			WebElement ToInputField = getToField();
			ToInputField.sendKeys(ToLocation);
		//	com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
			Thread.sleep(2000);
			
			Map<String, Object> params = new HashMap<>();
			params.put("screenshotName", scrnshotName);
			driver.executeScript("qlens.takeScreenshot", params);
			
			String ToLocationXpathInDropdown = "//android.widget.TextView[@text='"+FullToLocation+"']";
			WebElement ToFromLocationElement = driver.findElement(By.xpath(ToLocationXpathInDropdown));
			clickOnElement(ToFromLocationElement);
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION", " Failed to Search the To Location  ", e);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Search the To Location  ", e);
		}
	}

	@Step("Select To Location.")
	public void SelectToLocation(String ToLocation,String toLocatorValue, String FullToLocation, String ScrnShotname) throws Exception {
		try {

			
			String ToLocationXpath = "//android.widget.TextView[@text='"+toLocatorValue+"']";
			WebElement ToLocationElement = driver.findElement(By.xpath(ToLocationXpath));
			clickOnElement(ToLocationElement);
			
			WebElement ToInputField = getToField();
		//	com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
			ToInputField.sendKeys(ToLocation);
			
			Thread.sleep(2000); 
			Map<String, Object> params = new HashMap<>();
			params.put("screenshotName", ScrnShotname);
			driver.executeScript("qlens.takeScreenshot", params);
			
			
			String ToLocationXpathInDropdown = "//android.widget.TextView[@text='"+FullToLocation+"']";
			WebElement ToFromLocationElement = driver.findElement(By.xpath(ToLocationXpathInDropdown));
			clickOnElement(ToFromLocationElement);
		} catch (HandleException e) {
		//	obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION", " Failed to Select the To Location  ", e);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Select the To Location  ", e);
		}
	}

	@Step("Select destination")
	public void SelectDestination(String FromLocation,String FullFromAddress, String ToLocation, String FullToAddress) throws Exception {
		try {
			gestUtils.scrollDOWNtoObject("text",
					"From", null);
			SelectFromLocation(FromLocation,"From",FullFromAddress);
			SearchToLocation(ToLocation,FullToAddress,"FlightSearchFields");
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("TESTCASE_EXCEPTION", " Failed to Select Destination ", e);
		} catch (Exception e) {
			obj_handleexception.throwException("TESTCASE_EXCEPTION", " Failed to Select Destination ", e);
		}
	}

	@Step("Select date from calander")
	public void SelectDateFromCalander() throws Exception {
		try {
			WebElement DateBtn = getDateButton();
			clickOnElement(DateBtn);

			LocalDate today = LocalDate.now();
			int tomorrowDay = today.plusDays(2).getDayOfMonth();
			System.out.println("Next Day Flight Date: " + tomorrowDay);

			
			WebElement calendar = getcalander();
			WebElement dayElement = calendar
					.findElement(By.xpath("//android.widget.TextView[@text='" + tomorrowDay + "']"));
			clickOnElement(dayElement);
		//	com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
			ClickOnDoneButton();

		} catch (HandleException e) {
			obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION", " Failed to Select date from calander  ",
					e);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Select date from calander ", e);
		}
	}
	
	@Step("Select destination from home page")
	public void SelectDestinationFromHomePage(String FromLocation,String FullFromAddress, String ToLocation, String FullToAddress, String ScrnShotname, String ToLocatorValue) throws Exception {
		try {
			SelectFromLocation(FromLocation,FromLocation,FullFromAddress);
			SelectToLocation(ToLocation,ToLocatorValue,FullToAddress,ScrnShotname);  
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("TESTCASE_EXCEPTION", " Failed to Select Destination from home page", e);
		} catch (Exception e) {
			obj_handleexception.throwException("TESTCASE_EXCEPTION", " Failed to Select Destination from home page", e);
		}
	}

	@Step("Select Economic Class.")
	public void SelectEconomicClass() throws Exception {
		try {
			WebElement OneTravellerEco = getOneTravellerEconomy();
		//	com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
			clickOnElement(OneTravellerEco);

			WebElement premiumEconomy = getPremiumEconomyClass();
		//	com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
			clickOnElement(premiumEconomy);

			ClickOnDoneButton();
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION", " Failed to Select Economic Class  ", e);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Select Economic Class  ", e);
		}
	}

	@Step("Click On Done Button.")
	public void ClickOnDoneButton() throws Exception {
		try {
			WebElement DoneBtn = getDoneButton();
			clickOnElement(DoneBtn);
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION", " Failed to Click On Done Button  ", e);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Click On Done Button  ", e);
		}
	}

	@Step("Click On Round Trip Button.")
	public void ClickOnRoundTripButton() throws Exception {
		try {
			WebElement RoundTripButton = getRoundTripButton();
		//	com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
			clickOnElement(RoundTripButton);
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION", " Failed to Click On Round Trip Button  ",
					e);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Click On Round Trip Button  ", e);
		}
	}

	@Step("Click On Search Flights Button.")
	public void ClickOnSearchFlightsButton() throws Exception {
		try {
			WebElement SearchFlightsBtn = getSearchFlightsButton();
		//	com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
			clickOnElement(SearchFlightsBtn);
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION",
					" Failed to Click On Search Flights Button  ", e);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Click On Search Flights Button  ",
					e);
		}
	}

	@Step("Click On home Button.")
	public void ClickOnHomeButton() throws Exception {
		try {
			WebElement HomeBtn = getHomeButton();
		//	com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
			clickOnElement(HomeBtn);
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION", " Failed to Click On Home Button  ", e);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Click On Home Button  ", e);
		}
	}

}
