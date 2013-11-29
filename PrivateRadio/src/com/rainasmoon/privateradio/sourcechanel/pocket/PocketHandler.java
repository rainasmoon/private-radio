package com.rainasmoon.privateradio.sourcechanel.pocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rainasmoon.privateradio.utils.AccessTokenKeeper;
import com.rainasmoon.privateradio.utils.Utils;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class PocketHandler extends PocketConstants {

	java.util.logging.Logger log = java.util.logging.Logger.getLogger("wh:");

	private String retriveToken() throws JSONException,
			ClientProtocolException, IOException {
		System.out.println("yes.");

		HttpPost request = new HttpPost(PocketConstants.URL_TOKEN);
		// 先封装一个 JSON 对象
		JSONObject param = new JSONObject();
		param.put("consumer_key", PocketConstants.CONSUMER_KEY);
		param.put("redirect_uri", PocketConstants.REDIRECT_URI);
		// 绑定到请求 Entry
		StringEntity se = new StringEntity(param.toString());
		
		request.addHeader("Content-Type", "application/json; charset=UTF8");
		request.setEntity(se);
		// 发送请求
		DefaultHttpClient client = new DefaultHttpClient();

		HttpResponse httpResponse = client.execute(request);
		// 得到应答的字符串，这也是一个 JSON 格式保存的数据
		String retSrc = EntityUtils.toString(httpResponse.getEntity());
		// 生成 JSON 对象
		log.info("response:" + retSrc);
		JSONObject result = new JSONObject(retSrc);
		String token = (String) result.get("code");
		log.info("code:" + token);
		System.out.println("token:" + token + "END");
		return token;
	}

	private void openWebPage(String token) {
		Uri webpage = Uri
				.parse("https://getpocket.com/auth/authorize?request_token="
						+ token
						+ "&redirect_uri=pocketapp20568:authorizationFinished");
		Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);

		Utils.activity.startActivity(webIntent);

	}

	private String retriveAccessToken(String token) throws JSONException,
			ClientProtocolException, IOException {
		System.out.println("yes.");

		HttpPost request = new HttpPost(PocketConstants.URL_ACCESS_TOKEN);
		// 先封装一个 JSON 对象
		JSONObject param = new JSONObject();
		param.put("consumer_key", PocketConstants.CONSUMER_KEY);
		param.put("code", token);
		// 绑定到请求 Entry
		StringEntity se = new StringEntity(param.toString());
		request.addHeader("X-Accept", "application/json");
		request.addHeader("Content-Type", "application/json; charset=UTF8");
		request.setEntity(se);
		// 发送请求
		DefaultHttpClient client = new DefaultHttpClient();

		HttpResponse httpResponse = client.execute(request);
		// 得到应答的字符串，这也是一个 JSON 格式保存的数据
		String retSrc = EntityUtils.toString(httpResponse.getEntity());
		// 生成 JSON 对象
		log.info("response:" + retSrc);
		JSONObject result = new JSONObject(retSrc);
		String access_token = (String) result.get("access_token");
		String user_name = result.getString("username");
		log.info("access_token:" + access_token);
		System.out.println("access_token:" + access_token + "END");
		return access_token;
	}

	public List<String> retrivePocketList() {
		try {
			return retrivePoketListRaw();
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
		return Arrays.asList(new String[]{"没有最新的Pocket信息喽！！！"});

	}

	public List<String> retrivePoketListRaw() throws JSONException,
			ClientProtocolException, IOException {
		check();

		HttpPost request = new HttpPost(PocketConstants.URL_GET);

		JSONObject param = new JSONObject();
		param.put("consumer_key", PocketConstants.CONSUMER_KEY);
		param.put("access_token", access_token);
		param.put("count", DEFAULT_RETRIVE_LIMIT);
		StringEntity se = new StringEntity(param.toString());

		request.addHeader("X-Accept", "application/json");
		request.addHeader("Content-Type", "application/json; charset=UTF8");
		request.setEntity(se);

		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse httpResponse = client.execute(request);

		String retSrc = EntityUtils.toString(httpResponse.getEntity());
		log.info("response list:" + retSrc);
		JSONObject result = new JSONObject(retSrc);

		List<String> l = new ArrayList<String>();
		
		JSONObject list =   result.getJSONObject("list");
		Iterator it = list.keys();
		while (it.hasNext()) {
			String key = (String) it.next();
			JSONObject msg = list.getJSONObject(key);
			
			String titile =  msg.getString("given_title");
			l.add(titile);
			log.info("array:" + titile);
		}
		
		
		return l;
	}

	private void check() throws ClientProtocolException, JSONException,
			IOException {
		if (access_token == null) {
			PocketConstants.token = retriveToken();
			openWebPage(PocketConstants.token);
		}

	}

	public void dealWithCallBack() {
		try {
			String accessToken = retriveAccessToken(PocketConstants.token);
			PocketConstants.access_token = accessToken;
					

			Utils.log.info("Token is:" + accessToken);
	
			AccessTokenKeeper.writePocketAccessToken(Utils.context, accessToken);
			
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

		
		
	}

}
