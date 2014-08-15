package edu.rosehulman.dicewithfriends;

import android.accounts.AccountManager;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import edu.rosehulman.dicewithfriends.utils.PlayerUtils;
import edu.rosehulman.dicewithfriends.utils.ServiceUtils;

public class MainActivity extends Activity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	// For logging
	public static final String DWF = "DWF";

	static final int REQUEST_ACCOUNT_PICKER = 1;
	
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// CONSIDER: This may not be the best place to do this.
		PlayerUtils.setContext(this);
		ServiceUtils.setContext(this);
		
		if (ServiceUtils.getAccountName() == null) {
			// Not signed in, show login window or request an existing account.
			startActivityForResult(ServiceUtils.getCredential().newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
		} else {
			PlayerUtils.getPlayerForUser();
		}
		
		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case REQUEST_ACCOUNT_PICKER:
			if (data != null && data.getExtras() != null) {
				String accountName = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);
				if (accountName != null) {
					ServiceUtils.setAccountName(accountName); // User is authorized.
					Log.d(DWF, "Account name set to " + accountName);
					
					// TODO: Now I have an account name. But I need to get the player for me from the server, using an asynch task.
					PlayerUtils.getPlayerForUser();
				}
			}
			break;
		}
	}
	
	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.container, GameListFragment.newInstance(position + 1)).commit();
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.waiting_for_me);
			break;
		case 2:
			mTitle = getString(R.string.waiting_only_for_opponent);
			break;
		case 3:
			mTitle = getString(R.string.finished_multiplayer_games);
			break;
		case 4:
			mTitle = getString(R.string.in_progress_solo_games);
			break;
		case 5:
			mTitle = getString(R.string.finished_solo_games);
			break;
		}
		
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
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
