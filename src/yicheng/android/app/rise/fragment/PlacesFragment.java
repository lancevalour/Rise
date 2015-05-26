package yicheng.android.app.rise.fragment;

import yicheng.android.app.rise.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PlacesFragment extends Fragment {
	ImageView starting_slide_starting_fragment_imageView;

	ImageView starting_slide_starting_fragment_center_imageView;

	TextView starting_slide_starting_fragment_textView;

	ViewGroup rootView;

	String message;

	public PlacesFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = (ViewGroup) inflater.inflate(R.layout.fragment_content,
				container, false);

		initiateComponents();

		return rootView;

	}

	private void initiateComponents() {

		starting_slide_starting_fragment_textView = (TextView) rootView
				.findViewById(R.id.fragment_content_textView);

		starting_slide_starting_fragment_textView.setText("Places Fragment");
	}

}