package com.crestech.base;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.log4testng.Logger;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.crestech.appium.utils.ConfigurationManager;
import com.crestech.appium.utils.PcloudyUtils;
import com.crestech.common.api.QaseAPIClient;
import com.crestech.common.api.QaseUploader;
import com.crestech.common.api.ZipUtils;
import com.crestech.common.utilities.ExcelUtils;
import com.crestech.common.utilities.ExtentManager;
import com.crestech.common.utilities.ScreenshotUtils;
import com.crestech.common.utilities.WaitUtils;
import com.crestech.config.ContextManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.UUID;
import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.Listeners;

/**
 *  *  * @author KAPIL SHARMA  *  
 */

public class UserBaseTest extends TestListenerAdapter implements ITestListener {

	@SuppressWarnings("rawtypes")
	public AppiumDriver driver = null;
	public static ConfigurationManager prop;
	protected boolean dontStopAppOnReset = false; 
	public String device_udid;
	private String lastManufacturer;
	private String lastMinVer;
	private String lastMaxVer;
	private AppiumDriverLocalService service;
	private AppiumServiceBuilder builder;
	private static final AllureLifecycle ALLURE_LIFECYCLE = Allure.getLifecycle();
	public List<String> excelDataList;
	public ScreenshotUtils scrShotUtils = null;
	private static Object syncObj = new Object();
	WaitUtils waitut=null;
	protected static QaseAPIClient qase;
	protected static PcloudyUtils pcloudyutils;
    private static String qaseProjectCode = null;
    private String videoUrl;
    public static String pcloudySessionId = null;
  //  private static final String REPORT_PATH = System.getProperty("user.dir") + "/reports/ExtentReport.html";

	Logger logger = Logger.getLogger(UserBaseTest.class);

