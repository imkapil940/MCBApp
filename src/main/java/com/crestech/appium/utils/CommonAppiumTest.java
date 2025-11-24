package com.crestech.appium.utils;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;

import com.crestech.common.utilities.Asserts;
import com.crestech.common.utilities.GestureUtils;
import com.crestech.common.utilities.HandleException;
import com.crestech.common.utilities.WaitUtils;
import org.openqa.selenium.WebElement;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.StartsActivity;
import io.appium.java_client.android.connection.ConnectionStateBuilder;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Step;

public class CommonAppiumTest extends CommandPrompt {

	public AppiumDriver driver;
	public WaitUtils wait = null;
	public GestureUtils gesture = null;
	HandleException obj_handleexception=null;

	public CommonAppiumTest(AppiumDriver driver2) {
		this.driver = driver2;
		wait = new WaitUtils(this.driver);
		gesture = new GestureUtils(this.driver);
		obj_handleexception =new HandleException(null, null);
	}

	/**
	 *  Re launching the application
	 * @throws Exception
	 */
	@Step("application the Relanching")
	public void relanchApplication(String appPackage, String appActivity) throws Exception
	{
		try {
		
		Activity activity = new Activity(appPackage, appActivity);
		((AndroidDriver)driver).startActivity(activity);
		} 
		catch (Exception e) {			
			//System.out.println("Inside Appply debit card catch");
			obj_handleexception.throwException("RELAUNCHING_EXCEPTION", " Failed to Relaunching Application  ",e);
		}
	}
	/**
	 * Click on element
	 * @param element
	 * @throws Exception
	 */
	public void clickOnElement(WebElement element) throws Exception {
		try {
				//wait.waitForElementToBeClickable(element);
				//com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
				element.click();
		}
//		} catch (HandleException e) {	
//			obj_handleexception.throwHandleException("CLICK_ELEMENT_EXCEPTION", " Failed to Click On Element  ",e);
//				}
		catch (Exception e) {			
			//obj_handleexception.throwException("CLICK_ELEMENT_EXCEPTION", " Failed to Click On Element  ",e);
		throw e;
		}
	}
	/**
	 * SendText to textFiled
	 * 
	 * @param element
	 * @param TextWhich need to send to textfield
	 * @throws Exception
	 */
	public void enterTextInTextbox(WebElement element, String keysToSend) throws Exception {
		try {
			//wait.waitForElementVisibility(element);
			//com.crestech.listeners.TestListener.saveScreenshotPNG(driver);
			element.click();
			element.sendKeys(keysToSend);
		}
//		} catch (HandleException e) {	
//			obj_handleexception.throwHandleException("SEND_KEYS_EXCEPTION", " Failed to enter text in textbox  ",e);
//			//System.out.println("Inside Appply debit card catch "+e.getCode());		
//		}
		catch (Exception e) {			
			//System.out.println("Inside Appply debit card catch");
			//obj_handleexception.throwException("SEND_KEYS_EXCEPTION", " Failed to enter text in textbox  ",e);
		throw e;
		}
	}
	
	public void pressKey(AppiumDriver driver, Keys key) throws Exception {
		try {
			if(key!=null) {				
				Actions builder = new Actions(driver); 
				builder.sendKeys(key).build().perform();
			}else
				throw new Exception("Key not provided");
		} 
		catch (Exception e) {			
			//obj_handleexception.throwException("PRESSENTERKEY_EXCEPTION", " Failed to Pressing Enter Key ",e);
			throw e;
		}
	}

	/**
	 * Extract Text from Element
	 * @param element
	 * @return text
	 * @throws Exception
	 */
	public String getTexOfElement(WebElement element) throws Exception {
		try {
		//	wait.waitForElementVisibility(element);
			return element.getText();
		}
//		} catch (HandleException e) {	
//			obj_handleexception.throwHandleException("GETTEXT_EXCEPTION", " Failed to fetch the element's text  ",e);
//			//System.out.println("Inside Appply debit card catch "+e.getCode());		
//		}
		catch (Exception e) {			
			//System.out.println("Inside Appply debit card catch");
			//obj_handleexception.throwException("GETTEXT_EXCEPTION", " Failed to fetch the element's text  ",e);
			//return null;
			throw e;	
		}
	}

