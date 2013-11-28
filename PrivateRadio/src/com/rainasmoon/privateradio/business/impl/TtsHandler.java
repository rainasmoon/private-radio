package com.rainasmoon.privateradio.business.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.speech.tts.UtteranceProgressListener;
import android.widget.Toast;

import com.rainasmoon.privateradio.business.PlayHandlerImpl;
import com.rainasmoon.privateradio.program.AudioProgram;
import com.rainasmoon.privateradio.program.Program;
import com.rainasmoon.privateradio.program.TextProgram;
import com.rainasmoon.privateradio.utils.Utils;

public class TtsHandler {
	


	private TextToSpeech mSpeech;
	private Context context;
	
	private PlayHandlerImpl playHandlerImpl;


	public TtsHandler(PlayHandlerImpl playHandlerImpl) {
		context = Utils.context;
		this.playHandlerImpl = playHandlerImpl;
	}

	public void speakChineseTts(final String str) throws Exception {

		AudioManager audioManager = (AudioManager) Utils.activity
				.getSystemService(Context.AUDIO_SERVICE);
		OnAudioFocusChangeListener ac = new AudioManager.OnAudioFocusChangeListener() {

			@Override
			public void onAudioFocusChange(int focusChange) {

				// stop();

			}

		};
		int result = audioManager.requestAudioFocus(ac,
				AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

		if (result != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
			throw new Exception("can't get Audio Focus.");
		}

		mSpeech = new TextToSpeech(context, new OnInitListener() {
			@Override
			public void onInit(int status) {
				if (status == TextToSpeech.SUCCESS) {
					// Utils.log.info("the mas is :" +
					// mSpeech.getMaxSpeechInputLength());
					UtteranceProgressListener list = new UtteranceProgressListener() {

						@Override
						public void onDone(String arg0) {
							Utils.log.info("on Done is reached...");
							
							Utils.activity.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									
									Toast.makeText(Utils.context,
											"fuck is finished...", Toast.LENGTH_SHORT)
											.show();
								}
							});

						}

						@Override
						public void onError(String arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onStart(String arg0) {
							Utils.log.info("on start is reached...");
							
							Utils.activity.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									
									Toast.makeText(Utils.context,
											"fuch is begin...", Toast.LENGTH_SHORT)
											.show();
								}
							});

						}
					};
					mSpeech.setOnUtteranceProgressListener(list);
					
					mSpeech.setOnUtteranceCompletedListener(new OnUtteranceCompletedListener() {
						
						@Override
						public void onUtteranceCompleted(String utteranceId) {
							Utils.activity.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									
									
									
									Toast.makeText(Utils.context,
											"is finished...", Toast.LENGTH_SHORT)
											.show();
									
									playHandlerImpl.next();
									
								}
							});
							
							
						}
					});
					
					int result = mSpeech.setLanguage(Locale.CHINESE);

					if (result == TextToSpeech.LANG_MISSING_DATA
							|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
						Utils.log.info("not use");
					} else {
						Utils.log.info("speak begin...");
						
						HashMap<String, String> params = new HashMap<String, String>();
						params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "0");
						mSpeech.speak(str, TextToSpeech.QUEUE_FLUSH, params);
						
					}

				}
			}
		});

	}

	public void playSilence() {

		mSpeech = new TextToSpeech(context, new OnInitListener() {
			@Override
			public void onInit(int status) {
				if (status == TextToSpeech.SUCCESS) {
					int result = mSpeech.setLanguage(Locale.CHINESE);

					if (result == TextToSpeech.LANG_MISSING_DATA
							|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
						Utils.log.info("not use");
					} else {

						long durationInMs = 1;
						mSpeech.playSilence(durationInMs,
								TextToSpeech.QUEUE_FLUSH, null);
					}
				}
			}
		});

	}

	public void stop() {
		mSpeech.stop();
		mSpeech.shutdown();
	}



	public void speak(String article) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "0");
		mSpeech.speak(article, TextToSpeech.QUEUE_FLUSH, params);
		
	}
}