	public UserBaseTest() 	{
		try {
			prop = ConfigurationManager.getInstance();
			//scrShotUtils = new ScreenshotUtils();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author KAPIL SHARMA
	 * @param name- device name/udid
	 * @throws Exception
	 */
	@Parameters({ "device", "version", "os", "manafacturer", "min_Ver", "max_Ver", "individual_ID", "app_Name"})
	@BeforeMethod(alwaysRun = true)
	public void startApp(String device, String version, String os, String manafacturer, String min_Ver, String max_Ver, String individual_ID, Method method, String app_Name,ITestContext context) throws Exception {
		logger.info("Inside Before Method");
		
		// Clear any stale driver references before starting new test
		if (this.driver != null) {
			try {
				// Check if old driver is still valid
				if (this.driver.getSessionId() != null) {
					logger.warn("⚠️ Found existing driver with valid session in @BeforeMethod - this should not happen");
				}
			} catch (Exception e) {
				// Driver already quit or invalid - this is expected
				logger.info("Previous driver already quit - proceeding with new driver initialization");
			}
		}
		// Clear stale references
		this.driver = null;
		ContextManager.setDriver(null);
		
		if (prop.getProperty("ReportType").trim().equalsIgnoreCase("Extent")) {
			ContextManager.createNode(method.getName() + " " + device);
		}
		// Preserve incoming constraints for fallback use
		this.lastManufacturer = manafacturer;
		this.lastMinVer = min_Ver;
		this.lastMaxVer = max_Ver;
		synchronized (syncObj) {
		excelDataList = ExcelUtils.readExcel(System.getProperty("user.dir") + File.separator+"TestData"+File.separator+"TestData.xlsx", os, "Capabilities");
		logger.info("Capabilities row for key '" + os + "': " + excelDataList);
		}
		DesiredCapabilities androidCaps = androidNative(excelDataList, device, version, os, manafacturer, min_Ver, max_Ver, individual_ID);
		
		try {
			this.driver = startingServerInstance(androidCaps, os, excelDataList);
			//this.driver = new AndroidDriver(new URL("https://device.pcloudy.com/appiumcloud/wd/hub"), androidCaps);
			//	PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(5)), this);
			ContextManager.setDriver(this.driver); 
			waitut =new WaitUtils(this.driver);
			waitut.ImplicitlyWait(30);
			
			context.setAttribute("driver", driver);
			
		} catch (Exception e) {
		    logger.error("startApp() failed in @BeforeMethod: " + e.getMessage(), e);
		    System.out.println("startApp() configuration failure: " + e.getMessage());
		    e.printStackTrace();

		    if (prop.getProperty("ReportType").trim().equalsIgnoreCase("Extent")) {
		        ContextManager.getExtentReportForPrecondition()
		            .skip(MarkupHelper.createLabel("Test Case is SKIPPED", ExtentColor.YELLOW));
		        ContextManager.getExtentReportForPrecondition().log(Status.SKIP, e.getMessage());
		    }

		    // Print full stack trace in logs for clarity
		    StringWriter sw = new StringWriter();
		    e.printStackTrace(new PrintWriter(sw));

		    // ✅ Skip the test instead of failing configuration
		    throw new SkipException("Skipping test due to driver initialization failure: " + e.getMessage());
		}

	}

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() throws Exception {
//		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
//	        public void run() {
//	        	logger.info("In the middle of a shutdown");
//	            flushReport();
//	        }
//	        }));
		try {
            logger.info("Inside before suite");
            String projectCode = "FOMBPPCPOC";
            String token = "44c84833422e2bcd0f0a09e09323bd3aa9a1734bd0d26e80e33188f58001babd"; // consider loading from config or env
            qaseProjectCode = projectCode;
            qase = new QaseAPIClient(projectCode, token);
            
            // Use default epic description - will be updated when test runs
            String epicDesc = "FOMBPPCPOC-1 - Verify Confirmation Transcation Screen";
            String runTitle = "Epic: " + epicDesc + " - " + System.currentTimeMillis();
            qase.createRun(runTitle);
            if (prop.getProperty("ReportType").trim().equalsIgnoreCase("Extent")) {
                ContextManager.startReport();
            } else {
                try {
                    File fileClean = new File(System.getProperty("user.dir") + "/allure-results");
                    FileUtils.deleteDirectory(fileClean);
                } catch (Exception e) {
                    System.out.println("Dir does not exist");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	/*********************************
	 * FOR Extent Report Implementation
	 * @throws Exception 
	 ***********************************************/

	@Parameters({ "device", "os" })
	@AfterMethod
	public void getResult(ITestResult result, String device, String os) throws Exception {  
		if (prop.getProperty("ReportType").trim().equalsIgnoreCase("Extent")) {
			String screenshotProperty = prop.getProperty("ScreenshotForReport").trim();
			if (result.getStatus() == ITestResult.FAILURE) {
				ContextManager.getExtentReportForPrecondition().fail(MarkupHelper.createLabel("Test Case is FAILED", ExtentColor.RED));
				if (screenshotProperty.equalsIgnoreCase("None")) {
					ContextManager.getExtentReportForPrecondition().info("TestCase Failed");
				} else {
					try {
						ContextManager.getExtentReportForPrecondition().log(Status.FAIL, "Snapshot below:", MediaEntityBuilder
								.createScreenCaptureFromPath(ScreenshotUtils.getScreenshot(driver)).build());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				ContextManager.getExtentReportForPrecondition().fail(result.getThrowable());
			} else if (result.getStatus() == ITestResult.SUCCESS) {
				ContextManager.getExtentReportForPrecondition().pass(MarkupHelper.createLabel("Test Case is PASSED", ExtentColor.GREEN));
				if (screenshotProperty.equalsIgnoreCase("pass")) {
					try {
						ContextManager.getExtentReportForPrecondition().log(Status.PASS, "Snapshot below:", MediaEntityBuilder
								.createScreenCaptureFromPath(ScreenshotUtils.getScreenshot(driver)).build());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				ContextManager.getExtentReportForPrecondition().skip(MarkupHelper.createLabel("Test Case is SKIPPED", ExtentColor.YELLOW));
				ContextManager.getExtentReportForPrecondition().skip(result.getThrowable());
			}
		}
		try {
	        if (driver != null && driver.getSessionId() != null) {
	            pcloudySessionId = driver.getSessionId().toString();
	            logger.info("✅ Captured pCloudy Session ID before quit: " + pcloudySessionId);
	        } else {
	            logger.warn("⚠️ Could not capture session ID — driver or session is null.");
	        }
	    } catch (Exception e) {
	        logger.error("⚠️ Exception capturing session ID: " + e.getMessage());
	    }
		try {
	            updateResult(result);
	        } catch (Exception e) {
	            logger.error("Error while updating Qase result: " + e.getMessage());
	        }

				System.out.println("CloseApp");
				// Safely quit and clear driver references
				try {
					AppiumDriver driverToQuit = ContextManager.getDriver();
					if (driverToQuit != null) {
						try {
							// Check if session is still valid before quitting
							if (driverToQuit.getSessionId() != null) {
								driverToQuit.quit();
								logger.info("✅ Driver quit successfully in @AfterMethod");
							}
						} catch (Exception e) {
							logger.warn("⚠️ Driver already quit or session invalid: " + e.getMessage());
						}
					}
				} catch (Exception e) {
					logger.error("⚠️ Exception while quitting driver in ContextManager: " + e.getMessage());
				}
				
				// Clear driver references to prevent reuse of quit driver
				this.driver = null;
				ContextManager.setDriver(null);
				
				if (os != null && os.equalsIgnoreCase("Android") && service != null) {
					try {
						service.stop();
					} catch (Exception e) {
						logger.warn("⚠️ Exception stopping Appium service: " + e.getMessage());
					}
				}
	}


	  
	public void updateResult(ITestResult result) {
	    if (qase == null) return;

	    try {
	        String caseId = null;
	        String desc = result.getMethod().getDescription();

	        if (desc != null && desc.contains("qaseCaseId=")) {
	            int idx = desc.indexOf("qaseCaseId=");
	            String after = desc.substring(idx + "qaseCaseId=".length());
	            String candidate = after.split("\\s+|,|;")[0].trim();
	            caseId = candidate;
	        }

	        if (caseId == null) {
	            Object attr = result.getTestContext().getAttribute("qaseCaseId");
	            if (attr != null) {
	                caseId = String.valueOf(attr);
	            }
	        }

	        if (caseId == null) {
	            logger.info("No Qase case id mapping found for test: " + result.getMethod().getMethodName());
	            return;
	        }

	        String status;
	        if (result.getStatus() == ITestResult.SUCCESS) {
	            status = "passed";
	        } else if (result.getStatus() == ITestResult.FAILURE) {
	            status = "failed";
	        } else {
	            status = "skipped";
	        }

	        String comment = (result.getThrowable() != null)
	                ? result.getThrowable().getMessage()
	                : "Executed by automation";

	        // ✅ Get Epic title
	        String epicTitle = getEpicTitleFromClass(result);

	        // ✅ Add result with Epic Title
	        try {
	            int caseIdInt = Integer.parseInt(caseId);
	            qase.addTestResultWithTitle(caseIdInt, status, comment, epicTitle);
	        } catch (NumberFormatException nfe) {
	            qase.updateTestResultWithTitle(qaseProjectCode, qase.getRunId(), caseId, status, epicTitle);
	        }

	    } catch (Exception ex) {
	        logger.error("Exception while updating Qase: " + ex.getMessage());
	    }
	}
	
	
	@AfterSuite(alwaysRun = true)
	public void flushReport() {
		System.out.println("My Report Flushed Start.....");
		
		try {
			if (prop.getProperty("ReportType").trim().equalsIgnoreCase("Extent")) {
				File src= new File(System.getProperty("user.dir") + File.separator+ "AllureReport");
				try {
					FileUtils.deleteDirectory(src);
				} catch (IOException e) {
					e.printStackTrace();
				}
				ContextManager.endReport();
			}
			else {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
				Date date = new Date();
				File srcDir = new File(System.getProperty("user.dir") + File.separator+ "allure-results");
				File destDir = new File(System.getProperty("user.dir") + File.separator+ "AllureReport" + File.separator+ "Allure_"
						+ dateFormat.format(date).replace(" ", "_").replace("-", "") + File.separator+ "allure-results");
				
				try {
					File categoriesJsonFile = new File(System.getProperty("user.dir") + File.separator+ "src"+ File.separator+ 
							"test"+ File.separator+ "resources"+ File.separator+ "categories.json");
					
			        FileUtils.copyFileToDirectory(categoriesJsonFile, srcDir);
					FileUtils.forceMkdir(destDir);
					FileUtils.copyDirectory(srcDir, destDir);
					Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"allure generate "+destDir+" -o "+System.getProperty("user.dir") + File.separator+ "AllureReport"+ File.separator+"Allure_" + 
							dateFormat.format(date).replace(" ", "_").replace("-", "")+ File.separator+"allure-report");
					Runtime.getRuntime().exec("taskkill /f /im cmd.exe") ;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (qase != null) {
                int runId = qase.getRunId();
                if (runId > 0) {
                    qase.getRunSummary(qaseProjectCode, runId);
                    qase.completeRun(qaseProjectCode, runId);
                } else {
                    logger.info("Qase runId not available");
                }
            }
			String resultsDir = "allure-results";
			new File(resultsDir).mkdirs();
			String uuid = UUID.randomUUID().toString();

			String html = "<html><body><a href='" + videoUrl +
			              "' target='_blank'>Click here to view pCloudy Execution Video</a></body></html>";
			Files.writeString(Path.of(resultsDir, uuid + "-attachment.html"), html);

			String json = "{\n" +
			  "  \"name\": \"pCloudy Execution Video\",\n" +
			  "  \"source\": \"" + uuid + "-attachment.html\",\n" +
			  "  \"type\": \"text/html\"\n" +
			  "}";
			Files.writeString(Path.of(resultsDir, uuid + "-attachment.json"), json);

		} catch (Exception e) {
			e.printStackTrace();
		}
	//	Qase.attach(REPORT_PATH);
		System.out.println("My Report Flushed End.....");
	}

	@AfterSuite(alwaysRun = true)
	public void closeSuite() {
		try {
            logger.info("Inside @AfterSuite - closing all drivers...");
            if (driver != null) {
                driver.quit();
                driver = null;
                logger.info("✅ Driver closed successfully after suite execution.");
            } else {
                logger.warn("⚠️ No driver instance found during @AfterSuite cleanup.");
            }
        } catch (Exception e) {
            logger.error("⚠️ Exception during suite cleanup: {}");
        }
    }


	

	/**
	 * This will return the Desired Capabilities instance
	 *
	 * @param device Id
	 * @return capabilities instance
	 * @throws IOException
	 */

	public synchronized DesiredCapabilities androidNative(List<String> s, String device_udid, String version, String os, String manafacturer, String min_Ver, String max_Ver, String individual_ID)
			throws IOException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		switch (os) {
		case "Android":
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device_udid);
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, version);
			capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, s.get(1));
			capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY,  s.get(1));
			capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,s.get(2));
			capabilities.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir") + "\\App\\" + s.get(14));
			capabilities.setCapability(MobileCapabilityType.UDID, s.get(6));
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");

			if (checkDeviceVersion(version))
				capabilities.setCapability("automationName", s.get(4));
			else
				capabilities.setCapability("automationName", "UiAutomator1");

			if (dontStopAppOnReset == true)
				capabilities.setCapability(AndroidMobileCapabilityType.DONT_STOP_APP_ON_RESET, true);
			else
				capabilities.setCapability(MobileCapabilityType.NO_RESET, true);

			capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 600);
			break;

		case "iOS":
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device_udid);
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, version);
			capabilities.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir") + "/App/" + s.get(3));
			capabilities.setCapability(MobileCapabilityType.UDID, s.get(6));
			capabilities.setCapability("automationName", s.get(4));
			capabilities.setCapability("bundleId", s.get(7));
			if (dontStopAppOnReset == true) {
				capabilities.setCapability(AndroidMobileCapabilityType.DONT_STOP_APP_ON_RESET, true);
			} else {
				capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
			}
			capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 600);
			break;

		case "Android_Chrome":
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
			//capabilities.setCapability("automationName", s.get(4));
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, version);
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device_udid);
			//capabilities.setCapability(MobileCapabilityType.UDID, s.get(6));
			capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, s.get(5));
			//capabilities.setCapability("chromedriverExecutable", prop.getProperty("browserLocation"));
			//			if (checkDeviceVersion(version)) {
			//				capabilities.setCapability("automationName", s.get(4));
			//			} else {
			//				capabilities.setCapability("automationName", "UiAutomator1");
			//			}
			if (dontStopAppOnReset == true) {
				capabilities.setCapability(AndroidMobileCapabilityType.DONT_STOP_APP_ON_RESET, true);
			} else {
				capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
			}
			break;

