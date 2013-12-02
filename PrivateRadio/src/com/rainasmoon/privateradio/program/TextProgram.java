package com.rainasmoon.privateradio.program;

import java.util.Locale;

import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;

import com.rainasmoon.privateradio.business.impl.TtsHandler;

public class TextProgram  implements  Program  {

	private String description;
	
	private String article;
	private TtsHandler ttsHandler;
	
	public TextProgram(String article) {
		this.article = article;
	}

	@Override
	public void play() {
		

	}

	@Override
	public void getDuration() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void stop() {
		ttsHandler.stop();
	}

	@Override
	public boolean isCompleted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void getDuriation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isText() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAudio() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isVedio() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public long iid() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
	
}
