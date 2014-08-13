package edu.rosehulman.dicewithfriends;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.appspot.dice_with_friends.dicewithfriends.model.Game;

import edu.rosehulman.dicewithfriends.utils.DateUtils;
import edu.rosehulman.dicewithfriends.utils.PlayerUtils;

public class GameAdapter extends ArrayAdapter<Game> {

	public GameAdapter(Context context, List<Game> objects) {
		super(context, R.layout.game_list_row_view, R.id.opponent_text_view, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		Game game = getItem(position);

		TextView opponentTextView = (TextView)view.findViewById(R.id.opponent_text_view);
		opponentTextView.setText("Fetching...");
		PlayerUtils.setOpponentName(opponentTextView, game);

		TextView dateTextView = (TextView)view.findViewById(R.id.date_text_view);
		String dateString = game.getLastTouchDateTime();
		Date date = DateUtils.getDateFromServerString(dateString);
		dateTextView.setText(DateUtils.getDisplayStringFromDate(date));

		TextView statusTextView = (TextView)view.findViewById(R.id.status_text_view);
		statusTextView.setText("Round 5, 1850 points");
		return view;
	}
	
}
