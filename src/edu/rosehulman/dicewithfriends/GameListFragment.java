package edu.rosehulman.dicewithfriends;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.ListFragment;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.appspot.dice_with_friends.dicewithfriends.Dicewithfriends;
import com.appspot.dice_with_friends.dicewithfriends.model.Game;
import com.appspot.dice_with_friends.dicewithfriends.model.GameCollection;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import edu.rosehulman.dicewithfriends.utils.DateUtils;

public class GameListFragment extends ListFragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	private GameAdapter mGameAdapter;
	private ArrayList<Game> mGames;

	/**
	 * Credentials object that maintains tokens to send to the back end.
	 */
	GoogleAccountCredential mCredential;

	// FIXME: If we need to make this static, then replace this evil global
	// variable with a more robust singleton
	// object.
	private Dicewithfriends mService = null;

	/**
	 * Preference object where we store the name of the current user.
	 */
	SharedPreferences mSettings = null;

	public static final String SHARED_PREFERENCES_NAME = "DiceWithFriends";
	public static final String PREF_ACCOUNT_NAME = "PREF_ACCOUNT_NAME";
	static final int REQUEST_ACCOUNT_PICKER = 1;
	static final String KEY_ASSIGNMENT_ENTITY_KEY = "KEY_ASSIGNMENT_ID";
	static final String KEY_ASSIGNMENT_NAME = "KEY_ASSIGNMENT_NAME";
	static final String KEY_SERVICE = "KEY_SERVICE";

	
	
	
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static GameListFragment newInstance(int sectionNumber) {
		GameListFragment fragment = new GameListFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);

		// TODO: Everything associated with the account and service
		// Quick hack that avoids account info.
		fragment.mCredential.setSelectedAccountName("username@gmail.com");

		
		
		
		
		
		return fragment;
	}

	public GameListFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mGames = new ArrayList<Game>();
		mGames.add(new Game().setLastTouchDateTime(DateUtils
				.getServerParseableStringFromDate(new Date())));
		mGames.add(new Game().setLastTouchDateTime(DateUtils
				.getServerParseableStringFromDate(new Date())));
		mGames.add(new Game().setLastTouchDateTime(DateUtils
				.getServerParseableStringFromDate(new Date())));
		mGameAdapter = new GameAdapter(getActivity(), mGames);
		setListAdapter(mGameAdapter);

		View rootView = inflater.inflate(R.layout.fragment_main, container, false);

		// TODO: Update view to display the games for this user.

		// TODO: Set item listener to launch game.

		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
	}

	// ---------------------------------------------------------------------------------
	// Backend communication
	// ---------------------------------------------------------------------------------

	private void updateGames() {
//		new QueryForGamesTask().execute();
	}

//	class QueryForGamesTask extends AsyncTask<Void, Void, GameCollection> {
//
//		@Override
//		protected GameCollection doInBackground(Void... unused) {
//			GameCollection games = null;
//			try {
//				// The logs are here to help debug authentication issues I
//				// had...
//				// Need qualification here for import below to work unqualified,
//				// since there are two identically-named Game classes, in
//				// the service and the model.
//				// Log.d(MainActivity.DWF, "Using account name = " +
//				// mCredential.getSelectedAccountName());
//				Dicewithfriends.Game.List query = mService.game().list();
//				Log.d(MainActivity.DWF, "Query = " + (query == null ? "null " : query.toString()));
//				query.setLimit(50L);
//				games = query.execute();
//				Log.d(MainActivity.DWF, "Games = " + games);
//
//			} catch (IOException e) {
//				Log.d(MainActivity.DWF, "Failed loading " + e, e);
//
//			}
//			return games;
//		}
//
//		@Override
//		protected void onPostExecute(GameCollection result) {
//			super.onPostExecute(result);
//			if (result == null) {
//				Log.d(MainActivity.DWF, "Failed loading, result is null");
//				return;
//			}
//
//			if (result.getItems() == null) {
//				result.setItems(new ArrayList<Game>());
//			}
//
//			GameAdapter adapter = new GameAdapter(getActivity(), result.getItems());
//			setListAdapter(adapter);
//		}
//	}
//
//	class InsertGameTask extends AsyncTask<Game, Void, Game> {
//
//		@Override
//		protected Game doInBackground(Game... items) {
//			try {
//				Game game = mService.game().newgame(items[0]).execute();
//				return game;
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		@Override
//		protected void onPostExecute(Game result) {
//			super.onPostExecute(result);
//			if (result == null) {
//				Log.d(MainActivity.DWF, "Error inserting game, result is null");
//				return;
//			}
//			updateGames();
//		}
//
//	}
//
//	class DeleteGameTask extends AsyncTask<String, Void, Game> {
//
//		@Override
//		protected Game doInBackground(String... entityKeys) {
//			Game returnedGame = null;
//
//			try {
//				returnedGame = mService.game().delete(entityKeys[0]).execute();
//			} catch (IOException e) {
//				Log.d(MainActivity.DWF, "Failed deleting " + e);
//			}
//			return returnedGame;
//		}
//
//		@Override
//		protected void onPostExecute(Game result) {
//			super.onPostExecute(result);
//			if (result == null) {
//				Log.d(MainActivity.DWF, "Failed deleting, result is null");
//				return;
//			}
//			updateGames();
//		}
//	}

}
