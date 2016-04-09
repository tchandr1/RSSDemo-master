package com.iitevents.rssmain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.iitevents.util.LoadRSSFeed;
import com.iitevents.util.Sports;
import com.iitevents.util.SqlHelper;
import com.iitevents.rssmain.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class HomeActivity extends FragmentActivity implements OnClickListener,
		OnItemSelectedListener {

	Set<String> set;
	SqlHelper db;
	boolean blnFlag = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		db = new SqlHelper(this);
		/** CRUD Operations **/
		
		/*	Delete games 
		 * List<Sports> list1 = db.getAllSports();
		for(int i = 0; i <list1.size(); i++)
			db.deleteSport(list1.get(i));*/
		// checking if data exists before adding data to SQLite
		//comment only if statement and run let inside part be there..if you get nothing in spinner for first time..
		if (isDataExists()) {

			db.addSports(new Sports("News",
					"http://www.iit.edu/news/iittoday/?cat=3&feed=rss2"));
			db.addSports(new Sports("Students",
					"http://www.iit.edu/news/iittoday/?cat=45&feed=rss2"));
			db.addSports(new Sports("Faculty and Staff",
					"http://www.iit.edu/news/iittoday/?cat=6&feed=rss2"));
			db.addSports(new Sports("IIT in the News",
					"http://www.iit.edu/news/iittoday/?cat=7&feed=rss2"));
			db.addSports(new Sports("Athletics",
					"http://www.iit.edu/news/iittoday/?cat=43&feed=rss2"));
		}
		
		
		db.getAllSports();


		// Spinner element
		Spinner spinner;
		// Spinner element
		spinner = (Spinner) findViewById(R.id.spinner1);

		set = db.getName();
		// Convert set into list
		List<String> blist = new ArrayList<String>(set);
		// Sort Data Alphabetical order
		Collections.sort(blist, new Comparator<String>() {
			@Override
			public int compare(String lhs, String rhs) {
				return lhs.compareTo(rhs);
			}
		});
		blist.add(0, "Select Feed...");

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				HomeActivity.this, android.R.layout.simple_spinner_item, blist);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setWillNotDraw(false);
		spinner.setOnItemSelectedListener(this);

		/* MAP BUTTON */

        Button mapButton = (Button) findViewById(R.id.buttonMap);
        mapButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View m){
                    Log.d("MapActivity", "Loading Map..");
                    // Loading Google Map View
           //         startActivity(new Intent(HomeActivity.this, MapActivity.class));
                }       
        });
        /* Events BUTTON */

        Button scoreboard = (Button) findViewById(R.id.buttonRecent);
        scoreboard.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View m){
                    // Loading web view
             //       startActivity(new Intent(HomeActivity.this, WebViewActivity.class));
                //	startActivity(new Intent(HomeActivity.this, ListActivity.class));
                }       
        });
		
     
	}
	
	
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		if (blnFlag) //checking for flag before changing activity from displaying initially
		{
			if (arg2 > 0) {
				String name = arg0.getItemAtPosition(arg2).toString();
				
				String RSSFEEDURL = db.getUrl(name);
				
				Bundle bundle = new Bundle();
				bundle.putSerializable("RSSFEEDURL", RSSFEEDURL);

				// launch List activity
			//	Intent intent = new Intent(HomeActivity.this, MainActivity.class);
			//	intent.putExtras(bundle);
				//startActivity(intent);
			//	setContentView(R.layout.feed_list);
                new LoadRSSFeed(HomeActivity.this, RSSFEEDURL).execute();
				
			}
		}
		blnFlag = true;
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
	
	// shared preference check to see if data already exists in the SQLite db or not
	private boolean isDataExists() {
		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		boolean ranBefore = preferences.getBoolean("RanBefore", false);
		if (!ranBefore) {
			// first time
			SharedPreferences.Editor editor = preferences.edit();
			editor.putBoolean("RanBefore", true);
			editor.commit();
		}
		return !ranBefore;
	}
	
}
