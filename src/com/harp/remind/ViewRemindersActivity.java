package com.harp.remind;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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

	private static final String TAG = "ViewRemindersActivity";
	Button buttonOk;
	ListView listReminders;
	DatabaseHelper dh;
	// ArrayAdapter<String> adapter;
	RemindersAdapter adapter;
	ArrayList<Reminder> reminders;
	int positionClicked;

	protected void populateList()
	{
		reminders = Reminder.getReminders(dh);
		Log.d(TAG, "Reminders: " + reminders);
		adapter = new RemindersAdapter(this,
				android.R.layout.simple_list_item_1, reminders);
		listReminders.setAdapter(adapter);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_reminders);

		// INITALIZE instance variables
		Log.d(TAG, "onCreate");
		dh = new DatabaseHelper(this);
		buttonOk = (Button) findViewById(R.id.buttonOk);
		listReminders = (ListView) findViewById(R.id.listReminders);
		
		// POPULATE
		populateList();
		positionClicked = -1;
		
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
		positionClicked = position;
		Reminder reminder = adapter.getReminder(position);
		Log.d(TAG, "Item: " + reminder);
		Intent intent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("remind:" + reminder.id));
		startActivityForResult(intent, 1);
		Log.d(TAG, "Finished: " + reminder);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int deletedReminderId, Intent data) {
		super.onActivityResult(requestCode, deletedReminderId, data);
		Log.d(TAG, "resultCode: " + deletedReminderId);
		Log.d(TAG, "positionClicked: " + positionClicked);
		if (deletedReminderId > 0 && positionClicked != -1) {
			Log.d(TAG, "listReminders: "+ listReminders);
			Log.d(TAG, "Item : "+ listReminders.findViewById(deletedReminderId));
			// HIDE the row
			View view = listReminders.findViewById(deletedReminderId);
//			view.setVisibility(View.INVISIBLE);
//			view.setVisibility(View.GONE);
			listReminders.removeViewInLayout(view);
			listReminders.refreshDrawableState();
		} 
	}
	
	public void onDestroy() {
	    super.onDestroy();
	    dh.close();
	}; 
}