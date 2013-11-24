package com.aiaa.anualdiner.phonevote.sms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aiaa.anualdiner.phonevote.Result;

import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.util.Log;


public class SMSService {

	 // android获取短信所有内容 
 	// 注意设置权限[添加到AndroidMainfest.xml]   <uses-permission android:name="android.permission.READ_SMS" />  
	
 	public List<MessageItem> getSmsInPhone(ContentResolver cr, String hour)    
 	    {     	            
 	        List<MessageItem> smsBuilder = new ArrayList<MessageItem>();    
 	            
 	        try{     	               
 	            String[] projection = new String[]{"_id", "address", "person",     
 	                    "body", "date", "type"};    
 	             
 	            Cursor cur = cr.query(SMS.CONTENT_URI, projection, null, null, "date desc");    
 	       
 	            if (cur.moveToFirst()) {    
 	                String name;     
 	                String phoneNumber;           
 	                String smsbody;    
 	                Date date;    
 	                String type;    
 	                 
 	                int nameColumn = cur.getColumnIndex("person");    
 	                int phoneNumberColumn = cur.getColumnIndex("address");    
 	                int smsbodyColumn = cur.getColumnIndex("body");    
 	                int dateColumn = cur.getColumnIndex("date");    
 	                int typeColumn = cur.getColumnIndex("type");    
 	                 
 	                do{    
 	                    name = cur.getString(nameColumn);                 
 	                    phoneNumber = cur.getString(phoneNumberColumn);    
 	                    smsbody = cur.getString(smsbodyColumn);    
 	                        
 	                    SimpleDateFormat dateFormat = new SimpleDateFormat(    
 	                            "yyyy-MM-dd hh:mm:ss");    
 	                    date = new Date(Long.parseLong(cur.getString(dateColumn)));    
 	                    
 	                    Date baseDate = new Date();
 	                    String today = new SimpleDateFormat("yyyy-MM-dd").format(baseDate);
 	                    try {
							baseDate = new SimpleDateFormat("yyyy-MM-dd hh").parse(today + " " + hour);
						} catch (ParseException e) {
							Log.w("wh:", "paresing error:");
							e.printStackTrace();
						}
 	                    
 	                    if (date.before(baseDate)) {
 	                    	continue;
 	                    }
 	              	                        
 	                    int typeId = cur.getInt(typeColumn);    
 	                    
 	                   if(smsbody == null) smsbody = "";    
 	                   
 	                    if (smsbody.toLowerCase().startsWith(Result.FILTER)) {
 	                    	smsbody = smsbody.replaceFirst(Result.FILTER, "").trim();
 	                    	smsBuilder.add(new MessageItem(name, phoneNumber,smsbody, date, typeId)); 
 	                    }
 	                    
 	                   if (smsbody.toLowerCase().startsWith(Result.ADMIN)) {
	                    	smsbody = smsbody.replaceFirst(Result.ADMIN, "").trim();
	                    	smsBuilder.add(new MessageItem(name, phoneNumber,smsbody, date, typeId)); 
	                    }
 	                     
 	                      
 	                }while(cur.moveToNext());    
 	            }    
 	                  
 	        } catch(SQLiteException ex) {    
 	            Log.d("SQLiteException in getSmsInPhone", ex.getMessage());    
 	        }    
 	        return smsBuilder;    
 	    }
}
