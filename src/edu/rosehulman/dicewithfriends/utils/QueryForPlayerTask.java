package edu.rosehulman.dicewithfriends.utils;

import java.io.IOException;

import android.os.AsyncTask;
import android.util.Log;

import com.appspot.dice_with_friends.dicewithfriends.Dicewithfriends;
import com.appspot.dice_with_friends.dicewithfriends.model.Player;

import edu.rosehulman.dicewithfriends.MainActivity;

public class QueryForPlayerTask extends AsyncTask<Void, Void, Player> {

		@Override
		protected Player doInBackground(Void ... unused) {
			Player player = null;
			try {
				Dicewithfriends.Player.Get query = ServiceUtils.getService().player().get();
				Log.d(MainActivity.DWF, "Query = " + (query == null ? "null " : query.toString()));
				player = query.execute();

			} catch (IOException e) {
				Log.d(MainActivity.DWF, "Failed loading " + e, e);

			}
			return player;
		}

		@Override
		protected void onPostExecute(Player result) {
			super.onPostExecute(result);
			if (result == null) {
				Log.d(MainActivity.DWF, "Failed loading, result playername is null");
				return;
			}

			// Save this player for later
			Log.d(MainActivity.DWF, "Got player, name = " + result.getDisplayName());
			PlayerUtils.setPlayerForUser(result);
		}
	}