		case "pCloudyAndroid":

			//System.out.println(device_udid + "," + version);
			capabilities.setCapability("pCloudy_Username", s.get(12));
			capabilities.setCapability("pCloudy_ApiKey", s.get(13));
			capabilities.setCapability("pCloudy_Endpoint", s.get(3));
			capabilities.setCapability("pCloudy_DurationInMinutes", 20);
			capabilities.setCapability("newCommandTimeout", 20000);
			capabilities.setCapability("launchTimeout", 120000); // Increased to 120 seconds for slower app launches
			capabilities.setCapability("pCloudy_DeviceFullName", device_udid);
			capabilities.setCapability("platformVersion", version);
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("acceptInsecureCerts", true);
			capabilities.setCapability("pCloudy_ApplicationName", s.get(14));
			capabilities.setCapability("appPackage", s.get(2));
			capabilities.setCapability("appActivity", s.get(1));
			// Add appWaitActivity and appWaitPackage for better launch reliability
			capabilities.setCapability("appWaitActivity", s.get(1));
			capabilities.setCapability("appWaitPackage", s.get(2));
			capabilities.setCapability("appWaitDuration", 60000); // Wait up to 60 seconds for app to launch
			capabilities.setCapability("pCloudy_WildNet", false);
			capabilities.setCapability("autoGrantPermissions", true);
			capabilities.setCapability("pCloudy_EnableVideo", false);
			capabilities.setCapability("noReset", true);
			capabilities.setCapability("fullReset", false);
			capabilities.setCapability("project","Ixigo_Android");
			capabilities.setCapability("tag", "PublicCloud");
//			capabilities.setCapability("pCloudy_DeviceManafacturer", manafacturer);
//			capabilities.setCapability("pCloudy_MinVersion",min_Ver); 
//			capabilities.setCapability("pCloudy_MaxVersion",max_Ver); 
//			capabilities.setCapability("pCloudy_Individual",individual_ID);
//			capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
			
