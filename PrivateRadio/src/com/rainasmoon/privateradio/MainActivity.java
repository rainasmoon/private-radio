package com.rainasmoon.privateradio;

import java.io.File;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rainasmoon.privateradio.business.impl.PrivateRadio;
import com.rainasmoon.privateradio.sourcechanel.localmedia.SetFileFolder;
import com.rainasmoon.privateradio.ui.utils.SystemUiHider;
import com.rainasmoon.privateradio.utils.Utils;

public class MainActivity extends Activity implements TextToSpeech.OnUtteranceCompletedListener  {

	public static final String TAG = "wh";

	private PrivateRadio radio;

	private TextToSpeech mTTS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork() // or
																		// .detectAll()
																		// for
																		// all
																		// detectable
																		// problems
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects() // 探测SQLite数据库操作
				.penaltyLog() // 打印logcat
				.penaltyDeath().build());

		super.onCreate(savedInstanceState);

		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		setContentView(R.layout.activity_main);

		Utils.init(this);

	}

	@Override
	public void onUtteranceCompleted(final String utteranceId) {
		/* 测试该接口的Toast提示 */
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				Toast.makeText(getApplicationContext(),
						"is finished...", Toast.LENGTH_SHORT)
						.show();
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	// deal with the ear-phone unpluged...
	private class NoisyAudioStreamReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent
					.getAction())) {
				// Pause the playback
			}
		}
	}

	public void startRadio(View view) {

		Log.i(TAG, "play...");

		radio = new PrivateRadio();
		radio.play();

	}

	public void stopRadio(View view) {

		Log.i(TAG, "stop...");
		radio.stop();

	}

	public void likeIt(View view) {

		Log.i(TAG, "likt it...");

	}

	public void unlikeIt(View view) {

		Log.i(TAG, "unlike it...");

		radio.next();

	}

	private static final int REQ_TTS_STATUS_CHECK = 0;
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Check which request it is that we're responding to
		if (requestCode == SetFileFolder.PICK_FILE_REQUEST) {
			// Make sure the request was successful
			if (resultCode == RESULT_OK) {
				// Get the URI that points to the selected contact
				Uri contactUri = data.getData();
				// We only need the NUMBER column, because there will be only
				// one row in the result
				Bundle b = data.getExtras();

				Log.i(TAG, "number:" + contactUri);
				Log.i(TAG, "number:" + b.getString("filename"));
				File f = new File(b.getString("filename"));
				Uri uri = Uri.fromFile(f);
				// Do something with the phone number...
				com.rainasmoon.privateradio.sourcechanel.localmedia.Constants.MY_PICK_SONG_URI = uri;

			}
		} else if (requestCode == REQ_TTS_STATUS_CHECK) {
			switch (resultCode) {
			case TextToSpeech.Engine.CHECK_VOICE_DATA_PASS:			
				pass();
				break;
			case TextToSpeech.Engine.CHECK_VOICE_DATA_BAD_DATA:
				// 需要的语音数据已损坏
			case TextToSpeech.Engine.CHECK_VOICE_DATA_MISSING_DATA:
				// 缺少需要语言的语音数据
			case TextToSpeech.Engine.CHECK_VOICE_DATA_MISSING_VOLUME:
			// 缺少需要语言的发音数据
			{
				// 这三种情况都表明数据有错,重新下载安装需要的数据
				Log.v(TAG, "Need language stuff:" + resultCode);
				Intent dataIntent = new Intent();
				dataIntent
						.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(dataIntent);
			}
				break;
			case TextToSpeech.Engine.CHECK_VOICE_DATA_FAIL:
				// 检查失败
			default:
				Log.v(TAG, "Got a failure. TTS apparently not available");
				break;
			}
		} else {
			// 其他Intent返回的结果
		}
	}

	private void pass() {
		// 这个返回结果表明TTS Engine可以用
					
						// 使用的是TextToSpeechBeta
						mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
							
							@Override
							public void onInit(int status) {
								 // TODO Auto-generated method stub
				                // 判断TTS初始化的返回版本号，如果为-1，表示没有安装对应的TTS数据
				               
				                        // 提示安装所需的TTS数据
				                        
				                
				                        if (status == TextToSpeech.SUCCESS) {
				                                Log.v(TAG, "success to init tts");
				                                // 设置TTS引擎，com.google.tts即eSpeak支持的语言包含中文，使用Android系统默认的pico可以设置为com.google.tts
				                                mTTS.setEngineByPackageName("com.google.tts");
				                                int result = mTTS.setLanguage(Locale.CHINA);
				                                // 设置发音语言
				                                if (result == TextToSpeech.LANG_MISSING_DATA
				                                                || result == TextToSpeech.LANG_NOT_SUPPORTED)
				                                // 判断语言是否可用
				                                {
				                                        Log.v(TAG, "Language is not available");
//				                                        mBtn.setEnabled(false);
				                                } else {
				                                        mTTS.speak(" ", TextToSpeech.QUEUE_ADD, null);
//				                                        mBtn.setEnabled(true);
				                                }
				                        } else {
				                        	alertInstallEyesFreeTTSData();
				                                Log.v(TAG, "failed to init tts");
				                        }
				                }
								
							
						});
						Log.v(TAG, "TTS Engine is installed!");

					
		
	}

	// 弹出对话框提示安装所需的TTS数据
	private void alertInstallEyesFreeTTSData() {
		Builder alertInstall = new AlertDialog.Builder(this)
				.setTitle("缺少需要的语音包")
				.setMessage("下载安装缺少的语音包")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						// 下载eyes-free的语音数据包
						String ttsDataUrl = "http://eyes-free.googlecode.com/files/tts_3.1_market.apk";
						Uri ttsDataUri = Uri.parse(ttsDataUrl);
						Intent ttsIntent = new Intent(Intent.ACTION_VIEW,
								ttsDataUri);
						startActivity(ttsIntent);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();
					}
				});
		alertInstall.create().show();

	}

}
