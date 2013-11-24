package com.aiaa.anualdinaer.sms;



import java.util.List;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.aiaa.anualdinaer.phoneanswer.Result;
import android.telephony.SmsManager;

public class SMSSendHandler extends Handler{

    public static final String TAG = "SMSSendHandler";

    private SmsManager manager;
    
    public SMSSendHandler()    {
       super();
       manager = SmsManager.getDefault();
    }   

    public void handleMessage(Message message)    {

		Log.i(TAG, "handleMessage: " + message);
        ResponseMessage response = (ResponseMessage)message.obj;
        
		sendMsg(response.getPhone(), response.getResponseMsg());
    }
    
    private void sendMsg(String phone, String content) {
        Log.d(TAG, "send MSG:" + phone + ":" + content);
        List<String> messages=manager.divideMessage(content);        
        for(String ms:messages){           
           manager.sendTextMessage(phone, null, ms, null, null);    
        }       
    }

}