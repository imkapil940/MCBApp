package com.crestech.common.utilities;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebElement;

import com.crestech.appium.utils.CommonAppiumTest;

import io.appium.java_client.AppiumDriver;

public class ScreenshotUtils {

	/**
	 * This will capture the screen-shot
	 * 
	 * @param methodName
	 * @return Image Path
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public static String captureScreenShot(String methodName, AppiumDriver driver) throws Exception {

		try {
			String folder_name = "FailedTestsScreenshots";
			String screenShotNameWithTimeStamp = currentDateAndTime();
			File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String failedScreen = System.getProperty("user.dir") + "/" + folder_name + "/" + screenShotNameWithTimeStamp
					+ "_" + methodName + "_failed" + ".png";
			FileUtils.copyFile(srcFile, new File(failedScreen));
			String filePath = failedScreen.toString();
			return filePath;
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}

	}

	/**
	 * This will return Base64 url
	 * 
	 * @return Base64
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static String base64conversion(AppiumDriver driver) throws Exception {
		try {
			TakesScreenshot newScreen = (TakesScreenshot) driver;
			String scnShot = newScreen.getScreenshotAs(OutputType.BASE64);
			return "data:image/jpg;base64, " + scnShot;
		} catch (WebDriverException e) {
			e.printStackTrace(); throw e;
		}

	}

	/**
	 * This will return Current Date and Time
	 * 
	 * @return CurentDate and Time
	 * @throws Exception 
	 */
	private static String currentDateAndTime() throws Exception {
		try {
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
			return now.format(dtf);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static String getScreenshot(AppiumDriver appiumDriver) throws Exception {
		try {
			TakesScreenshot ts = (TakesScreenshot) appiumDriver;
			File src = ts.getScreenshotAs(OutputType.FILE);
			String path = System.getProperty("user.dir") + "\\screenshots\\" + System.currentTimeMillis() + ".png";
			File destination = new File(path);
			FileUtils.copyFile(src, destination);
			return path;
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		} 
	}
}
