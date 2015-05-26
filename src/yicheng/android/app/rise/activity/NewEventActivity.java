package yicheng.android.app.rise.activity;

import yicheng.android.app.rise.R;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class NewEventActivity extends ActionBarActivity {

	Toolbar activity_new_event_toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_new_event);

		initiateComponents();
		setComponentStyle();
		setComponentControl();
	}

	private void setComponentStyle() {
		if (Build.VERSION.SDK_INT >= 21) {
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(getResources().getColor(
					R.color.theme_primary_dark));
		}

	}

	private void initiateComponents() {

		activity_new_event_toolbar = (Toolbar) findViewById(R.id.activity_new_event_toolbar);

		activity_new_event_toolbar
				.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back);

		setSupportActionBar(activity_new_event_toolbar);
		getSupportActionBar().setTitle(R.string.toolbar_new_event_title);
	}

	private void setComponentControl() {
		setToolbarControl();
	}

	private void setToolbarControl() {
		activity_new_event_toolbar
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
		getMenuInflater().inflate(R.menu.menu_new_event, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		switch (item.getItemId()) {
		case R.id.menu_new_event_add: {
			addNewEvent();
		}
			break;
		case R.id.menu_new_event_settings: {

		}
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void addNewEvent() {
		finish();
	}

}
