package yicheng.android.app.rise.receiver;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionApi;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;

import yicheng.android.app.rise.R;
import yicheng.android.app.rise.activity.NavigationDrawerActivity;
import yicheng.android.app.rise.service.EventAlarmIntentService;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class EventAlarmReceiver extends BroadcastReceiver {

	Context context;

	String eventName;
	int eventID;
	String eventLocationList;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		this.context = context;

		eventName = intent.getStringExtra("event_content");
		eventID = intent.getIntExtra("event_id", -1);
		eventLocationList = intent.getStringExtra("event_location_list");

		// showNotification();
		startEventAlarmIntentService();

	}

	private void startEventAlarmIntentService() {
		Intent intent = new Intent(context, EventAlarmIntentService.class);
		intent.putExtra("event_content", eventName);
		intent.putExtra("event_id", eventID);
		intent.putExtra("event_location_list", eventLocationList);
		context.startService(intent);
	}

}
