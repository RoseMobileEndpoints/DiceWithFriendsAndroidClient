package edu.rosehulman.dicewithfriends.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import junit.framework.Assert;
import android.util.Log;

public class DateUtils {
 
	private static final String TAG = "TimeUtils";
	private static final String SERVER_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
	private static final String CURRENT_DAY_FORMAT = "h:mm:ss a";
	private static final String CURRENT_YEAR_FORMAT = "MMM d h:mm:ss a";
	private static final String PREVIOUS_YEAR_FORMAT = "M/d/yy";
	public static final long SECOND_MS = 1000;
	public static final long MINUTE_MS = 60000;
	public static final long DAY_MS = 86400000;
	public static final long YEAR_MS = 365 * DAY_MS;

	public static String getDisplayStringFromDate(Date time) {
		Assert.assertNotNull(time);
		SimpleDateFormat f;
		f = new SimpleDateFormat(PREVIOUS_YEAR_FORMAT, Locale.US);
		f.setTimeZone(TimeZone.getDefault());
		return f.format(time);
	}

	public static String getServerParseableStringFromDate(Date time) {
		SimpleDateFormat f = new SimpleDateFormat(SERVER_FORMAT, Locale.US);
		f.setTimeZone(TimeZone.getTimeZone("UTC"));
		return f.format(time) + "000";
	}

	public static Date getDateFromServerString(String timeStr) {
		Date d = null;
		if (timeStr != null) {
			try {
				SimpleDateFormat f = new SimpleDateFormat(SERVER_FORMAT, Locale.US);
				f.setTimeZone(TimeZone.getTimeZone("UTC"));
				// We need to remove the last three zeroes so SimpleDateFormat
				// can parse properly.
				// See http://stackoverflow.com/questions/5636491/ for details.
				d = f.parse(timeStr.substring(0, timeStr.length() - 3));
			} catch (ParseException pe) {
				Log.e(TAG, "Failed to parse time string: " + timeStr);
			}
		}
		return d;
	}

}