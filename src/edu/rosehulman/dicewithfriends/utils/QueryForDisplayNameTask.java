package edu.rosehulman.dicewithfriends.utils;

import java.io.IOException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.appspot.dice_with_friends.dicewithfriends.Dicewithfriends;
import com.appspot.dice_with_friends.dicewithfriends.model.PlayerProtoDisplayName;

class QueryForDisplayNameTask extends AsyncTask<String, Void, PlayerProtoDisplayName> {

	private TextView mTextView = null;
	private String mEntityKey = null;
	// private Context mContext = null;

	public QueryForDisplayNameTask(Context context, TextView tv) {
		// mContext = context;
		mTextView = tv;
	}

	@Override
	protected PlayerProtoDisplayName doInBackground(String... entityKeys) {
		PlayerProtoDisplayName playerName = null;
		try {
			mEntityKey = entityKeys[0];
			Dicewithfriends.Player.Getname query = ServiceUtils.getService().player().getname(mEntityKey);
			Log.d(Utils.DWF, "Query = " + (query == null ? "null " : query.toString()));
			playerName = query.execute();

		} catch (IOException e) {
			Log.d(Utils.DWF, "Failed loading " + e, e);

		}
		return playerName;
	}

	@Override
	protected void onPostExecute(PlayerProtoDisplayName result) {
		super.onPostExecute(result);
		if (result == null) {
			Log.d(Utils.DWF, "Failed loading, result playername is null");
			return;
		}
		PlayerUtils.addNameToMap(mEntityKey, result.getDisplayName());
		mTextView.setText(result.getDisplayName());
	}
}
