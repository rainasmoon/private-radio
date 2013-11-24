package com.rainasmoon.privateradio.sourcechanel.pocket;

import java.io.IOException;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rainasmoon.privateradio.utils.Utils;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class PocketHandler extends Constants {

	java.util.logging.Logger log = java.util.logging.Logger.getLogger("wh:");

	private String retriveToken() throws JSONException,
			ClientProtocolException, IOException {
		System.out.println("yes.");

		HttpPost request = new HttpPost(Constants.URL_TOKEN);
		// 先封装一个 JSON 对象
		JSONObject param = new JSONObject();
		param.put("consumer_key", Constants.CONSUMER_KEY);
		param.put("redirect_uri", Constants.REDIRECT_URI);
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

		HttpPost request = new HttpPost(Constants.URL_ACCESS_TOKEN);
		// 先封装一个 JSON 对象
		JSONObject param = new JSONObject();
		param.put("consumer_key", Constants.CONSUMER_KEY);
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

	public String retrivePocketList() {
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
		return "你应该能查询出一个字符串来,为什么那个字希找不到呢....";

	}

	public String retrivePoketListRaw() throws JSONException,
			ClientProtocolException, IOException {
		check();

		HttpPost request = new HttpPost(Constants.URL_GET);

		JSONObject param = new JSONObject();
		param.put("consumer_key", Constants.CONSUMER_KEY);
		param.put("access_token", access_token);
		param.put("count", 1);
		StringEntity se = new StringEntity(param.toString());

		request.addHeader("X-Accept", "application/json");
		request.addHeader("Content-Type", "application/json; charset=UTF8");
		request.setEntity(se);

		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse httpResponse = client.execute(request);

		String retSrc = EntityUtils.toString(httpResponse.getEntity());
		log.info("response list:" + retSrc);
		JSONObject result = new JSONObject(retSrc);
		//TODO: deal with the result.
		
		//TODO: foucs on 
		JSONObject list =   result.getJSONObject("list");
		Iterator it = list.keys();
		String key = (String) it.next();
		JSONObject msg = list.getJSONObject(key);
		
		String titile =  msg.getString("given_title");
		
		log.info("array:" + titile);
		
		
		return titile;
	}

	private void check() throws ClientProtocolException, JSONException,
			IOException {
		if (access_token == null) {
			Constants.token = retriveToken();
			openWebPage(Constants.token);
		}

	}

	public void dealWithCallBack() {
		try {
			String accessToken = retriveAccessToken(Constants.token);
			Constants.access_token = accessToken;
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
