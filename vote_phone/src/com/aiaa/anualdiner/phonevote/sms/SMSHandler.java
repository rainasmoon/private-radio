package com.aiaa.anualdiner.phonevote.sms;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.aiaa.anualdiner.phonevote.Result;

 

public class SMSHandler extends Handler{

    public static final String TAG = "SMSHandler";

    private Context mContext;   

    public SMSHandler(Context context)    {

       super();
       this.mContext = context;
    }   

    public void handleMessage(Message message)    {

    	if (Result.isPaused) {
    		return;
    	}
    	
		Log.i(TAG, "handleMessage: " + message);
       MessageItem item = (MessageItem) message.obj;
         
       if (item.getBody().toLowerCase().startsWith(Result.ADMIN)) {
          	item.setBody(item.getBody().replaceFirst(Result.ADMIN, "").trim());
         }
       
       if (item.getBody().toLowerCase().startsWith(Result.FILTER)) {
        	item.setBody(item.getBody().replaceFirst(Result.FILTER, "").trim());
       }       
       
       if (message.what != 2 ) {
    	   Result.exec_msg(item, mContext);
       }
       else {
    	   Result.exec_admin_msg(item);
       }
       
       Uri uri = ContentUris.withAppendedId(SMS.CONTENT_URI, item.getId());
       ContentValues values=new ContentValues();  
       values.put(SMS.READ, SMS.MESSAGE_READ);
       mContext.getContentResolver().update(uri, values, null, null);
    }

}