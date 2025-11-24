package com.crestech.android.tests;

import org.apache.log4j.Logger;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.crestech.annotation.values.Author;
import com.crestech.base.UserBaseTest;
import com.crestech.common.utilities.Asserts;
import com.crestech.common.utilities.CommonTestData;
import com.crestech.common.utilities.HandleException;
import com.crestech.listeners.RetryAnalyzer;
import com.crestech.listeners.TestListener;
import com.crestech.pages.Mcb_IOSpage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@Listeners(TestListener.class)
public class Mcb_IOSTest extends UserBaseTest {

	Logger logger = Logger.getLogger(Mcb_IOSTest.class.getName());
	Mcb_IOSpage IosPage1 = null;
	
	@Epic("Log In")
	@Feature(value =  "Login Page" ) 
	@Story("Login the Mcb Application.")
	@Parameters({ "userName", "password", "app_Name" })
	@Test(priority = 1, enabled = true, description = "Login the Mcb Application")//, retryAnalyzer = RetryAnalyzer.class)
	@Author(name = "Divya Devi")
	public void PreRequisiteScript(String userName, String password, String app_Name) throws Exception {
		try {
			System.out.println("App Installed Successfully");
			Mcb_IOSpage IosPage = new Mcb_IOSpage(driver);
			IosPage1 = IosPage;
			IosPage1.preRequisiteScript(userName, password);
		} catch (HandleException e) {
			Asserts.assertFail(e.getCode() + "--> " + e.getMessage());
		} catch (Exception e) {
			Asserts.assertFail("Unable to execute Pre-Requisite Script " + e.getMessage());
		}
	}
	
	@Epic("Launching Other Apps Using Mcb")
	@Feature(value =  "Hotel App" ) 
	@Story("Lauching the Hotel APP.")
	@Parameters({ "userName", "password", "app_Name" })
	@Test(priority = 2, enabled = true, description = "Lauching the Hotel APP")//, retryAnalyzer = RetryAnalyzer.class)
	@Author(name = "Divya Devi")
	public void LaunchingHotelApp(String userName, String password, String app_Name) throws Exception {
		try {
			Mcb_IOSpage IosPage = new Mcb_IOSpage(driver);
			IosPage1 = IosPage;
			IosPage1.OpenHotelsApp(CommonTestData.HOTELS.getEnumValue(), CommonTestData.HOTELS_APP_TITLE.getEnumValue());
		} catch (HandleException e) {
			Asserts.assertFail(e.getCode() + "--> " + e.getMessage());
		} catch (Exception e) {
			Asserts.assertFail("Unable to execute Launching Hotel App Script " + e.getMessage());
		}
	}

	@Epic("Launching Other Apps Using Mcb")
	@Feature(value =  "Trains App" ) 
	@Story("Lauching the Trains APP.")
	@Parameters({ "userName", "password", "app_Name" })
	@Test(priority = 3, enabled = true, description = "Lauching the Trains APP")//, retryAnalyzer = RetryAnalyzer.class)
	@Author(name = "Divya Devi")
	public void LaunchingTrainsApp(String userName, String password, String app_Name) throws Exception {
		try {
			Mcb_IOSpage IosPage = new Mcb_IOSpage(driver);
			IosPage1 = IosPage;
			IosPage1.OpenTrainsApp(CommonTestData.TRAINS.getEnumValue(),CommonTestData.TRAINS_APP_TITLE.getEnumValue());
		} catch (HandleException e) {
			Asserts.assertFail(e.getCode() + "--> " + e.getMessage());
		} catch (Exception e) {
			Asserts.assertFail("Unable to execute Launching Trains App Script " + e.getMessage());
		}
	}
	

	
	@Epic("Logout")
	@Feature(value =  "Logout App" ) 
	@Story("Logout APP.")
	@Parameters({ "userName", "password", "app_Name" })
	@Test(priority = 4, enabled = true, description = "Log Out APP")//, retryAnalyzer = RetryAnalyzer.class)
	@Author(name = "Divya Devi")
	public void LogOutApp(String userName, String password, String app_Name) throws Exception {
		try {
			Mcb_IOSpage IosPage = new Mcb_IOSpage(driver);
			IosPage1 = IosPage;
			IosPage1.LogOutAPP(CommonTestData.IOS_SIGNED_OUT_MESSAGE.getEnumValue());
		} catch (HandleException e) {
			Asserts.assertFail(e.getCode() + "--> " + e.getMessage());
		} catch (Exception e) {
			Asserts.assertFail("Unable to execute Log Out App Script " + e.getMessage());
		}
	}
	
	@Epic("Flight Search")
	@Feature(value =  "Domestic SRP")
	@Story("Verify Search Result Page Without Login the application.")
	@Parameters({ "userName", "password", "app_Name" })
	@Test(priority = 5, enabled = true, description = "SRP Without Login")
	@Author(name = "Divya Devi")
	public void SRPWithoutLogin(String userName, String password, String app_Name) throws Exception {
		try {
			Mcb_IOSpage IosPage = new Mcb_IOSpage(driver);
			IosPage1 = IosPage;
			IosPage1.SearchResultPageWithoutLogin(CommonTestData.FROM.getEnumValue(),
					CommonTestData.FROM_FULL_LOCATION.getEnumValue(), CommonTestData.TO.getEnumValue(),CommonTestData.TO_FULL_LOCATION.getEnumValue());
		
		} catch (HandleException e) {
			Asserts.assertFail(e.getCode()+"--> "+ e.getMessage());
		}
		catch (Exception e) {
			Asserts.assertFail( "Unable to execute SRP Without Login "+e.getMessage());
		}
	}


}
