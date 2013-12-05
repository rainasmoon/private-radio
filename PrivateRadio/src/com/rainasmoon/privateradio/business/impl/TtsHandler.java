package com.rainasmoon.privateradio.business.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class TtsHandler implements OnInitListener, OnUtteranceCompletedListener {

	public static final int REQ_CHECK_TTS_DATA = 110; // TTS数据校验请求值

	private TextToSpeech mSpeech;
	private Context context;

	private PlayHandlerImpl playHandlerImpl;

	private String article;

	private boolean STATUS_READY;
	private static TtsHandler ttsHandler;
	
	private boolean isOnFocused;

	private TtsHandler(PlayHandlerImpl playHandlerImpl) {
		context = Utils.context;
		this.playHandlerImpl = playHandlerImpl;
	}

	public static TtsHandler instanceTtsHandler(PlayHandlerImpl playHandlerImpl) {
		if (ttsHandler == null) {
			ttsHandler = new TtsHandler(playHandlerImpl);
		}
		return ttsHandler;
	}

	public static void checkTts(int resultCode) {
		switch (resultCode) {
		case TextToSpeech.Engine.CHECK_VOICE_DATA_PASS: // TTS引擎可用

			break;
		case TextToSpeech.Engine.CHECK_VOICE_DATA_BAD_DATA: // 数据错误
		case TextToSpeech.Engine.CHECK_VOICE_DATA_MISSING_DATA: // 缺失数据资源
		case TextToSpeech.Engine.CHECK_VOICE_DATA_MISSING_VOLUME: // 缺少数据存储量
			notifyReinstallDialog(); // 提示用户是否重装TTS引擎数据的对话框
			break;
		case TextToSpeech.Engine.CHECK_VOICE_DATA_FAIL: // 检查失败
		default:
			break;
		}
	}

	private void initTTS() throws Exception {

		mSpeech = new TextToSpeech(context, this);

	}

	private void playSilence() {

		mSpeech = new TextToSpeech(context, new OnInitListener() {
			@Override
			public void onInit(int status) {
				if (status == TextToSpeech.SUCCESS) {
					int result = mSpeech.setLanguage(Locale.CHINESE);

					if (result == TextToSpeech.LANG_MISSING_DATA
							|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
						Utils.log.info("not use");
					} else {

						long durationInMs = 5000;
						mSpeech.playSilence(durationInMs,
								TextToSpeech.QUEUE_FLUSH, null);
					}
				}
			}
		});

	}
	
	
	public void playTest() {

		mSpeech = new TextToSpeech(context, new OnInitListener() {
			@Override
			public void onInit(int status) {
				if (status == TextToSpeech.SUCCESS) {
					int result = mSpeech.setLanguage(Locale.CHINESE);

					if (result == TextToSpeech.LANG_MISSING_DATA
							|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
						Utils.log.info("not use");
					} else {

						mSpeech.speak("这是第一个节目", TextToSpeech.QUEUE_ADD, null);
						long durationInMs = 3000;
						mSpeech.playSilence(durationInMs,
								TextToSpeech.QUEUE_ADD, null);
						
						mSpeech.speak("这是第2个节目", TextToSpeech.QUEUE_ADD, null);
						mSpeech.stop();
						mSpeech.shutdown();
						mSpeech.playSilence(durationInMs,
								TextToSpeech.QUEUE_ADD, null);
						mSpeech.speak("这是第3个节目", TextToSpeech.QUEUE_ADD, null);
						mSpeech.playSilence(durationInMs,
								TextToSpeech.QUEUE_ADD, null);
						
						//QUEUE_FLASH will remove all the pre text.
						mSpeech.speak("这是第4个节目", TextToSpeech.QUEUE_ADD, null);
					}
				}
			}
		});

	}

	/** 提示用户是否重装TTS引擎数据的对话框 */
	private static void notifyReinstallDialog() {
		new AlertDialog.Builder(Utils.activity).setTitle("TTS引擎数据错误")
				.setMessage("是否尝试重装TTS引擎数据到设备上？")
				.setPositiveButton("是", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 触发引擎在TTS引擎在设备上安装资源文件
						Intent dataIntent = new Intent();
						dataIntent
								.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
						Utils.activity.startActivity(dataIntent);
					}
				}).setNegativeButton("否", null).show();
	}

	/** 校验TTS引擎安装及资源状态 */
	public static boolean checkTtsData() {
		try {
			Intent checkIntent = new Intent();
			checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
			Utils.activity.startActivityForResult(checkIntent,
					REQ_CHECK_TTS_DATA);
			return true;
		} catch (ActivityNotFoundException e) {
			return false;
		}
	}

	private void stop() {
		if (mSpeech != null) {
			Utils.log.info("mSpeech is not null and try to stop...");
			mSpeech.stop();
			mSpeech.shutdown();
			STATUS_READY = false;
			mSpeech = null;
		}
	}

	public void speak(String article) throws Exception {

		this.article = article;
		
		AudioManager audioManager = (AudioManager) Utils.activity
				.getSystemService(Context.AUDIO_SERVICE);
		OnAudioFocusChangeListener ac = new AudioManager.OnAudioFocusChangeListener() {

			@Override
			public void onAudioFocusChange(int focusChange) {
				Utils.log.info("TTS focus changed..." + focusChange);
				if (mSpeech != null && mSpeech.isSpeaking()) {
//					stop();

					mSpeech.stop();
				}
				
			}

		};
		int result = audioManager.requestAudioFocus(ac,
				AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

		if (result != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
			throw new Exception("can't get Audio Focus.");
		}

		
		if (mSpeech == null || !STATUS_READY) {

			// TODO init it?

			Utils.log.info("TTS is null");
			try {
				initTTS();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			Utils.log.info("directly say...");
			say();
		}
			
		Utils.log.info("end say...");
	}

	private void say() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "0");
		mSpeech.speak(article, TextToSpeech.QUEUE_FLUSH, params);
	}
	
	@Override
	public void onUtteranceCompleted(String utteranceId) {

		Utils.activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {

//				stop();
				mSpeech.stop();
				Utils.log.info("TTS plays end." + article);
				Toast.makeText(Utils.context, "is finished...",
						Toast.LENGTH_SHORT).show();

				playHandlerImpl.next();

			}
		});

	}

	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {

			mSpeech.setOnUtteranceCompletedListener(TtsHandler.this);

			int result = mSpeech.setLanguage(Locale.CHINESE);

			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Utils.log.info("not use");
			} else {
				Utils.log.info("speak begin...");
				STATUS_READY = true;
				say();
			}

		}
		
	}
}
