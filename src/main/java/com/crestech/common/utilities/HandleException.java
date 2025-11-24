package com.crestech.common.utilities;

import org.testng.Assert;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Allure;

public class HandleException extends Exception {
    private String code;
    private AppiumDriver driver;
    
    public HandleException(AppiumDriver driver) {
        this.driver = driver;
    }
    public void handleException(Exception e, String message) {
        System.err.println("[ERROR] " + message + ": " + e.getMessage());
        e.printStackTrace();
    }

    public HandleException(String code, String message) {
        super(message);
        this.setCode(code);
    }

    public HandleException(String code, String message, Throwable cause) {
        super(message, cause);
        this.setCode(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public void throwHandleException(String code, String message, HandleException e) throws HandleException
    {
    	//System.out.println("Inside throwhandleexception kywprd");
		if(e.getCode()==null)
		{
			throw new HandleException(code, message,e);
		}
		else
		{
			throw new HandleException(e.getCode(), e.getMessage(),e.getCause());
			
		}
    }
    public void throwException(String code, String message, Exception e) throws HandleException
    {
    	//System.out.println("Inside throwexception keyword");
		throw new HandleException(code, message,e);
    }
    /**
     * Handles exception with message + stacktrace + Allure logging
     */
    public void handleException2(Exception e, String message) {
        try {
            // Log to console
            System.err.println("[ERROR] " + message + " -> " + e.getMessage());
            e.printStackTrace();

            // Log to Allure report
            Allure.step("❌ Exception: " + message);
            Allure.step("Stacktrace: " + e.toString());

            // Take a screenshot and attach it to Allure
            attachScreenshotToAllure("Failure Screenshot");

        } catch (Exception ex) {
            System.err.println("[ERROR] Failed while handling exception: " + ex.getMessage());
        } finally {
            // Fail the test gracefully
            Assert.fail(message + " | Exception: " + e.getMessage());
        }
    }
    /**
     * Capture and attach screenshot to Allure
     */
    private void attachScreenshotToAllure(String name) {
        try {
            if (driver != null) {
                byte[] screenshot = driver.getScreenshotAs(org.openqa.selenium.OutputType.BYTES);
                Allure.getLifecycle().addAttachment(name, "image/png", "png", screenshot);
            }
        } catch (Exception e) {
            System.err.println("[WARN] Unable to capture screenshot: " + e.getMessage());
        }
    }
}
