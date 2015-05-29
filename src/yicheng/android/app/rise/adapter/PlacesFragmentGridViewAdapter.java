package yicheng.android.app.rise.adapter;

import java.util.List;

import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import yicheng.android.app.rise.R;
import yicheng.android.app.rise.database.RisePlace;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PlacesFragmentGridViewAdapter extends BaseSwipeAdapter {
	private Context context;
	List<RisePlace> placesList;

	public PlacesFragmentGridViewAdapter(Context context,
			List<RisePlace> placesList) {
		this.context = context;
		this.placesList = placesList;
	}

	@Override
	public int getSwipeLayoutResourceId(int position) {
		return R.id.fragment_places_swipelayout;
	}

	@Override
	public View generateView(int position, ViewGroup parent) {
		return LayoutInflater.from(context).inflate(
				R.layout.fragment_places_grid_item, null);
	}

	@Override
	public void fillValues(int position, View convertView) {
		TextView fragment_places_place_name_textView = (TextView) convertView
				.findViewById(R.id.fragment_places_place_name_textView);
		fragment_places_place_name_textView.setText(placesList.get(position)
				.getPlaceName());
		TextView fragment_places_place_address_textView = (TextView) convertView
				.findViewById(R.id.fragment_places_place_address_textView);
		fragment_places_place_address_textView.setText(placesList.get(position)
				.getPlaceAddress());
		TextView fragment_places_place_latlong_textView = (TextView) convertView
				.findViewById(R.id.fragment_places_place_latlong_textView);
		fragment_places_place_latlong_textView.setText(placesList.get(position)
				.getPlaceLatitude()
				+ " "
				+ placesList.get(position).getPlaceLongitude());
	}

	@Override
	public int getCount() {
		return placesList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
