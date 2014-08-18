package edu.rosehulman.dicewithfriends.utils;

import java.util.ArrayList;
import java.util.List;

import com.appspot.dice_with_friends.dicewithfriends.model.Game;

public class GameUtils {
	
	public static final int GAME_SCORE_TO_WIN = 10000;
	
	public static int getUserRound(Game game) {
		List<Long> scores;
		if (game.getCreatorKey().equals(PlayerUtils.getPlayerForUser().getEntityKey())) {
			scores = game.getCreatorScores();
		} else {
			scores = game.getInviteeScores();
		}
		if (scores == null) {
			scores = new ArrayList<Long>();
		}
		return scores.size();
	}	
	
	public static Long getUserScore(Game game) {
		List<Long> scores;
		if (game.getCreatorKey().equals(PlayerUtils.getPlayerForUser().getEntityKey())) {
			scores = game.getCreatorScores();
		} else {
			scores = game.getInviteeScores();
		}
		if (scores == null) {
			scores = new ArrayList<Long>();
		}
		Long sum = 0L;
		for (Long score : scores) {
			sum += score;
		}
		return sum;
	}	
	

	
}
