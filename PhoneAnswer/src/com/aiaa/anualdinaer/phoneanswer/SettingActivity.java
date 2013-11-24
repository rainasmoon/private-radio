package com.aiaa.anualdinaer.phoneanswer;

import com.aiaa.anualdinaer.phoneanswer.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SettingActivity  extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
				
		setContentView(R.layout.settings);
	}
	
	@Override
    protected void onResume() {
        super.onResume();
       
        EditText filter = (EditText) findViewById(R.id.sms_prefix);		
		if (Result.FILTER != null ) {
			filter.setText(Result.FILTER);
		}
		EditText numWiner = (EditText) findViewById(R.id.num_winners);
		if (Result.NUM_WINERS > 0) {
			numWiner.setText("" + Result.NUM_WINERS);
		} 
		
		EditText numGamers = (EditText) findViewById(R.id.num_gamers);
		if (Result.NUM_GAMERS > 0) {
			numGamers.setText("" + Result.NUM_GAMERS);
		} 
		
		EditText baseHour = (EditText) findViewById(R.id.base_hour);
		if (Result.BEGIN_HOUR != null) {
			baseHour.setText("" + Result.BEGIN_HOUR);
		} 

    }
	
	
	public void save(View view) {
	    
		EditText editText = (EditText) findViewById(R.id.sms_prefix);
		String smsPrefix = editText.getText().toString();
		
		EditText editText1 = (EditText) findViewById(R.id.num_winners);
		int numWiner = Integer.parseInt(editText1.getText().toString());
		
		EditText vipWeight = (EditText) findViewById(R.id.num_gamers);
		int numGamers = Integer.parseInt(vipWeight.getText().toString());
		
		EditText baseHour = (EditText) findViewById(R.id.base_hour);
		String baseH = baseHour.getText().toString();
		
		if (smsPrefix != null && smsPrefix.indexOf(' ') > 0) {
			toast("prefix can't contains space...");
			return;
		}
		
		if (numWiner < 1) {
			toast("num of winners can't smaller than 1");
			return;
		}
		
		if (numGamers < 1) {
			toast("num of gamers can't smaller than 1");
			return;
		}
		
		if (numWiner > numGamers) {
			toast("winers must smaller than all gamers");
			return;
		}
		
		if (baseH == null) {
			toast("base hour can't be null.");
			return;
		}
		
		if (smsPrefix != null) {
			Result.FILTER = smsPrefix.trim();
		}
		else {
			Result.FILTER = "";
		}
				
		if (numWiner > 1) {
			Result.NUM_WINERS = numWiner;
		}	
		
		if (numGamers > 1) {
			Result.NUM_GAMERS = numGamers;
		}
		
		if (baseH != null) {
			Result.BEGIN_HOUR = baseH;
		}
		
		toast("save success.");
	}
	
	private void toast(String msg) {
		Context context = getApplicationContext();
		CharSequence text = msg;
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
}
