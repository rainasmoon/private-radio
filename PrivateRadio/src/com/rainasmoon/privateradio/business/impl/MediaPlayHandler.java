package com.rainasmoon.privateradio.business.impl;

import java.io.IOException;
import java.util.List;

import com.rainasmoon.privateradio.business.PlayHandlerImpl;
import com.rainasmoon.privateradio.program.AudioProgram;
import com.rainasmoon.privateradio.program.Program;
import com.rainasmoon.privateradio.utils.Utils;

import android.content.ContentUris;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.PowerManager;
import android.view.View;

public class MediaPlayHandler {

	private MediaPlayer mediaPlayer;
	private Context context;
	private PlayHandlerImpl playHandlerImpl;

	private static MediaPlayHandler mediaPlayHandler;

	private MediaPlayHandler(PlayHandlerImpl playHandlerImpl) {
		context = Utils.context;
		this.playHandlerImpl = playHandlerImpl;
	}

	public static MediaPlayHandler instanceMediaPlayHandler(
			PlayHandlerImpl playHandlerImpl) {
		if (mediaPlayHandler == null) {
			mediaPlayHandler = new MediaPlayHandler(playHandlerImpl);
		}

		return mediaPlayHandler;
	}

	public void init() {

		AudioManager audioManager = (AudioManager) Utils.activity
				.getSystemService(Context.AUDIO_SERVICE);
		OnAudioFocusChangeListener ac = new AudioManager.OnAudioFocusChangeListener() {

			@Override
			public void onAudioFocusChange(int focusChange) {
//				 switch (focusChange) {
//			        case AudioManager.AUDIOFOCUS_GAIN:
//			            // resume playback
//			            if (mMediaPlayer == null) initMediaPlayer();
//			            else if (!mMediaPlayer.isPlaying()) mMediaPlayer.start();
//			            mMediaPlayer.setVolume(1.0f, 1.0f);
//			            break;
//
//			        case AudioManager.AUDIOFOCUS_LOSS:
//			            // Lost focus for an unbounded amount of time: stop playback and release media player
//			            if (mMediaPlayer.isPlaying()) mMediaPlayer.stop();
//			            mMediaPlayer.release();
//			            mMediaPlayer = null;
//			            break;
//
//			        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
//			            // Lost focus for a short time, but we have to stop
//			            // playback. We don't release the media player because playback
//			            // is likely to resume
//			            if (mMediaPlayer.isPlaying()) mMediaPlayer.pause();
//			            break;
//
//			        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
//			            // Lost focus for a short time, but it's ok to keep playing
//			            // at an attenuated level
//			            if (mMediaPlayer.isPlaying()) mMediaPlayer.setVolume(0.1f, 0.1f);
//			            break;
//			    }

				stop();

			}

		};
		int result = audioManager.requestAudioFocus(ac,
				AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

		if (result != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
			throw new RuntimeException("can't get Audio Focus.");
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
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				playHandlerImpl.next();

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
		if (mediaPlayer == null) {
			init();
		} else {
			mediaPlayer.reset();
		}

		setSource(id);
		start();
	}

	public void playUrlMedia(String url) throws Exception {
		if (mediaPlayer == null) {
			init();
		} else {
			mediaPlayer.reset();
		}

		setSource(url);
		start();
	}

	public void playFileMedia(AssetFileDescriptor afd) throws Exception {
		if (mediaPlayer == null) {
			init();
		} else {
			mediaPlayer.reset();
		}

		mediaPlayer.setDataSource(afd.getFileDescriptor(),
				afd.getStartOffset(), afd.getLength());
		start();
	}

	public void playUriSong(Uri uri) throws Exception {

		if (mediaPlayer == null) {
			init();
		} else {
			mediaPlayer.reset();
		}

		setSource(uri);
		start();
	}

	public void reset() {
		mediaPlayer.reset();

	}

	public void playProgram(Program p) {
		if (((AudioProgram) p).getFile() != null) {
			Uri u = Uri.fromFile(((AudioProgram) p).getFile());
			try {
				playUriSong(u);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// try {
			// AssetFileDescriptor afd =
			// Utils.activity.getAssets().openFd(((AudioProgram)
			// p).getFile().getAbsolutePath());
			// playFileMedia(afd);
			// } catch (Exception e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			//

		} else if (((AudioProgram) p).getId() != null) {

			try {
				playBanchOfLocalMedia(((AudioProgram) p).getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (((AudioProgram) p).getUrl() != null) {
			try {
				playUrlMedia(((AudioProgram) p).getUrl());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
