package com.crestech.common.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    
    private static ExtentReports extent;
    private static String reportFolderPath;

    public static ExtentReports createInstance() {
        if (extent == null) {
            extent = new ExtentReports();

            // 🕒 Create folder with current date and time
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            reportFolderPath = System.getProperty("user.dir") + "/test-output/ExtentReports/" + timeStamp + "/";
            String reportFilePath = reportFolderPath + ".zip";

            // 🔧 Create Spark Reporter
            ExtentSparkReporter spark = new ExtentSparkReporter(reportFilePath);
            spark.config().setDocumentTitle("Mobile App Automation Report");
            spark.config().setReportName("MCB App Execution Results");
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setEncoding("utf-8");

            // 🧩 Attach Spark Reporter
            extent.attachReporter(spark);

            // Optional System Info
            extent.setSystemInfo("Platform", "Android/iOS");
            extent.setSystemInfo("Tester", "Kapil Sharma");
        }
        return extent;
    }

    public static String getReportFolderPath() {
        return reportFolderPath;
    }
}
