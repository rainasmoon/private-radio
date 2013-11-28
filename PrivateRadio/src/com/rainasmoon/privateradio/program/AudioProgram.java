package com.rainasmoon.privateradio.program;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.net.Uri;

import com.rainasmoon.privateradio.business.impl.MediaPlayHandler;
import com.rainasmoon.privateradio.utils.Utils;

public class AudioProgram implements Program  {

	private long id;
	private Uri uri;
	private File file;
	
	public AudioProgram(long id) {
		this.id = id;
	}
	
	public AudioProgram(Uri uri) {
		this.uri = uri;
	}
	
	public AudioProgram(File f) {
		file = f;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCompleted() {
		// TODO Auto-generated method stub
		return false;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Uri getUri() {
		return uri;
	}

	public void setUri(Uri uri) {
		this.uri = uri;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	@Override
	public void getDuriation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isText() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAudio() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isVedio() {
		// TODO Auto-generated method stub
		return false;
	}

}
