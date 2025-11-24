package com.crestech.pages;

import java.util.logging.Logger;
import org.openqa.selenium.remote.RemoteWebElement;
import com.crestech.appium.utils.CommonAppiumTest;
import com.crestech.common.utilities.CommonTestData;
import com.crestech.common.utilities.GestureUtils;
import com.crestech.common.utilities.HandleException;
import com.crestech.pages.iospage.settingsPage;
import com.crestech.pages.iospage.loginPage;
import com.crestech.pages.iospage.homePage;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;

public class Mcb_IOSpage extends CommonAppiumTest {

	static Logger log = Logger.getLogger(McbAndroidPage.class.getName());
	GestureUtils gestUtils = null;
	HandleException obj_handleexception = null;
	homePage homepage = null;
	loginPage loginpage = null;
	settingsPage settingspage = null;

	@SuppressWarnings("rawtypes")
	public Mcb_IOSpage(AppiumDriver driver) throws Exception {
		super(driver);
		gestUtils = new GestureUtils(driver);
		homepage = new homePage(driver);
		obj_handleexception = new HandleException(null, null);
		loginpage = new loginPage(driver);
		settingspage = new settingsPage(driver);
	}
	
	@Step("Search Reasult Page Without Login")
	public void SearchResultPageWithoutLogin(String FromLocation, String FullFromAddress, String ToLocation, String FullToLocation) throws Exception {
		try {
			homepage.ClickOnHomeButton();
			//homepage.SelectDestination(FromLocation,"From", FullFromAddress,ToLocation,"BOM",FullToLocation);
			homepage.SelectDateFromCalander();
			homepage.ClickOnSearchFlightsButton();
			
		//	searchflightspage.SelectFirstFlight();
			//searchflightspage.verifyLoginToAccessMessage(CommonTestData.LOGIN_TO_ACCESS_MESSAGE_IOS.getEnumValue());
			
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("TESTCASE_EXCEPTION", " Failed to Exceute Search Reasult Page Without Login", e);
		} catch (Exception e) {
			obj_handleexception.throwException("TESTCASE_EXCEPTION", " Failed to Exceute Search Reasult Page Without Login ", e);
		}
	}

	@Step("Pre - requisite script")
	public void preRequisiteScript(String MobNo, String OTP)
			throws Exception {
		try {
			homepage.ClickOnCloseButton();
			
			LogInApplication( MobNo, OTP);
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("TESTCASE_EXCEPTION", " Failed to Exceute pre-Requisite Script ",
					e);
		} catch (Exception e) {
			obj_handleexception.throwException("TESTCASE_EXCEPTION", " Failed to Exceute pre-RequisiteScript ", e);
		}
	}
	
	@Step("Log In the Application")
	public void LogInApplication(String MobNo, String Otp) throws Exception {
		try {
			loginpage.EnterLoginDetails(MobNo, Otp);
			
			homepage.ClickOnHomeButton();
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("TESTCASE_EXCEPTION", " Failed to Exceute Log In Application ", e);
		} catch (Exception e) {
			obj_handleexception.throwException("TESTCASE_EXCEPTION", " Failed to Exceute Log In Application ", e);
		}
	}



	@Step("Open And Verify the Hotels App")
	public void OpenHotelsApp(String AppName, String AppTitle) throws Exception {
		try {
			homepage.OpenApp(AppName); 
			
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("TESTCASE_EXCEPTION", " Failed to Open Hotels App  ",
					e);
		} catch (Exception e) {
			obj_handleexception.throwException("TESTCASE_EXCEPTION", " Failed to Open Hotels App  ", e);
		}
	}
	
	@Step("Open And Verify the Trains App")
	public void OpenTrainsApp(String AppName, String AppTitle) throws Exception {
		try {
			homepage.OpenApp(AppName); 
	
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("TESTCASE_EXCEPTION", " Failed to Open Trains App  ",
					e);
		} catch (Exception e) {
			obj_handleexception.throwException("TESTCASE_EXCEPTION", " Failed to Open Trains App  ", e);
		}
	}
	
	
	@Step("Log Out App")
	public void LogOutAPP(String LoginToExploreMsg) throws Exception {
		try {
		
			settingspage.ClickOnSignOutButton();
			settingspage.ClickOnYesButton();
			settingspage.ClickOnBackButton();
		
		} catch (HandleException e) {
			obj_handleexception.throwHandleException("TESTCASE_EXCEPTION", " Failed to LogOut App  ",
					e);
		} catch (Exception e) {
			obj_handleexception.throwException("TESTCASE_EXCEPTION", " Failed to Open LogOut App  ", e);
		}
	}

	

}
