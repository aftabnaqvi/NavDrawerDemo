package com.example.syed.navdrawerdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ExpandableListView;

public class DrawerActivity extends FragmentActivity {
	private FragmentNavigationDrawer dlDrawer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawer);
		// Find our drawer view
		dlDrawer = (FragmentNavigationDrawer) findViewById(R.id.drawer_layout);
		
		// populate the data....
		ArrayList<String> headers = new ArrayList<String>();
		Map<String, List<String>> mMapChildData = new HashMap<String, List<String> >();
		
		headers.add("Home");
		headers.add("Share");
		headers.add("Backup");
		headers.add("Timeline");
		headers.add("Sync");
		
		// Adding child data
		List<String> share = new ArrayList<String>();
		share.add("Share File");
		share.add("Share Folder");
		share.add("Share Link");
		mMapChildData.put("Share", share);
		
		// Adding child data
		List<String> backup = new ArrayList<String>();
		backup.add("Backup File");
		backup.add("Backup Folder");
		backup.add("backup Link");
		mMapChildData.put("Backup", backup);
				
		// Adding child data
		List<String> timeLine = new ArrayList<String>();
		timeLine.add("Share File");
		timeLine.add("Share Folder");
		timeLine.add("Share Link");
		mMapChildData.put("Timeline", timeLine);
		
		// Adding child data
		List<String> sync = new ArrayList<String>();
		sync.add("Sync File");
		sync.add("Sync Folder");
		sync.add("Sync Link");
		mMapChildData.put("Sync", sync);
		
		// Setup drawer view
		dlDrawer.setupDrawerConfiguration((ExpandableListView) findViewById(R.id.expandLvDrawer), 
                     R.layout.drawer_nav_item, 
                     R.id.flContent,
                     headers,
                     mMapChildData);
				
				
		// Add nav items
//		dlDrawer.addNavItem("First", R.drawable.ic_one, "First Fragment", FirstFragment.class);
//		dlDrawer.addNavItem("Second", R.drawable.ic_two, "Second Fragment", SecondFragment.class);
//		dlDrawer.addNavItem("Third", R.drawable.ic_three, "Third Fragment", ThirdFragment.class);
		
		// Select default
//		if (savedInstanceState == null) {
//			dlDrawer.selectDrawerItem(0);	
//		}
	}


	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		if (dlDrawer.isDrawerOpen()) {
			// Uncomment to hide menu items                        
			// menu.findItem(R.id.mi_test).setVisible(false);
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		// Uncomment to inflate menu items to Action Bar      
		// inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (dlDrawer.getDrawerToggle().onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		dlDrawer.getDrawerToggle().syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		dlDrawer.getDrawerToggle().onConfigurationChanged(newConfig);
	}

}