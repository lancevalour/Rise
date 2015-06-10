package yicheng.android.app.rise.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.gc.materialdesign.views.Slider;
import com.gc.materialdesign.views.Slider.OnValueChangedListener;

import yicheng.android.app.rise.R;
import yicheng.android.app.rise.database.RiseEvent;
import yicheng.android.app.rise.database.RisePlace;
import yicheng.android.app.rise.database.SQLiteHelper;
import yicheng.android.app.rise.fragment.TimePickerDialogFragment;
import yicheng.android.app.rise.receiver.EventAlarmReceiver;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextClock;

public class NewEventActivity extends ActionBarActivity {

	Toolbar activity_new_event_toolbar;

	Button activity_new_event_start_time_button,
			activity_new_event_end_time_button;

	Slider activity_new_event_time_interval_slider;

	RatingBar activity_new_event_event_priority_ratingBar;

	EditText activity_new_event_event_name_editText,
			activity_new_event_event_content_editText;

	TextView activity_new_event_time_interval_textView;

	ImageButton activity_new_event_event_location_add_button;

	RelativeLayout activity_new_event_event_location_add_location_layout;

	CheckBox activity_new_event_event_location_checkbox;

	ListView activity_new_event_event_location_listView;

	Switch new_event_actionbar_switch;

	ArrayAdapter<String> placeListAdapter;

	String eventStartTime;
	String eventEndTime;
	String eventCycleInterval;

	String eventName;
	String eventContent;
	String eventCreateDate;
	String eventPriority;

	String isEventFinished;
	String isNotificationOn = String.valueOf(true);

	String eventLocationList;

	SQLiteHelper eventSQLiteHelper;

