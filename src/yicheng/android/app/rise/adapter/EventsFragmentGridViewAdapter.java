package yicheng.android.app.rise.adapter;

import yicheng.android.app.rise.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.swipe.adapters.BaseSwipeAdapter;

public class EventsFragmentGridViewAdapter extends BaseSwipeAdapter {
	private Context context;

	public EventsFragmentGridViewAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getSwipeLayoutResourceId(int position) {
		return R.id.fragment_events_swipelayout;
	}

	@Override
	public View generateView(int position, ViewGroup parent) {
		return LayoutInflater.from(context).inflate(
				R.layout.fragment_events_grid_item, null);
	}

	@Override
	public void fillValues(int position, View convertView) {
		TextView t = (TextView) convertView.findViewById(R.id.position);
		if (position == 0) {
			t.setText("hah");
		}
		else if (position == 1) {
			t.setText("hahg\n\n\n\nadfas\n\n\n");
		}
	}

	@Override
	public int getCount() {
		return 5;
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
