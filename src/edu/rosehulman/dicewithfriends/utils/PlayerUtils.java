package edu.rosehulman.dicewithfriends.utils;

import java.util.HashMap;

import junit.framework.Assert;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.appspot.dice_with_friends.dicewithfriends.model.Game;
import com.appspot.dice_with_friends.dicewithfriends.model.Player;

public class PlayerUtils {

	private static HashMap<String, String> sPlayerNameMap = new HashMap<String, String>();

	private static final String PREF_PLAYER_ENTITY_KEY = "KEY_PLAYER_ENTITY_KEY";
	private static final String PREF_PLAYER_DISPLAY_NAME = "KEY_PLAYER_DISPLAY_NAME";
	private static Player sPlayerForCurrentUser = null;

	public static void setOpponentName(TextView opponentTextView, Game game) {
		// Who is the opponent?
		Assert.assertNotNull("No player for current user", sPlayerForCurrentUser);

		String opponentKey = sPlayerForCurrentUser.getEntityKey().equals(game.getCreatorKey()) ? game.getInviteeKey()
				: game.getCreatorKey();

		if (sPlayerNameMap.containsKey(opponentKey)) {
			opponentTextView.setText(sPlayerNameMap.get(opponentKey));
		} else {
			// Get from server
			(new QueryForDisplayNameTask(Utils.getContext(), opponentTextView)).execute(opponentKey);
		}
	}

	public static void addNameToMap(String playerEntityKey, String displayName) {
		sPlayerNameMap.put(playerEntityKey, displayName);
	}


	public static boolean hasPlayer() {
		return getPlayerForUser() != null;
	}

	public static Player getPlayerForUser() {
		if (sPlayerForCurrentUser == null) {
			SharedPreferences prefs = Utils.getContext().getSharedPreferences(Utils.SHARED_PREFERENCES_NAME, 0);
			String potentialEntityKey = prefs.getString(PREF_PLAYER_ENTITY_KEY, null);
			String potentialPlayerName = prefs.getString(PREF_PLAYER_DISPLAY_NAME, null);
			if (potentialEntityKey != null && potentialPlayerName != null) {
				sPlayerForCurrentUser = new Player();
				sPlayerForCurrentUser.setEntityKey(potentialEntityKey);
				sPlayerForCurrentUser.setDisplayName(potentialPlayerName);
			}
		}
		return sPlayerForCurrentUser;
	}

	public static void setPlayerForUser(Player player) {
		sPlayerForCurrentUser = player;
		SharedPreferences prefs = Utils.getContext().getSharedPreferences(Utils.SHARED_PREFERENCES_NAME, 0);
		SharedPreferences.Editor editor = prefs.edit();
		if (player == null) {
			// For logout
			editor.remove(PREF_PLAYER_ENTITY_KEY);
			editor.remove(PREF_PLAYER_DISPLAY_NAME);
		} else {
			editor.putString(PREF_PLAYER_DISPLAY_NAME, player.getDisplayName());
			editor.putString(PREF_PLAYER_ENTITY_KEY, player.getEntityKey());
		}
		editor.commit();
	}

	
	
	
}
