package com.aiaa.anualdiner.phonevote;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.aiaa.anualdiner.phonevote.R;
import com.aiaa.anualdiner.phonevote.sms.BootService;
import com.aiaa.anualdiner.phonevote.sms.SMSService;

import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.telephony.SmsManager;

public class MainActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.aiaa.anualdiner.phonevote.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
	
	public void settings() {
		Intent intent = new Intent(this, SettingActivity.class);
		startActivity(intent);
	}
	
	public void clear() {
		Result.clear();
		
		toast("all datas are cleared...");
	}
	
	public void initTest() {
		Result.addTeam("1");
		Result.addTeam("2");
		Result.addTeam("3");

		
		Result.getTeams().get("1").setVoteNumber(0);
		Result.getTeams().get("2").setVoteNumber(0);
		Result.getTeams().get("3").setVoteNumber(0);

//		Simeon Preston	00852 63177882
//		Troy Barnes	00852 64661717
//		Alyssa Tam	00852 92758536
//		Graeme Truner	0061 408252342
//		Steffen Schade	00852 63177833
//		Michael Lee	13681426183
//		Bill Liu	18601124876
//		Anna Lu	13924221283
//		Tiger Wang	13601288697
//		Tracy Mo	18665582373
//		Lilia Jiang	13911512846
//		Marson Ma	13825158932
//		Kent Huang	13609737654
//		Helen Lu	18610430362
//		Jessica Fu	18688392002
//		Mandy Mo	13604460484
//		Brad Wu	13671340870
//		Chenky Chen	13922298106
//		Sherlly Yuan	18601207865
//		Oliver Hao	13701328851
		
//		Simeon Preston	
		Result.addVipPhone("0085263177882");		
		Result.addVipPhone("63177882");
//		Troy Barnes	
		Result.addVipPhone("0085264661717");
		Result.addVipPhone("64661717");
//		Alyssa Tam	
		Result.addVipPhone("0085292758536");
		Result.addVipPhone("92758536");
//		Graeme Truner	
		Result.addVipPhone("0061408252342");
		Result.addVipPhone("408252342");
//		Steffen Schade	
		Result.addVipPhone("0085263177833");
		Result.addVipPhone("63177833");
//		Michael Lee	
		Result.addVipPhone("13681426183");
//		Bill Liu	
		Result.addVipPhone("18601124876");
//		Anna Lu	
		Result.addVipPhone("13924221283");
//		Tiger Wang	
		Result.addVipPhone("13601288697");
//		Tracy Mo	
		Result.addVipPhone("18665582373");
//		Lilia Jiang	
		Result.addVipPhone("13911512846");
//		Marson Ma	
		Result.addVipPhone("13825158932");
//		Kent Huang	
		Result.addVipPhone("13609737654");
//		Helen Lu	
		Result.addVipPhone("18610430362");
//		Jessica Fu	
		Result.addVipPhone("18688392002");
//		Mandy Mo	
		Result.addVipPhone("13604460484");
//		Brad Wu	
		Result.addVipPhone("13671340870");
//		Chenky Chen	
		Result.addVipPhone("13922298106");
//		Sherlly Yuan	
		Result.addVipPhone("18601207865");
//		Oliver Hao	
		Result.addVipPhone("13701328851");
	
		Result.initTeams();
		
		toast("init data is inited...");
	}
		
	public void listTeam(View view) {
		Intent intent = new Intent(this, ListTeamActivity.class);
		startActivity(intent);
	}
	
	public void listVipPhone(View view) {
		Intent intent = new Intent(this, ListVipPhoneActivity.class);
		startActivity(intent);
	}

	public void viewListResult(View view) {

		Intent intent = new Intent(this, DisplayListResultActivity.class);
		startActivity(intent);
	}

	public void viewVoteChart(View view) {
		Intent intent = new Intent(this, CreateChartActivity.class);

		startActivity(intent);
	}
	
	public void viewFullChart(View view) {
		Intent intent = new Intent(this, FullChartActivity.class);

		startActivity(intent);
	}

	public void startService(View view) {
		Intent intent = new Intent(this, BootService.class);
		startService(intent);
		
		toast("listenning to sms...");
	}

	public void stopService(View view) {
		Intent intent = new Intent(this, BootService.class);
		stopService(intent);
		toast("service stoped...");
		
	}
	
	public void showIllegalMsg(View view) {
		Intent intent = new Intent(this, ListIllegalMsgActivity.class);

		startActivity(intent);
	}
	
	private void restoreFormPhoneMsg() {
		Result.initTeams();
		Result.exec_msg(new SMSService().getSmsInPhone(getContentResolver(), Result.BEGIN_HOUR));
		toast("restore successful...");
	}

	private void exit() {
		stopService((View)null);
		Process.killProcess(Process.myPid());
		//TODO: glen why i need to exit(0)???
		System.exit(0);
	}
	
	private void toast(String msg) {
		Context context = getApplicationContext();
		CharSequence text = msg;
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
}
