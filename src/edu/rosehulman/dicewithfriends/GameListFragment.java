package edu.rosehulman.dicewithfriends;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.appspot.dice_with_friends.dicewithfriends.model.Game;

import edu.rosehulman.dicewithfriends.utils.DateUtils;

public class GameListFragment extends ListFragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	private GameAdapter mGameAdapter;
	private ArrayList<Game> mGames;

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static GameListFragment newInstance(int sectionNumber) {
		GameListFragment fragment = new GameListFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);

		return fragment;
	}

	public GameListFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mGames = new ArrayList<Game>();
		mGames.add(new Game().setLastTouchDateTime(DateUtils.getServerParseableStringFromDate(new Date())));
		mGames.add(new Game().setLastTouchDateTime(DateUtils.getServerParseableStringFromDate(new Date())));
		mGames.add(new Game().setLastTouchDateTime(DateUtils.getServerParseableStringFromDate(new Date())));
		mGameAdapter = new GameAdapter(getActivity(), mGames);
		setListAdapter(mGameAdapter);
		
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
	}
}
