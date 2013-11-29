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
			WifiLock wifiLock = ((WifiManager) Utils.activity.getSystemService(Context.WIFI_SERVICE))
			    .createWifiLock(WifiManager.WIFI_MODE_FULL, "mylock");

			wifiLock.acquire();																		// here

		List<String> l = new ArrayList<String> ();
		Utils.log.info("the wifi status:"  + isWiFiActive());
		if (isWiFiActive()) {
			for (String url : InternetConstants.song_urls) {
				l.add(url);
			}
		}
		wifiLock.release();
		
		return l;
	}
}
