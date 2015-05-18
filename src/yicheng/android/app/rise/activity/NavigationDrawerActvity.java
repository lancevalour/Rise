package yicheng.android.app.rise.activity;

import yicheng.android.app.rise.R;
import yicheng.android.app.rise.adapter.NavigationDrawerRecyclerViewAdapter;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class NavigationDrawerActvity extends ActionBarActivity {
	String TITLES[] = { "Home", "Events", "Mail", "Shop", "Travel" };
	int ICONS[] = { R.drawable.ic_action_navigation_arrow_back,
			R.drawable.ic_action_navigation_arrow_back,
			R.drawable.ic_action_navigation_arrow_back,
			R.drawable.ic_action_navigation_arrow_back,
			R.drawable.ic_action_navigation_arrow_back };

	String NAME = "Yicheng Zhang";
	String EMAIL = "yicheng@gmail.com";
	int PROFILE = R.drawable.ic_launcher;

	Toolbar activity_navigation_drawer_toolbar;

	RecyclerView activity_navigation_drawer_recyclerView;
	RecyclerView.Adapter activity_navigation_drawer_recyclerView_adapter;
	RecyclerView.LayoutManager activity_navigation_drawer_recyclerView_layoutManager;

	DrawerLayout activity_navigation_drawer_layout;

	ActionBarDrawerToggle actionBarDrawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/*		Window window = getWindow();
				window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
				window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
				window.setStatusBarColor(getResources().getColor(
						R.color.theme_primary_dark));*/

		setContentView(R.layout.activity_navigation_drawer);

		activity_navigation_drawer_toolbar = (Toolbar) findViewById(R.id.activity_navigation_drawer_toolbar);

		setSupportActionBar(activity_navigation_drawer_toolbar);

		activity_navigation_drawer_recyclerView = (RecyclerView) findViewById(R.id.activity_navigation_drawer_recyclerView); // Assigning

		activity_navigation_drawer_recyclerView.setHasFixedSize(true);

		activity_navigation_drawer_recyclerView_adapter = new NavigationDrawerRecyclerViewAdapter(
				TITLES, ICONS, NAME, EMAIL, PROFILE);

		activity_navigation_drawer_recyclerView
				.setAdapter(activity_navigation_drawer_recyclerView_adapter);

		activity_navigation_drawer_recyclerView_layoutManager = new LinearLayoutManager(
				this);

		activity_navigation_drawer_recyclerView
				.setLayoutManager(activity_navigation_drawer_recyclerView_layoutManager);

		activity_navigation_drawer_layout = (DrawerLayout) findViewById(R.id.activity_navigation_drawer_layout);

		actionBarDrawerToggle = new ActionBarDrawerToggle(this,
				activity_navigation_drawer_layout,
				activity_navigation_drawer_toolbar,
				R.drawable.ic_action_navigation_menu,
				R.drawable.ic_action_navigation_arrow_back) {

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				// code here will execute once the drawer is opened( As I dont
				// want anything happened whe drawer is
				// open I am not going to put anything here)
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				// Code here will execute once drawer is closed
			}

		};
		activity_navigation_drawer_layout
				.setDrawerListener(actionBarDrawerToggle);

		actionBarDrawerToggle.syncState();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		// noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
