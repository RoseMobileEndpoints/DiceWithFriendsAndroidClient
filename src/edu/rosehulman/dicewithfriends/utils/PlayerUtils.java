package edu.rosehulman.dicewithfriends.utils;

import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.appspot.dice_with_friends.dicewithfriends.model.Game;
import com.appspot.dice_with_friends.dicewithfriends.model.Player;

import edu.rosehulman.dicewithfriends.MainActivity;

public class PlayerUtils {

	private static Player mPlayerForCurrentUser = null;
	private static HashMap<String, String> sPlayerNameMap = new HashMap<String, String>();
	private static Context sContext;

	public static void setContext(Context context) {
		sContext = context;
	}

	public static void setOpponentName(TextView opponentTextView, Game game) {
		// Who is the opponent?

		// If we try to do this before it is initialized
		if (mPlayerForCurrentUser == null) {
			// TODO: Ask the server for me, in an AsyncTask. But better to do
			// it in Main once an account is chosen.
			return;
		}

		// FIXME: The player has a valid display name (Matt), but a NULL entityKey. Why? 
		// Check the backend, since when you call player_get on it from the explorer, it doesn't return an entity key either.
		Log.d(MainActivity.DWF, "Player for current user = " + mPlayerForCurrentUser.getDisplayName() + " "
				+ mPlayerForCurrentUser.getEntityKey() + " " + mPlayerForCurrentUser);
		String opponentKey = mPlayerForCurrentUser.getEntityKey().equals(game.getCreatorKey()) ? game.getInviteeKey()
				: game.getCreatorKey();

		if (sPlayerNameMap.containsKey(opponentKey)) {
			opponentTextView.setText(sPlayerNameMap.get(opponentKey));
		} else {
			// Get from server
			(new QueryForDisplayNameTask(sContext, opponentTextView)).execute(opponentKey);
		}
	}

	public static void addNameToMap(String playerEntityKey, String displayName) {
		sPlayerNameMap.put(playerEntityKey, displayName);
	}

	public static void getPlayerForUser() {
		if (mPlayerForCurrentUser == null) {
			// TODO: need the player's info from the service.
			(new QueryForPlayerTask()).execute();
		}
	}

	public static void setPlayerForUser(Player player) {
		mPlayerForCurrentUser = player;
	}

}
