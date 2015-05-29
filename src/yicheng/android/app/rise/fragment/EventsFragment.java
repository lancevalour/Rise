package yicheng.android.app.rise.fragment;

import com.daimajia.swipe.util.Attributes;

import yicheng.android.app.rise.R;
import yicheng.android.app.rise.adapter.EventsFragmentGridViewAdapter;
import yicheng.android.app.rise.adapter.PlacesFragmentGridViewAdapter;
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

public class EventsFragment extends Fragment {

	ViewGroup rootView;

	StaggeredGridView fragment_events_gridView;

	public EventsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = (ViewGroup) inflater.inflate(R.layout.fragment_events,
				container, false);

		initiateComponents();
		setComponentControl();

		return rootView;

	}

	private void initiateComponents() {
		fragment_events_gridView = (StaggeredGridView) rootView
				.findViewById(R.id.fragment_events_gridView);

		EventsFragmentGridViewAdapter gridViewAdapter = new EventsFragmentGridViewAdapter(
				rootView.getContext());
		gridViewAdapter.setMode(Attributes.Mode.Multiple);
		fragment_events_gridView.setAdapter(gridViewAdapter);
		fragment_events_gridView.setSelected(false);
	}

	private void setComponentControl() {
		setGridViewControl();
	}

	private void setGridViewControl() {
		fragment_events_gridView
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {

						return false;
					}
				});
		fragment_events_gridView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

					}
				});

		fragment_events_gridView
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {

					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});
	}
}