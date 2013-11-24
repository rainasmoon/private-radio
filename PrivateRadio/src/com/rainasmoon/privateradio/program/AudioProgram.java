package com.rainasmoon.privateradio.program;

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
	
	public AudioProgram(long id) {
		this.id = id;
	}
	
	public AudioProgram(Uri uri) {
		this.uri = uri;
	}
	
	@Override
	public void play() {
		// TODO Auto-generated method stub

		
		
		final MediaPlayHandler handler = new MediaPlayHandler();
		
		
		try {
			if (id != 0) {
				handler.playBanchOfLocalMedia(id);
			}
			if (uri != null) {
				handler.playUriSong(uri);
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
