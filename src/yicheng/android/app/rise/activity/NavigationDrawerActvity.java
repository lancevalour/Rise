package yicheng.android.app.rise.activity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.Person.Image;

import yicheng.android.app.rise.R;
import yicheng.android.app.rise.adapter.NavigationDrawerRecyclerViewAdapter;
import yicheng.android.app.rise.database.SQLiteHelper;
import yicheng.android.app.rise.fragment.EventsFragment;
import yicheng.android.app.rise.fragment.PlacesFragment;
import yicheng.android.ui.floatingactionbutton.FloatingActionButton;
import yicheng.android.ui.floatingactionbutton.FloatingActionsMenu;
import yicheng.android.ui.floatingactionbutton.FloatingActionsMenu.OnFloatingActionsMenuUpdateListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.internal.widget.AdapterViewCompat.OnItemClickListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

public class NavigationDrawerActvity extends ActionBarActivity {
	String DRAWER_ROW_ITEM_TITLES[] = { "Events", "Places" };
	int DRAWER_ROW_ITEM_IMAGE_IDS[] = { R.drawable.ic_action_events,
			R.drawable.ic_action_places };

	String USER_NAME = "Yicheng Zhang";
	String USER_EMAIL = "yicheng@gmail.com";
	int USER_AVATER_IMAGE_ID = R.drawable.ic_launcher;
	Uri USER_BACKGROUND_IMAGE_ID;

	FloatingActionsMenu activity_navigation_drawer_floatingActionMenu;

	FloatingActionButton test_floatingActionButton1,
			test_floatingActionButton2, test_floatingActionButton3;

	FrameLayout activity_navigation_drawer_content_framelayout;

	Toolbar activity_navigation_drawer_toolbar;

	RecyclerView activity_navigation_drawer_recyclerView;
	RecyclerView.Adapter activity_navigation_drawer_recyclerView_adapter;
	RecyclerView.LayoutManager activity_navigation_drawer_recyclerView_layoutManager;

	DrawerLayout activity_navigation_drawer_layout;

	ActionBarDrawerToggle actionBarDrawerToggle;

