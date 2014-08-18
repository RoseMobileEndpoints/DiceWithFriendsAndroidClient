package edu.rosehulman.dicewithfriends;

import java.io.IOException;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.appspot.dice_with_friends.dicewithfriends.Dicewithfriends;
import com.appspot.dice_with_friends.dicewithfriends.model.Player;

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
		Utils.setContext(this);

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
					ServiceUtils.setAccountName(accountName); // User is
																// authorized.
					Log.d(Utils.DWF, "Account name set to " + accountName);

					// Now I have an account name. But I need to get the player
					// for me from the server, using an asynch task.
					(new QueryForPlayerTask()).execute();
				}
			}
			break;
		}
	}

	public class QueryForPlayerTask extends AsyncTask<Void, Void, Player> {

		private ProgressDialog mProgressDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(LoginActivity.this);
			mProgressDialog.setMessage("Loading account");
			mProgressDialog.show();

		}

		@Override
		protected Player doInBackground(Void... unused) {
			Player player = null;
			try {
				Dicewithfriends.Player.Get query = ServiceUtils.getService().player().get();
				Log.d(Utils.DWF, "Query = " + (query == null ? "null " : query.toString()));
				player = query.execute();

			} catch (IOException e) {
				Log.d(Utils.DWF, "Failed loading " + e, e);
				mProgressDialog.dismiss();
			}
			return player;
		}

		@Override
		protected void onPostExecute(final Player result) {
			super.onPostExecute(result);
			mProgressDialog.dismiss();
			if (result == null) {
				Log.d(Utils.DWF, "Failed loading, result playername is null");
				return;
			}

			// Save this player for later
			Log.d(Utils.DWF, "Got player, name = " + result.getDisplayName());
			// TODO: Check if there is a display name. If not, then ask for one
			// via dialog fragment. Then insert here and into backend.
			if (result.getDisplayName() != null) {
				PlayerUtils.setPlayerForUser(result);
				startActivity(new Intent(LoginActivity.this, GameDisplayActivity.class));
			} else {
				DialogFragment df = new DialogFragment() {
					@Override
					public Dialog onCreateDialog(Bundle savedInstanceState) {
						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
						builder.setTitle("Enter display name");
						View view = getLayoutInflater().inflate(R.layout.dialog_display_name, null);
						final EditText nameTextView = (EditText) view.findViewById(R.id.dialogDisplayNameEditText);
						builder.setView(view);
						builder.setPositiveButton(android.R.string.ok, new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								result.setDisplayName(nameTextView.getText().toString());
								(new InsertPlayerTask()).execute(result);
							}
						});
						builder.setNegativeButton(android.R.string.cancel, null);
						return builder.create();
					}
				};
				df.show(getFragmentManager(), "displayName");
			}
		}
	}

	class InsertPlayerTask extends AsyncTask<Player, Void, Player> {
		private ProgressDialog mProgressDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(LoginActivity.this);
			mProgressDialog.setMessage("Loading account");
			mProgressDialog.show();

		}

		@Override
		protected Player doInBackground(Player... players) {
			try {
				Player player = ServiceUtils.getService().player().insert(players[0]).execute();
				return player;
			} catch (IOException e) {
				mProgressDialog.dismiss();
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Player result) {
			super.onPostExecute(result);
			mProgressDialog.dismiss();
			if (result == null) {
				Log.d(Utils.DWF, "Error inserting game, result is null");
				return;
			}
			startActivity(new Intent(LoginActivity.this, GameDisplayActivity.class));
		}
	}

}
