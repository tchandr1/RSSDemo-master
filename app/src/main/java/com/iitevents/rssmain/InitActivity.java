package com.iitevents.rssmain;

import com.iitevents.rssmain.R;

import static com.iitevents.parser.RSSUtil.*;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Base screen shown as the feed is initially loaded. Not 
 * shown after the initial loading. Not needed, but here
 * as a placeholder in case you're loading a lot of data.
 * 
 * @author Swaroop L
 * @version 23/04/2015
 */
public class InitActivity extends Activity {

	// Keep track of when feed exists
	private SharedPreferences prefs;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// If we're above Honeycomb
		/*if(android.os.Build.VERSION.SDK_INT >= 11){
			// Remove the icon from the ActionBar
			getActionBar().setDisplayShowHomeEnabled(false);
		}*/
		// Get our preferences
	//	prefs = PreferenceManager.getDefaultSharedPreferences(this);
		// Check if a feed exists
	//	if(!prefs.getBoolean("isSetup", false)){
			// Set the content view
			setContentView(R.layout.splash);
			// Detect if there's a connection issue or not
			ConnectivityManager conMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
			// If there's a connection problem
			if (conMgr.getActiveNetworkInfo() == null
					|| !conMgr.getActiveNetworkInfo().isConnected()
					|| !conMgr.getActiveNetworkInfo().isAvailable()) {
				// Display an alert to the user
				AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertBox));
				// Tell the user what happened
				builder.setMessage("Unable to reach server.\nPlease check your connectivity.")
				// Alert title
				.setTitle("Connection Error")
				// Can't exit via back button
				.setCancelable(false)
				// Create exit button
				.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						// Exit the application
						finish();
					}
				});
				// Create dialog from builder
				AlertDialog alert = builder.create();
				// Show dialog
				alert.show();
				// Center the message of the dialog
				((TextView)alert.findViewById(android.R.id.message)).setGravity(Gravity.CENTER);
				// Center the title of the dialog
				((TextView)alert.findViewById((getResources().getIdentifier("alertTitle", "id", "android")))).setGravity(Gravity.CENTER);
			} else {
				// Change the feed
			//	changeFeed(false, this);
		//	}
	//	} else {
			// Start the new activity
			//setContentView(R.layout.home);
			startActivity(new Intent(this, HomeActivity.class));
			// Kill this one
			//finish();
		}
	}
}