	/**
	 * Check whether element is displayed
	 * 
	 * @param element
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isElementVisible(WebElement element) throws Exception {
		try {
			//wait.waitForElementVisibility(element);
			return element.isDisplayed();
		} catch (Exception e) {
//			System.out.println("Inside take ele visi catch" );
//			throw new HandleException ("WAITELEMENTVISIBLE_EXCEPTION", "Element not visible on the screen ::",e);
			throw e;	
		}
	}
	/**
	 * Check whether element is enable
	 * 
	 * @param element
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isElementEnable(WebElement element) throws Exception {
		try {
			//wait.waitForElementVisibility(element);
			return element.isEnabled();
		} catch (Exception e) {
			return false;
			//throw new HandleException ("WAITELEMENT_ENABLE_EXCEPTION", "Element not Enable on the screen ::",e);
			//throw e;	
		}
	}
	/**
	 * Check whether element is selected
	 * 
	 * @param element
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isElementSelected(WebElement element) throws Exception {
		try {
		//	wait.waitForElementVisibility(element);
			return element.isSelected();
		} catch (Exception e) {
			//e.printStackTrace(); 
			throw e;
		}
	}

	/**
	 * 
	 * @param element
	 * @return Point(x and Y co-ordinate)
	 * @throws Exception
	 */
	public static Point getLocationOfElement(WebElement element) throws Exception {
		try {
			return element.getLocation();
		} catch (Exception e) {
			throw e;	
		}
	}

	/**
	 * 
	 * @param element
	 * @return X co-ordinate
	 * @throws Exception
	 */
	public int getXCoordinateOfElement(WebElement element) throws Exception {
		try {
			//wait.waitForElementToBeClickable(element);
			return element.getLocation().getX();
		} catch (Exception e) {
			throw e;	
		}
	}

	/**
	 * 
	 * @param element
	 * @return Y co-ordinate
	 * @throws Exception
	 */
	public int getYCoordinateOfElement(WebElement element) throws Exception {
		try {
			//wait.waitForElementToBeClickable(element);
			return element.getLocation().getY();
		} catch (Exception e) {
			throw e;	
		}
	}

	/**
	 * Click On Home Button
	 * 
	 * @throws Exception
	 */
	//WORKING FINE
	public void clickOnHomeButton() throws Exception {
		try {
			((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.HOME));
		} catch (Exception e) {
			throw e;	
		}
	}

	/**
	 * Lock the device
	 * 
	 * @throws Exception
	 */
	public void lockTheDevice() throws Exception {
		try {
			((AndroidDriver) driver).lockDevice();
		} catch (Exception e) {
			throw e;	
		}
	}

	/**
	 * Check if device is locked
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean isDeviceLocked() throws Exception {
		try {
			boolean isLocked = ((AndroidDriver) driver).isDeviceLocked();
			return isLocked;
		} catch (Exception e) {
			throw e;	
		}
	}

	/**
	 * Unlock the device
	 * 
	 * @throws Exception
	 */

	public void unlockTheDevice() throws Exception {
		try {
			((AndroidDriver) driver).unlockDevice();
		} catch (Exception e) {
			throw e;	
		}
	}

	/**
	 * Enable Wifi
	 * 
	 * @throws Exception
	 */
	public void enableWifi(String os) throws Exception {

		try {
			//ConfigurationManager prop = ConfigurationManager.getInstance();
			if (os.equalsIgnoreCase("Local")) {
				((AndroidDriver) driver)
						.setConnection(new ConnectionStateBuilder().withWiFiEnabled().build());
				//System.out.println("Enable Wifi");
			} else {
				driver.executeScript("pCloudy_enableWifi", true);
			}
		} catch (Exception e) {
			throw e;	
		}
	}

	/**
	 * Open Notification bar
	 * 
	 * @throws Exception
	 */
	public void OpenNotificationDrawer() throws Exception {
		try {
			((AndroidDriver) driver).openNotifications();
		} catch (Exception e) {
			throw e;	
		}

	}

	/**
	 * @throws Exception
	 */
	public void backButton() throws Exception {
		try {
			((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.BACK));
		} catch (Exception e) {
			//throw new HandleException ("BACKBUTTONCLICK_EXCEPTION", "Failed To click on Back Button ::",e);
			throw e;	
		}
	}
	
	public void hideKeyboard() {
		try {
			// In Appium 8.x, hideKeyboard is available via executeScript
			driver.executeScript("mobile: hideKeyboard");
		} catch (Exception e) {
			// Ignore if keyboard is not visible
		}
	}

	/**
	 * disable Wifi
	 * 
	 * @throws Exception
	 */
	public void disableWifi(String os) throws Exception {
		try {
			//ConfigurationManager prop = ConfigurationManager.getInstance();
			if (os.equalsIgnoreCase("Local")) {
				((AndroidDriver) driver)
						.setConnection(new ConnectionStateBuilder().withWiFiDisabled().build());
				//System.out.println("Disable Wifi");
			} else {
				driver.executeScript("pCloudy_enableWifi", false);

			}
		} catch (Exception e) {
			throw e;	
		}
	}

