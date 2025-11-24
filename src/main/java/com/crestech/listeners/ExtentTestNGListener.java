package com.crestech.listeners;

import java.io.IOException;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.testng.*;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.markuputils.*;
import com.crestech.common.api.QaseUploader;
import com.crestech.common.utilities.ExtentManager;
import com.crestech.common.utilities.ScreenshotUtils;


import io.appium.java_client.AppiumDriver;

public class ExtentTestNGListener implements ITestListener ,IExecutionListener {

    private static ExtentReports extent = ExtentManager.createInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    // Qase project info
    private static final String projectCode = "FOMBPPCPOC";
    private static final String apiToken = "44c84833422e2bcd0f0a09e09323bd3aa9a1734bd0d26e80e33188f58001babd";

    @Override
    public void onExecutionStart() {
        try {
            PatternLayout layout = new PatternLayout("[%t] %d{HH:mm:ss,SSS} %-5p [%c{1}] %m%n");
            FileAppender fileAppender = new FileAppender(layout, "logs/migo.log", false);
            Logger.getRootLogger().addAppender(fileAppender);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass(MarkupHelper.createLabel(result.getMethod().getMethodName() + " PASSED", ExtentColor.GREEN));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().fail(result.getThrowable());
        
        // ✅ Fetch driver from context
        ITestContext context = result.getTestContext();
        Object driverObj = context.getAttribute("driver");

        if (driverObj instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) driverObj;
            String screenshotPath = null;
			try {
				screenshotPath = ScreenshotUtils.captureScreenShot(result.getMethod().getMethodName(), driver);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            try {
                test.get().addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip(MarkupHelper.createLabel(result.getMethod().getMethodName() + " SKIPPED", ExtentColor.ORANGE));
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        String reportPath = ExtentManager.getReportFolderPath();

        System.out.println("✅ Extent Report generated at: " + reportPath);

        // 🔹 Qase Integration
     //   String projectCode = "FOMBPPCPOC";
      //  String apiToken = "44c84833422e2bcd0f0a09e09323bd3aa9a1734bd0d26e80e33188f58001babd";

        // true = reuse latest Qase run
        // false = always create new run
        QaseUploader.uploadExtentReportToQase(projectCode, apiToken, reportPath, true);
    }

}
