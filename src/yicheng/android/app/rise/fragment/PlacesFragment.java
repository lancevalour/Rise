package yicheng.android.app.rise.fragment;

import java.util.ArrayList;
import java.util.List;

import yicheng.android.app.rise.R;
import yicheng.android.app.rise.adapter.PlacesFragmentGridViewAdapter;
import yicheng.android.app.rise.database.RisePlace;
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

import com.daimajia.swipe.util.Attributes;

public class PlacesFragment extends Fragment {

	ViewGroup rootView;

	StaggeredGridView fragment_places_gridView;

	List<RisePlace> placesList;

	SQLiteHelper placeSQLiteHelper;

	public PlacesFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = (ViewGroup) inflater.inflate(R.layout.fragment_places,
				container, false);

		loadPlacesFromDatabase();
		initiateComponents();
		setComponentControl();

		return rootView;

	}

	private void loadPlacesFromDatabase() {
		placeSQLiteHelper = new SQLiteHelper(rootView.getContext(),
				SQLiteHelper.TABLE_PLACE);
		placesList = placeSQLiteHelper.getAllPlaces();
	}

	private void initiateComponents() {
		fragment_places_gridView = (StaggeredGridView) rootView
				.findViewById(R.id.fragment_places_gridView);

		PlacesFragmentGridViewAdapter gridViewAdapter = new PlacesFragmentGridViewAdapter(
				rootView.getContext(), placesList);
		gridViewAdapter.setMode(Attributes.Mode.Multiple);
		fragment_places_gridView.setAdapter(gridViewAdapter);
		fragment_places_gridView.setSelected(false);

	}

	private void setComponentControl() {
		setGridViewControl();
	}

	private void setGridViewControl() {
		fragment_places_gridView
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {

						return false;
					}
				});
		fragment_places_gridView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

					}
				});

		fragment_places_gridView
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

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		loadPlacesFromDatabase();
		PlacesFragmentGridViewAdapter gridViewAdapter = new PlacesFragmentGridViewAdapter(
				rootView.getContext(), placesList);
		gridViewAdapter.setMode(Attributes.Mode.Multiple);
		fragment_places_gridView.setAdapter(gridViewAdapter);
		fragment_places_gridView.setSelected(false);
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}