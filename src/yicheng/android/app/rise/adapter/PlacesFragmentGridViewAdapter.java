package yicheng.android.app.rise.adapter;

import java.util.List;

import com.gc.materialdesign.widgets.SnackBar;

import yicheng.android.app.rise.R;
import yicheng.android.app.rise.activity.NavigationDrawerActvity;
import yicheng.android.app.rise.database.RisePlace;
import yicheng.android.app.rise.fragment.PlacesFragment;
import yicheng.android.app.rise.ui.utility.SwipeDimissTouchListener;
import yicheng.android.ui.floatingactionbutton.FloatingActionsMenu;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PlacesFragmentGridViewAdapter extends BaseAdapter {
	private Context context;
	List<RisePlace> placesList;

	private Activity activity;

	RisePlace deletedPlace;

	public PlacesFragmentGridViewAdapter(Context context, Activity activity,
			List<RisePlace> placesList) {
		this.context = context;
		this.activity = activity;
		this.placesList = placesList;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (placesList != null) {
			return placesList.size();
		}
		else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (this.placesList != null) {
			return this.placesList.get(position);
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
			gridView = inflater.inflate(R.layout.fragment_places_grid_item,
					null);

			TextView fragment_places_place_name_textView = (TextView) gridView
					.findViewById(R.id.fragment_places_place_name_textView);
			fragment_places_place_name_textView.setText(placesList
					.get(position).getPlaceName());
			TextView fragment_places_place_address_textView = (TextView) gridView
					.findViewById(R.id.fragment_places_place_address_textView);
			fragment_places_place_address_textView.setText(placesList.get(
					position).getPlaceAddress());
			TextView fragment_places_place_latlong_textView = (TextView) gridView
					.findViewById(R.id.fragment_places_place_latlong_textView);
			fragment_places_place_latlong_textView.setText(placesList.get(
					position).getPlaceLatitude()
					+ " " + placesList.get(position).getPlaceLongitude());

		}
		else {
			gridView = (View) convertView;

			TextView fragment_places_place_name_textView = (TextView) gridView
					.findViewById(R.id.fragment_places_place_name_textView);
			fragment_places_place_name_textView.setText(placesList
					.get(position).getPlaceName());
			TextView fragment_places_place_address_textView = (TextView) gridView
					.findViewById(R.id.fragment_places_place_address_textView);
			fragment_places_place_address_textView.setText(placesList.get(
					position).getPlaceAddress());
			TextView fragment_places_place_latlong_textView = (TextView) gridView
					.findViewById(R.id.fragment_places_place_latlong_textView);
			fragment_places_place_latlong_textView.setText(placesList.get(
					position).getPlaceLatitude()
					+ " " + placesList.get(position).getPlaceLongitude());

		}

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

						deletedPlace = placesList.remove(curPosition);

						notifyDataSetChanged();

						NavigationDrawerActvity.activity_navigation_drawer_floatingActionMenu
								.animate()
								.translationY(-120)
								.setDuration(100)
								.setInterpolator(
										new AccelerateDecelerateInterpolator());

						SnackBar snackbar = new SnackBar(activity,
								"Place Deleted", "UNDO",
								new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										Toast.makeText(context, "Clicked",
												Toast.LENGTH_SHORT).show();

										Toast.makeText(context,
												"curPosition: " + curPosition,
												Toast.LENGTH_SHORT).show();

										placesList.add(curPosition,
												deletedPlace);

										notifyDataSetChanged();

										isDeleted = false;
									}

								});

						snackbar.setOnDismissListener(new OnDismissListener() {

							@Override
							public void onDismiss(DialogInterface dialog) {
								// TODO Auto-generated method stub

								NavigationDrawerActvity.activity_navigation_drawer_floatingActionMenu
										.animate()
										.translationY(0)
										.setDuration(100)
										.setInterpolator(
												new AccelerateDecelerateInterpolator());

								if (isDeleted) {
									deletePlaceFromDatabase();
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

	private void deletePlaceFromDatabase() {
		PlacesFragment.placeSQLiteHelper.deletePlaceByName(deletedPlace
				.getPlaceName());
	}

	/*	private void updateGridView() {
			loadPlacesFromDatabase();
			PlacesFragmentGridViewAdapter gridViewAdapter = new PlacesFragmentGridViewAdapter(
					rootView.getContext(), getActivity(), placesList);

			fragment_places_gridView.setAdapter(gridViewAdapter);
		}*/

}
