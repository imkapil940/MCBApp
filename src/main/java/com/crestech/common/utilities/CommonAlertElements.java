package com.crestech.common.utilities;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;

import com.crestech.annotation.values.ElementDescription;

import org.openqa.selenium.WebElement;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class CommonAlertElements {
	
	@SuppressWarnings("rawtypes")
	public AppiumDriver driver1;
	@SuppressWarnings("rawtypes")
	public CommonAlertElements(AppiumDriver driver) {
		this.driver1 = driver; 
	//	PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(5)), this);
	}

	@SuppressWarnings("rawtypes")
	public AppiumDriver driver;
	@ElementDescription(value = "Toast Message Element")
	@AndroidFindBy(xpath = "//android.widget.Toast")
	private WebElement ToastMessageElement;

	@ElementDescription(value = "Toast Message Element List")
	@AndroidFindBy(xpath = "//android.widget.Toast")
	private List<WebElement> ToastMessageElementList;

	@ElementDescription(value = "Toggle List")
	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='android:id/switch_widget']")
	private List<WebElement> toggleList;

	@ElementDescription(value = "Title Alert")
	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='android:id/alertTitle']")
	private WebElement TitleAlert;

	@ElementDescription(value = "Message Alert")
	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='android:id/message']")
	private WebElement MessageAlert;

	@ElementDescription(value = "Ok Alert")
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='OK']")
	private WebElement OKAlert;

	@ElementDescription(value = "Cancel Alert")
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='CANCEL']")
	private WebElement CancelAlert;

	@ElementDescription(value = "AllOW Button")
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='AllOW']")
	private WebElement AllOWAlert;

	@ElementDescription(value = "DENY Button")
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='DENY']")
	private WebElement DENYAlert;

	@ElementDescription(value = "DENY ANYWAY Button")
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='DENY ANYWAY']")
	private WebElement DENYANYWAYAlert;

	@ElementDescription(value = "Not Responding Title")
	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='android:id/alertTitle']")
	private WebElement NotRespondingTitle;

	@ElementDescription(value = "Not Responding Close")
	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='android:id/aerr_close']")
	private WebElement NotRespondingClose;

	@ElementDescription(value = "Not Responding Wait")
	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='android:id/aerr_wait']")
	private WebElement NotRespondingWait;

	@ElementDescription(value = "Permission Aler Message")
	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.android.permissioncontroller:id/permissions_message']")
	private WebElement PermissionAlertMessage;

	@ElementDescription(value = "Permission Alert Cancel")
	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.android.permissioncontroller:id/cancel_button']")
	private WebElement PermissionAlertCancel;

	@ElementDescription(value = "Permission Alert Continue")
	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.android.permissioncontroller:id/continue_button']")
	private WebElement PermissionAlertContinue;

	public WebElement getToastMessageElement() {
		return ToastMessageElement;
	}

	public List<WebElement> getToastMessageElementList() {
		return ToastMessageElementList;
	}

	public List<WebElement> toggleList() {
		return toggleList;
	}

	public WebElement PermissionAlertContinueButton() {
		return PermissionAlertContinue;
	}

	public WebElement PermissionAlertCancelButton() {
		return PermissionAlertCancel;
	}

	public WebElement getPermissionMessage() {
		return PermissionAlertMessage;
	}

	public WebElement NotRespondingWaitButton() {
		return NotRespondingWait;
	}

	public WebElement NotRespondingCloseButton() {
		return NotRespondingClose;
	}

	public WebElement getNotRespondingTitle() {
		return NotRespondingTitle;
	}

	public WebElement DenyAnywayButton() {
		return DENYANYWAYAlert;
	}

	public WebElement DenyButton() {
		return DENYAlert;
	}

	public WebElement OkButton() {
		return OKAlert;
	}

	public WebElement CancelButton() {
		return CancelAlert;
	}

	public WebElement AllowButton() {
		return AllOWAlert;
	}

	public WebElement getalertMessage() {
		return MessageAlert;
	}

	public WebElement getalertTitle() {
		return TitleAlert;
	}
	/**
	 * This is applications alert element asking to relaunch the application
	 */
	
	@ElementDescription(value = "Message")
	@AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView[2]")
	private WebElement Message;
	
	
	@ElementDescription(value = "Quit Button")
	@AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.Button")
	private WebElement QuitButton;
	
	@ElementDescription(value = "Title")
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Alert']")
	private WebElement Title;
	
	public WebElement getMessage() {
		return Message;
	}
	
	public WebElement getTitle() {
		return Title;
	}
	public WebElement quitButton() {
		return QuitButton;
	}
	/**
	 * 
	 */
	
	
	/**
	 * This is applications alert element After login 
	 */
	   @ElementDescription(value = "Alert header")
		@AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id,'txt_header')]")
		private WebElement headerMessage;
		
		
		@ElementDescription(value = "Swipe Button")
		@AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.view.View")
		private WebElement swipeButton;
		
		
		public WebElement headerMessage() {
			return headerMessage;
		}
		
		public WebElement swipeButton() {
			return swipeButton;
		}
		
		/**
		 * 
		 */
		
		/**
		 * This is applications alert element asking to enable fingerprint
		 */
		   @ElementDescription(value = "Alert Fingerprint Message")
			@AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id,'id/desc')]")
			private WebElement headerFingerprintMessage;
			
			
			@ElementDescription(value = "Close Button")
			@AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='CLOSE']")
			private WebElement closeButton;
			
			
			public WebElement headerFingerprintMessage() {
					return headerFingerprintMessage;
			}
			
			public WebElement closeButton() {
				 return closeButton;
			}
			
			/**
			 * 
			 */
			
			/**
			 * This is applications alert element asking to enable recording
			 */
			   @ElementDescription(value = "Alert recordingt Message")
				@AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id,'id/title')]")
				private WebElement headerRecordingMessage;
			
				
			   //android.widget.ImageView[@content-desc="CLOSE"]
			   // closed button is same as previous popup
				
				public WebElement headerRecordingMessage() {
					return headerRecordingMessage;
				}
				
			
}
