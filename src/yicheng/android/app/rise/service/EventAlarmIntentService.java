package yicheng.android.app.rise.service;

import java.util.ArrayList;
import java.util.List;

import yicheng.android.app.rise.R;
import yicheng.android.app.rise.database.RisePlace;
import yicheng.android.app.rise.database.SQLiteHelper;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class EventAlarmIntentService extends IntentService {

	NotificationManager notificationManager;

	String placeName;
	String placeLatitude;
	String placeLongitude;
	String alarm_interval;

	String eventContent;
	int eventID;
	String eventLocationList;

	SQLiteHelper placeSQLiteHelper;
	List<Location> locationList;

	final static String GROUP_KEY_EVENT = "notification_group_event";

	public EventAlarmIntentService() {
		super("EventAlarmIntentService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		eventContent = intent.getStringExtra("event_content");
		eventID = intent.getIntExtra("event_id", -1);
		eventLocationList = intent.getStringExtra("event_location_list");

		readLocationListFromDatabase();

		getCurrentPlace();

	}

	private void readLocationListFromDatabase() {
		String[] locations = eventLocationList.split(",");
		locationList = new ArrayList<Location>();
		placeSQLiteHelper = new SQLiteHelper(getBaseContext(),
				SQLiteHelper.TABLE_PLACE);

		for (String s : locations) {
			RisePlace place = placeSQLiteHelper.getPlaceByName(s);
			Location location = new Location("");
			location.setLatitude(Double.parseDouble(place.getPlaceLatitude()));
			location.setLongitude(Double.parseDouble(place.getPlaceLongitude()));
			locationList.add(location);
		}
	}

	private void showNotification() {
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.ic_action_event)
				.setContentTitle("Current Place").setContentText(eventContent)
				.setGroup(GROUP_KEY_EVENT);
		/*
				Intent resultIntent = new Intent(context, NavigationDrawerActvity.class);

				PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
						0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

				mBuilder.setContentIntent(resultPendingIntent);*/

		notificationManager.notify(eventID, mBuilder.build());
	}

	GoogleApiClient mGoogleApiClient;

	private void getCurrentPlace() {
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addApi(Places.GEO_DATA_API)
				.addApi(Places.PLACE_DETECTION_API)
				.addApi(LocationServices.API)
				.addConnectionCallbacks(new ConnectionCallbacks() {

					@Override
					public void onConnected(Bundle arg0) {
						// TODO Auto-generated method stub
						Location mLastLocation = LocationServices.FusedLocationApi
								.getLastLocation(mGoogleApiClient);
						if (mLastLocation != null) {
							placeLatitude = String.valueOf(mLastLocation
									.getLatitude());
							placeLongitude = String.valueOf(mLastLocation
									.getLongitude());

						}

						for (Location location : locationList) {
							if (location.distanceTo(mLastLocation) < 50) {

								showNotification();
							}
						}

					}

					@Override
					public void onConnectionSuspended(int arg0) {
						// TODO Auto-generated method stub

					}

				})
				.addOnConnectionFailedListener(
						new OnConnectionFailedListener() {

							@Override
							public void onConnectionFailed(ConnectionResult arg0) {
								// TODO Auto-generated method stub

							}

						}).build();
		mGoogleApiClient.connect();
	}
}
