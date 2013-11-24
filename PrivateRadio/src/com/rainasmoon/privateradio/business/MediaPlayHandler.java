package com.rainasmoon.privateradio.business;

import java.io.IOException;

import com.rainasmoon.privateradio.utils.Utils;

import android.content.ContentUris;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.net.Uri;
import android.os.PowerManager;
import android.view.View;

public class MediaPlayHandler {

	private MediaPlayer mediaPlayer;
	private Context context;

	public MediaPlayHandler(Context context) {
		this.context = context;

	}

	public MediaPlayHandler() {
		this(Utils.context);
	}

	public void init() throws Exception {

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

		mediaPlayer = new MediaPlayer();
		mediaPlayer.setWakeMode(Utils.context, PowerManager.PARTIAL_WAKE_LOCK);
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

		mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

			@Override
			public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
				// ... react appropriately ...
				// The MediaPlayer has moved to the Error state, must be reset!

				return false;
			}

		});
	}

	public void start() {

		mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer player) {
				mediaPlayer.start();
			}

		});

		mediaPlayer.prepareAsync();
	}

	public void stop() {
		mediaPlayer.stop();

		mediaPlayer.release();
		mediaPlayer = null;

	}

	public void setSource(long id) throws IllegalArgumentException,
			SecurityException, IllegalStateException, IOException {
		Uri contentUri = ContentUris.withAppendedId(
				android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				id);
		mediaPlayer.setDataSource(context, contentUri);
	}

	public void setSource(Uri uri) throws IllegalArgumentException,
			SecurityException, IllegalStateException, IOException {

		mediaPlayer.setDataSource(context, uri);
	}

	public void setSource(String url) throws IllegalArgumentException,
			SecurityException, IllegalStateException, IOException {

		mediaPlayer.setDataSource(url);
	}

	public void playBanchOfLocalMedia(long id) throws Exception {
		init();
		setSource(id);
		start();
	}

	public void playOneLocalSong() {
		try {
			getLocalMedia();
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

	public void getLocalMedia() throws Exception {
		Uri myUri = Uri.parse("content://media/external/audio/media/113685"); // initialize
		init();
		setSource(myUri);
		start();
	}

	public void playUriSong(Uri uri) throws Exception {

		init();
		setSource(uri);
		start();
	}

}
