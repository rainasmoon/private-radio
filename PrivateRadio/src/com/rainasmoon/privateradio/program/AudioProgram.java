package com.rainasmoon.privateradio.program;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.net.Uri;

import com.rainasmoon.privateradio.business.MediaPlayHandler;
import com.rainasmoon.privateradio.business.Play;
import com.rainasmoon.privateradio.utils.Utils;

public class AudioProgram extends Program implements Play {

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
		
		
		final MediaPlayHandler handler = new MediaPlayHandler();
		
		
		try {
			if (id != 0) {
				handler.playBanchOfLocalMedia(id);
			}
			if (uri != null) {
				handler.playUriSong(uri);
			}
			if (file != null) {
				Uri u = Uri.fromFile(file);
				handler.playUriSong(u);
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
