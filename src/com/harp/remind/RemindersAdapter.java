package com.harp.remind;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RemindersAdapter extends ArrayAdapter<String> {

	private final Context context;
	ArrayList<Reminder> reminders;

	public RemindersAdapter(Context context, int textViewResourceId,
			ArrayList<Reminder> reminders) {
		super(context, textViewResourceId);
		this.context = context;
		this.reminders = reminders;
	}
	

	@Override
	public int getCount() {
		return this.reminders.size();
	}
	
	public Reminder getReminder(int position) {
		return this.reminders.get(position);
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
		
		if (position >= this.reminders.size())
		{
			return row;
		}
		// POPULATE views
		Reminder reminder = this.reminders.get(position);
		row.setId((int)reminder.id);
		
		title.setText(reminder.title);
		description.setText(reminder.description);
		return row;
	}
}
