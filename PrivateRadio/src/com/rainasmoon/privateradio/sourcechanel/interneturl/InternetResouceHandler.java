package com.rainasmoon.privateradio.sourcechanel.interneturl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.rainasmoon.privateradio.business.impl.MediaPlayHandler;
import com.rainasmoon.privateradio.utils.Utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;

public class InternetResouceHandler {

	
	public boolean isWiFiActive() {    
        ConnectivityManager connectivity = (ConnectivityManager)Utils.activity.getSystemService(Context.CONNECTIVITY_SERVICE);    
        if (connectivity != null) {    
            NetworkInfo[] infos = connectivity.getAllNetworkInfo();    
            if (infos != null) {    
            	for(NetworkInfo ni : infos){
            		if(ni.getTypeName().equals("WIFI") && ni.isConnected()){
            			return true;
            		}
            	}
            }    
        }    
        return false;    
    } 
	
	public List<String> getInternetMedia() throws Exception {
		String url = "http://music.baidu.com/data/music/file?link=http://zhangmenshiting.baidu.com/data2/music/39483780/20606561385103661128.mp3?xcode=206eac36e7cfc3802a5e4fc3a704eef150d75764209da2b1&song_id=2060656"; // your
		String url2 = "http://124.167.232.77/ws.cs.hotchanson.com/m4a_96_0/e7/0d/e7845637ca86f72a25844c6fdbed1f0d.m4a?k=b37bdab921defcb9&t=1385587404&wsiphost=local";
		String  url3 = "http://open.migu.cn:8100/product/music/3/999989/2013/08/15/7e9cc65099432a704cc1e08324432a95_3.mp3?m";
		WifiLock wifiLock = ((WifiManager) Utils.activity.getSystemService(Context.WIFI_SERVICE))
			    .createWifiLock(WifiManager.WIFI_MODE_FULL, "mylock");

			wifiLock.acquire();																		// here

		List<String> l = new ArrayList<String> ();
		if (isWiFiActive()) {
			l.add(url);
			l.add(url2);
			l.add(url3);
		}
		wifiLock.release();
		
		return l;
	}
}
