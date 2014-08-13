package edu.rosehulman.dicewithfriends.utils;

import android.content.Context;

import com.appspot.dice_with_friends.dicewithfriends.Dicewithfriends;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;

public class ServiceUtils {
	/**
	 * Credentials object that maintains tokens to send to the back end.
	 */
	static GoogleAccountCredential mCredential;

	private static Dicewithfriends mService = null;

	public static Dicewithfriends getService(Context context) {
		if (mService == null) {
			// Web client id
			mCredential = GoogleAccountCredential.usingAudience(context,
					"server:client_id:1034873038322-8s341nnf24dhd1i0es7k9drldjm7o59g.apps.googleusercontent.com");
			// TODO assign from shared prefs, else get one.
			mCredential.setSelectedAccountName("boutell@gmail.com");
			mService = new Dicewithfriends(AndroidHttp.newCompatibleTransport(), new GsonFactory(), mCredential);
		} 
		return mService;
	}
	
	
	
	
}
