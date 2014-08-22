package edu.rosehulman.dicewithfriends;

import java.io.IOException;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.appspot.dice_with_friends.dicewithfriends.model.Game;
import com.appspot.dice_with_friends.dicewithfriends.model.GameProtoInviteeEmail;
import com.appspot.dice_with_friends.dicewithfriends.model.Player;

import edu.rosehulman.dicewithfriends.utils.ServiceUtils;
import edu.rosehulman.dicewithfriends.utils.Utils;

public class GameListActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	private GameListFragment mGameListFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_display);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(
				R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getFragmentManager();
		mGameListFragment = GameListFragment.newInstance(position);
		fragmentManager.beginTransaction().replace(R.id.container, mGameListFragment).commit();
	}

	public void onFragmentAttached(int number) {
		mTitle = getResources().getStringArray(R.array.game_types)[number];
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.action_add:
			addGame();
			return true;
		case R.id.action_sync:
			mGameListFragment.updateGames();
			return true;
		case R.id.action_settings:
			return true;
		case R.id.action_signout:
			ServiceUtils.signout();
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void addGame() {
		DialogFragment df = new DialogFragment() {
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("Create a new game");
				View view = getLayoutInflater().inflate(R.layout.dialog_new_game, null);
				final EditText emailTextView = (EditText) view.findViewById(R.id.dialogOpponentEmailEditText);
				builder.setView(view);
				builder.setPositiveButton(android.R.string.ok, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String opponentEmail = emailTextView.getText().toString();
						(new NewGameTask()).execute(opponentEmail);
					}
				});
				builder.setNegativeButton(android.R.string.cancel, null);
				return builder.create();
			}
		};
		df.show(getFragmentManager(), "displayName");

	}

	class NewGameTask extends AsyncTask<String, Void, Game> {

		private ProgressDialog mProgressDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(GameListActivity.this);
			mProgressDialog.setMessage("Creating new game");
			mProgressDialog.show();
		}

		@Override
		protected Game doInBackground(String... emails) {
			try {
				GameProtoInviteeEmail content = new GameProtoInviteeEmail();
				content.setInviteeEmail(emails[0]);
				Game game = ServiceUtils.getService().game().newgame(content).execute();
				return game;
			} catch (IOException e) {
				mProgressDialog.dismiss();
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Game game) {
			super.onPostExecute(game);
			mProgressDialog.dismiss();
			if (game == null) {
				Log.d(Utils.DWF, "Error creating new game, result is null");
				return;
			}
			Intent intent = new Intent(GameListActivity.this, GameActivity.class);
			GameExtras.putGameExtras(intent, game);
			startActivity(intent);
		}
	}

}
