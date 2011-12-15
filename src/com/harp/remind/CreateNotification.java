package com.harp.remind;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.net.Uri;
import android.os.Bundle;

public class CreateNotification extends BroadcastReceiver {

	Notification notification = new Notification(R.drawable.ic_launcher,
			"A new notification", System.currentTimeMillis());

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			Bundle bundle = intent.getExtras();
			long reminderId = bundle.getLong("reminder_id");
			Reminder reminder = Reminder.get(new DatabaseHelper(context),
					reminderId);
			createNotification(context, reminder);
		} catch (Exception e) {
			Toast.makeText(
					context,
					"There was an error somewhere, but we still received an alarm.",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();

		}
	}

	public void createNotification(Context context, Reminder reminder) {
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		// Hide the notification after its selected
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("remind:"
				+ reminder.id));
		
		// Retrieve PendingIntent to start new activity
		PendingIntent activity = PendingIntent.getActivity(context, 0, intent,0);
		notification.setLatestEventInfo(context, reminder.title, reminder.description, activity);
		notification.number += 1;
		
		// Place notification on bar
		notificationManager.notify(0, notification); 
	}

}