	GoogleApiClient googleApiClient;
	boolean googleSignOutClicked;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_navigation_drawer);

		initiateComponents();
		setGoogleApiClient();
		setComponentStyle();
		setComponentControl();
		setRecyclerViewControl();
	}

	private void goToLoginActivity() {
		Intent intent = new Intent(NavigationDrawerActvity.this,
				LoginActivity.class);
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
						googleSignOutClicked = false;
						if (Plus.PeopleApi.getCurrentPerson(googleApiClient) != null) {
							Person currentPerson = Plus.PeopleApi
									.getCurrentPerson(googleApiClient);

							String personName = currentPerson.getDisplayName();
							Image personPhoto = currentPerson.getImage();

							String personGooglePlusProfile = currentPerson
									.getUrl();

							USER_BACKGROUND_IMAGE_ID = Uri.parse(currentPerson
									.getImage().getUrl().toString());

							System.out.println(currentPerson.getImage()
									.getUrl());

						}

					}

					@Override
					public void onConnectionSuspended(int cause) {
						// TODO Auto-generated method stub
						googleApiClient.connect();
					}

				})
				.addOnConnectionFailedListener(
						new OnConnectionFailedListener() {

							@Override
							public void onConnectionFailed(
									ConnectionResult result) {
								// TODO Auto-generated method stub

							}
						}).addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
				.build();
	}

	private void setComponentStyle() {
		if (Build.VERSION.SDK_INT >= 21) {
			Window window = getWindow();

			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(getResources().getColor(
					R.color.theme_primary_dark));

		}

	}

	private void initiateComponents() {
		test_floatingActionButton1 = (FloatingActionButton) findViewById(R.id.test_floatingActionButton1);
		test_floatingActionButton2 = (FloatingActionButton) findViewById(R.id.test_floatingActionButton2);
		test_floatingActionButton3 = (FloatingActionButton) findViewById(R.id.test_floatingActionButton3);

		activity_navigation_drawer_floatingActionMenu = (FloatingActionsMenu) findViewById(R.id.activity_navigation_drawer_floatingActionMenu);

		activity_navigation_drawer_content_framelayout = (FrameLayout) findViewById(R.id.activity_navigation_drawer_content_framelayout);

		activity_navigation_drawer_toolbar = (Toolbar) findViewById(R.id.activity_navigation_drawer_toolbar);
		setSupportActionBar(activity_navigation_drawer_toolbar);

		activity_navigation_drawer_recyclerView = (RecyclerView) findViewById(R.id.activity_navigation_drawer_recyclerView); // Assigning

		activity_navigation_drawer_recyclerView.setHasFixedSize(true);

		activity_navigation_drawer_recyclerView_adapter = new NavigationDrawerRecyclerViewAdapter(
				getBaseContext(), DRAWER_ROW_ITEM_TITLES,
				DRAWER_ROW_ITEM_IMAGE_IDS, USER_NAME, USER_EMAIL,
				USER_AVATER_IMAGE_ID, USER_BACKGROUND_IMAGE_ID);

		activity_navigation_drawer_recyclerView
				.setAdapter(activity_navigation_drawer_recyclerView_adapter);

		activity_navigation_drawer_recyclerView_layoutManager = new LinearLayoutManager(
				this);

		activity_navigation_drawer_recyclerView
				.setLayoutManager(activity_navigation_drawer_recyclerView_layoutManager);

		activity_navigation_drawer_layout = (DrawerLayout) findViewById(R.id.activity_navigation_drawer_layout);

		actionBarDrawerToggle = new ActionBarDrawerToggle(this,
				activity_navigation_drawer_layout,
				activity_navigation_drawer_toolbar,
				R.drawable.ic_action_navigation_menu,
				R.drawable.ic_action_navigation_arrow_back) {

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				// code here will execute once the drawer is opened( As I dont
				// want anything happened whe drawer is
				// open I am not going to put anything here)
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				// Code here will execute once drawer is closed
			}

		};

		activity_navigation_drawer_layout
				.setDrawerListener(actionBarDrawerToggle);

		actionBarDrawerToggle.syncState();

		Fragment frontFragment = new PlacesFragment();

		getFragmentManager()
				.beginTransaction()
				.add(R.id.activity_navigation_drawer_content_framelayout,
						frontFragment).commit();

		getSupportActionBar().setTitle(
				R.string.toolbar_navigation_drawer_title_events);
	}

	private void setComponentControl() {
		setRecyclerViewControl();
		setFloatingActionMenuControl();
		setFloatingActionButtonControl();

	}

	SQLiteHelper placeSQLiteHelper, eventSQLiteHelper;

	private void setFloatingActionButtonControl() {
		placeSQLiteHelper = new SQLiteHelper(getBaseContext(),
				SQLiteHelper.TABLE_PLACE);
		test_floatingActionButton1
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						/*	placeSQLiteHelper.addPlace(new Place("home",
									"227 Namar Ave", "place_id", "100", "200",
									"type"));
						*/

						openPlacePickerActivity();
					}
				});
		test_floatingActionButton2
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						goToNewPlaceActivity();

					}
				});

		test_floatingActionButton3
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						/*placeSQLiteHelper.deletePlaceById(placeSQLiteHelper
								.getPlacesCount());*/
						goToNewEventActivity();
					}
				});
	}

	int PLACE_PICKER_REQUEST = 1;

	private void openPlacePickerActivity() {
		PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

		try {
			startActivityForResult(builder.build(getApplicationContext()),
					PLACE_PICKER_REQUEST);
		}
		catch (GooglePlayServicesRepairableException
				| GooglePlayServicesNotAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PLACE_PICKER_REQUEST) {
			if (resultCode == RESULT_OK) {
				Place place = PlacePicker.getPlace(data, this);
				String toastMsg = String.format("Place: %s", place.getName());
				Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
			}
		}
	}

	private void goToNewEventActivity() {

		Intent intent = new Intent(NavigationDrawerActvity.this,
				NewEventActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);

		// finish();
	}

	private void goToNewPlaceActivity() {
		Intent intent = new Intent(NavigationDrawerActvity.this,
				NewPlaceActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	private void setFloatingActionMenuControl() {

		activity_navigation_drawer_floatingActionMenu
				.setOnFloatingActionsMenuUpdateListener(new OnFloatingActionsMenuUpdateListener() {

					@Override
					public void onMenuExpanded() {
						// TODO Auto-generated method stub

					}

					@Override
					public void onMenuCollapsed() {
						// TODO Auto-generated method stub

					}

				});
	}

	private void setRecyclerViewControl() {
		final GestureDetector mGestureDetector = new GestureDetector(
				NavigationDrawerActvity.this,
				new GestureDetector.SimpleOnGestureListener() {

					@Override
					public boolean onSingleTapUp(MotionEvent e) {
						return true;
					}

				});

		activity_navigation_drawer_recyclerView
				.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
					@Override
					public boolean onInterceptTouchEvent(
							RecyclerView recyclerView, MotionEvent motionEvent) {
						View child = recyclerView.findChildViewUnder(
								motionEvent.getX(), motionEvent.getY());

						if (child != null
								&& mGestureDetector.onTouchEvent(motionEvent)) {

							activity_navigation_drawer_layout.closeDrawers();
							Toast.makeText(
									NavigationDrawerActvity.this,
									"The Item Clicked is: "
											+ recyclerView
													.getChildPosition(child),
									Toast.LENGTH_SHORT).show();

							switch (recyclerView.getChildPosition(child)) {

							case 0: {
								if (googleApiClient.isConnected()) {
									Plus.AccountApi
											.clearDefaultAccount(googleApiClient);
									googleApiClient.disconnect();
									googleApiClient.connect();
									Toast.makeText(getBaseContext(),
											"Logged Out!", Toast.LENGTH_SHORT)
											.show();

									goToLoginActivity();
								}

							}
								break;

							case 1: {
								FragmentManager fragmentManager = getFragmentManager();

								Fragment frontFragment = new EventsFragment();

								fragmentManager
										.beginTransaction()
										.replace(
												R.id.activity_navigation_drawer_content_framelayout,
												frontFragment).commit();
								getSupportActionBar()
										.setTitle(
												R.string.toolbar_navigation_drawer_title_events);
							}
								break;
							case 2: {
								FragmentManager fragmentManager = getFragmentManager();

								Fragment frontFragment = new PlacesFragment();

								fragmentManager
										.beginTransaction()
										.replace(
												R.id.activity_navigation_drawer_content_framelayout,
												frontFragment).commit();
								getSupportActionBar()
										.setTitle(
												R.string.toolbar_navigation_drawer_title_places);
							}
								break;
							}

							return true;

						}

						return false;
					}

					@Override
					public void onTouchEvent(RecyclerView recyclerView,
							MotionEvent motionEvent) {

					}
				});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_navigation_drawer, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		// noinspection SimplifiableIfStatement
		if (id == R.id.menu_new_event_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
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
