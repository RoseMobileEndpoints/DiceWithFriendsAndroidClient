package edu.rosehulman.dicewithfriends;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.appspot.dice_with_friends.dicewithfriends.Dicewithfriends;
import com.appspot.dice_with_friends.dicewithfriends.model.Game;
import com.appspot.dice_with_friends.dicewithfriends.model.GameCollection;

import edu.rosehulman.dicewithfriends.utils.GameUtils;
import edu.rosehulman.dicewithfriends.utils.ServiceUtils;
import edu.rosehulman.dicewithfriends.utils.Utils;

public class GameListFragment extends ListFragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";
	protected static final int REQUEST_CODE_GAME = 1;

	enum GameListType {
		WAITING_FOR_ME, WAITING_ONLY_FOR_OPPONENT, FINISHED_MULTIPLAYER_GAMES, IN_PROGRESS_SOLO_GAMES, FINISHED_SOLO_GAMES
	}

	private GameListType mType;

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

	public GameListFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_game_display, container, false);

		// Update view to display the games for this user.
		int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
		mType = GameListType.values()[sectionNumber];
		
		// Takes awhile to query.
		updateGames();

		return rootView;
	}

	// TODO: CONSIDER: Not sure if this is where this listener should go. There was no listview yet when it was in onCreate. 
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Set item listener to launch game.
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Game game = (Game) getListAdapter().getItem(position);
				Intent intent = new Intent(getActivity(), GameActivity.class);
				// TODO: Code-review and test passing game to the other activity. Some testing done already.
				GameExtras.putGameExtras(intent, game);
				startActivity(intent);

				// TODO: Write onResume to refresh once we get here after a game
				// completes.
			}
		});
	}
	
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((GameDisplayActivity) activity).onFragmentAttached(getArguments().getInt(ARG_SECTION_NUMBER));
	}

	// ---------------------------------------------------------------------------------
	// Backend communication
	// ---------------------------------------------------------------------------------

	void updateGames() {
		new QueryForGamesTask().execute();
	}

	class QueryForGamesTask extends AsyncTask<Void, Void, GameCollection> {

		@Override
		protected GameCollection doInBackground(Void... unused) {
			GameCollection games = null;
			try {
				// The logs are here to help debug authentication issues I
				// had...
				// Need qualification here for import below to work unqualified,
				// since there are two identically-named Game classes, in
				// the service and the model.
				// Log.d(MainActivity.DWF, "Using account name = " +
				// mCredential.getSelectedAccountName());
				Dicewithfriends.Game.List query = ServiceUtils.getService().game().list();
				Log.d(Utils.DWF, "Query = " + (query == null ? "null " : query.toString()));
				query.setLimit(50L);
				switch (mType) {
				case WAITING_FOR_ME:
				case WAITING_ONLY_FOR_OPPONENT:
					query.setIsComplete(false);
					query.setIsSolo(false);
					break;
				case FINISHED_MULTIPLAYER_GAMES:
					query.setIsComplete(true);
					query.setIsSolo(false);
					break;
				case IN_PROGRESS_SOLO_GAMES:
					query.setIsComplete(false);
					query.setIsSolo(true);
					break;
				case FINISHED_SOLO_GAMES:
					query.setIsComplete(true);
					query.setIsSolo(true);
					break;
				}
				games = query.execute();
				Log.d(Utils.DWF, "Games = " + games);

			} catch (IOException e) {
				Log.d(Utils.DWF, "Failed loading " + e, e);

			}
			return games;
		}

		@Override
		protected void onPostExecute(GameCollection result) {
			super.onPostExecute(result);
			if (result == null) {
				Log.d(Utils.DWF, "Failed loading, result is null");
				return;
			}

			if (result.getItems() == null) {
				result.setItems(new ArrayList<Game>());
			}
			if (mType == GameListType.WAITING_FOR_ME || mType == GameListType.WAITING_ONLY_FOR_OPPONENT) {
				filterGames(result.getItems());

			}
			GameAdapter adapter = new GameAdapter(getActivity(), result.getItems());
			setListAdapter(adapter);
		}

		private void filterGames(List<Game> games) {
			for (int i = games.size() - 1; i >= 0; i--) {
				Game game = games.get(i);
				Long score = GameUtils.getUserScore(game);
				boolean waitingForMeFilter = mType == GameListType.WAITING_FOR_ME
						&& score >= GameUtils.GAME_SCORE_TO_WIN;
				boolean waitingForOpponentFilter = mType == GameListType.WAITING_ONLY_FOR_OPPONENT
						&& score < GameUtils.GAME_SCORE_TO_WIN;
				if (waitingForMeFilter || waitingForOpponentFilter) {
					games.remove(i);
				}
			}
		}
	}

	// class InsertGameTask extends AsyncTask<Game, Void, Game> {
	//
	// @Override
	// protected Game doInBackground(Game... items) {
	// try {
	// Game game = mService.game().newgame(items[0]).execute();
	// return game;
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	// @Override
	// protected void onPostExecute(Game result) {
	// super.onPostExecute(result);
	// if (result == null) {
	// Log.d(MainActivity.DWF, "Error inserting game, result is null");
	// return;
	// }
	// updateGames();
	// }
	//
	// }
	//
	// class DeleteGameTask extends AsyncTask<String, Void, Game> {
	//
	// @Override
	// protected Game doInBackground(String... entityKeys) {
	// Game returnedGame = null;
	//
	// try {
	// returnedGame = mService.game().delete(entityKeys[0]).execute();
	// } catch (IOException e) {
	// Log.d(MainActivity.DWF, "Failed deleting " + e);
	// }
	// return returnedGame;
	// }
	//
	// @Override
	// protected void onPostExecute(Game result) {
	// super.onPostExecute(result);
	// if (result == null) {
	// Log.d(MainActivity.DWF, "Failed deleting, result is null");
	// return;
	// }
	// updateGames();
	// }
	// }

}
