package edu.rosehulman.dicewithfriends.utils;

import android.content.Context;

public class Utils {
	// For logging
	public static final String DWF = "DWF";
	
	public static final String SHARED_PREFERENCES_NAME = "DiceWithFriends";
	private static Context sContext;
	
	public static Context getContext() {
		return sContext;
	}
	public static void setContext(Context context) {
		Utils.sContext = context;
	}

	
	
}
