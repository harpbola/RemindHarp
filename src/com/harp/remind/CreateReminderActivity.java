package com.harp.remind;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CreateReminderActivity extends Activity implements OnClickListener {

	private static final String TAG = "RemindHarp";
	// UI elements
	Button buttonAdd, buttonCancel, buttonHourPlus, buttonHourMinus,
			buttonMinutePlus, buttonMinuteMinus;
	TextView textHour, textMinute;
	// hour: 0-23, minute: 1-59
	int hour = 0, minute = 0;
	DatabaseHelper db;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_reminder);

		// INITALIZE instance variables
        db = new DatabaseHelper(this);
        
		textHour = (TextView) findViewById(R.id.textHour);
		buttonHourPlus = (Button) findViewById(R.id.buttonHourPlus);
		buttonHourMinus = (Button) findViewById(R.id.buttonHourMinus);

		textMinute = (TextView) findViewById(R.id.textMinute);
		buttonMinutePlus = (Button) findViewById(R.id.buttonMinutePlus);
		buttonMinuteMinus = (Button) findViewById(R.id.buttonMinuteMinus);

		buttonAdd = (Button) findViewById(R.id.buttonAdd);
		buttonCancel = (Button) findViewById(R.id.buttonCancel);

		// INITIALIZE view fields
		setHourText(this.hour);
		setMinuteText(this.minute);

		// ASSIGN on click listeners
		buttonAdd.setOnClickListener(this);
		buttonCancel.setOnClickListener(this);

		buttonHourPlus.setOnClickListener(this);
		buttonHourMinus.setOnClickListener(this);

		buttonMinutePlus.setOnClickListener(this);
		buttonMinuteMinus.setOnClickListener(this);
	}

	@Override
	public void onClick(View source) {
		Intent intent;
		switch (source.getId()) {
			case R.id.buttonHourPlus:
				Log.d(TAG, "Clicked buttonHourPlus.");
				setHourText(this.hour + 1);
				break;
			case R.id.buttonHourMinus:
				Log.d(TAG, "Clicked buttonHourMinus.");
				setHourText(this.hour - 1);
				break;

			case R.id.buttonMinutePlus:
				Log.d(TAG, "Clicked buttonMinutePlus.");
				setMinuteText(this.minute + 1);
				break;
			case R.id.buttonMinuteMinus:
				Log.d(TAG, "Clicked buttonMinuteMinus.");
				setMinuteText(this.minute - 1);
				break;

			case R.id.buttonAdd:
				Log.d(TAG, "Clicked buttonAdd.");
				TextView textTitle = (TextView) findViewById(R.id.textTitle);
				TextView textDescription = (TextView) findViewById(R.id.textDescription);
				Reminder.insert(this.db, textTitle.getText(), textDescription.getText(), hour, minute);
				finish();
				break;
				
			case R.id.buttonCancel:
				Log.d(TAG, "Clicked buttonCancel.");
				finish();
				break;

		}
	}

	private void setHourText(int hour) {
		if (hour < 0) {
			hour = 24 + hour;
		}
		this.hour = hour % 24;
		Log.d(TAG, "Hour: " + this.hour);
		String meridiem = this.hour < 12 ? "AM" : "PM";
		String text = (this.hour % 12 == 0 ? "12" : this.hour % 12) + " "
				+ meridiem;
		this.textHour.setText(String.valueOf(text));
	}

	private void setMinuteText(int minute) {
		if (minute < 0) {
			minute = 60 + minute;
		}
		this.minute = minute % 60;
		this.textMinute.setText(String.valueOf(minute));
	}
}