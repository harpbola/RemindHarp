package com.harp.remind;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class CreateReminderService extends IntentService {
	private static final String TAG = "CreateReminderTask";

	public CreateReminderService() {
		super("CreateReminderService");
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.e("Service Example", "Service Started.. ");
		// pushBackground();
		// Reminder.insert(this.db, textTitle.getText(),
		// textDescription.getText(), hour, minute);
		Context context = CreateReminderService.this;
		CharSequence text = "Hello toast!";
		int duration = Toast.LENGTH_SHORT;
		Log.d(TAG, "I feel sleepy");
		try {
			// do what you want to do before sleeping
			Thread.currentThread().sleep(2000);// sleep for 1000 ms
			// do what you want to do after sleeptig
		} catch (InterruptedException ie) {
			// If this thread was intrrupted by nother thread
		}
		Log.d(TAG, "hey: ");
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.e("Service Example", "Service Destroyed.. ");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Log.d(TAG, "Intent: " + intent);
	}

}
