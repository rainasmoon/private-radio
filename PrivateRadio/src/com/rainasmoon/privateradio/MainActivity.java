package com.rainasmoon.privateradio;

import java.io.File;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
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
import android.widget.Toast;

import com.rainasmoon.privateradio.business.impl.PrivateRadio;
import com.rainasmoon.privateradio.business.impl.TtsHandler;
import com.rainasmoon.privateradio.sourcechanel.localmedia.SetFileFolder;
import com.rainasmoon.privateradio.utils.AccessTokenKeeper;
import com.rainasmoon.privateradio.utils.Utils;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

public class MainActivity extends Activity {

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
				if (radio != null) {
					radio.stop();
				}
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
		
		Toast.makeText(getApplicationContext(),
				"功能还在开发中哦...亲", Toast.LENGTH_SHORT)
				.show();

	}

	public void likeIt(View view) {

		Log.i(TAG, "likt it...");
		TtsHandler.checkTtsData();				
		Toast.makeText(getApplicationContext(),
				"功能还在开发中哦...亲", Toast.LENGTH_SHORT)
				.show();

	}

	public void unlikeIt(View view) {

		Log.i(TAG, "unlike it...");
		radio.next();

	}


	
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
				com.rainasmoon.privateradio.sourcechanel.localmedia.LocalMediaConstants.MY_PICK_SONG_URI = uri;

			}
		} 
		else if (requestCode == TtsHandler.REQ_CHECK_TTS_DATA) {
			TtsHandler.checkTts(resultCode);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	

	

}
