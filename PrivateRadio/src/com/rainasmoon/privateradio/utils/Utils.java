package com.rainasmoon.privateradio.utils;

import com.rainasmoon.privateradio.MainActivity;
import com.rainasmoon.privateradio.sourcechanel.pocket.PocketConstants;
import com.rainasmoon.privateradio.sourcechanel.weibo.WeiboConstants;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

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
		
		Oauth2AccessToken readSinaToken = AccessTokenKeeper.readSinaAccessToken(context);
		Utils.log.info("Sina token is:" + readSinaToken.getToken());
		if (readSinaToken.getToken() != null  && !readSinaToken.getToken().isEmpty()) {
			WeiboConstants.access_token = readSinaToken.getToken();
		}
		
		String readPocketToken = AccessTokenKeeper.readPocketAccessToken(context);
		Utils.log.info("Pocket token is:" + readPocketToken);
		if (readPocketToken != null  && !readPocketToken.isEmpty()) {
			PocketConstants.access_token = readPocketToken;
		}
	}
	
	public static java.util.logging.Logger log = java.util.logging.Logger.getLogger("wh:");
	
	
}
