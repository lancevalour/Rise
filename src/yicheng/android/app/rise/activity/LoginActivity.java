package yicheng.android.app.rise.activity;

import yicheng.android.app.rise.R;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends Activity {

	SignInButton activity_login_signInButton;

	static final int RC_SIGN_IN = 0;

	GoogleApiClient googleApiClient;

	private boolean googleIntentInProgress;
	private boolean googleSignInClicked;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		initiateComponents();
		setGoogleApiClient();
		setComponentControl();
	}

	private void initiateComponents() {
		activity_login_signInButton = (SignInButton) findViewById(R.id.activity_login_signInButton);

	}

	private void goToNavigationDrawerActivity() {
		Intent intent = new Intent(LoginActivity.this,
				NavigationDrawerActvity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	private void setGoogleApiClient() {
		googleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(new ConnectionCallbacks() {

					@Override
					public void onConnected(Bundle connectionHint) {
						// TODO Auto-generated method stub
						googleSignInClicked = false;
						Toast.makeText(getBaseContext(), "Logged in!",
								Toast.LENGTH_SHORT).show();

						goToNavigationDrawerActivity();
					}

					@Override
					public void onConnectionSuspended(int cause) {
						// TODO Auto-generated method stub

					}

				})
				.addOnConnectionFailedListener(
						new OnConnectionFailedListener() {

							@Override
							public void onConnectionFailed(
									ConnectionResult result) {
								// TODO Auto-generated method stub
								if (!googleIntentInProgress) {
									if (googleSignInClicked
											&& result.hasResolution()) {
										// The user has already clicked
										// 'sign-in' so we attempt to resolve
										// all
										// errors until the user is signed in,
										// or they cancel.
										try {
											result.startResolutionForResult(
													LoginActivity.this,
													RC_SIGN_IN);
											googleIntentInProgress = true;
										}
										catch (SendIntentException e) {
											// The intent was canceled before it
											// was sent. Return to the default
											// state and attempt to connect to
											// get an updated ConnectionResult.
											googleIntentInProgress = false;
											googleApiClient.connect();
										}
									}
								}
							}
						}).addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
				.build();
	}

	protected void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
		if (requestCode == RC_SIGN_IN) {
			if (responseCode != RESULT_OK) {
				googleSignInClicked = false;
			}

			googleIntentInProgress = false;

			if (!googleApiClient.isConnected()) {
				googleApiClient.reconnect();
			}
		}
	}

	private void setComponentControl() {
		setGoogleSignInButtonControl();
	}

	private void setGoogleSignInButtonControl() {
		activity_login_signInButton
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (v.getId() == R.id.activity_login_signInButton
								&& !googleApiClient.isConnecting()) {
							googleSignInClicked = true;
							googleApiClient.connect();
						}
					}
				});
	}

	protected void onStart() {
		super.onStart();
		googleApiClient.connect();
	}

	protected void onStop() {
		super.onStop();

		if (googleApiClient.isConnected()) {
			googleApiClient.disconnect();
		}
	}
}
