package com.crestech.pages.iospage;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import com.crestech.annotation.values.ElementDescription;
import com.crestech.appium.utils.CommonAppiumTest;
import com.crestech.common.utilities.AndroidAlert;
import com.crestech.common.utilities.Asserts;
import com.crestech.common.utilities.GestureUtils;
import com.crestech.common.utilities.HandleException;
import org.openqa.selenium.WebElement;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.qameta.allure.Step;

/**
 * @author Divya
 *
 */
public class homePage extends CommonAppiumTest {

	@SuppressWarnings("rawtypes")
	public AppiumDriver driver = null;
	HandleException obj_handleexception = null;
	Asserts Assert = null;

	@SuppressWarnings("rawtypes")
	public homePage(AppiumDriver driver) throws Exception {
		super(driver);
		try {
			this.driver = driver;
			obj_handleexception = new HandleException(null, null);
			Assert = new Asserts();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	// Locators
	private By homeButtonLocator = By.xpath("//XCUIElementTypeButton[@name='HomeIdentifier']");
	private By closeButtonLocator = By.xpath("//XCUIElementTypeImage/following-sibling::XCUIElementTypeOther/XCUIElementTypeButton");
	private By searchFromButtonLocator = By.xpath("//XCUIElementTypeTextField[@value='Origin city/airport code']");
	private By destinationButtonLocator = By.xpath("//XCUIElementTypeTextField[@value='Destination city/airport code']");
	private By calanderLocator = By.xpath("//XCUIElementTypeCollectionView");
	private By DoneButtonLocator = By.xpath("//XCUIElementTypeStaticText[@name='Done']");
	private By LogInLocator = By.xpath("//XCUIElementTypeButton[@name='Log In']");
	private By crossBlackButtonLocator = By.xpath("//XCUIElementTypeButton[@name='cross black']");
	
	public WebElement getLoginButton() {
		return driver.findElement(LogInLocator);
	}
	
	public WebElement getCrossBlackIcon() {
		return driver.findElement(crossBlackButtonLocator);
	}
	
	
	public WebElement getSearchFromButton() {
		return driver.findElement(searchFromButtonLocator);
	}
	
	public WebElement getToField() {
		return driver.findElement(destinationButtonLocator);
	}
	
	public WebElement getcalander() {
		return driver.findElement(calanderLocator);
	}
	
	public WebElement getDoneButton() {
		return driver.findElement(DoneButtonLocator);
	}
	
	public WebElement getCloseButton() {
		return driver.findElement(closeButtonLocator);
	}
	
	public WebElement getHomeButton() {
		return driver.findElement(homeButtonLocator);
	}
	
	
	@Step("Open App.")
	public void OpenApp(String AppName) throws Exception {
		try {
			String AppXpath = "//XCUIElementTypeOther[@name='"+AppName+"']";
			WebElement AppBtn = driver.findElement(By.xpath(AppXpath)); 
			com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
			clickOnElement(AppBtn);
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION", " Failed to Click On App Button  ", e);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Click On App Button  ", e);
		}
	}
	
	@Step("Click On home Button.")
	public void ClickOnHomeButton() throws Exception {
		try {
			WebElement HomeBtn = getHomeButton();
			com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
			clickOnElement(HomeBtn);
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION", " Failed to Click On Home Button  ", e);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Click On Home Button  ", e);
		}
	}
	
	@Step("Click On Close Button.")
	public void ClickOnCloseButton() throws Exception {
		try {
			try {
				if (isElementVisible2(driver.findElement(closeButtonLocator))) {
					WebElement CloseBtn = getCloseButton();
					com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
					clickOnElement(CloseBtn);
				}
			}
			catch (Exception e) {
				System.out.println("Close button is not visible on the page");
			}
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Click On Close Button  ", e);
		}
	}
	
	@Step("Select destination")
	public void SelectDestination(String FromLocation, String FromLocatorValue,String FullFromAddress, String ToLocation, String ToLocatorValue, String FullToAddress) throws Exception {
		try {
			SelectFromLocation(FromLocation,FromLocatorValue,FullFromAddress);
			SearchToLocation(ToLocation,ToLocatorValue,FullToAddress);
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("TESTCASE_EXCEPTION", " Failed to Select Destination ", e);
		} catch (Exception e) {
			obj_handleexception.throwException("TESTCASE_EXCEPTION", " Failed to Select Destination ", e);
		}
	}
	
