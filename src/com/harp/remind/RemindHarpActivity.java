package com.harp.remind;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RemindHarpActivity extends Activity implements OnClickListener {
	
	private static final String TAG = "RemindHarp";
	Button buttonCreateReminder, buttonViewReminders;
	
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
		switch(source.getId())
		{
			case R.id.buttonCreateReminder:
				Log.d(TAG, "Clicked buttonCreateReminder.");
				intent = new Intent(this, CreateReminderActivity.class);
				startActivity(intent);
				break;
			case R.id.buttonViewReminders:
				Log.d(TAG, "Clicked buttonViewReminders.");
				intent = new Intent(this, ViewRemindersActivity.class);
				startActivity(intent);
				break;
				
		}
	}
}