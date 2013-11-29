package com.rainasmoon.privateradio.sourcechanel.weibo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rainasmoon.privateradio.utils.Utils;


import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class WeiboHandler extends WeiboConstants {

	java.util.logging.Logger log = java.util.logging.Logger.getLogger("wh:");


	public List<String> retriveWeiboList() {
		try {
			return retriveWeiboListRaw();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Arrays.asList(new String[]{"微博没有更新喽！！！"});

	}

	public List<String> retriveWeiboListRaw() throws JSONException,
			ClientProtocolException, IOException {
		check();

		String url = WeiboConstants.URL_GET + "?access_token=" + WeiboConstants.access_token;
		HttpGet request = new HttpGet(url);
		
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse httpResponse = client.execute(request);

		String retSrc = EntityUtils.toString(httpResponse.getEntity());
		
		
		JSONObject result = new JSONObject(retSrc);
		
		JSONArray list =   result.getJSONArray("statuses");
		List<String>  l = new ArrayList<String>();
		for (int i=0; i< list.length(); i++) {
			JSONObject msg = (JSONObject) list.get(i);
			log.info("response list:" + msg.getString("text"));
			l.add(msg.getString("text"));
		}
		
		return l;
	}

	private void check() throws ClientProtocolException, JSONException,
			IOException {
		if (access_token == null) {
			Utils.activity.startActivity(new Intent(Utils.context, WBAuthCodeActivity.class));
		}

	}

	
}
