package yicheng.android.app.rise.fragment;

import java.util.List;

import yicheng.android.app.rise.R;
import yicheng.android.app.rise.adapter.EventsFragmentGridViewAdapter;
import yicheng.android.app.rise.adapter.PlacesFragmentGridViewAdapter;
import yicheng.android.app.rise.database.RiseEvent;
import yicheng.android.app.rise.database.SQLiteHelper;
import yicheng.android.ui.staggeredgridview.StaggeredGridView;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EventsFragment extends Fragment {

	ViewGroup rootView;

	StaggeredGridView fragment_events_gridView;

	List<RiseEvent> eventList;

	EventsFragmentGridViewAdapter gridViewAdapter;

	public static SQLiteHelper eventSQLiteHelper;

	public EventsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = (ViewGroup) inflater.inflate(R.layout.fragment_events,
				container, false);

		loadEventsFromDatabase();
		initiateComponents();
		setComponentControl();

		return rootView;

	}

	private void loadEventsFromDatabase() {
		eventSQLiteHelper = new SQLiteHelper(rootView.getContext(),
				SQLiteHelper.TABLE_EVENT);
		eventList = eventSQLiteHelper.getAllEvents();
		System.out.println(eventList.size());

	}

	private void initiateComponents() {
		fragment_events_gridView = (StaggeredGridView) rootView
				.findViewById(R.id.fragment_events_gridView);

		gridViewAdapter = new EventsFragmentGridViewAdapter(
				rootView.getContext(), getActivity(), this.eventList);

		fragment_events_gridView.setAdapter(gridViewAdapter);

	}

	private void setComponentControl() {
		setGridViewControl();
	}

	private void setGridViewControl() {

	}

	private void updateGridView() {
		loadEventsFromDatabase();
		gridViewAdapter = new EventsFragmentGridViewAdapter(
				rootView.getContext(), getActivity(), this.eventList);

		fragment_events_gridView.setAdapter(gridViewAdapter);

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		updateGridView();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}