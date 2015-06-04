package yicheng.android.app.rise.activity;

import java.util.List;

import yicheng.android.app.rise.R;
import yicheng.android.app.rise.adapter.NewEventAddPlaceGridViewAdapter;
import yicheng.android.app.rise.adapter.PlacesFragmentGridViewAdapter;
import yicheng.android.app.rise.database.RisePlace;
import yicheng.android.app.rise.database.SQLiteHelper;
import yicheng.android.ui.staggeredgridview.StaggeredGridView;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class NewEventAddPlaceActivity extends ActionBarActivity {

	Toolbar activity_new_event_add_place_toolbar;

	StaggeredGridView activity_new_event_add_place_gridView;

	NewEventAddPlaceGridViewAdapter gridViewAdapter;

	List<RisePlace> placesList;

	SQLiteHelper placeSQLiteHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_event_add_place);

		// setGoogleApiClient();
		loadPlacesFromDatabase();
		initiateComponents();
		setComponentStyle();

		setComponentControl();
	}

	private void loadPlacesFromDatabase() {
		placeSQLiteHelper = new SQLiteHelper(getBaseContext(),
				SQLiteHelper.TABLE_PLACE);
		placesList = placeSQLiteHelper.getAllPlaces();

	}

	private void initiateComponents() {
		activity_new_event_add_place_toolbar = (Toolbar) findViewById(R.id.activity_new_event_add_place_toolbar);

		activity_new_event_add_place_toolbar
				.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back);

		setSupportActionBar(activity_new_event_add_place_toolbar);
		getSupportActionBar().setTitle(R.string.toolbar_add_place_title);

		activity_new_event_add_place_gridView = (StaggeredGridView) findViewById(R.id.activity_new_event_add_place_gridView);

		gridViewAdapter = new NewEventAddPlaceGridViewAdapter(getBaseContext(),
				this, placesList);

		activity_new_event_add_place_gridView.setAdapter(gridViewAdapter);

	}

	private void setComponentStyle() {

	}

	private void setComponentControl() {
		setToolbarControl();
	}

	private void setToolbarControl() {
		activity_new_event_add_place_toolbar
				.setNavigationOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_new_place, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_new_place_add: {
			// addNewPlace();
		}
			break;
		case R.id.menu_new_place_settings: {

		}
			break;
		}

		return super.onOptionsItemSelected(item);
	}

}
