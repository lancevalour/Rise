package yicheng.android.app.rise.adapter;

import java.util.List;

import yicheng.android.app.rise.R;
import yicheng.android.app.rise.database.RisePlace;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AddPlaceActivityGridViewAdapter extends BaseAdapter {

	Context context;
	Activity activity;
	List<RisePlace> placesList;

	public AddPlaceActivityGridViewAdapter(Context context, Activity activity,
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View gridView;

		if (convertView == null) {

			gridView = new View(context);

			// get layout from mobile.xml
			gridView = inflater.inflate(R.layout.activity_add_place_grid_item,
					null);

			TextView activity_add_place_place_name_textView = (TextView) gridView
					.findViewById(R.id.activity_add_place_place_name_textView);
			activity_add_place_place_name_textView.setText(placesList.get(
					position).getPlaceName());
			TextView activity_add_place_place_address_textView = (TextView) gridView
					.findViewById(R.id.activity_add_place_place_address_textView);
			activity_add_place_place_address_textView.setText(placesList.get(
					position).getPlaceAddress());
			TextView activity_add_place_place_latlong_textView = (TextView) gridView
					.findViewById(R.id.activity_add_place_place_latlong_textView);
			activity_add_place_place_latlong_textView.setText(placesList.get(
					position).getPlaceLatitude()
					+ " " + placesList.get(position).getPlaceLongitude());
		}
		else {
			gridView = (View) convertView;
			TextView activity_add_place_place_name_textView = (TextView) gridView
					.findViewById(R.id.activity_add_place_place_name_textView);
			activity_add_place_place_name_textView.setText(placesList.get(
					position).getPlaceName());
			TextView activity_add_place_place_address_textView = (TextView) gridView
					.findViewById(R.id.activity_add_place_place_address_textView);
			activity_add_place_place_address_textView.setText(placesList.get(
					position).getPlaceAddress());
			TextView activity_add_place_place_latlong_textView = (TextView) gridView
					.findViewById(R.id.activity_add_place_place_latlong_textView);
			activity_add_place_place_latlong_textView.setText(placesList.get(
					position).getPlaceLatitude()
					+ " " + placesList.get(position).getPlaceLongitude());

		}

		/*	final RelativeLayout fragment_places_layout = (RelativeLayout) gridView
					.findViewById(R.id.fragment_places_layout);

			gridView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					if (fragment_places_layout.getBackground() == activity
							.getResources().getDrawable(
									R.drawable.green_card_background)) {
						fragment_places_layout
								.setBackgroundResource(R.drawable.card_background);

					}
					else {
						fragment_places_layout
								.setBackgroundResource(R.drawable.green_card_background);

					}
				}

			});*/
		return gridView;
	}

}
