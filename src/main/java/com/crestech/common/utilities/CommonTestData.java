package com.crestech.common.utilities;

public enum CommonTestData {

	EMAIL("abc@gmail.com"), SIGNED_OUT_MESSAGE("Log In To Explore"),
	 IOS_SIGNED_OUT_MESSAGE("Login to Explore"),
	LOGIN_TO_ACCESS_MESSAGE("Login to access amazing offers & redeem ixigo money on your flight booking."), OTP("3672"),
	LOGIN_TO_ACCESS_MESSAGE_IOS("Login to access amazing offers & redeem your ixigo money on your flight booking."),
	
	// Domestic Flights Details
	FROM("DEL"), TO("BOM"), FROM_FULL_LOCATION("New Delhi, India"), TO_FULL_LOCATION("Mumbai, India"),

	// International Flights Details
	DESTINATION("DXB"), TO_FULL_LOCATION_INTERNATIONAL("Dubai, United Arab Emirates"),

	// App Package And Activity
	IXIGO_APPS_ACTIVITY("com.ixigo.DefaultSplashScreenActivity"), IXIGO_APP_PACKAGE("com.ixigo"),
	IXIGO_APP_APK("ixigo_app.apk"),

    //Other Apps
	HOTELS("HotelsIdentifier"),
	HOTELS_APP_TITLE("Book Hotels"),
	TRAINS("TrainsIdentifier"),
	TRAINS_APP_TITLE("ixigo Trains"),
	ABHI_BUS("BusesIdentifier"),
	ABHI_BUS_APP_TITLE("Book Buses With"),
	
	
	// Server Details
	N4_SERVER("UAT N4"), M2_SERVER("UAT M2"), N1_SERVER("UAT N1"), N3_SERVER("UAT N3"),

	;

	private final String message;

	private CommonTestData(String message) {
		this.message = message;
	}

	public String getEnumValue() throws Exception {
		try {
			if (this.message == null)
				throw new Exception("The Message isn't provided");
			return this.message;
		} catch (Exception e) {
			throw new Exception("CommonTestData::EnumValue() Exception : " + e.getLocalizedMessage());
		}
	}

}
