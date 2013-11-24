package com.aiaa.anualdiner.phonevote;

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
		EditText maxVoteNum = (EditText) findViewById(R.id.max_vote_num);
		if (Result.MAX_VOTE_NUM > 0) {
			maxVoteNum.setText("" + Result.MAX_VOTE_NUM);
		} 
		
		EditText vipWeight = (EditText) findViewById(R.id.vip_weight);
		if (Result.VIP_WEIGHT > 0) {
			vipWeight.setText("" + Result.VIP_WEIGHT);
		} 
		
		EditText baseHour = (EditText) findViewById(R.id.base_hour);
		if (Result.BEGIN_HOUR != null) {
			baseHour.setText("" + Result.BEGIN_HOUR);
		} 
		
		
		EditText startPoint = (EditText) findViewById(R.id.start_point);
		if (Result.START_POINT > 0) {
			startPoint.setText("" + Result.START_POINT);
		} 
		
		EditText screenHight = (EditText) findViewById(R.id.screen_hight);
		if (Result.SCREEN_HEIGHT > 0) {
			screenHight.setText("" + Result.SCREEN_HEIGHT);
		} 
		
		EditText screenWidth = (EditText) findViewById(R.id.screen_width);
		if (Result.SCREEN_WIDTH > 0) {
			screenWidth.setText("" + Result.SCREEN_WIDTH);
		} 
		
		EditText paintWidth = (EditText) findViewById(R.id.paint_width);
		if (Result.PAINT_WIDTH > 0) {
			paintWidth.setText("" + Result.PAINT_WIDTH);
		} 
		
		EditText paintDistance = (EditText) findViewById(R.id.paint_distance);
		if (Result.PAINT_DISTANCE > 0) {
			paintDistance.setText("" + Result.PAINT_DISTANCE);
		} 
		
		ToggleButton toggle = (ToggleButton) findViewById(R.id.toggle_allow_vote_one);
		toggle.setChecked(Result.enableMultiVote);
    }
	
	public void onToggleClicked(View view) {
	    
	    boolean on = ((ToggleButton) view).isChecked();
	    
	    if (on) {
	        Result.enableMultiVote = true;
	    } else {
	    	Result.enableMultiVote = false;
	    }
	}
	
	public void save(View view) {
	    
		EditText editText = (EditText) findViewById(R.id.sms_prefix);
		String smsPrefix = editText.getText().toString();
		
		EditText editText1 = (EditText) findViewById(R.id.max_vote_num);
		int maxVoteNum = Integer.parseInt(editText1.getText().toString());
		
		EditText vipWeight = (EditText) findViewById(R.id.vip_weight);
		int vipW = Integer.parseInt(vipWeight.getText().toString());
		
		EditText baseHour = (EditText) findViewById(R.id.base_hour);
		String baseH = baseHour.getText().toString();
		
		EditText startPoint = (EditText) findViewById(R.id.start_point);
		int startP = Integer.parseInt(startPoint.getText().toString());
		
		EditText screenHight = (EditText) findViewById(R.id.screen_hight);
		int scHight = Integer.parseInt(screenHight.getText().toString());
		
		EditText screenWidth = (EditText) findViewById(R.id.screen_width);
		int scWidth = Integer.parseInt(screenWidth.getText().toString());
		
		EditText paintWidth = (EditText) findViewById(R.id.paint_width);
		int ptWidth = Integer.parseInt(paintWidth.getText().toString());
		
		EditText paintDistance = (EditText) findViewById(R.id.paint_distance);
		int ptDistance = Integer.parseInt(paintDistance.getText().toString());
		
		if (smsPrefix != null && smsPrefix.indexOf(' ') > 0) {
			toast("prefix can't contains space...");
			return;
		}
		
		if (maxVoteNum < 1) {
			toast("max vote num can't smaller than 1");
			return;
		}
				
		if (vipW < 1) {
			toast("vip weight can't smaller than 1");
			return;
		}
		
		if (baseH == null) {
			toast("base hour can't be null.");
			return;
		}
		
		if (startP < 0) {
			toast("start point can't smaller than 0");
			return;
		}
	
		if (scHight < 0) {
			toast("screen hight can't smaller than 0");
			return;
		}
		
		if (scWidth < 0) {
			toast("screen width can't smaller than 0");
			return;
		}
		
		if (ptWidth < 0) {
			toast("paint width can't smaller than 0");
			return;
		}
		
		if (ptDistance < 0) {
			toast("paint distance can't smaller than 0");
			return;
		}
		
		if (smsPrefix != null) {
			Result.FILTER = smsPrefix.trim();
		}
		else {
			Result.FILTER = "";
		}
				
		if (maxVoteNum > 1) {
			Result.MAX_VOTE_NUM = maxVoteNum;
		}	
		
		if (vipW > 1) {
			Result.VIP_WEIGHT = vipW;
		}
		
		if (baseH != null) {
			Result.BEGIN_HOUR = baseH;
		}
		
		if (startP > 0) {
			Result.START_POINT = startP;
		}
		
		if (scHight > 0) {
			Result.SCREEN_HEIGHT = scHight;
		}
		
		if (scWidth > 0) {
			Result.SCREEN_WIDTH = scWidth;
		}
		
		if (ptWidth > 0) {
			Result.PAINT_WIDTH = ptWidth;
		}
		
		if (ptDistance > 0) {
			Result.PAINT_DISTANCE = ptDistance;
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
