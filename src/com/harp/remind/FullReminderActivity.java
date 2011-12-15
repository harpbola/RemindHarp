package com.harp.remind;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FullReminderActivity extends Activity implements OnClickListener {

	private static final String TAG = "RemindHarp";
	// UI elements
	Button buttonDelete, buttonKeep;
	TextView textTitle, textDescription;
	// hour: 0-23, minute: 1-59
	int hour = 0, minute = 0;
	DatabaseHelper dh;
	Reminder reminder;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.full_reminder);

		// INITALIZE instance variables
		dh = new DatabaseHelper(this);
		Intent intent = getIntent();
		Uri data = intent.getData();
		int reminder_id = Integer.parseInt(data.getSchemeSpecificPart());
		reminder = Reminder.get(dh, reminder_id);
        
		buttonDelete = (Button) findViewById(R.id.buttonDelete);
		buttonKeep = (Button) findViewById(R.id.buttonKeep);
		textTitle = (TextView) findViewById(R.id.textTitle);
		textDescription = (TextView) findViewById(R.id.textDescription);
		
		// INITIALIZE view fields
		textTitle.setText(reminder.title);
		textDescription.setText(reminder.description);

		// ASSIGN on click listeners
		buttonDelete.setOnClickListener(this);
		buttonKeep.setOnClickListener(this);

	}

	@Override
	public void onClick(View source) {
		Log.d(TAG, "Clicked: " + source.getId());
		Intent intent;
		switch (source.getId()) {
			case R.id.buttonDelete:
				// CALL the service
				intent = new Intent(this, ReminderService.class);
				intent.putExtra("action", ReminderService.DELETE_INTENT);
				Log.d(TAG, "Delete reminder #" + reminder.id);
				intent.putExtra("reminder_id", reminder.id);
				startService(intent);
				
				// Close this and the parent
				intent = new Intent();
				setResult((int)reminder.id, intent);
				finish();
				break;
				
			case R.id.buttonKeep:
				finish();
				break;

		}
	}
	
	public void onDestroy() {
	    super.onDestroy();
	    dh.close();
	}; 
}