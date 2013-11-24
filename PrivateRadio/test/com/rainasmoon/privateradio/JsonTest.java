package com.rainasmoon.privateradio;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import android.util.Log;

import com.rainasmoon.privateradio.MainActivity;


public class JsonTest {

	@Test
	public void shouldTest() throws ClientProtocolException, JSONException, IOException {
		System.out.println("what's that?");
		retriveJOSN();
	}
	@Test
	public void shouldFunny() {
		
	}
	
	private void retriveJOSN() throws JSONException, ClientProtocolException, IOException {
		System.out.println("yes.");
		String url = "https://getpocket.com/v3/get";
		String url_token = "https://getpocket.com/v3/oauth/request";
	    HttpPost request = new HttpPost(url_token);  
	    // 先封装一个 JSON 对象  
	    JSONObject param = new JSONObject();  
	    param.put("consumer_key", "20568-8a201d7aa60e8279853d7ca6");  
	    param.put("redirect_uri", "pocketapp20568:authorizationFinished");  
	    // 绑定到请求 Entry  
	    StringEntity se = new StringEntity(param.toString());   
	    request.setEntity(se);  
	    // 发送请求  
	    HttpResponse httpResponse = new DefaultHttpClient().execute(request);  
	    // 得到应答的字符串，这也是一个 JSON 格式保存的数据  
	    String retSrc = EntityUtils.toString(httpResponse.getEntity());  
	    // 生成 JSON 对象  
	    JSONObject result = new JSONObject( retSrc);  
	    String token = (String) result.get("code");  
	    
	    System.out.println("token:" + token);
	}
}
