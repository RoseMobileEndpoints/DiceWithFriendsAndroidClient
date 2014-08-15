package edu.rosehulman.dicewithfriends;

import java.io.IOException;

import com.appspot.dice_with_friends.dicewithfriends.Dicewithfriends;
import com.appspot.dice_with_friends.dicewithfriends.model.Player;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import edu.rosehulman.dicewithfriends.utils.PlayerUtils;
import edu.rosehulman.dicewithfriends.utils.ServiceUtils;
import edu.rosehulman.dicewithfriends.utils.Utils;

public class LoginActivity extends Activity {
	static final int REQUEST_ACCOUNT_PICKER = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// CONSIDER: This may not be the best place to do this.
		PlayerUtils.setContext(this);
		ServiceUtils.setContext(this);
		
		if (PlayerUtils.hasPlayer()) {
			startActivity(new Intent(this, GameDisplayActivity.class));
		} 
	}
	
	public void loginHandler(View view) {
		startActivityForResult(ServiceUtils.getCredential().newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
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
					Log.d(Utils.DWF, "Account name set to " + accountName);
					
					// TODO: Now I have an account name. But I need to get the player for me from the server, using an asynch task.
					(new QueryForPlayerTask()).execute();
				}
			}
			break;
		}
	}
	
	public class QueryForPlayerTask extends AsyncTask<Void, Void, Player> {
		@Override
		protected Player doInBackground(Void ... unused) {
			Player player = null;
			try {
				Dicewithfriends.Player.Get query = ServiceUtils.getService().player().get();
				Log.d(Utils.DWF, "Query = " + (query == null ? "null " : query.toString()));
				player = query.execute();

			} catch (IOException e) {
				Log.d(Utils.DWF, "Failed loading " + e, e);

			}
			return player;
		}

		@Override
		protected void onPostExecute(Player result) {
			super.onPostExecute(result);
			if (result == null) {
				Log.d(Utils.DWF, "Failed loading, result playername is null");
				return;
			}

			// Save this player for later
			Log.d(Utils.DWF, "Got player, name = " + result.getDisplayName());
			// TODO: Check if there is a display name. If not, then ask for one via dialog fragment. Then insert here and into backend.

			PlayerUtils.setPlayerForUser(result);
		}
	}
}
