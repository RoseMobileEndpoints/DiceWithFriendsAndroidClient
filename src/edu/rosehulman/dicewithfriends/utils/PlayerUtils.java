package edu.rosehulman.dicewithfriends.utils;

import java.util.HashMap;

import android.content.Context;
import android.widget.TextView;

import com.appspot.dice_with_friends.dicewithfriends.model.Game;
import com.appspot.dice_with_friends.dicewithfriends.model.Player;

public class PlayerUtils {

	private static Player mPlayerForCurrentUser;
	private static HashMap<String, String> sPlayerNameMap = new HashMap<String, String>();
	private static Context sContext;

	public static void setContext(Context context) {
		sContext = context;
	}

	public static void setOpponentName(TextView opponentTextView, Game game) {
		// Who is the opponent?

		// If we try to do this before it is initialized
		if (mPlayerForCurrentUser == null) {
			// TODO: Ask the server for me, in an asynch task. But better to do it in Main once an account is chosen.
			
			return;
		}
		
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
}
