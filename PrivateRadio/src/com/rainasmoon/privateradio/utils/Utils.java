package com.rainasmoon.privateradio.utils;

import com.rainasmoon.privateradio.MainActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;

public class Utils {

	public static Activity activity;
	public static Context context;
	public static ContentResolver contentResolver;

	public static void init(MainActivity mainActivity) {
		activity = mainActivity;
		context = activity.getApplicationContext();
		contentResolver = activity.getContentResolver();
	}
	
	public static java.util.logging.Logger log = java.util.logging.Logger.getLogger("wh:");
	
	
}
