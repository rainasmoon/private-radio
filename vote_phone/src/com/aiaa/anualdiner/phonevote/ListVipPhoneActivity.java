package com.aiaa.anualdiner.phonevote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ListVipPhoneActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ListView listView = new ListView(this);
		listView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, Result
						.getAllVipPhones()));
		setContentView(listView);

	}

	@Override
	protected void onResume() {
		super.onResume();
		ListView listView = new ListView(this);
		listView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, Result
						.getAllVipPhones()));
		setContentView(listView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_add, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_add:
			addVipPhone();
			return true;		
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void addVipPhone() {
		Intent intent = new Intent(this, AddVipPhoneActivity.class);

		startActivity(intent);
	}

	public void showHelp() {
		Log.d("wh:", "help...");
	}

	private OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
		public void onItemClick(AdapterView parent, View v, int position,
				long id) {
			Log.d("wh:", "intend to delete team...");
		}
	};

}
