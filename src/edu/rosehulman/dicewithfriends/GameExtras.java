package edu.rosehulman.dicewithfriends;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;

import com.appspot.dice_with_friends.dicewithfriends.model.Game;

public class GameExtras {
	static final String EXTRA_GAME_ENTITY_KEY = "EXTRA_GAME_ENTITY_KEY";
	static final String EXTRA_GAME_CREATOR_KEY = "EXTRA_GAME_CREATOR_KEY";
	static final String EXTRA_GAME_INVITEE_KEY = "EXTRA_GAME_INVITEE_KEY";
	static final String EXTRA_GAME_CREATOR_SCORES = "EXTRA_GAME_CREATOR_SCORES";
	static final String EXTRA_GAME_INVITEE_SCORES = "EXTRA_GAME_INVITEE_SCORES";
	static final String EXTRA_GAME_LAST_TOUCH_DATE_TIME = "EXTRA_GAME_LAST_TOUCH_DATE_TIME";
	static final String EXTRA_GAME_IS_COMPLETE = "EXTRA_GAME_IS_COMPLETE";
	static final String EXTRA_GAME_IS_SOLO = "EXTRA_GAME_IS_SOLO";

	static void putGameExtras(Intent intent, Game game) {
		intent.putExtra(EXTRA_GAME_ENTITY_KEY, game.getEntityKey());
		intent.putExtra(EXTRA_GAME_CREATOR_KEY, game.getCreatorKey());
		intent.putExtra(EXTRA_GAME_INVITEE_KEY, game.getInviteeKey());
		List<Long> creatorScores = game.getCreatorScores();
		if (creatorScores != null) {
			long[] ar = new long[creatorScores.size()];
			for (int i = 0; i < ar.length; i++) {
				ar[i] = creatorScores.get(i);
			}
			intent.putExtra(EXTRA_GAME_CREATOR_SCORES, ar);
		}
		List<Long> inviteeScores = game.getInviteeScores();
		if (inviteeScores != null) {
			long[] ar = new long[inviteeScores.size()];
			for (int i = 0; i < ar.length; i++) {
				ar[i] = inviteeScores.get(i);
			}
			intent.putExtra(EXTRA_GAME_INVITEE_SCORES, ar);
		}
		intent.putExtra(EXTRA_GAME_LAST_TOUCH_DATE_TIME, game.getLastTouchDateTime());
		intent.putExtra(EXTRA_GAME_IS_COMPLETE, game.getIsComplete());
		intent.putExtra(EXTRA_GAME_IS_SOLO, game.getIsSolo());
	}

	static Game getGameFromExtras(Intent intent) {
		Game game = new Game();
		game.setEntityKey(intent.getStringExtra(EXTRA_GAME_ENTITY_KEY));
		game.setCreatorKey(intent.getStringExtra(EXTRA_GAME_CREATOR_KEY));
		game.setInviteeKey(intent.getStringExtra(EXTRA_GAME_INVITEE_KEY));

		// Do ourselves, since toArray gives an Object[] not a long[], and
		// intents don't like those.
		long[] scores = intent.getLongArrayExtra(EXTRA_GAME_CREATOR_SCORES);
		if (scores != null) {
			ArrayList<Long> creatorScores = new ArrayList<Long>();
			for (long score : scores) {
				creatorScores.add(score);
			}
			game.setCreatorScores(creatorScores);
		}

		scores = intent.getLongArrayExtra(EXTRA_GAME_INVITEE_SCORES);
		if (scores != null) {
			ArrayList<Long> inviteeScores = new ArrayList<Long>();
			for (long score : scores) {
				inviteeScores.add(score);
			}
			game.setInviteeScores(inviteeScores);
		}
		game.setLastTouchDateTime(intent.getStringExtra(EXTRA_GAME_LAST_TOUCH_DATE_TIME));
		game.setIsComplete(intent.getBooleanExtra(EXTRA_GAME_IS_COMPLETE, false));
		game.setIsSolo(intent.getBooleanExtra(EXTRA_GAME_IS_SOLO, false));
		return game;
	}

}
