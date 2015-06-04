package yicheng.android.app.rise.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

	String eventStartTime;
	String eventEndTime;
	String eventCycleInterval;

	String eventName;
	String eventContent;
	String eventCreateDate;
	String eventPriority;

	String isEventFinished;
	String isNotificationOn;

	String eventLocationList;

	SQLiteHelper eventSQLiteHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_new_event);

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

	private void initiateComponents() {

		activity_new_event_event_location_add_location_layout = (RelativeLayout) findViewById(R.id.activity_new_event_event_location_add_location_layout);

		activity_new_event_event_location_checkbox = (CheckBox) findViewById(R.id.activity_new_event_event_location_checkbox);
		activity_new_event_event_location_checkbox.setChecked(true);

		activity_new_event_event_location_listView = (ListView) findViewById(R.id.activity_new_event_event_location_listView);

		String[] items = new String[] { "1", "2", "3", "4", "5", "6", "7", "1",
				"1", "2", "1", "2", "3", "4", "5", "6", "7", "1", "1", "2" };

		ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items);

		activity_new_event_event_location_listView.setAdapter(itemsAdapter);

		activity_new_event_event_location_add_button = (ImageButton) findViewById(R.id.activity_new_event_event_location_add_button);

		activity_new_event_event_name_editText = (EditText) findViewById(R.id.activity_new_event_event_name_editText);
		activity_new_event_event_content_editText = (EditText) findViewById(R.id.activity_new_event_event_content_editText);

		activity_new_event_toolbar = (Toolbar) findViewById(R.id.activity_new_event_toolbar);

		activity_new_event_toolbar
				.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back);

		setSupportActionBar(activity_new_event_toolbar);
		getSupportActionBar().setTitle(R.string.toolbar_new_event_title);

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
	}

	private void setComponentControl() {
		setToolbarControl();
		setStartTimeButtonControl();
		setEndTimeButtonControl();
		setSliderControl();
		setRatingBarControl();
		setCheckBoxControl();
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_new_event, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		switch (item.getItemId()) {
		case R.id.menu_new_event_add: {
			addNewEvent();
		}
			break;
		case R.id.menu_new_event_settings: {

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

		if (this.eventName == null || this.eventName.length() == 1
				|| this.eventContent == null || this.eventContent.length() == 1
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

	public void startAlarm() {
		Intent alarmIntent = new Intent(this, EventAlarmReceiver.class);

		alarmIntent.putExtra("alarm_interval", "44"); // data to pass

		pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		int interval = 60000;

		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis(), interval, pendingIntent);

		Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
	}

}
