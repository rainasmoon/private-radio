package com.aiaa.anualdiner.phonevote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AddVipPhoneActivity  extends Activity {

	 public final static String EXTRA_MESSAGE = "com.aiaa.anualdiner.phonevote.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		setContentView(R.layout.add_vip_phone);
	}
	
	
	
	public void saveVipPhone(View view) {
	    
		EditText editText = (EditText) findViewById(R.id.edit_vip_phone);
		String phoneNumber = editText.getText().toString();
		
		if (phoneNumber != null && !phoneNumber.trim().equals("") && phoneNumber.indexOf(' ') < 0 && phoneNumber.length() > 1) {
			Result.addVipPhone(phoneNumber);	
			toast("save vip phone: " + phoneNumber);
			editText.setText(null);
		}
		else {
			toast("input legal digitial phone number.");
		}
		
	}
	
	private void toast(String msg) {
		Context context = getApplicationContext();
		CharSequence text = msg;
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}		
	
}
