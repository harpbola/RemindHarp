package com.harp.remind;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;
import android.util.Log;

public class Reminder {

	private static final String TAG = "REMINDER";
	long id;
	String title, description;
	Date alarmTime;

	Reminder(Cursor cursor) {
		this.id = cursor.getInt(0);
		this.title = cursor.getString(1);
		this.description = cursor.getString(2);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		try {
			this.alarmTime = dateFormat.parse(cursor.getString(3));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * DATABASE helper functions
	 */
	public static class Table {
		public static final String NAME = "reminders";
		public static final String COL_ID = "id";
		public static final String COL_TITLE = "title";
		public static final String COL_DESCRIPTION = "description";
		public static final String COL_ALARAM_TIME = "alarm_time";
	}
	
	public static String[] getColumns() {
		String [] columns = new String[] {
				Table.COL_ID,
				Table.COL_TITLE, 
				Table.COL_DESCRIPTION,
				Table.COL_ALARAM_TIME,
				};
		return columns;
	}

	public static String getSchema() {
		String query = String.format(
				"CREATE TABLE IF NOT EXISTS `%s` ("
				+ "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
				+ "`%s` VARCHAR NOT NULL,"
				+ "`%s` text ,"
				+ "`%s` datetime NOT NULL" + ")", 
				Table.NAME, 
				Table.COL_TITLE,
				Table.COL_DESCRIPTION, 
				Table.COL_ALARAM_TIME
			);
		return query;
	}
	
	public static ArrayList<Reminder> getReminders(DatabaseHelper dh){
		SQLiteDatabase db = dh.getDatabase();
		ArrayList<Reminder> reminders = new ArrayList<Reminder>();
		Cursor cursor = db.query(Reminder.Table.NAME, Reminder.getColumns(),
				null, null, null, null, null);
		Log.d(TAG, "cursor: "+ cursor);
		while (cursor.moveToNext()) {
			Reminder reminder = new Reminder(cursor);
			reminders.add(reminder);
		}
		return reminders;
	}

	private static ContentValues createContentValues(String title, String description,
			String alarm_time) {
		ContentValues values = new ContentValues();
		values.put(Table.COL_TITLE, title);
		values.put(Table.COL_DESCRIPTION, description);
		values.put(Table.COL_ALARAM_TIME, alarm_time);
		return values;
	}
	public static long insert(DatabaseHelper dh, CharSequence title, CharSequence description, 
			int hour, int minute) {
		SQLiteDatabase db = dh.getDatabase();
		// Proper data
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date dateTime = new Date();
		dateTime.setHours(hour);
		dateTime.setMinutes(minute);
		dateTime.setSeconds(0);
		ContentValues values = createContentValues(
				title.toString(), description.toString(), dateFormat.format(dateTime));
		return db.insert(Table.NAME, null, values);
	}

	public boolean delete(DatabaseHelper dh) {
		SQLiteDatabase db = dh.getDatabase();
		return db.delete(Table.NAME, Table.COL_ID + "=" + this.id, null) > 0;
	}

	public static Reminder get(DatabaseHelper dh, long id) {
		SQLiteDatabase db = dh.getDatabase();
		Cursor cursor = db.query(Reminder.Table.NAME, Reminder.getColumns(),
				Table.COL_ID + "=" + id, null, null, null, null);
		Log.d(TAG, "cursor: "+ cursor);
		if(cursor.moveToFirst()){
			return  new Reminder(cursor);
		}
		return null;		
	}
	
	public static Reminder get(DatabaseHelper dh, int id) {
		return Reminder.get(dh, (long) id);
	}

	public static boolean delete(DatabaseHelper dh, long reminder_id) {
		SQLiteDatabase db = dh.getDatabase();
		return db.delete(Table.NAME, Table.COL_ID + "=" + reminder_id, null) > 0;
	}

}