			if (checkDeviceVersion(version)) {
				capabilities.setCapability("automationName", "UiAutomator2");
				capabilities.setCapability("uiautomator2ServerLaunchTimeout", 120000); // Increased timeout
				capabilities.setCapability("noSign", true);
			} else {
				capabilities.setCapability("automationName", "UiAutomator1");
			}

			break;

		case "pCloudyIOS":
			capabilities.setCapability("pCloudy_Username", s.get(12));
			capabilities.setCapability("pCloudy_ApiKey", s.get(13));
			capabilities.setCapability("pCloudy_ApplicationName", s.get(14));
			capabilities.setCapability("pCloudy_DurationInMinutes", 20);
	        capabilities.setCapability("pCloudy_DeviceFullName", device_udid);
		    capabilities.setCapability("platformVersion", version);
			capabilities.setCapability("newCommandTimeout", 20000);
			capabilities.setCapability("launchTimeout", 90000);
			capabilities.setCapability("bundleId", s.get(7));
			capabilities.setCapability("automationName", s.get(4));
			capabilities.setCapability("pCloudy_WildNet", false);
			capabilities.setCapability("platformName", "ios");
			capabilities.setCapability("noReset", true);
			capabilities.setCapability("fullReset", false);
		    capabilities.setCapability("pCloudy_EnableVideo", true);
		    capabilities.setCapability("autoGrantPermissions", true);
		    capabilities.setCapability("acceptAlerts", true);
		    capabilities.setCapability("idleTimeout",10);
		    //capabilities.setCapability("autoAcceptAlerts", "true"); 
		    //capabilities.setCapability("autoDissmissAlerts", "true");
		    capabilities.setCapability("locationServicesAuthorized", true);
		    capabilities.setCapability("clearSystemFiles", true);
		    
//		    capabilities.setCapability("project", "DBSAutomation");
//			capabilities.setCapability("build", "DBSBuild1");
//			capabilities.setCapability("name", method.getName());//method.getName()
//			capabilities.setCapability("tag", "Pr_Testing");
//			capabilities.setCapability("pCloudy_DeviceManafacturer", manafacturer);
//			capabilities.setCapability("pCloudy_MinVersion",min_Ver); 
//			capabilities.setCapability("pCloudy_MaxVersion",max_Ver); 
//			capabilities.setCapability("pCloudy_Individual",individual_ID);

