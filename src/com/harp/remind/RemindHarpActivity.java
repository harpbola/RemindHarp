package com.harp.remind;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RemindHarpActivity extends Activity implements OnClickListener {

	static final boolean debug = true;
	private static final String TAG = "RemindHarp";
	static final int KEEP_ALIVE = 0;
	static final int KILL = 1;
	
	Button buttonCreateReminder, buttonViewReminders;
	int requestCode;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// INITALIZE instance variables
		buttonCreateReminder = (Button) findViewById(R.id.buttonCreateReminder);
		buttonViewReminders = (Button) findViewById(R.id.buttonViewReminders);
		// ASSIGN on click listeners
		buttonCreateReminder.setOnClickListener(this);
		buttonViewReminders.setOnClickListener(this);
	}

	@Override
	public void onClick(View source) {
		Intent intent;
		switch (source.getId()) {
		case R.id.buttonCreateReminder:
			Log.d(TAG, "Clicked buttonCreateReminder.");
			intent = new Intent(this, CreateReminderActivity.class);
			startActivityForResult(intent, requestCode);
			break;
		case R.id.buttonViewReminders:
			Log.d(TAG, "Clicked buttonViewReminders.");
			intent = new Intent(this, ViewRemindersActivity.class);
			startActivity(intent);
			break;

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "onActivityResult and resultCode = "
				+ resultCode);
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RemindHarpActivity.KILL) {
			finish();
		} 
	}
}