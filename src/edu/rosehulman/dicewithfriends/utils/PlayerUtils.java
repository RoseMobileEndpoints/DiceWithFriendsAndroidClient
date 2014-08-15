package edu.rosehulman.dicewithfriends.utils;

import java.util.HashMap;

import junit.framework.Assert;
import android.content.Context;
import android.widget.TextView;

import com.appspot.dice_with_friends.dicewithfriends.model.Game;
import com.appspot.dice_with_friends.dicewithfriends.model.Player;

public class PlayerUtils {

	private static Player mPlayerForCurrentUser = null;
	private static HashMap<String, String> sPlayerNameMap = new HashMap<String, String>();
	private static Context sContext;

	public static void setContext(Context context) {
		sContext = context;
	}

	public static void setOpponentName(TextView opponentTextView, Game game) {
		// Who is the opponent?
		Assert.assertNotNull("No player for current user", mPlayerForCurrentUser);

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
