package com.siagabanjir;

import com.flurry.android.FlurryAgent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class AboutActivity extends ActionBarActivity {

	private ActionBar actionBar;
	private Fragment fragment;
	
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
		setContentView(R.layout.about);

		actionBar = getSupportActionBar();
		actionBar.setIcon(R.drawable.ico_actionbarcopy);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		Intent i = getIntent();
		FlurryAgent.logEvent("View_About");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.overflow, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.action_about:
			Intent ii = new Intent(this, AboutActivity.class);
			startActivity(ii);
		case R.id.action_information:
			Intent i = new Intent(this, InformationActivity.class);
			startActivity(i);
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
}
