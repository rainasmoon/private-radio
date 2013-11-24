package com.aiaa.anualdiner.phonevote.sms;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;

public class BootService extends Service {

	public static final String TAG = "BootService";
	private ContentObserver mObserver;
	private Handler mHandler = new Handler();

	@Override
	public void onCreate() {
		Log.i(TAG, "onCreate().");
		super.onCreate();

		addSMSObserver();
	}

	public void addSMSObserver() {
		Log.i(TAG, "add a SMS observer. ");
		ContentResolver resolver = getContentResolver();

		Handler handler = new SMSHandler(this);
		mObserver = new SMSObserver(resolver, handler);
		resolver.registerContentObserver(SMS.CONTENT_URI, true, mObserver);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;

	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy().");

		this.getContentResolver().unregisterContentObserver(mObserver);
		super.onDestroy();

		Process.killProcess(Process.myPid());
		//TODO: glen why i need to exit(0)???
		System.exit(0);
	}

}