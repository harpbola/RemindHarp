package com.harp.remind;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


public class ReminderService extends IntentService{
	
	private static final String TAG = "ReminderServicer";
	public static final String DELETE_INTENT = "hey";
	public static final String INSERT_INTENT = "you";
	private DatabaseHelper dh;
	private Handler mHandler;
	
	public ReminderService() {
		super(TAG);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate() {
	    super.onCreate();
	    mHandler = new Handler();
	}; 
	
	@Override
	protected void onHandleIntent(Intent intent) {
		Context context = getApplicationContext();
		// INITALIZE instance variables
		dh = new DatabaseHelper(this);
		
        String action = intent.getStringExtra("action");
        if (action.equals(DELETE_INTENT)) 
        {
            handleDelete(context, intent);
        } 
        else if (action.equals(INSERT_INTENT)) 
        {
        	handleInsert(context, intent);
        }	
        
        stopSelf();
	}
	
	private void handleDelete(Context context, Intent intent) {
		Log.d(TAG, "handleDelete");
		// EXTRACT fields
		long reminderId = intent.getLongExtra("reminder_id", 1);
		
		// Save to database
		boolean success = Reminder.delete(this.dh, reminderId);

		// Set the alarm
		if(success)
		{
			this.showToast("Reminder deleted.");
		}
		else
		{
			this.showToast("Failed to delete reminder #" + reminderId +".");
		}
		
		}

	public boolean setAlarm(long reminder_id, int hour, int minute)
	{
		boolean success = true;
		int requestCode = 0;
		// Get a Calendar object with reminder's time
		Calendar calendar = Calendar.getInstance();
		if(RemindHarpActivity.debug)
		{
			calendar.add(Calendar.SECOND, 5);
		}
		else
		{
			calendar.set(Calendar.HOUR_OF_DAY, hour);
			calendar.set(Calendar.MINUTE, minute);
			calendar.set(Calendar.SECOND, 00);
		}
		
		Intent serviceIntent = new Intent(this, CreateNotification.class);
		serviceIntent.putExtra("reminder_id", reminder_id);
		PendingIntent sender = PendingIntent.getBroadcast(this, requestCode,
				serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		// Get the AlarmManager service
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
		return success;
	}
	
	public void showToast(final String message){

	    mHandler.post(new Runnable() {            
	        @Override
	        public void run() {		
	        	Context context = getApplicationContext();
	        	CharSequence text = message;
	        	int duration = Toast.LENGTH_SHORT;
	            Toast.makeText(context, text, duration).show();                
	        }
	    });

	}
	
	private void handleInsert(Context context, Intent intent) {
		Log.d(TAG, "handleInsert");
		// EXTRACT fields
		String title = intent.getStringExtra("title");
		String description = intent.getStringExtra("description");
		int hour = intent.getIntExtra("hour", 0);
		int minute = intent.getIntExtra("minute", 0);
		
		// Save to database
		long new_reminder_id = Reminder.insert(this.dh, title,description, hour, minute);

		// Set the alarm
		this.setAlarm(new_reminder_id, hour, minute);
		this.showToast("Successfully created reminder.");
		
	}
	
	public void onDestroy() {
	    super.onDestroy();
	    dh.close();
	}; 


}