			break;

		case "pCloudyAndroidChrome":
			capabilities.setCapability("pCloudy_Username", s.get(12));
			capabilities.setCapability("pCloudy_ApiKey", s.get(13));
			capabilities.setCapability("pCloudy_DurationInMinutes", s.get(15));
			capabilities.setCapability("newCommandTimeout", 600);
			capabilities.setCapability("launchTimeout", 90000);
			capabilities.setCapability("pCloudy_DeviceFullName", device_udid);
			capabilities.setCapability("platformVersion", version);
			capabilities.setCapability("platformName", "Android");
			capabilities.setBrowserName(s.get(5));
			capabilities.setCapability("pCloudy_WildNet", "false");

			if (checkDeviceVersion(version)) {
				capabilities.setCapability("automationName", "UiAutomator2");
				capabilities.setCapability("uiautomator2ServerLaunchTimeout", 90000);
				capabilities.setCapability("noSign", true);
			} else {
				capabilities.setCapability("automationName", "UiAutomator1");
			}
			break;

		default:
			System.out.println("Please Select pCloudyAndroid OR pCloudyIOS in properties File");
			capabilities = null;
		}

		return capabilities;
	}

	/**
	 * This will Start the Server
	 *
	 * @param Capabilities
	 * @return Driver Instance
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public AppiumDriver startingServerInstance(DesiredCapabilities androidCaps, String os, List<String> s)
			throws Exception {

		if (os.equalsIgnoreCase("Android") || os.equalsIgnoreCase("Android_Chrome")) {
			// if simple appium installed
			/*
			 * driver = new AndroidDriver<RemoteWebElement>(new
			 * URL("http://127.0.0.1:4723/wd/hub"), androidCaps);
			 */


			//			  //install nodejs in your system ->through nodejs install appium 
			//			  // Build the Appium service
			builder = new AppiumServiceBuilder();
			builder.withIPAddress(prop.getProperty("server_address"));
			builder.usingPort(Integer.parseInt(prop.getProperty("port")));
			builder.withCapabilities(androidCaps);
			builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
			builder.withLogFile(new File("C:\\Users\\Public\\Desktop\\Appium.text"));


			// Start the server with the builder 
			service =  AppiumDriverLocalService.buildService(builder);
			
			//System.out.println(service.getUrl().toString()); 
			try {
				service.start();
				logger.info("Appium service started successfully at: " + service.getUrl());
			} catch (Exception serviceEx) {
				logger.error("Failed to start Appium service: " + serviceEx.getMessage());
				// Don't stop service if it failed to start
				service = null;
			}

			//			Process p = Runtime.getRuntime().exec("cmd.exe /c start appium");
			//			Thread.sleep(5000);
			
			//	 driver = new AndroidDriver<RemoteWebElement>(service.getUrl(), androidCaps);
			//This time out is set because test can be run on slow Android SDK emulator
			// PageFactory.initElements(new AppiumFieldDecorator(driver, ofSeconds(5)), this);

			// Use service URL if service is running, otherwise use default localhost
			if (service != null && service.isRunning()) {
				driver = new AndroidDriver(service.getUrl(), androidCaps);
			} else {
				logger.warn("Appium service not running, using default localhost connection");
				driver = new AndroidDriver(androidCaps);
			}
			String sessionId = driver.getSessionId().toString();
			System.out.println("Session ID: " + sessionId);
		} else if (os.equalsIgnoreCase("pCloudyAndroid") || os.equalsIgnoreCase("pCloudyAndroidChrome")) {
			System.out.println("androidCaps: "+androidCaps); 
			String hub = s.get(3) + "/appiumcloud/wd/hub";
			System.out.println("Connecting to pCloudy at: " + hub);
			URL hubUrl = new URL(hub);
			
			int attempts = 0;
			Exception last = null;
			while (attempts < 3) {
				try {
					System.out.println("Attempting AndroidDriver init, attempt: " + (attempts + 1));
					// Use the standard constructor - compatibility will be handled by dependency versions
					driver = new AndroidDriver(hubUrl, androidCaps);
					String sessionId = driver.getSessionId().toString();
					System.out.println("Session ID: " + sessionId);
					
					// Wait for app to launch and verify it's running
					if (os.equalsIgnoreCase("pCloudyAndroid")) {
						logger.info("Waiting for MCB app to launch...");
						try {
							String appPackage = (String) androidCaps.getCapability("appPackage");
							if (appPackage != null && !appPackage.isEmpty()) {
								// Wait for the app to be ready - give it time to fully launch
								Thread.sleep(10000); // Wait 10 seconds for initial app launch
								logger.info("✅ Driver session established. App should be launching...");
								
								// Try to activate the app explicitly to ensure it's in foreground
								try {
									logger.info("Activating MCB app: " + appPackage);
									((AndroidDriver) driver).activateApp(appPackage);
									Thread.sleep(5000); // Wait for activation
									logger.info("✅ MCB app activation attempted");
								} catch (Exception activateEx) {
									logger.warn("Could not activate app explicitly (app may already be active): " + activateEx.getMessage());
								}
								
								// Additional wait to ensure app is fully loaded
								Thread.sleep(5000);
								logger.info("✅ MCB app launch sequence completed");
							}
						} catch (Exception launchEx) {
							logger.warn("Error during app launch verification: " + launchEx.getMessage() + ". Continuing with test...");
						}
					}
					
					break;
				} catch (Exception ex) {
					attempts++;
					last = ex;
					Throwable root = ex;
					while (root.getCause() != null) root = root.getCause();
					System.out.println("Driver init failed for pCloudy (attempt " + attempts + "): " + root.getClass().getSimpleName() + " - " + root.getMessage());
					ex.printStackTrace();
					
					// Fallback: if requested device is not available, try dynamic allocation on next attempt
					String rootMsg = String.valueOf(root.getMessage());
					boolean deviceUnavailable = rootMsg != null && (
							rootMsg.toLowerCase().contains("requested device is not available")
							|| rootMsg.toLowerCase().contains("no device is available"));
					if (attempts == 1 && deviceUnavailable) {
						try {
							System.out.println("Switching to dynamic device allocation fallback for next attempt...");
							
							// Rebuild capabilities excluding strict device targeting fields
							DesiredCapabilities rebuilt = new DesiredCapabilities();
							for (String capName : androidCaps.getCapabilityNames()) {
								String name = capName == null ? "" : capName;
								String lower = name.toLowerCase();
								// Drop exact device and strict version pins (handle possible appium: prefix)
								if (lower.endsWith("pcloudy_devicefullname")) continue;
								if (lower.endsWith("platformversion")) continue;
								
								Object value = androidCaps.getCapability(capName);
								// Skip empty-string values as pCloudy rejects empty fields
								if (value instanceof String && ((String) value).trim().isEmpty()) continue;
								rebuilt.setCapability(capName, androidCaps.getCapability(capName));
							}
							DesiredCapabilities fallbackCaps = rebuilt;
								
							// Derive manufacturer from DeviceFullName if present (e.g., SAMSUNG_GalaxyA33_Android_14.0.0_xxxx)
							String deviceFull = String.valueOf(androidCaps.getCapability("pCloudy_DeviceFullName"));
							if (deviceFull != null && deviceFull.contains("_")) {
								String manufacturer = deviceFull.split("_")[0];
								if ("SAMSUNG".equalsIgnoreCase(manufacturer)) {
									manufacturer = "Samsung";
								}
								// Use correct key; also set legacy misspelled key for backward compatibility
								fallbackCaps.setCapability("pCloudy_DeviceManufacturer", manufacturer);
								fallbackCaps.setCapability("pCloudy_DeviceManafacturer", manufacturer);
							}
							
							// Constrain version range to the requested version to keep environment stable
							// Prefer suite-provided range; otherwise fall back to requested platformVersion
							if (lastMinVer != null && !lastMinVer.isBlank()) {
								fallbackCaps.setCapability("pCloudy_MinVersion", lastMinVer);
							}
							if (lastMaxVer != null && !lastMaxVer.isBlank()) {
								fallbackCaps.setCapability("pCloudy_MaxVersion", lastMaxVer);
							}
							if ((fallbackCaps.getCapability("pCloudy_MinVersion") == null
									|| fallbackCaps.getCapability("pCloudy_MaxVersion") == null)) {
								String requestedVersion = String.valueOf(androidCaps.getCapability("platformVersion"));
								if (requestedVersion != null && !requestedVersion.isBlank()) {
									fallbackCaps.setCapability("pCloudy_MinVersion", requestedVersion);
									fallbackCaps.setCapability("pCloudy_MaxVersion", requestedVersion);
								}
							}
							
							// If manufacturer not derived, use provided one if available
							if (fallbackCaps.getCapability("pCloudy_DeviceManufacturer") == null
									&& lastManufacturer != null && !lastManufacturer.isBlank()) {
								fallbackCaps.setCapability("pCloudy_DeviceManufacturer", lastManufacturer);
								fallbackCaps.setCapability("pCloudy_DeviceManafacturer", lastManufacturer);
							}
							
							androidCaps = fallbackCaps;
							
							// Log final fallback caps keys to verify we didn't keep empty DeviceFullName
							try {
								System.out.println("Fallback capabilities keys:");
								for (String k : androidCaps.getCapabilityNames()) {
									System.out.println(" - " + k + " = " + androidCaps.getCapability(k));
								}
							} catch (Exception ignoreLog) {}
						} catch (Exception ignore) {
							// If fallback setup fails, continue with normal retry
						}
					}
					if (attempts < 3) {
						try { Thread.sleep(3000); } catch (InterruptedException ie) { /* ignore */ }
					}
				}
			}
			if (driver == null && last != null) throw last;
		}
		else {
			// iOS Driver initialization for pCloudy
			String hub = s.get(3) + "/appiumcloud/wd/hub";
			URL hubUrl = new URL(hub);
			
			driver = new IOSDriver(hubUrl, androidCaps);
			String sessionId = driver.getSessionId().toString();
			System.out.println("Session ID: " + sessionId);
		}
		return driver;
	}

	public void quitDriver() {
	    try {
	        if (driver != null && driver.getSessionId() != null) {
	            pcloudySessionId = driver.getSessionId().toString();
	            logger.info("Captured session ID before quitting driver: " + pcloudySessionId);
	        } else {
	            logger.warn("⚠️ No active session to capture before quitting driver.");
	        }

	        if (driver != null) {
	            driver.quit();
	            driver = null;
	            logger.info("✅ Driver quit successfully.");
	        }
	    } catch (Exception e) {
	        logger.error("⚠️ Exception while quitting driver: " + e.getMessage(), e);
	    }
	}
	/**
	 * This method is to check Memory is deducted or not
	 *
	 * @param availableMemoryBeforeDownload
	 * @param availableMemoryAfterDownload
	 * @return
	 */
	public boolean isMemoryDeducted(double availableMemoryBeforeDownload, double availableMemoryAfterDownload) {

		if (availableMemoryBeforeDownload > availableMemoryAfterDownload)
			return true;
		else
			return false;
	}

	/**
	 * This method is to check Memory is reverted or not
	 *
	 * @param availableMemoryBeforeCancel -Memory Space At start
	 * @param availableMemoryAfterCancel   -Memory Space After Cancellation
	 * @return-return the boolean value
	 */
	public boolean isMemoryReverted(double availableMemoryAfterCancel, double availableMemoryBeforeCancel) {

		if (availableMemoryAfterCancel > availableMemoryBeforeCancel)
			return true;
		else
			return false;
	}

	public boolean isEpisodeFitOnDeviceQueue(int startEpisode, int lastEpisode, Set<Integer> episodeList) {
		for (Integer episode : episodeList) {
			if (episode >= startEpisode && episode <= lastEpisode) {
				return true;
			} else {
				break;
			}
		}
		return false;
	}

	/**
	 * To check whether the device version is >=6
	 *
	 * @param deviceVersion
	 * @return true/false
	 */
	public boolean checkDeviceVersion(String deviceVersion) {
		try {
			String str = deviceVersion.replace(".", ",").split(",")[0];
			int version = Integer.parseInt(str);
			if (version >= 6) {
				return true;
			}
			return false;
		} catch (Exception e) {
			System.out.println("version not given. Required to set automationName:UiAutomator2");
		}

		return false;
	}

	public static void addAttachment() {
		ALLURE_LIFECYCLE.addAttachment(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yy_hh:mm:ss")), "image/png", "png", ((TakesScreenshot) ContextManager.getDriver()).getScreenshotAs(OutputType.BYTES));
	}

	public void addAttachment(RemoteWebDriver driver) {
		ALLURE_LIFECYCLE.addAttachment(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yy_hh:mm:ss")), "image/png", "png", ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
	}
	private String getEpicTitleFromClass(ITestResult result) {
	    try {
	        Class<?> testClass = result.getTestClass().getRealClass();

	        // if you're using io.qameta.allure.Epic
	        io.qameta.allure.Epic epic = testClass.getAnnotation(io.qameta.allure.Epic.class);
	        if (epic != null) {
	            return epic.value();
	        }

	        // fallback to class name or test description
	        return testClass.getSimpleName();
	    } catch (Exception e) {
	        return "Untitled Epic";
	    }
	}

}
