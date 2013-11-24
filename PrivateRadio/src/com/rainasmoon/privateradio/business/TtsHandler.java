package com.rainasmoon.privateradio.business;

import java.util.Locale;

import com.rainasmoon.privateradio.utils.Utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;


public class TtsHandler {

	private TextToSpeech mSpeech;
	private Context context;
		
	public TtsHandler(Context context) {
		this.context = context;
	}
	
	public TtsHandler() {
		this(Utils.context);
	}
	
	
	
	public void speakTts() {
		 mSpeech = new TextToSpeech(context, new OnInitListener()   
	        {  
	            @Override  
	            public void onInit(int status)  
	            {  
	                if (status == TextToSpeech.SUCCESS)  
	                {  
	                    int result = mSpeech.setLanguage(Locale.ENGLISH);//设置只能朗读英文  
	                    if (result == TextToSpeech.LANG_MISSING_DATA|| result == TextToSpeech.LANG_NOT_SUPPORTED)  
	                    {//要是结果没值，就在后台打印出来  
	                        Utils.log.info("not use");  
	                    }   
	                    else   
	                    {//模拟机在启动时朗读下面的英文  
	                      
	                        mSpeech.speak("Hello World,Hello Android", TextToSpeech.QUEUE_FLUSH, null);  
	                    }  
	                }  
	            }  
	        });  
	}
	
	public void speakChineseTts(final String str) throws Exception {
		
		AudioManager audioManager = (AudioManager) Utils.activity
				.getSystemService(Context.AUDIO_SERVICE);
		OnAudioFocusChangeListener ac = new AudioManager.OnAudioFocusChangeListener() {

			@Override
			public void onAudioFocusChange(int focusChange) {

				stop();

			}

		};
		int result = audioManager.requestAudioFocus(ac,
				AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

		if (result != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
			throw new Exception("can't get Audio Focus.");
		}
		
		 mSpeech = new TextToSpeech(context, new OnInitListener()   
	        {  
	            @Override  
	            public void onInit(int status)  
	            {  
	                if (status == TextToSpeech.SUCCESS)  
	                {  
	                    int result = mSpeech.setLanguage(Locale.CHINESE); 
	                    
	                    if (result == TextToSpeech.LANG_MISSING_DATA|| result == TextToSpeech.LANG_NOT_SUPPORTED)  
	                    {
	                    	Utils.log.info("not use");  
	                    }   
	                    else   
	                    {
	                      
	                        mSpeech.speak(str, TextToSpeech.QUEUE_FLUSH, null);  
	                    }  
	                }  
	            }  
	        });  
	}
	
	public void playSilence() {
		
			
			 mSpeech = new TextToSpeech(context, new OnInitListener()   
		        {  
		            @Override  
		            public void onInit(int status)  
		            {  
		                if (status == TextToSpeech.SUCCESS)  
		                {  
		                    int result = mSpeech.setLanguage(Locale.CHINESE); 
		                    
		                    if (result == TextToSpeech.LANG_MISSING_DATA|| result == TextToSpeech.LANG_NOT_SUPPORTED)  
		                    {
		                    	Utils.log.info("not use");  
		                    }   
		                    else   
		                    {
		                      
		                        long durationInMs = 1;
								mSpeech.playSilence(durationInMs , TextToSpeech.QUEUE_FLUSH, null);
		                    }  
		                }  
		            }  
		        });  
		
	}
	
	private void stop() {
		mSpeech.stop();
		mSpeech.shutdown();
	}
}
