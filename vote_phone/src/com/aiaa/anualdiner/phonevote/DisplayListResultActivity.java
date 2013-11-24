package com.aiaa.anualdiner.phonevote;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.aiaa.anualdiner.phonevote.sms.MessageItem;

public class DisplayListResultActivity extends Activity {
	
	public DisplayListResultActivity() {
		
	}
	
	@SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
     // Get the message from the intent
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        //get how many teams 
        
        Result.initTeams();

        // Create the text view
        TextView textView = new TextView(this);
        textView.setTextSize(40);        
        String result = "";
        for(String teamName : Result.getAllTeamsName()) {
        	List<MessageItem> m = Result.getTeams().get(teamName).getVoteMsgs();
        	
        	result += teamName + ":" +Result.getTeams().get(teamName).getVoteNumber() + "\n";
        }
        
        result += "Err:" + Result.getIllegalMsg().size() + "\n";
        
        for(MessageItem mm: Result.getIllegalMsg()) {
    		Log.d("wh:ill:", mm.getBody());
    	}
        
        textView.setText(result);

        // Set the text view as the activity layout
        setContentView(textView);
    }

	
	
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    
   

}
