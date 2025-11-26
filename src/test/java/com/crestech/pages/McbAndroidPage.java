package com.crestech.pages;


import java.util.logging.Logger;

import org.openqa.selenium.WebElement;

import com.crestech.android.tests.McbAndroidTest;
import com.crestech.appium.utils.CommonAppiumTest;
import com.crestech.common.utilities.Asserts;
import com.crestech.common.utilities.HandleException;
import com.crestech.common.utilities.WaitUtils;
import com.crestech.pages.androidpage.loginPage;
import com.crestech.pages.androidpage.LoginpageMCB;
import com.crestech.pages.androidpage.homePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;

/**
 * @author KAPIL SHARMA
 *
 */
public class McbAndroidPage extends CommonAppiumTest {

	static Logger log = Logger.getLogger(McbAndroidTest.class.getName());
	@SuppressWarnings("rawtypes")
	public AppiumDriver driver = null;
	
	WaitUtils wait = null;
	
	Asserts Assert = null;
	HandleException obj_handleexception = null;
	LoginpageMCB login = null;
	
	homePage homepage = null;
	loginPage loginpage=null;
	@SuppressWarnings("rawtypes")
	public McbAndroidPage(AppiumDriver driver) throws Exception {
		super(driver);
		try {
			this.driver = driver;
		
			wait = new WaitUtils(driver);
			Assert = new Asserts();
			obj_handleexception = new HandleException(null, null);
			login = new LoginpageMCB(driver);
			loginpage = new loginPage(driver);
			homepage = new homePage(driver);
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
//--------------------------Methods--------------------------------------------------------	

//	@Step("Login to MCB App with username, password and Entrust e-code (with retry for invalid ecode)")
//	public void loginToMcbApp(String username, String password, String entrustPin, String registerPin) throws Exception {
//	    try {
//	        log.info("=== Starting Login Flow for MCB App ===");
//
//	        // Step 1: Enter username and password
//	        log.info("Entering username and password...");
//	        loginpage.enterUsername(username);
//	        loginpage.clickContinue();
//	        loginpage.enterPassword(password);
//
//	        // Retry loop for eCode verification
//	        boolean loginSuccessful = false;
//	        int retryCount = 0;
//	        int maxRetries = 3; // you can increase if needed
//
//	        while (!loginSuccessful && retryCount < maxRetries) {
//	            retryCount++;
//	            log.info("Attempt #" + retryCount + " to login with eCode...");
//
//	            // Step 2: Switch to Entrust Identity app and fetch e-code
//	            log.info("Switching to Entrust Identity app to fetch e-code...");
//	            String ecodeValue = loginpage.fetchEcodeFromEntrust();
//	            log.info("✅ E-code fetched successfully: " + ecodeValue);
//
//	            // Step 3: Switch back to MCB app
//	            log.info("Switching back to MCB app...");
//	            loginpage.switchToMCBApp();
//
//	            // Wait for the e-code field to be visible
//	            wait.fluentWaitForElement(loginpage.getEcodeField());
//
//	            // Step 4: Enter e-code and click Login
//	            log.info("Entering fetched e-code...");
//	            loginpage.getEcodeField().clear();
//	            loginpage.getEcodeField().sendKeys(ecodeValue);
//	            loginpage.clickLogin();
//
//	            // Step 5: Check for "Invalid eCode" popup
//	            Thread.sleep(4000); // small wait for popup to appear if invalid
//	            if (loginpage.isInvalidEcodePopupDisplayed()) {
//	                log.warning("⚠️ Invalid eCode detected. Clicking OK and retrying...");
//	                loginpage.clickPopupOk();
//	                Thread.sleep(2000);
//	                continue; // retry with new eCode
//	            }
//
//	            // Step 6: Check if Register screen is reached
//	            try {
//	                wait.fluentWaitForElement(loginpage.getRegisterText());
//	                String registerText = loginpage.getRegisterText().getText();
//	                if (registerText.contains("Register")) {
//	                    log.info("✅ Successfully reached Register Device screen!");
//	                    loginSuccessful = true;
//	                    break;
//	                }
//	            } catch (Exception e) {
//	                log.warning("Register screen not detected yet. Retrying...");
//	            }
//	        }
//
//	        if (!loginSuccessful) {
//	            throw new Exception("❌ Failed to login after " + retryCount + " attempts. eCode might be invalid.");
//	        }
//
//	        // Step 7: Complete registration (PIN setup, allow permissions, confirm)
//	        log.info("Completing registration with PIN...");
//	        loginpage.completeRegistration(registerPin);
//
//	        // Step 8: Verify Confirm Transaction screen
//	        wait.fluentWaitForElement(loginpage.getConfirmTransaction());
//	        String confirmText = loginpage.getConfirmTransaction().getText();
//	        Assert.assertTrue(confirmText.contains("Confirm"), "❌ Transaction confirmation screen not displayed!");
//	        log.info("✅ Confirm Transaction screen displayed successfully.");
//
//	        log.info("=== Login flow completed successfully ===");
//
//	    } catch (Exception e) {
//	        obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", "Error occurred during MCB login flow", e);
//	    }
//	}

	@Step("Login to MCB App with username, password and Entrust e-code (with retry for invalid ecode)")
	public void loginToMcbApp(String username, String password, String entrustPin, String registerPin) throws Exception {
	    try {
	        log.info("=== Starting Login Flow for MCB App ===");

	        // Step 1: Enter username and password
	        log.info("Entering username and password...");
	        try {
	            loginpage.enterUsername(username);
	            Thread.sleep(2000); // Wait after username entry
	            loginpage.clickContinue(); // This will wait internally for password screen
	            loginpage.enterPassword(password);
	            log.info("✅ Username and password entered successfully");
	        } catch (Exception loginEx) {
	            log.severe("Failed during username/password entry: " + loginEx.getMessage());
	            throw loginEx;
	        }

//	        boolean loginSuccessful = false;
//	        int retryCount = 0;
//	        int maxRetries = 5;
//
//	        while (!loginSuccessful && retryCount < maxRetries) {
//	            retryCount++;
//	            log.info("Attempt #" + retryCount + " to login with eCode...");
//
//	            // Step 2: Switch to Entrust and fetch new eCode
//	            log.info("Switching to Entrust Identity app to fetch e-code...");
//	            String ecodeValue = loginpage.fetchEcodeFromEntrust();
//	            log.info("✅ E-code fetched successfully: " + ecodeValue);
//
//	            // Step 3: Switch back to MCB app
//	            log.info("Switching back to MCB app...");
//	            loginpage.switchToMCBApp();
//
//	            // Wait for eCode field
//	            WebElement ecodeField = loginpage.getEcodeField();
//	            wait.fluentWaitForElement(ecodeField);
//
//	            // Step 4: Ensure field is empty (reliable clear)
//	            log.info("Clearing existing eCode field before entering new code...");
//	            try {
//	                ecodeField.click();
//	                String existingText = ecodeField.getText();
//	                for (int i = 0; i < existingText.length(); i++) {
//	                    ((AndroidDriver) driver).pressKey(
//	                        new io.appium.java_client.android.nativekey.KeyEvent(
//	                            io.appium.java_client.android.nativekey.AndroidKey.DEL));
//	                }
//	                // Sometimes Appium clear() fails — use BACKSPACE fallback
//	                if (!ecodeField.getText().isEmpty()) {
//	                    ((AndroidDriver) driver).pressKey(
//	                        new io.appium.java_client.android.nativekey.KeyEvent(
//	                            io.appium.java_client.android.nativekey.AndroidKey.DEL));
//	                }
//	            } catch (Exception ex) {
//	                log.warning("Could not clear eCode field normally: " + ex.getMessage());
//	            }
//
//	            // Step 5: Enter new eCode and click Login
//	            log.info("Entering new fetched eCode: " + ecodeValue);
//	            ecodeField.sendKeys(ecodeValue);
//	            loginpage.clickLogin();
//
//	            // Step 6: Check for invalid popup
//	            Thread.sleep(4000); // short wait for popup
//	            if (loginpage.isInvalidEcodePopupDisplayed()) {
//	                log.warning("⚠️ Invalid eCode detected. Clicking OK and retrying...");
//	                loginpage.clickPopupOk();
//	                Thread.sleep(2000);
//	                continue; // fetch next eCode
//	            }
//
//	            // Step 7: Check for Register screen
//	            try {
//	                wait.fluentWaitForElement(loginpage.getRegisterText());
//	                String registerText = loginpage.getRegisterText().getText();
//	                if (registerText.contains("Register")) {
//	                    log.info("✅ Successfully reached Register Device screen!");
//	                    loginSuccessful = true;
//	                    break;
//	                }
//	            } catch (Exception e) {
//	                log.warning("Register screen not detected yet. Retrying...");
//	            }
//	        }
//
//	        if (!loginSuccessful) {
//	            throw new Exception("❌ Failed to login after " + retryCount + " attempts. eCode might be invalid.");
//	        }
//
//	        // Step 8: Complete registration
//	        log.info("Completing registration with PIN...");
//	        loginpage.completeRegistration(registerPin);
//
//	        // Step 9: Verify confirmation
//	        wait.fluentWaitForElement(loginpage.getConfirmTransaction());
//	        String confirmText = loginpage.getConfirmTransaction().getText();
//	        Assert.assertTrue(confirmText.contains("Confirm"), "❌ Confirmation screen not displayed!");
//	        log.info("✅ Confirm Transaction screen displayed successfully.");
//
//	        log.info("=== Login flow completed successfully ===");

	    } catch (Exception e) {
	        obj_handleexception.throwException("FUNCTIONAL_EXCEPTION", "Error during MCB login flow", e);
	    }
	}

	public void entertestData() throws Exception {
		// TODO Auto-generated method stub
		login.enterdata();
		
	}
	
}
