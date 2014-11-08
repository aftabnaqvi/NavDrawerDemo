package com.example.syed.navdrawerdemo;

/*
** FragmentNavigationDrawer object for use with support-v4 library using compatibility fragments
*/
 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.syed.navdrawerdemo.adapters.ExpandableListAdapter;
import com.example.syed.navdrawerdemo.models.NavDrawerItem;
 
public class FragmentNavigationDrawer extends DrawerLayout {
	private ActionBarDrawerToggle mDrawerToggle;
	//private ListView mLvDrawer;
	//private NavDrawerListAdapter mDrawerAdapter;
	
	private ExpandableListView mLvDrawer;
	private ExpandableListAdapter mDrawerAdapter;
	
	Map<String, List<String>> mMapChildData;
	List<String> mDataHeaderList;
	
	
	private ArrayList<NavDrawerItem> mNavDrawerItems; // model
	private ArrayList<FragmentNavItem> mDrawerNavItems; //array of Fragments
	
	private int mDrawerContainerRes;
 
	public FragmentNavigationDrawer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
 
	public FragmentNavigationDrawer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
 
	public FragmentNavigationDrawer(Context context) {
		super(context);
	}
    
	// setupDrawerConfiguration((ListView) findViewById(R.id.lvDrawer), R.layout.drawer_list_item, R.id.flContent);
	public void setupDrawerConfiguration(ExpandableListView drawerListView, 
											int drawerItemRes, 
											int drawerContainerRes,
											ArrayList<String> headersData,
											Map<String, List<String>> mapChildData) {
		
		// Setup navigation items array
		mDrawerNavItems = new ArrayList<FragmentNavigationDrawer.FragmentNavItem>();
		mNavDrawerItems = new ArrayList<NavDrawerItem>();
		this.mDrawerContainerRes = drawerContainerRes; 
		
		// Setup drawer list view
		mLvDrawer = drawerListView; 
		// Setup item listener
		mLvDrawer.setOnItemClickListener(new FragmentDrawerItemListener());
		
		//listener for child row click
		mLvDrawer.setOnChildClickListener(listChildItemClicked);
		//listener for group heading click
		mLvDrawer.setOnGroupClickListener(listGroupClicked);
		        
		// ActionBarDrawerToggle ties together the the proper interactions
 		// between the sliding drawer and the action bar app icon
 		mDrawerToggle = setupDrawerToggle();
 		setDrawerListener(mDrawerToggle);
 		
 		mDataHeaderList = headersData;
 		mMapChildData = mapChildData;
		
 		// Set the adapter for the list view
 		mDrawerAdapter = new ExpandableListAdapter(getActivity(), mDataHeaderList, mMapChildData); 	
 		mLvDrawer.setAdapter(mDrawerAdapter);
 		
 		// set a custom shadow that overlays the main content when the drawer
 		setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
 		// Setup action buttons
	 	getActionBar().setDisplayHomeAsUpEnabled(true);
	 	getActionBar().setHomeButtonEnabled(true);
	}
 
	//our child listener
	private OnChildClickListener listChildItemClicked =  new OnChildClickListener() {
		public boolean onChildClick(ExpandableListView parent, View v,
										int groupPosition, int childPosition, long id) {
		  	//get the group header
			String  headerName = mDataHeaderList.get(groupPosition);
	   
			//get the child info
			List<String> childInfo =  mMapChildData.get(headerName);
				  
			String childeData = childInfo.get(childPosition);
			//display it or do something with it
			Toast.makeText(getContext(), "Clicked on Detail " + headerName 
					+ "/" + childeData, Toast.LENGTH_LONG).show();
			return false;
		}
	};
	  
	 //our group listener
	 private OnGroupClickListener listGroupClicked =  new OnGroupClickListener() {
	 
		 public boolean onGroupClick(ExpandableListView parent, View v,
				 						int groupPosition, long id) {
	    	 //get the group header
			 String  headerName = mDataHeaderList.get(groupPosition);
			 //display it or do something with it
			 Toast.makeText(getContext(), "Child on Header " + headerName, 
					 Toast.LENGTH_LONG).show();
	     
			 return false;
		 }
	 };
	 
	// addNavItem("First", R.drawable.ic_one, "First Fragment", FirstFragment.class);
	public void addNavItem(String navTitle, int icon, String windowTitle, Class<? extends Fragment> fragmentClass) {
		// adding nav drawer items to array
 		mNavDrawerItems.add(new NavDrawerItem(navTitle, icon)); // create a model and set in the array.
 		mDrawerNavItems.add(new FragmentNavItem(windowTitle, fragmentClass));
	}
	
	/** Swaps fragments in the main content view */
	public void selectDrawerItem(int position) {
		// Create a new fragment and specify the planet to show based on
		// position
		FragmentNavItem navItem = mDrawerNavItems.get(position); 
		Fragment fragment = null;
		try {
			fragment = navItem.getFragmentClass().newInstance();
			Bundle args = navItem.getFragmentArgs();
			if (args != null) { 
			  fragment.setArguments(args);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		// Insert the fragment by replacing any existing fragment
		FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(mDrawerContainerRes, fragment).commit();
 
		// Highlight the selected item, update the title, and close the drawer
		mLvDrawer.setItemChecked(position, true);
		setTitle(navItem.getTitle());
		closeDrawer(mLvDrawer);
	}
	
 
	public ActionBarDrawerToggle getDrawerToggle() {
		return mDrawerToggle;
	}
 
	
	private FragmentActivity getActivity() {
		return (FragmentActivity) getContext();
	}
 
	private ActionBar getActionBar() {
		return getActivity().getActionBar();
	}
 
	private void setTitle(CharSequence title) {
		getActionBar().setTitle(title);
	}
	
	private class FragmentDrawerItemListener implements ListView.OnItemClickListener {		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectDrawerItem(position);
		}	
	}
	
	private ActionBarDrawerToggle setupDrawerToggle() {
		return new ActionBarDrawerToggle(getActivity(), /* host Activity */
		this, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				// setTitle(getCurrentTitle());
				//getActivity().invalidateOptionsMenu(); // call onPrepareOptionsMenu()
			}
 
			public void onDrawerOpened(View drawerView) {
				// setTitle("Navigate");
				//getActivity().invalidateOptionsMenu(); // call onPrepareOptionsMenu()
			}
		};
	}
 
	public boolean isDrawerOpen() {
		return isDrawerOpen(mLvDrawer);
	}
	
	//------------------------ inner class for FragmentNavItem ------------------
	private class FragmentNavItem {
		private Class<? extends Fragment> fragmentClass;
		private String title;
		private Bundle fragmentArgs;
		
		public FragmentNavItem(String title, Class<? extends Fragment> fragmentClass) {
			this(title, fragmentClass, null);
		}
		
		public FragmentNavItem(String title, Class<? extends Fragment> fragmentClass, Bundle args) {
			this.fragmentClass = fragmentClass;
			this.fragmentArgs = args;
			this.title = title;
		}
		
		public Class<? extends Fragment> getFragmentClass() {
			return fragmentClass;
		}
		
		public String getTitle() {
			return title;
		}
		
		public Bundle getFragmentArgs() {
			return fragmentArgs;
		}
	}
}