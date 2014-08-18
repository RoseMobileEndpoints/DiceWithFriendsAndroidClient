package edu.rosehulman.dicewithfriends.utils;

import java.util.ArrayList;
import java.util.List;

import com.appspot.dice_with_friends.dicewithfriends.model.Game;

public class GameUtils {

	public static final int GAME_SCORE_TO_WIN = 10000;

	public static int getUserRound(Game game) {
		return getScores(game).size();
	}

	public static long getUserScore(Game game) {
		List<Long> scores = getScores(game);
		long sum = 0L;
		for (Long score : scores) {
			sum += score;
		}
		return sum;
	}

	private static List<Long> getScores(Game game) {
		List<Long> scores;
		if (game.getCreatorKey().equals(PlayerUtils.getPlayerForUser().getEntityKey())) {
			scores = game.getCreatorScores();
		} else {
			scores = game.getInviteeScores();
		}
		if (scores == null) {
			scores = new ArrayList<Long>();
		}
		return scores;
	}

}
