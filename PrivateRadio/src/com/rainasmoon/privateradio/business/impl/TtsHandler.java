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

public class TtsHandler {
	
	public static final int REQ_CHECK_TTS_DATA = 110; // TTS数据校验请求值

	private TextToSpeech mSpeech;
	private Context context;
	
	private PlayHandlerImpl playHandlerImpl;


	public TtsHandler(PlayHandlerImpl playHandlerImpl) {
		context = Utils.context;
		this.playHandlerImpl = playHandlerImpl;
	}
	
	public static void checkTts(int resultCode) {
		switch (resultCode) {
		case TextToSpeech.Engine.CHECK_VOICE_DATA_PASS: // TTS引擎可用
			// 针对于重新绑定引擎，需要先shutdown()
//			if (null != mTts) {
//				ttsStop(); // 停止当前发声
//				ttsShutDown(); // 释放资源
//			}
//			mTts = new TextToSpeech(this, this); // 创建TextToSpeech对象
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
			Utils.activity.startActivityForResult(checkIntent, REQ_CHECK_TTS_DATA);
			return true;
		} catch (ActivityNotFoundException e) {
			return false;
		}
	}
	

	
	public void stop() {
		mSpeech.stop();
		mSpeech.shutdown();
	}



	public void speak(String article) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "0");
		if (mSpeech == null) {
			
//			TODO init it?
			
			Utils.log.info("TTS is null");
			try {
				speakChineseTts(article);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		mSpeech.speak(article, TextToSpeech.QUEUE_FLUSH, params);
		
	}
}
