package com.crestech.android.tests;

import java.util.Properties;
import java.util.logging.Logger;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.crestech.annotation.values.Author;
import com.crestech.base.UserBaseTest;
import com.crestech.common.utilities.Asserts;
import com.crestech.common.utilities.HandleException;
import com.crestech.common.utilities.WaitUtils;
import com.crestech.listeners.TestListener;
import com.crestech.pages.McbAndroidPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.commons.annotation.*;


@Listeners(TestListener.class)
@Epic("FOMBPPCPOC-1 - Verify Confirmation Transcation Screen")
public class McbAndroidTest extends UserBaseTest {

	Asserts Assert = null;
	McbAndroidPage mcbpage1 = null;
	WaitUtils wait = null;

	public McbAndroidTest() throws Exception {
		super();
		Assert = new Asserts();
		// wait will be initialized when driver is available
	}

	Logger logger = Logger.getLogger(McbAndroidTest.class.getName());

	/******************
	 * Start Test Script For MCB App
	 ************************************/
	
	@Test
	@QaseId(1)
	@Author(name = "KAPIL SHARMA")
	public void checkConfig() throws Exception {
	    var is = getClass().getClassLoader().getResourceAsStream("teams.properties");
	    System.out.println("File found? " + (is != null));
	    if (is != null) {
	        Properties p = new Properties();
	        p.load(is);
	        System.out.println("Webhook: " + p.getProperty("teams.webhook.url"));
	    }
	}


	@Feature(value =  "Confirmation Transcation Screen") 
	@Story("Verify Confirmation Transcation Account with Login the application.")
	@Parameters({ "userName", "password", "app_Name" })
	@Test(priority = 1, enabled = true, description = "Verify Through MCB App")
	@QaseId(2)
	@Author(name = "KAPIL SHARMA")
	public void MCBAppLogin(String userName, String password, String app_Name) throws Exception {
		try {
			McbAndroidPage mcbpage = new McbAndroidPage(driver);
			mcbpage1=mcbpage;
			System.out.println("MCB app open");
		//	mcbpage1.entertestData();
			mcbpage1.loginToMcbApp("pcloudy", "Test12345", "2024", "11111");
		
		} catch (HandleException e) {
			e.printStackTrace();
			Asserts.assertFail(e.getCode()+"--> "+ e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			Asserts.assertFail( "Unable to execute SRP Without Login "+e.getMessage());
		}
	}
	
	
//	@Feature(value =  "Confirmation Transcation Screen") 
//	@Story("Verify Confirmation Transcation Account with Login the application.")
//	@Parameters({ "userName", "password", "app_Name" })
//	@Test(priority = 1, enabled = true, description = "Verify Through MCB App")
//	@QaseId(2)
//	@Author(name = "KAPIL SHARMA")
//	public void MCBAppLogin1(String userName, String password, String app_Name) throws Exception {
//		try {
//			McbAndroidPage mcbpage = new McbAndroidPage(driver);
//			mcbpage1=mcbpage;
//			System.out.println("MCB app open");
//		//	mcbpage1.entertestData();
//			//mcbpage1.loginToMcbApp("pcloudy", "Test12345", "2024", "11111");
//		
//		} catch (HandleException e) {
//			e.printStackTrace();
//			//Asserts.assertFail(e.getCode()+"--> "+ e.getMessage());
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//			Asserts.assertFail( "Unable to execute SRP Without Login "+e.getMessage());
//		}
//	}

	/******************
	 * End Test Script For MCB App
	 ************************************/
}
