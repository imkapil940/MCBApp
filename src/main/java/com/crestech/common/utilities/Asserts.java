package com.crestech.common.utilities;

import java.io.IOException;

import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.crestech.base.UserBaseTest;
import com.crestech.config.ContextManager;

import io.appium.java_client.AppiumDriver;

public class Asserts extends UserBaseTest {

	public static void assertTrueScreenshot(boolean condition, String message) throws Exception {
		try {
			if (prop.getProperty("ReportType").trim().equalsIgnoreCase("Allure")) {
				Assert.assertTrue(condition, message);
				addAttachment();
			} else if (prop.getProperty("ReportType").trim().equalsIgnoreCase("extent")) {
				logScreenshot(condition, message, "assertTrue");
				Assert.assertTrue(condition, message);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); throw e;
		}
	}

	public static void addAttachment() {
		// TODO Auto-generated method stub
		
	}

	public static void assertTrue(boolean condition, String message) throws Exception {
		try {
			if (prop.getProperty("ReportType").trim().equalsIgnoreCase("Allure")) {
				Assert.assertTrue(condition, message);
			} else if (prop.getProperty("ReportType").trim().equalsIgnoreCase("extent")) {
				log(condition, message, "assertTrue");
				Assert.assertTrue(condition, message);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); throw e;
		}
	}

	public void assertFalseScreenshot(boolean condition, String message) throws Exception {
		try {
			if (prop.getProperty("ReportType").trim().equalsIgnoreCase("Allure")) {
				Assert.assertFalse(condition, message);
				addAttachment();
			} else if (prop.getProperty("ReportType").trim().equalsIgnoreCase("extent")) {
				logScreenshot(condition, message, "assertFalse");
				Assert.assertFalse(condition, message);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); throw e;
		}
	}

	public  void assertFalse(boolean condition, String message) throws Exception {
		try {
			if (prop.getProperty("ReportType").trim().equalsIgnoreCase("Allure")) {
				Assert.assertFalse(condition, message);
			} else if (prop.getProperty("ReportType").trim().equalsIgnoreCase("extent")) {
				log(condition, message, "assertFalse");
				Assert.assertFalse(condition, message);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); throw e;
		}
	}

	public static void assertEqualsScreenshot(String actual, String expected, String message) throws Exception {
		try {
			if (prop.getProperty("ReportType").trim().equalsIgnoreCase("Allure")) {
				Assert.assertEquals(actual, expected, message);
				addAttachment();
			} else if (prop.getProperty("ReportType").trim().equalsIgnoreCase("extent")) {
				try {
					if (actual.equals(expected)) {
						ContextManager.getExtentReportForPrecondition()
								.log(Status.PASS, message + "\n" + "Snapshot below:",
										MediaEntityBuilder
												.createScreenCaptureFromPath(
														ScreenshotUtils.getScreenshot(ContextManager.getDriver()))
												.build());
					} else {
						ContextManager.getExtentReportForPrecondition()
								.log(Status.FAIL, message + "\n" + "Snapshot below:",
										MediaEntityBuilder
												.createScreenCaptureFromPath(
														ScreenshotUtils.getScreenshot(ContextManager.getDriver()))
												.build());
					}
					Assert.assertEquals(actual, expected, message);
				} catch (IOException e) {
					e.printStackTrace(); throw e;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); throw e;
		}
	}

	public static void assertEquals(String actual, String expected, String message) throws Exception {
		try {
			if (prop.getProperty("ReportType").trim().equalsIgnoreCase("Allure")) {
				Assert.assertEquals(actual, expected, message);
			} else if (prop.getProperty("ReportType").trim().equalsIgnoreCase("extent")) {
				if (actual.equals(expected)) {
					ContextManager.getExtentReportForPrecondition().log(Status.PASS, message);
				} else {
					ContextManager.getExtentReportForPrecondition().log(Status.FAIL, message);
				}
				Assert.assertEquals(actual, expected, message);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); throw e;
		}
	}

	public static void log(boolean condition, String message, String assertCondition) {
		switch (assertCondition) {
		case "assertTrue": {
			if (condition)
				ContextManager.getExtentReportForPrecondition().log(Status.PASS, message);
			else
				ContextManager.getExtentReportForPrecondition().log(Status.FAIL, message);
			break;
		}
		case "assertFalse": {
			if (condition)
				ContextManager.getExtentReportForPrecondition().log(Status.FAIL, message);
			else
				ContextManager.getExtentReportForPrecondition().log(Status.PASS, message);
			break;
		}
		default:
			System.out.println("Incorrect option.");
		}
	}

	public  static void logScreenshot(boolean condition, String message, String assertCondition) throws Exception {
		try {
			switch (assertCondition) {
			case "assertTrue": {
				if (condition)
					try {
						ContextManager.getExtentReportForPrecondition()
								.log(Status.PASS, message + "\n" + "Snapshot below:",
										MediaEntityBuilder
												.createScreenCaptureFromPath(
														ScreenshotUtils.getScreenshot(ContextManager.getDriver()))
												.build());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				else
					try {
						ContextManager.getExtentReportForPrecondition()
								.log(Status.FAIL, message + "\n" + "Snapshot below:",
										MediaEntityBuilder
												.createScreenCaptureFromPath(
														ScreenshotUtils.getScreenshot(ContextManager.getDriver()))
												.build());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace(); throw e;
					}
				break;
			}
			case "assertFalse": {
				if (condition)
					try {
						ContextManager.getExtentReportForPrecondition()
								.log(Status.FAIL, message + "\n" + "Snapshot below:",
										MediaEntityBuilder
												.createScreenCaptureFromPath(
														ScreenshotUtils.getScreenshot(ContextManager.getDriver()))
												.build());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				else
					try {
						ContextManager.getExtentReportForPrecondition()
								.log(Status.PASS, message + "\n" + "Snapshot below:",
										MediaEntityBuilder
												.createScreenCaptureFromPath(
														ScreenshotUtils.getScreenshot(ContextManager.getDriver()))
												.build());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace(); throw e;
					}
				break;
			}
			default:
				System.out.println("Incorrect option.");
			}
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
	}
	
	public static void assertFail(String message) throws Exception {
		try {
			
//			Object androidAlert;
//			if(androidAlert.isAlertPresent()) {
//				System.out.println("Alert title :: "+ this.driver.switchTo().alert().getText()); 
//				Asserts.assertFail(this.driver.switchTo().alert().getText());
//			}	
			
			if (prop.getProperty("ReportType").trim().equalsIgnoreCase("Allure")) {
				Assert.fail( message);
			} else if (prop.getProperty("ReportType").trim().equalsIgnoreCase("extent")) {
				//log( message, "assertfail");
				Assert.fail( message);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); throw e;
		}
	}

}