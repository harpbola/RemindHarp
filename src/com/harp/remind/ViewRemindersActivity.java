package com.harp.remind;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.content.Context;

public class ViewRemindersActivity extends Activity implements OnClickListener,
		OnItemClickListener {

	private static final String TAG = "RemindHarp";
	Button buttonOk;
	ListView listReminders;
	DatabaseHelper db;
	ArrayAdapter<String> adapter;
	/*
	public class RemindersAdapter extends ArrayAdapter<String> {

		private final Context context;
		ArrayList<Reminder> reminders;

		public RemindersAdapter(Context context, int textViewResourceId,
				ArrayList<Reminder> reminders) {
			super(context, textViewResourceId, new String[] {"train", "from", "to"});
			String [] dummy = {};
			this.context = context;
			this.reminders = reminders;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			// return super.getView(position, convertView, parent);
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row = inflater.inflate(R.layout.row, parent, false);
			// INITIALIZE variables
			TextView title = (TextView) row.findViewById(R.id.title);
			TextView description = (TextView) row.findViewById(R.id.description);
			
			// POPULATE views
			Reminder reminder = this.reminders.get(position);
			title.setText(reminder.title);
			description.setText(reminder.description);
			return row;
		}
	}
	*/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_reminders);

		// INITALIZE instance variables
		db = new DatabaseHelper(this);
		buttonOk = (Button) findViewById(R.id.buttonOk);
		listReminders = (ListView) findViewById(R.id.listReminders);

		// POPULATE
//		ArrayList<Reminder> reminders = Reminder.getReminders(db);
//		Log.d(TAG, "Reminders: " + reminders);
//		RemindersAdapter adapter = new RemindersAdapter(this,
//				android.R.layout.simple_list_item_1, reminders);
//		listReminders.setAdapter(adapter);
		String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
				"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
				"Linux", "OS/2" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, values);
		listReminders.setAdapter(adapter);
		Log.d(TAG, "Database: " + db);

		// ASSIGN on click listeners
		buttonOk.setOnClickListener(this);
		listReminders.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View source) {
		Log.d(TAG, "Clicked: " + source);
		switch (source.getId()) {
			case R.id.buttonOk:
				finish();
				break;

		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View source, int position,
			long id) {
		Log.d(TAG, "Clicked: " + source);
		String item = adapter.getItem(position);
		Log.d(TAG, "Item: " + item);
		// Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
	}
}