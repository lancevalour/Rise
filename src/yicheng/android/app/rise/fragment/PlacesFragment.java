package yicheng.android.app.rise.fragment;

import java.util.ArrayList;
import java.util.List;

import yicheng.android.app.rise.R;
import yicheng.android.app.rise.adapter.PlacesFragmentGridViewAdapter;
import yicheng.android.app.rise.database.RisePlace;
import yicheng.android.app.rise.database.SQLiteHelper;
import yicheng.android.app.rise.ui.utility.SwipeDimissTouchListener;
import yicheng.android.ui.staggeredgridview.StaggeredGridView;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PlacesFragment extends Fragment {

	ViewGroup rootView;

	public static StaggeredGridView fragment_places_gridView;

	List<RisePlace> placesList;

	public static PlacesFragmentGridViewAdapter gridViewAdapter;

	public static SQLiteHelper placeSQLiteHelper;

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
		System.out.println(placesList.size());
	}

	private void initiateComponents() {
		fragment_places_gridView = (StaggeredGridView) rootView
				.findViewById(R.id.fragment_places_gridView);

		gridViewAdapter = new PlacesFragmentGridViewAdapter(
				rootView.getContext(), getActivity(), placesList);

		fragment_places_gridView.setAdapter(gridViewAdapter);

	}

	private void setComponentControl() {
		setGridViewControl();
	}

	private void setGridViewControl() {

	}

	private void updateGridView() {
		loadPlacesFromDatabase();

		// gridViewAdapter.notifyDataSetChanged();
		gridViewAdapter = new PlacesFragmentGridViewAdapter(
				rootView.getContext(), getActivity(), placesList);

		fragment_places_gridView.setAdapter(gridViewAdapter);
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