	@Step("Select From Location.")
	public void SelectFromLocation(String FromLocation, String LocatorValue, String FullFromLocation) throws Exception {
		try {
		    System.out.println("Click on from location manually and Press \"ENTER\" on console to continue...");
    	    Scanner scanner = new Scanner(System.in);
    	    scanner.nextLine();
			
			WebElement FromInputField = getSearchFromButton();
			FromInputField.sendKeys(FromLocation);
			
			String FromLocationXpathInDropdown = "//XCUIElementTypeStaticText[@name='"+FullFromLocation+"']";
			WebElement FullFromLocationElement = driver.findElement(By.xpath(FromLocationXpathInDropdown));
			clickOnElement(FullFromLocationElement);
			
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION", " Failed to Select the From Location  ", e);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", "  Failed to Select the From Location   ", e);
		}
	}
	
	@Step("Search To Location.")
	public void SearchToLocation(String ToLocation, String LocatorValue, String FullToLocation) throws Exception {
		try {
			
			WebElement ToInputField = getToField();
			ToInputField.sendKeys(ToLocation);
			com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
			String ToLocationXpathInDropdown = "//XCUIElementTypeStaticText[@name='"+FullToLocation+"']";
			WebElement ToFromLocationElement = driver.findElement(By.xpath(ToLocationXpathInDropdown));
			clickOnElement(ToFromLocationElement);
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION", " Failed to Search the To Location  ", e);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Search the To Location  ", e);
		}
	}
	
	@Step("Select date from calander")
	public void SelectDateFromCalander() throws Exception {
		try {
			try {
				   System.out.println("Click on date button manually and Press \"ENTER\" on console to continue...");
		    	   Scanner scanner = new Scanner(System.in);
		    	   scanner.nextLine();
			} catch (Exception e) {
				obj_handleexception.throwException("ENTER_DATE_EXCEPTION", " Failed to click on date button  ", e);
			}
 
			LocalDate today = LocalDate.now();
			int tomorrowDay = today.plusDays(2).getDayOfMonth();
			System.out.println("Next Day Flight Date: " + tomorrowDay);
 
			
			WebElement calendar = getcalander();
			WebElement dayElement = calendar
					.findElement(By.xpath("//XCUIElementTypeStaticText[@name='"+tomorrowDay+"']"));
			clickOnElement(dayElement);
			com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
			//ClickOnDoneButton();
 
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION", " Failed to Select date from calander  ",
					e);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Select date from calander ", e);
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
	
	@Step("handle Session Expired Popup.")
	public void handleExpiredSessionPopup() throws Exception {
		try {
			
			try {
				if (isElementVisible2(getLoginButton())) {
					WebElement LoginBtn = getLoginButton();
					clickOnElement(LoginBtn);
					WebElement CrossIcon = getCrossBlackIcon();
					clickOnElement(CrossIcon);
				}
			} catch (Exception e) {
				System.out.println("No Need to Handle Session expired Popup.");
			}
			
			
			
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to handle Session expired Popup  ", e);
		}
	}
	
	@Step("Click On Search Flights Button.")
	public void ClickOnSearchFlightsButton() throws Exception {
		try {
			try {
				   System.out.println("Click on Search flight button manually and Press \"ENTER\" on console to continue...");
		    	   Scanner scanner = new Scanner(System.in);
		    	   scanner.nextLine();
		    	   
		    	   handleExpiredSessionPopup();
		    	   
		    	   System.out.println("Click on Search flight button manually and Press \"ENTER\" on console to continue...");
		    	   scanner = new Scanner(System.in);
		    	   scanner.nextLine();
		    	   
		    	   handleExpiredSessionPopup();
		    	   
		    	   System.out.println("Click on Search flight button manually and Press \"ENTER\" on console to continue...");
		    	   scanner = new Scanner(System.in);
		    	   scanner.nextLine();
		    	   
		    	   handleExpiredSessionPopup();
			} catch (Exception e) {
				obj_handleexception.throwException("SEARCH_FLIGHT_BUTTON_EXCEPTION", " Failed to click on Search flight button  ", e);
			}
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("FUNCTIONAL_EXCEPTION",
					" Failed to Click On Search Flights Button  ", e);
		} catch (Exception e) {
			obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", " Failed to Click On Search Flights Button  ",
					e);
		}
	}
 

}
