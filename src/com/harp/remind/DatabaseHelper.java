package com.harp.remind;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	// Define the version and database file name
	private static final String DB_NAME = "harp.db";
	private static final int DB_VERSION = 1;

	private SQLiteDatabase db;

	// Constructor to simplify Business logic access to the repository
	public DatabaseHelper(Context context) {

		super(context, DB_NAME, null, DB_VERSION);
		// Android will look for the database defined by DB_NAME
		// And if not found will invoke your onCreate method
		this.db = this.getWritableDatabase();

	}

	public SQLiteDatabase getDatabase() {
		return this.db;
	}
	

	@Override
	public void onCreate(SQLiteDatabase db) {

		// Android has created the database identified by DB_NAME
		// The new database is passed to you via the db arg.
		// Now it is up to you to create the Schema.
		db.execSQL(Reminder.getSchema());

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// Later when you change the DB_VERSION
		// This code will be invoked to bring your database
		// Upto the correct specification
	}
	
}