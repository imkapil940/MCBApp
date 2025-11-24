package com.crestech.listeners;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.crestech.appium.utils.CommonAppiumTest;
import com.crestech.appium.utils.ConfigurationManager;
import com.crestech.config.ContextManager;
import io.appium.java_client.AppiumDriver;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.Attachment;

public class TestListener implements ITestListener {
	
	private ConfigurationManager prop;
	
	public TestListener() {
		try {
			prop = ConfigurationManager.getInstance();
		} catch (Exception e) {
			// ignore: reporting type will be treated as non-allure
		}
	}

	//private static String deviceName =null;
	
	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}
	
	private static String getTeststatus(ITestResult iTestResult) {
		 switch (iTestResult.getStatus()) {
	        case ITestResult.SUCCESS:
	            return "Passed";
	        case ITestResult.FAILURE:
	            return "Failed";
	        case ITestResult.SKIP:
	            return "Skipped";
	        default:
	            return "Unknown";	
		 }
	}
	
//	private static String getDeviceName(ITestContext iTestContext) {
//		return deviceName;
//	}
//	
//	private static void setDeviceName(ITestContext iTestContext) {
//		deviceName= iTestContext.getName();
//	}

	// Text attachments for Allure
	@Attachment(value = "Page screenshot", type = "image/png")
	public static byte[] saveScreenshotPNG(WebDriver driver) {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

	// Text attachments for Allure
	@Attachment(value = "{0}", type = "text/plain")
	public static String saveTextLog(String message) {
		return message;
	}

	// HTML attachments for Allure
	@Attachment(value = "{0}", type = "text/html")
	public static String attachHtml(String html) {
		return html;
	}

	@Override
	public void onStart(ITestContext iTestContext) {
		try {
		//	setDeviceName(iTestContext);
			System.out.println("I am in onStart method " + iTestContext.getName()	);
			//System.out.println("HI");
			@SuppressWarnings("rawtypes")
			AppiumDriver driver = ContextManager.getDriver();
			if (driver != null) {
				iTestContext.setAttribute("WebDriver", driver);
			}
			if (prop != null && "allure".equalsIgnoreCase(prop.getProperty("ReportType"))) {
				try {
					File fileClean = new File(System.getProperty("user.dir") + "/allure-results");
					FileUtils.deleteDirectory(fileClean);
				} catch (Exception e) {
					System.out.println("Dir doesno exist");
				}
			}
		} catch (Exception e) {
			System.out.println("TestListener::onStart()::"+e.getLocalizedMessage());
		}
	}

	@Override
	public void onFinish(ITestContext iTestContext) {
		System.out.println("I am in onFinish method " + iTestContext.getName());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Date date = new Date();
		// Do tier down operations for extent reports reporting!
		/*
		 * ExtentTestManager.endTest(); ExtentManager.getReporter().flush();
		 */
		/*
		 * File srcDir = new File(System.getProperty("user.dir") + "\\allure-results");
		 * File destDir = new File(System.getProperty("user.dir") +
		 * "\\AllureReport\\allure-results_" + dateFormat.format(date).replace(" ",
		 * "_").replace("-", "")); try { FileUtils.forceMkdir(destDir);
		 * FileUtils.copyDirectory(srcDir, destDir); } catch (IOException e) {
		 * e.printStackTrace(); }
		 */

	}

	@Override
	public void onTestStart(ITestResult iTestResult) {
		//System.out.println(Thread.currentThread().getId());
		System.out.println("I am in onTestStart " +  "Device: "+ iTestResult.getTestContext().getName()+ " Test Case Name: " + getTestMethodName(iTestResult) + " :start");
		// ExtentTestManager.getTest().log(LogStatus.INFO,
		// getTestMethodName(iTestResult) + " test is starting.");
		// Start operation for extent reports.
	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		System.out.println("I am in onTestSuccess method " + getTestMethodName(iTestResult) + " succeed");
		// Extent reports log operation for passed tests.
		// ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
		@SuppressWarnings("rawtypes")
		AppiumDriver driver = ContextManager.getDriver();
		try {
			AllureLifecycle lifecycle = Allure.getLifecycle();
			lifecycle.updateTestCase(testResult -> testResult.setDescription(getTeststatus(iTestResult)));
		} catch (Exception ignored) {
			// Ignore when no allure test case is active
		}
		// Allure ScreenShotRobot and SaveTestLog
		if (driver instanceof WebDriver) {
			System.out.println("Screenshot captured for test case:" + getTestMethodName(iTestResult));
			saveScreenshotPNG(driver);
		}
		
	}

	@Override
	public void onTestFailure(ITestResult iTestResult) {
		System.out.println("I am in onTestFailure method " + getTestMethodName(iTestResult) + " failed");

		// Get driver from BaseTest and assign to local webdriver variable.
		Object testClass = iTestResult.getInstance();
		@SuppressWarnings("rawtypes")
		AppiumDriver driver = ContextManager.getDriver();

		try {
			AllureLifecycle lifecycle = Allure.getLifecycle();
			lifecycle.updateTestCase(testResult -> testResult.setDescription(iTestResult.getThrowable().getLocalizedMessage()));
		} catch (Exception ignored) {
			// Ignore when no allure test case is active
		}
		// Allure ScreenShotRobot and SaveTestLog
		if (driver instanceof WebDriver) {
			System.out.println("Screenshot captured for test case:" + getTestMethodName(iTestResult));
			saveScreenshotPNG(driver);
		}

		// Save a log on allure.
		try {
			saveTextLog(getTestMethodName(iTestResult) + " failed and screenshot taken!");
		} catch (Exception ignored) {
			// Ignore when no allure test case is active
		}

	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		System.out.println("I am in onTestSkipped method " + getTestMethodName(iTestResult) + " skipped");
		// Extent reports log operation for skipped tests.
		// ExtentTestManager.getTest().log(LogStatus.SKIP,
		// getTestMethodName(iTestResult) + " Test Skipped");
		try {
			AllureLifecycle lifecycle = Allure.getLifecycle();
			lifecycle.updateTestCase(testResult -> testResult.setDescription(getTeststatus(iTestResult))); 
		} catch (Exception ignored) {
			// Ignore when no allure test case is active
		}
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
		System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
	}
}