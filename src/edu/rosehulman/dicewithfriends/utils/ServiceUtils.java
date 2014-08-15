package edu.rosehulman.dicewithfriends.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.appspot.dice_with_friends.dicewithfriends.Dicewithfriends;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;

public class ServiceUtils {

	/**
	 * Credentials object that maintains tokens to send to the back end.
	 */
	static GoogleAccountCredential sCredential;

	private static Dicewithfriends mService = null;

	/**
	 * Preference object where we store the name of the current user.
	 */

	public static final String SHARED_PREFERENCES_NAME = "DiceWithFriends";
	public static final String PREF_ACCOUNT_NAME = "PREF_ACCOUNT_NAME";
	private static Context sContext;

	public static void setContext(Context context) {
		sContext = context;
	}

	public static Dicewithfriends getService() {
		if (mService == null) {

			mService = new Dicewithfriends(AndroidHttp.newCompatibleTransport(), new GsonFactory(), getCredential());
		}
		return mService;
	}

	public static String getAccountName() {
		SharedPreferences prefs = sContext.getSharedPreferences(SHARED_PREFERENCES_NAME, 0);
		return prefs.getString(PREF_ACCOUNT_NAME, null);
	}

	/**
	 * Save the account name in preferences and the credentials
	 * 
	 * @param accountName
	 */
	public static void setAccountName(String accountName) {
		SharedPreferences prefs = sContext.getSharedPreferences(SHARED_PREFERENCES_NAME, 0);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PREF_ACCOUNT_NAME, accountName);
		editor.commit();
		sCredential.setSelectedAccountName(accountName);
	}

	public static GoogleAccountCredential getCredential() {
		if (sCredential == null) {
			// Web client id
			sCredential = GoogleAccountCredential.usingAudience(sContext,
					"server:client_id:1034873038322-8s341nnf24dhd1i0es7k9drldjm7o59g.apps.googleusercontent.com");
			sCredential.setSelectedAccountName(getAccountName());
		}
		return sCredential;
	}

	// CONSIDER: Save player as a SharedPref or sqlite
	
}
