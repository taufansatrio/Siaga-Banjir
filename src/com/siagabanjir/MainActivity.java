package com.siagabanjir;

import java.util.ArrayList;

import com.flurry.android.FlurryAgent;
import com.siagabanjir.DataPintuAir;
import com.siagabanjir.adapter.TabsPagerAdapter;
import com.siagabanjir.follow.GcmBroadcastReceiver;
import com.siagabanjir.AboutActivity;

import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements
		ActionBar.TabListener {

	private Fragment fragment;

	private MenuItem refreshItem;
	private HomeFragment homeFragment;
	private MyPlaceFragment myPlaceFragment;
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	static SharedPreferences sharedPreferences;

	private ArrayList<DataPintuAir> dataKritis;

	// Tab titles
	private String[] tabs = { "Home", "My Place" };
	
	@Override
	protected void onStart()
	{
		super.onStart();
		FlurryAgent.onStartSession(this, "CZWJXGNWJVHM35JYDTRC");
	}
	
	@Override
	protected void onStop()
	{
		super.onStop();
		FlurryAgent.onEndSession(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		sharedPreferences = getSharedPreferences("firstRunPreference", 0);

		// Checking if the boolean value of "isFirstRun" is true

		if (FirstRun.isFirstRun() == true) {

			// calling this method changes the boolean value to false.
			// on new launch of the activity this if block is not interpreted.
			FirstRun.appRunned();
			Intent intent = new Intent(this, WalkthroughActivity.class);
			this.startActivity(intent);
		}

		/*
		 * if (savedInstanceState == null) {
		 * getSupportFragmentManager().beginTransaction() .add(R.id.container,
		 * new PlaceholderFragment()).commit(); }
		 * 
		 * 
		 * sharedPreferences = getSharedPreferences("firstRunPreference", 0);
		 * 
		 * 
		 * // Checking if the boolean value of "isFirstRun" is true
		 * 
		 * if (FirstRun.isFirstRun() == true) {
		 * 
		 * // calling this method changes the boolean value to false. // on new
		 * launch of the activity this if block is not interpreted.
		 * FirstRun.appRunned(); Intent intent = new Intent(this,
		 * WalkthroughActivity.class); this.startActivity(intent); } else {
		 */

		ActionBar actionBar = getSupportActionBar();
		actionBar.setIcon(R.drawable.ico_actionbarcopy);
		actionBar.setDisplayShowTitleEnabled(false);

		// setUp data
		dataKritis = new ArrayList<DataPintuAir>();

		// set up data
		/*
		 * for(int i = 0; i < 5; i++) { DataPintuAir dp = new
		 * DataPintuAir("Pintu air " + i); dp.setTanggal("2014/05/01");
		 * dp.addTinggiAir(528, "KRITIS", 7*i);
		 * 
		 * dataKritis.add(dp); }
		 */

		// Initilization
		/*
		 * viewPager = (ViewPager) findViewById(R.id.container); mAdapter = new
		 * TabsPagerAdapter(getSupportFragmentManager(), dataKritis, this);
		 * 
		 * viewPager.setAdapter(mAdapter);
		 */
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}
		

		// }

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_refresh:
			FlurryAgent.logEvent("Refresh");
			refreshItem = item;

			if (fragment instanceof HomeFragment)
				((HomeFragment) fragment).refreshHome();

			return true;
		case R.id.action_view_as_list:
			Intent m = new Intent(this, ListFragmentActivity.class);
			startActivity(m);
			return true; 
		case R.id.action_about:
			Intent ii = new Intent(this, AboutActivity.class);
			startActivity(ii);
			return true;
		case R.id.action_information:
			Intent i = new Intent(this, InformationActivity.class);
			startActivity(i);
			return true;
		case R.id.action_tutorial:
			Intent iii = new Intent(this, WalkthroughActivity.class);
			startActivity(iii);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		String tag = "CURRENT_FRAGMENT";
		switch (tab.getPosition()) {
		case 0:
			fragment = new HomeFragment(dataKritis, this);
			break;
		case 1:
			fragment = new MyPlaceFragment(this);
			break;
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, fragment, tag).commit();
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	public void setRefreshActionButtonState(boolean refreshing) {
		if (refreshItem != null) {
			if (refreshing) {
				MenuItemCompat.setActionView(refreshItem,
						R.layout.action_progressbar);
			} else {
				MenuItemCompat.setActionView(refreshItem, null);
			}
		}
	}
}