	boolean isUpdatingEvent = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_new_event);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			eventStartTime = extras.getString("event_start_time");
			eventEndTime = extras.getString("event_end_time");
			eventCycleInterval = extras.getString("event_cycle_interval");
			eventName = extras.getString("event_name");
			eventContent = extras.getString("event_content");
			eventPriority = extras.getString("event_priority");
			isNotificationOn = extras.getString("event_is_notification_on");
			eventLocationList = extras.getString("event_location_list");
			isUpdatingEvent = true;
		}

		initiateComponents();
		setComponentStyle();
		setComponentControl();

	}

	private void setComponentStyle() {
		/*	if (Build.VERSION.SDK_INT >= 21) {
				Window window = getWindow();
				window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
				window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
				window.setStatusBarColor(getResources().getColor(
						R.color.theme_primary_dark));
			}
		*/
	}

	private void updatePlaceList() {

		placeListAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,
				selectedNameList.toArray(new String[selectedNameList.size()]));

		activity_new_event_event_location_listView.setAdapter(placeListAdapter);
	}

	private void initiateComponents() {

		activity_new_event_event_location_add_location_layout = (RelativeLayout) findViewById(R.id.activity_new_event_event_location_add_location_layout);

		activity_new_event_event_location_checkbox = (CheckBox) findViewById(R.id.activity_new_event_event_location_checkbox);
		activity_new_event_event_location_checkbox.setChecked(true);

		activity_new_event_event_location_add_button = (ImageButton) findViewById(R.id.activity_new_event_event_location_add_button);

		activity_new_event_event_name_editText = (EditText) findViewById(R.id.activity_new_event_event_name_editText);
		activity_new_event_event_content_editText = (EditText) findViewById(R.id.activity_new_event_event_content_editText);

		activity_new_event_toolbar = (Toolbar) findViewById(R.id.activity_new_event_toolbar);

		activity_new_event_toolbar
				.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back);

		setSupportActionBar(activity_new_event_toolbar);
		if (isUpdatingEvent) {
			getSupportActionBar().setTitle(R.string.toolbar_edit_event_title);
		}
		else {
			getSupportActionBar().setTitle(R.string.toolbar_new_event_title);
		}
		activity_new_event_start_time_button = (Button) findViewById(R.id.activity_new_event_start_time_button);
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		// get current date time with Date()
		activity_new_event_start_time_button.setText(dateFormat
				.format(new Date()));

		activity_new_event_end_time_button = (Button) findViewById(R.id.activity_new_event_end_time_button);

		activity_new_event_end_time_button.setText(dateFormat
				.format(new Date()));

		activity_new_event_event_priority_ratingBar = (RatingBar) findViewById(R.id.activity_new_event_event_priority_ratingBar);

		LayerDrawable stars = (LayerDrawable) activity_new_event_event_priority_ratingBar
				.getProgressDrawable();
		stars.getDrawable(2).setColorFilter(
				getResources().getColor(R.color.theme_accent), Mode.SRC_ATOP);

		stars.getDrawable(0).setColorFilter(
				getResources().getColor(R.color.theme_accent), Mode.SRC_ATOP);

		activity_new_event_time_interval_slider = (Slider) findViewById(R.id.activity_new_event_time_interval_slider);

		activity_new_event_time_interval_textView = (TextView) findViewById(R.id.activity_new_event_time_interval_textView);
		activity_new_event_time_interval_textView.setText(""
				+ activity_new_event_time_interval_slider.getValue());

		selectedNameList = new ArrayList<String>();
		selectedAddressList = new ArrayList<String>();
		selectedIDList = new ArrayList<String>();
		selectedLatList = new ArrayList<String>();
		selectedLongList = new ArrayList<String>();
		selectedTypeList = new ArrayList<String>();

		activity_new_event_event_location_listView = (ListView) findViewById(R.id.activity_new_event_event_location_listView);

		if (isUpdatingEvent) {
			activity_new_event_event_content_editText
					.setText(this.eventContent);
			activity_new_event_event_name_editText.setText(this.eventName);
			activity_new_event_event_name_editText.setEnabled(false);

			activity_new_event_start_time_button.setText(this.eventStartTime);
			activity_new_event_end_time_button.setText(this.eventEndTime);

			activity_new_event_event_priority_ratingBar.setRating(Float
					.valueOf(this.eventPriority));

			activity_new_event_time_interval_slider.setValue(Integer
					.valueOf(this.eventCycleInterval));
			activity_new_event_time_interval_textView.setText(""
					+ this.eventCycleInterval);

			String[] array = eventLocationList.split(",");
			for (String s : array) {
				selectedNameList.add(s);
			}
		}

		updatePlaceList();

	}

	private void setComponentControl() {
		setToolbarControl();
		setStartTimeButtonControl();
		setEndTimeButtonControl();
		setSliderControl();
		setRatingBarControl();
		setCheckBoxControl();
		setAddPlaceButtonControl();

	}

	private void setAddPlaceButtonControl() {
		activity_new_event_event_location_add_button
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						goToAddPlaceActivity();
					}
				});
	}

	private void goToAddPlaceActivity() {

		Intent intent = new Intent(NewEventActivity.this,
				AddPlaceActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		intent.putExtra("selected_name_list", selectedNameList);
		intent.putExtra("selected_address_list", selectedAddressList);
		intent.putExtra("selected_id_list", selectedIDList);
		intent.putExtra("selected_lat_list", selectedLatList);
		intent.putExtra("selected_long_list", selectedLongList);
		intent.putExtra("selected_type_list", selectedTypeList);

		startActivityForResult(intent, REQUEST_CODE);

		// finish();
	}

	private void setCheckBoxControl() {
		activity_new_event_event_location_checkbox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							activity_new_event_event_location_add_location_layout
									.setVisibility(View.VISIBLE);
						}
						else {
							activity_new_event_event_location_add_location_layout
									.setVisibility(View.GONE);
						}
					}

				});

	}

	private void setSliderControl() {
		activity_new_event_time_interval_slider
				.setOnValueChangedListener(new OnValueChangedListener() {

					@Override
					public void onValueChanged(int value) {
						// TODO Auto-generated method stub

						activity_new_event_time_interval_textView.setText(""
								+ value);
					}

				});
	}

	private void setRatingBarControl() {
		activity_new_event_event_priority_ratingBar
				.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

					@Override
					public void onRatingChanged(RatingBar ratingBar,
							float rating, boolean fromUser) {
						// TODO Auto-generated method stub

					}

				});
	}

	private void setStartTimeButtonControl() {
		activity_new_event_start_time_button
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						TimePickerDialogFragment fragment = new TimePickerDialogFragment(
								activity_new_event_start_time_button,
								activity_new_event_start_time_button);
						// fragment.getDialog().getWindow().setLayout(100, 100);
						fragment.show(getFragmentManager(),
								"start_time_picker_dialog");

					}
				});
	}

	private void setEndTimeButtonControl() {
		activity_new_event_end_time_button
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						TimePickerDialogFragment fragment = new TimePickerDialogFragment(
								activity_new_event_start_time_button,
								activity_new_event_end_time_button);
						fragment.show(getFragmentManager(),
								"end_time_picker_dialog");
					}
				});
	}

	private void setToolbarControl() {
		activity_new_event_toolbar
				.setNavigationOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();

					}
				});
	}

	private void setSwitchControl() {
		new_event_actionbar_switch
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							isNotificationOn = String.valueOf(true);
						}
						else {
							isNotificationOn = String.valueOf(false);
						}
					}

				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_new_event, menu);

		if (isUpdatingEvent) {
			menu.findItem(R.id.menu_new_event_add).setIcon(
					R.drawable.ic_done_white_48dp);
		}
		new_event_actionbar_switch = (Switch) menu.findItem(
				R.id.menu_new_event_switch).getActionView();

		new_event_actionbar_switch
				.setChecked(Boolean.valueOf(isNotificationOn));

		setSwitchControl();

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		switch (item.getItemId()) {
		case R.id.menu_new_event_add: {

			addNewEvent();
		}
			break;

		}

		return super.onOptionsItemSelected(item);
	}

	private void addNewEvent() {
		this.eventName = activity_new_event_event_name_editText.getText()
				.toString().trim();
		this.eventContent = activity_new_event_event_content_editText.getText()
				.toString().trim();

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		this.eventCreateDate = df.format(Calendar.getInstance().getTime());

		this.eventPriority = String
				.valueOf(activity_new_event_event_priority_ratingBar
						.getRating());

		this.eventStartTime = activity_new_event_start_time_button.getText()
				.toString();
		this.eventEndTime = activity_new_event_end_time_button.getText()
				.toString();

		this.eventCycleInterval = String
				.valueOf(activity_new_event_time_interval_slider.getValue());

		this.isEventFinished = String.valueOf(false);

		/*	this.isNotificationOn = String.valueOf(true);*/

		StringBuilder sBuilder = new StringBuilder();
		for (String s : selectedNameList) {
			sBuilder.append(s).append(",");
		}

		this.eventLocationList = sBuilder.toString();

		if (this.eventName == null || this.eventName.length() == 0
				|| this.eventContent == null || this.eventContent.length() == 0
				|| this.eventCreateDate == null || this.eventPriority == null
				|| this.eventStartTime == null || this.eventEndTime == null
				|| this.eventCycleInterval == null
				|| this.isEventFinished == null
				|| this.isNotificationOn == null
				|| this.eventLocationList == null) {
			Toast.makeText(getBaseContext(), "One of the event info is empty!",
					Toast.LENGTH_SHORT).show();
		}
		else {
			addNewEventInDatabase();

			startAlarm(isUpdatingEvent);
			finish();
		}

	}

	private void addNewEventInDatabase() {
		eventSQLiteHelper = new SQLiteHelper(getBaseContext(),
				SQLiteHelper.TABLE_EVENT);

		if (eventSQLiteHelper.getEventByName(this.eventName) != null) {
			eventSQLiteHelper.updateEventByName(this.eventName, new RiseEvent(
					this.eventName, this.eventContent, this.eventCreateDate,
					this.eventPriority, this.eventStartTime, this.eventEndTime,
					this.eventCycleInterval, this.isEventFinished,
					this.isNotificationOn, this.eventLocationList));
		}
		else {
			eventSQLiteHelper.addEvent(new RiseEvent(this.eventName,
					this.eventContent, this.eventCreateDate,
					this.eventPriority, this.eventStartTime, this.eventEndTime,
					this.eventCycleInterval, this.isEventFinished,
					this.isNotificationOn, this.eventLocationList));

		}

	}

	private AlarmManager alarmManager;
	PendingIntent pendingIntent;

	public void startAlarm(boolean isUpdatingEvent) {

		if (Boolean.valueOf(isNotificationOn)) {
			int eventID = Integer.valueOf(eventSQLiteHelper
					.getEventPrimaryIDByName(this.eventName));

			System.out.println(eventID);

			Intent alarmIntent = new Intent(this, EventAlarmReceiver.class);

			alarmIntent.putExtra("event_content", this.eventContent);
			alarmIntent.putExtra("event_id", eventID);

			pendingIntent = PendingIntent.getBroadcast(this, eventID,
					alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

			alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
			int interval = 60000;

			/*	alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
						System.currentTimeMillis(), interval, pendingIntent);*/

			String[] startTime = eventStartTime.split(":");

			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(startTime[0]));
			calendar.set(Calendar.MINUTE, Integer.valueOf(startTime[1]));

			// setRepeating() lets you specify a precise custom interval--in
			// this
			// case,
			// 20 minutes.

			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
					calendar.getTimeInMillis(), (long) 1000 * 60 * 1,
					pendingIntent);

			Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
		}
		else {
			int eventID = Integer.valueOf(eventSQLiteHelper
					.getEventPrimaryIDByName(this.eventName));

			System.out.println(eventID);

			Intent alarmIntent = new Intent(this, EventAlarmReceiver.class);

			alarmIntent.putExtra("event_content", this.eventContent);
			alarmIntent.putExtra("event_id", eventID);

			pendingIntent = PendingIntent.getBroadcast(this, eventID,
					alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

			alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

			/*	alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
						System.currentTimeMillis(), interval, pendingIntent);*/

			/*	String[] startTime = eventStartTime.split(":");

				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(System.currentTimeMillis());
				calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(startTime[0]));
				calendar.set(Calendar.MINUTE, Integer.valueOf(startTime[1]));*/

			// setRepeating() lets you specify a precise custom interval--in
			// this
			// case,
			// 20 minutes.
			alarmManager.cancel(pendingIntent);

			/*	alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
						calendar.getTimeInMillis(), (long) 1000 * 60 * 1,
						pendingIntent);*/

			Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
		}

	}

	int REQUEST_CODE = 1;

	ArrayList<String> selectedNameList;
	ArrayList<String> selectedAddressList;
	ArrayList<String> selectedIDList;
	ArrayList<String> selectedLatList;
	ArrayList<String> selectedLongList;
	ArrayList<String> selectedTypeList;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			if (data.hasExtra("selected_name_list")) {
				selectedNameList = (ArrayList<String>) data
						.getSerializableExtra("selected_name_list");
				selectedAddressList = (ArrayList<String>) data
						.getSerializableExtra("selected_address_list");
				selectedIDList = (ArrayList<String>) data
						.getSerializableExtra("selected_id_list");
				selectedLatList = (ArrayList<String>) data
						.getSerializableExtra("selected_lat_list");
				selectedLongList = (ArrayList<String>) data
						.getSerializableExtra("selected_long_list");
				selectedTypeList = (ArrayList<String>) data
						.getSerializableExtra("selected_type_list");
				updatePlaceList();

			}
		}
	}
}
