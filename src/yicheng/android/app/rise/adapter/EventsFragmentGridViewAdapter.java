package yicheng.android.app.rise.adapter;

import java.util.List;

import yicheng.android.ui.materialdesignlibrary.widgets.SnackBar;

import yicheng.android.app.rise.R;
import yicheng.android.app.rise.activity.NavigationDrawerActivity;
import yicheng.android.app.rise.activity.NewEventActivity;
import yicheng.android.app.rise.database.RiseEvent;
import yicheng.android.app.rise.fragment.EventsFragment;
import yicheng.android.app.rise.fragment.PlacesFragment;
import yicheng.android.app.rise.receiver.EventAlarmReceiver;
import yicheng.android.app.rise.ui.utility.SwipeDimissTouchListener;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class EventsFragmentGridViewAdapter extends BaseAdapter {
	private Context context;
	private Activity activity;
	private List<RiseEvent> eventsList;

	RiseEvent deletedEvent;
	boolean isBigScreen;

	public EventsFragmentGridViewAdapter(Context context, Activity activity,
			List<RiseEvent> eventsList, boolean isBigScreen) {
		this.context = context;
		this.activity = activity;
		this.eventsList = eventsList;
		this.isBigScreen = isBigScreen;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (eventsList != null) {
			return eventsList.size();
		}
		else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (this.eventsList != null) {
			return this.eventsList.get(position);
		}
		else {
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	boolean isDeleted = true;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		final int curPosition = position;

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View gridView;

		if (convertView == null) {

			gridView = new View(context);

			// get layout from mobile.xml
			gridView = inflater.inflate(R.layout.fragment_events_grid_item,
					null);

			TextView fragment_events_event_name_textView = (TextView) gridView
					.findViewById(R.id.fragment_events_event_name_textView);
			fragment_events_event_name_textView.setText(eventsList
					.get(position).getEventName());
			TextView fragment_events_event_content_textView = (TextView) gridView
					.findViewById(R.id.fragment_events_event_content_textView);
			fragment_events_event_content_textView.setText(eventsList.get(
					position).getEventContent());

		}
		else {
			gridView = (View) convertView;

			TextView fragment_events_event_name_textView = (TextView) gridView
					.findViewById(R.id.fragment_events_event_name_textView);
			fragment_events_event_name_textView.setText(eventsList
					.get(position).getEventName());
			TextView fragment_events_event_content_textView = (TextView) gridView
					.findViewById(R.id.fragment_events_event_content_textView);
			fragment_events_event_content_textView.setText(eventsList.get(
					position).getEventContent());
		}

		gridView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (NavigationDrawerActivity.activity_navigation_drawer_floatingActionMenu
						.isExpanded()) {
					NavigationDrawerActivity.activity_navigation_drawer_floatingActionMenu
							.collapse();
				}
				goToNewEventActivity(eventsList.get(curPosition));
			}
		});

		gridView.setOnTouchListener(new SwipeDimissTouchListener(gridView,
				null, new SwipeDimissTouchListener.DismissCallbacks() {

					@Override
					public boolean canDismiss(Object token) {
						// TODO Auto-generated method stub
						return true;
					}

					@Override
					public void onDismiss(View view, Object token) {
						// TODO Auto-generated method stub
						Toast.makeText(activity, "" + curPosition,
								Toast.LENGTH_SHORT).show();

						deletedEvent = eventsList.remove(curPosition);

						notifyDataSetChanged();

						NavigationDrawerActivity.activity_navigation_drawer_floatingActionMenu
								.animate()
								.translationY(-120)
								.setDuration(100)
								.setInterpolator(
										new AccelerateDecelerateInterpolator());

						SnackBar snackbar = new SnackBar(activity,
								"Event Deleted", "UNDO",
								new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										Toast.makeText(context, "Clicked",
												Toast.LENGTH_SHORT).show();

										Toast.makeText(context,
												"curPosition: " + curPosition,
												Toast.LENGTH_SHORT).show();

										eventsList.add(curPosition,
												deletedEvent);

										notifyDataSetChanged();

										isDeleted = false;
									}

								}, !isBigScreen);

						snackbar.setOnDismissListener(new OnDismissListener() {

							@Override
							public void onDismiss(DialogInterface dialog) {
								// TODO Auto-generated method stub

								NavigationDrawerActivity.activity_navigation_drawer_floatingActionMenu
										.animate()
										.translationY(0)
										.setDuration(100)
										.setInterpolator(
												new AccelerateDecelerateInterpolator());

								if (isDeleted) {
									cancelEventAlarm(Integer
											.valueOf(EventsFragment.eventSQLiteHelper
													.getEventPrimaryIDByName(deletedEvent
															.getEventName())));

									deleteEventFromDatabase();
								}

							}

						});

						snackbar.setIndeterminate(true);
						snackbar.show();
						snackbar.setColorButton(activity.getResources()
								.getColor(R.color.theme_primary));
						snackbar.setCanceledOnTouchOutside(true);

					}

				}));

		return gridView;

	}

	private void deleteEventFromDatabase() {
		EventsFragment.eventSQLiteHelper.deleteEventByName(this.deletedEvent
				.getEventName());
	}

	private void cancelEventAlarm(int eventID) {
		Intent myIntent = new Intent(activity, EventAlarmReceiver.class);

		PendingIntent pendingIntent = PendingIntent.getBroadcast(activity,
				eventID, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		AlarmManager alarmManager = (AlarmManager) activity
				.getSystemService(Context.ALARM_SERVICE);

		alarmManager.cancel(pendingIntent);

	}

	private void goToNewEventActivity(RiseEvent event) {

		Intent intent = new Intent(activity, NewEventActivity.class);
		intent.putExtra("event_name", event.getEventName());
		intent.putExtra("event_content", event.getEventContent());
		intent.putExtra("event_start_time", event.getEventStartTime());
		intent.putExtra("event_end_time", event.getEventEndTime());
		intent.putExtra("event_cycle_interval", event.getEventCycleInterval());
		intent.putExtra("event_location_list", event.getEventLocationList());
		intent.putExtra("event_priority", event.getEventPriority());
		intent.putExtra("event_is_notification_on", event.getIsNotificationOn());

		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		activity.startActivity(intent);

		// finish();
	}

}
