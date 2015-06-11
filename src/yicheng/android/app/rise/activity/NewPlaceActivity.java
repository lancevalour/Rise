package yicheng.android.app.rise.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import yicheng.android.app.rise.R;
import yicheng.android.app.rise.adapter.PlaceAutoCompleteAdapter;
import yicheng.android.app.rise.database.RisePlace;
import yicheng.android.app.rise.database.SQLiteHelper;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class NewPlaceActivity extends ActionBarActivity {

	ImageButton activity_new_place_autocomplete_clear_button,
			activity_new_place_place_picker_button;

	CheckBox activity_new_place_place_label_work_checkbox,
			activity_new_place_place_label_home_checkbox,
			activity_new_place_place_label_play_checkbox;

	Toolbar activity_new_place_toolbar;

	AutoCompleteTextView activity_new_place_autocomplete_textView;

	PlaceAutoCompleteAdapter autoCompleteAdapter;

	GoogleApiClient googleApiClient;

	String placeName;
	String placeAddress;
	String placeID;
	String placeLatitude;
	String placeLongitude;
	String placeTypes;

	SQLiteHelper placeSQLiteHelper;

	Set<String> placeTypesSet;

	boolean isUpdatingPlace = false;

	private static final LatLngBounds BOUNDS_UNITED_STATES = new LatLngBounds(
			new LatLng(18.911064, 172.445896),
			new LatLng(71.386775, -66.945395));

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_place);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			placeName = extras.getString("place_name");
			placeAddress = extras.getString("place_address");
			placeTypes = extras.getString("place_types");
			isUpdatingPlace = true;
		}

		setGoogleApiClient();

		initiateComponents();
		setComponentStyle();

		setComponentControl();
	}

	private void setGoogleApiClient() {
		googleApiClient = new GoogleApiClient.Builder(this)
				.enableAutoManage(this, 0, new OnConnectionFailedListener() {

					@Override
					public void onConnectionFailed(ConnectionResult result) {
						// TODO Auto-generated method stub

						// TODO(Developer): Check error code and notify the user
						// of error state and resolution.

						Toast.makeText(
								getBaseContext(),
								"Could not connect to Google API Client: Error "
										+ result.getErrorCode(),
								Toast.LENGTH_SHORT).show();
					}

				}).addApi(Places.GEO_DATA_API).build();
	}

	private void initiateComponents() {

		activity_new_place_autocomplete_clear_button = (ImageButton) findViewById(R.id.activity_new_place_autocomplete_clear_button);
		activity_new_place_place_picker_button = (ImageButton) findViewById(R.id.activity_new_place_place_picker_button);

		activity_new_place_place_label_work_checkbox = (CheckBox) findViewById(R.id.activity_new_place_place_label_work_checkbox);
		activity_new_place_place_label_home_checkbox = (CheckBox) findViewById(R.id.activity_new_place_place_label_home_checkbox);
		activity_new_place_place_label_play_checkbox = (CheckBox) findViewById(R.id.activity_new_place_place_label_play_checkbox);

		activity_new_place_toolbar = (Toolbar) findViewById(R.id.activity_new_place_toolbar);

		activity_new_place_toolbar
				.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back);
		setSupportActionBar(activity_new_place_toolbar);
		if (isUpdatingPlace) {
			getSupportActionBar().setTitle(R.string.toolbar_edit_place_title);
		}
		else {
			getSupportActionBar().setTitle(R.string.toolbar_new_place_title);
		}
		activity_new_place_autocomplete_textView = (AutoCompleteTextView) findViewById(R.id.activity_new_place_autocomplete_textView);

		autoCompleteAdapter = new PlaceAutoCompleteAdapter(this,
				android.R.layout.simple_list_item_1, googleApiClient,
				BOUNDS_UNITED_STATES, null);

		activity_new_place_autocomplete_textView
				.setAdapter(autoCompleteAdapter);

		placeTypesSet = new HashSet<String>();

		if (placeAddress != null) {
			activity_new_place_autocomplete_textView.setText(placeAddress);
		}

		if (isUpdatingPlace) {
			activity_new_place_autocomplete_textView.setEnabled(false);
			activity_new_place_autocomplete_clear_button.setEnabled(false);
			activity_new_place_place_picker_button.setEnabled(false);
		}

		if (placeTypes != null) {
			String[] placeTypesArray = placeTypes.split(",");

			System.out.println(Arrays.toString(placeTypesArray));
			for (String s : placeTypesArray) {
				placeTypesSet.add(s);
			}

			if (placeTypesSet.contains("work")) {
				activity_new_place_place_label_work_checkbox.setChecked(true);
			}
			if (placeTypesSet.contains("play")) {
				activity_new_place_place_label_play_checkbox.setChecked(true);
			}
			if (placeTypesSet.contains("home")) {
				activity_new_place_place_label_home_checkbox.setChecked(true);
			}
			placeTypes = "";

		}
	}

	private void setComponentStyle() {

	}

	private void setComponentControl() {
		setToolbarControl();
		setAutoCompleteTextViewControl();
		setCheckBoxControl();
		setPlacePickerButtonControl();
		setClearButtonControl();
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

				activity_new_place_autocomplete_textView.setText(place
						.getAddress());

				placeName = "" + place.getName();
				placeAddress = "" + place.getAddress();
				placeID = "" + place.getId();
				placeLatitude = "" + place.getLatLng().latitude;
				placeLongitude = "" + place.getLatLng().longitude;

			}
		}
	}

	private void setPlacePickerButtonControl() {
		activity_new_place_place_picker_button
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						openPlacePickerActivity();
					}
				});
	}

	private void setClearButtonControl() {
		activity_new_place_autocomplete_clear_button
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						activity_new_place_autocomplete_textView.setText("");
					}
				});
	}

	private void setCheckBoxControl() {

		activity_new_place_place_label_work_checkbox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							placeTypesSet.add("work");

						}
						else {
							placeTypesSet.remove("work");
						}
					}

				});

		activity_new_place_place_label_home_checkbox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							placeTypesSet.add("home");
						}
						else {
							placeTypesSet.remove("home");
						}
					}

				});
		activity_new_place_place_label_play_checkbox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							placeTypesSet.add("play");
						}
						else {
							placeTypesSet.remove("play");
						}
					}

				});

	}

	private void setAutoCompleteTextViewControl() {
		activity_new_place_autocomplete_textView
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						final PlaceAutoCompleteAdapter.PlaceAutocomplete item = autoCompleteAdapter
								.getItem(position);
						final String placeId = String.valueOf(item.placeId);

						/*
						 Issue a request to the Places Geo Data API to retrieve a Place object with additional
						  details about the place.
						  */
						PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
								.getPlaceById(googleApiClient, placeId);
						placeResult
								.setResultCallback(new ResultCallback<PlaceBuffer>() {

									@Override
									public void onResult(PlaceBuffer places) {
										// TODO Auto-generated method stub
										if (!places.getStatus().isSuccess()) {
											// Request did not complete
											// successfully

											places.release();
											return;
										}
										// Get the Place object from the buffer.
										final Place place = places.get(0);

										// Format details of the place for
										// display and show it in a TextView.
										/*				mPlaceDetailsText
																.setText(formatPlaceDetails(
																		getResources(),
																		place.getName(),
																		place.getId(),
																		place.getAddress(),
																		place.getPhoneNumber(),
																		place.getWebsiteUri()));
										*/

										placeName = "" + place.getName();
										placeAddress = "" + place.getAddress();
										placeID = "" + place.getId();
										placeLatitude = ""
												+ place.getLatLng().latitude;
										placeLongitude = ""
												+ place.getLatLng().longitude;
										// Display the third party attributions
										// if set.
										final CharSequence thirdPartyAttribution = places
												.getAttributions();
										/*	if (thirdPartyAttribution == null) {
												mPlaceDetailsAttribution
														.setVisibility(View.GONE);
											}
											else {
												mPlaceDetailsAttribution
														.setVisibility(View.VISIBLE);
												mPlaceDetailsAttribution.setText(Html
														.fromHtml(thirdPartyAttribution
																.toString()));
											}

											Log.i(TAG, "Place details received: "
													+ place.getName());*/

										places.release();
									}

								});

						Toast.makeText(getApplicationContext(),
								"Clicked: " + item.description,
								Toast.LENGTH_SHORT).show();

					}

				});
	}

	private void setToolbarControl() {
		activity_new_place_toolbar
				.setNavigationOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();

					}
				});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_new_place, menu);

		if (isUpdatingPlace) {
			menu.findItem(R.id.menu_new_place_add).setIcon(
					R.drawable.ic_action_done);
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_new_place_add: {
			addNewPlace();
		}
			break;
		/*	case R.id.menu_new_place_settings: {

			}
				break;*/
		}

		return super.onOptionsItemSelected(item);
	}

	private void addNewPlace() {

		for (String s : placeTypesSet) {
			if (placeTypes == null) {
				placeTypes = "";
			}

			placeTypes += s + ",";
		}

		if (!isUpdatingPlace) {
			if (this.placeName == null || this.placeAddress == null
					|| this.placeID == null || this.placeLatitude == null
					|| this.placeLongitude == null || this.placeTypes == null
					|| this.placeTypes.length() <= 0) {
				Toast.makeText(getBaseContext(),
						"One of the place info is empty!", Toast.LENGTH_SHORT)
						.show();
			}
			else {

				addNewPlaceInDatabase();
				finish();
			}
		}
		else {
			if (this.placeTypes == null || this.placeTypes.length() <= 0) {
				Toast.makeText(getBaseContext(),
						"One of the place info is empty!", Toast.LENGTH_SHORT)
						.show();
			}
			else {
				updatePlaceInDatabase();
				finish();
			}
		}

	}

	private void updatePlaceInDatabase() {
		placeSQLiteHelper = new SQLiteHelper(getBaseContext(),
				SQLiteHelper.TABLE_PLACE);
		RisePlace place = placeSQLiteHelper.getPlaceByName(placeName);
		placeSQLiteHelper.updatePlaceByName(
				this.placeName,
				new RisePlace(this.placeName, this.placeAddress, place
						.getPlaceID(), place.getPlaceLatitude(), place
						.getPlaceLongitude(), placeTypes));
	}

	private void addNewPlaceInDatabase() {

		System.out.println(placeTypes);

		placeSQLiteHelper = new SQLiteHelper(getBaseContext(),
				SQLiteHelper.TABLE_PLACE);

		if (placeSQLiteHelper.getPlaceByName(this.placeName) != null) {

			placeSQLiteHelper.updatePlaceByName(this.placeName, new RisePlace(
					this.placeName, this.placeAddress, this.placeID,
					this.placeLatitude, this.placeLongitude, this.placeTypes));
		}
		else {
			placeSQLiteHelper.addPlace(new RisePlace(this.placeName,
					this.placeAddress, this.placeID, this.placeLatitude,
					this.placeLongitude, this.placeTypes));

		}

	}
}
