package com.rainasmoon.privateradio.program;

import java.util.Locale;

import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;

import com.rainasmoon.privateradio.business.Play;
import com.rainasmoon.privateradio.business.TtsHandler;

public class TextProgram  extends Program implements Play {

	private String article;
	
	public TextProgram(String article) {
		this.article = article;
	}

	@Override
	public void play() {
		TtsHandler ttsHandler = new TtsHandler();
		
		try {
			ttsHandler.speakChineseTts(article);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void getDuration() {
		// TODO Auto-generated method stub

	}

	
	
	
}
