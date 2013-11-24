package com.aiaa.anualdinaer.phoneanswer;

import java.util.Date;
import java.util.List;

import com.aiaa.anualdinaer.phoneanswer.R;
import com.aiaa.anualdinaer.sms.BootService;
import com.aiaa.anualdinaer.sms.MessageItem;
import com.aiaa.anualdinaer.sms.SMSService;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	@Override
    protected void onResume() {
        super.onResume();
      		
		ToggleButton toggle = (ToggleButton) findViewById(R.id.toggle_pause);
		toggle.setChecked(Result.isPaused);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_settings:
			settings();
			return true;
		case R.id.menu_clear:
			clear();
			return true;
		case R.id.menu_test:
			initTest();
			return true;
		case R.id.menu_exit:
			exit();
			return true;
		case R.id.menu_restore:
        	restoreFormPhoneMsg();
        	return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void refreshStatus() {
		//
		TextView status = (TextView) findViewById(R.id.text_status);
		String text = "";
		text += "the current game is:" + Result.getCurrentGame();
		text += "\n the answer is :" + Result.getCurrentAnswer();
		text += "\n the total winners is :" + Result.getCurrentWinnersNum();
		text += "\n the total msg is :" + Result.getCurrentMsgNum();
		status.setText(text);
	}

	public void settings() {
		Intent intent = new Intent(this, SettingActivity.class);
		startActivity(intent);
	}

	public void clear() {
		Result.clear();

		toast("all datas are cleared...");
	}

	public void initTest() {
		Result.addAnswer("abcd");
		Result.addAnswer("1234");
		Result.addAnswer("0000");

		Result.init();
		MessageItem item = new MessageItem();
		item.setPhone("12345678900");
		item.setBody("abcd");
		item.setDate(new Date());
		Result.allGames.get("1").addToWiners(item );

		item = new MessageItem();
		item.setPhone("15811015803");
		item.setBody("abcd");
		item.setDate(new Date());
		Result.allGames.get("1").addToWiners(item );
		
		item = new MessageItem();
		item.setPhone("12345678900");
		item.setBody("1234");
		item.setDate(new Date());
		Result.allGames.get("2").addToWiners(item );
		
		toast("test data is inited...");
	}

	public void listAnswer(View view) {
		Intent intent = new Intent(this, ListAnswerActivity.class);
		startActivity(intent);
	}
	
	public void listWinner(View view) {
		Intent intent = new Intent(this, ListWinnerActivity.class);
		startActivity(intent);
	}

	public void listAllMsg(View view) {
		Intent intent = new Intent(this, ListAllGamersActivity.class);
		startActivity(intent);
	}
	
	public void previousGame(View view) {
		if (Result.currentGame > 0) {
			Result.currentGame--;
		}
		else {
			Result.currentGame = 0;
			toast("already the first game.");
		}
	}

	public void nextGame(View view) {
		if (Result.currentGame < Result.getAllAnswers().size() -1) {
			Result.currentGame++;
		}
		else {
			Result.currentGame = Result.getAllAnswers().size() -1;
			toast("alread the last game.");
		}

	}

	public void startService(View view) {
		Intent intent = new Intent(this, BootService.class);
		startService(intent);
		
		if (!thread.isAlive()) {
			thread.start();
		}
		toast("listenning to sms...");
	}

	public void stopService(View view) {
		Intent intent = new Intent(this, BootService.class);
		stopService(intent);
		toast("service stoped...");

	}
	
	

	public void onToggleClicked(View view) {

		boolean on = ((ToggleButton) view).isChecked();

		if (on) {
			Result.isPaused = true;
		} else {
			Result.isPaused = false;
		}
	}
	
	private void restoreFormPhoneMsg() {
		Result.init();
		Result.exec_msg(new SMSService().getSmsInPhone(getContentResolver(), Result.BEGIN_HOUR));
		toast("restore successful...");
	}
	
	private void exit() {
		stopService((View)null);
		Process.killProcess(Process.myPid());
		//TODO: glen why i need to exit(0)???
		System.exit(0);
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);			
			refreshStatus();
		}
	};
	
	private Thread thread = new Thread() {
		@Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
				try {					
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.err.println("InterruptedException！线程关闭");
					this.interrupt();
				}
			}
		}
	};

	private void toast(String msg) {
		Context context = getApplicationContext();
		CharSequence text = msg;
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
	
}