	public void resetApplication() throws Exception {
		try {
			// In Appium 8.x, use executeScript for app management
			String appPackage = driver.getCapabilities().getCapability("appPackage").toString();
			driver.executeScript("mobile: terminateApp", Map.of("appId", appPackage));
			driver.executeScript("mobile: activateApp", Map.of("appId", appPackage));
		} catch (Exception e) {
			throw e;	
		}
	}

	/**
	 * Launch the App again.
	 * 
	 * @throws Exception
	 */

	public void launchApp() throws Exception {
		try {
			// In Appium 8.x, use executeScript for app activation
			String appPackage = driver.getCapabilities().getCapability("appPackage").toString();
			driver.executeScript("mobile: activateApp", Map.of("appId", appPackage));
		} catch (Exception e) {
			throw e;	
		}
	}

	/**
	 * Swiping from top panel to open quick settings.
	 * 
	 * @throws Exception
	 */
	public void scrollQuickSettings() throws Exception {
		try {
			Dimension windowSize = driver.manage().window().getSize();
			int starty = (int) (windowSize.height * 0.10);
			int endy = (int) (windowSize.height * 0.90);
			int startx = windowSize.width / 2;
			// In Appium 8.x, use mobile: swipeGesture instead of deprecated TouchAction
			Map<String, Object> params = new HashMap<>();
			params.put("startX", startx);
			params.put("startY", endy);
			params.put("endX", startx);
			params.put("endY", starty);
			params.put("speed", 2000);
			((org.openqa.selenium.JavascriptExecutor) driver).executeScript("mobile: swipeGesture", params);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
	}

	/**
	 * Keep App in background
	 * 
	 * @throws Exception
	 */
	public void runInBackgroundApp() throws Exception {
		try {
			// In Appium 8.x, use executeScript for background app
			driver.executeScript("mobile: backgroundApp", Map.of("seconds", 0.01));
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
	}

	/**
	 * Open Background app
	 * 
	 * @throws Exception
	 */
	public void openbackGroundAPP() throws Exception {
		try {
			((StartsActivity) driver).currentActivity();
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
	}
	
	public String getExceptionMessage(Exception ex) {
		try {
			String exceptionMessage = ex.getLocalizedMessage();
			
			String message = Thread.currentThread().getStackTrace()[2].getClassName() + "::"
					+ Thread.currentThread().getStackTrace()[2].getMethodName() + "() Exception : " + exceptionMessage;

			return message;
		} catch (Exception e) {
			return ex.getLocalizedMessage();
		}
	}
	
	/**
	 * Click on element if enable
	 * @param element
	 * @throws Exception
	 */
	public void clickOnElementOnEnable(WebElement element) throws Exception {
		try {
			if (isElementEnable(element)) {
				//Asserts.assertTrue(isElementEnable(element), "button Not enable");
				element.click();
			} else
				throw new Exception("The element isn't provided or may be null.");
		} catch (Exception e) {
			throw e;
		}
	}
	
	public boolean isElementVisible2(WebElement element) throws Exception {
		try {
			wait.waitForElementVisibility(element);
			return element.isDisplayed();
		} catch (Exception e) {
			return false;
//			System.out.println("Inside take ele visi catch" );
//			throw new HandleException ("WAITELEMENTVISIBLE_EXCEPTION", "Element not visible on the screen ::",e);
		
		}
	}
		
		
		public WebElement returnElementFromList(List<WebElement> elementList, String elementTextToBeVerified)
				throws Exception {
			WebElement element = null;
			try {
				if (elementList.size() > 0) {
					int l = elementList.size();

					String accountFromList = null;
					for (int i = 0; i < l; i++) {
						accountFromList = elementList.get(i).getText();
						if (accountFromList.contains(elementTextToBeVerified)) {
							element = elementList.get(i);
							break;
						}
					}
					return element;
				}

			} catch (Exception e) {
			throw e;
			}
			return element;
		}
		public void WaitForElementForNExtPage(WebElement element) throws Exception {
			try {
				Thread.sleep(20000); 
				int i=0;
				while(i<5) {
				    boolean flag = isElementVisible2(element);
				    if(flag)
				        break;
				    i++;
				}
			} catch (Exception e) {
				//e.printStackTrace();
				throw e;
			}
		}
		
		public String getTexOfElementForIos(WebElement element) throws Exception {
			try {
			//	wait.waitForElementVisibility(element);
				return element.getAttribute("name");
			}
//			} catch (HandleException e) {	
//				obj_handleexception.throwHandleException("GETTEXT_EXCEPTION", " Failed to fetch the element's text  ",e);
//				//System.out.println("Inside Appply debit card catch "+e.getCode());		
//			}
			catch (Exception e) {			
				//System.out.println("Inside Appply debit card catch");
				//obj_handleexception.throwException("GETTEXT_EXCEPTION", " Failed to fetch the element's text  ",e);
				//return null;
				throw e;	
			}
		}
}