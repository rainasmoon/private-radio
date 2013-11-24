package com.rainasmoon.privateradio;

import java.io.File;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;

import com.rainasmoon.privateradio.business.PrivateRadio;
import com.rainasmoon.privateradio.business.RemoteControlReceiver;
import com.rainasmoon.privateradio.sourcechanel.localmedia.SetFileFolder;
import com.rainasmoon.privateradio.sourcechanel.pocket.Constants;
import com.rainasmoon.privateradio.sourcechanel.pocket.PocketHandler;
import com.rainasmoon.privateradio.ui.utils.SystemUiHider;
import com.rainasmoon.privateradio.utils.Utils;

public class MainActivity extends Activity {
	
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = true;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;

	public static final String TAG = "wh";
	private Intent mRequestFileIntent;

	private View mDecorView;

	private PrivateRadio radio;
	
	
	

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

		// Get the intent that started this activity
		dealWithCallBack();

		mRequestFileIntent = new Intent(Intent.ACTION_PICK);
		mRequestFileIntent.setType("audio/mpeg");
		
//		final View controlsView = findViewById(R.id.fullscreen_content_controls);
//		final View contentView = findViewById(R.id.fullscreen_content);
//
//		View decorView = getWindow().getDecorView();
//		// Hide both the navigation bar and the status bar.
//		int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//		              | View.SYSTEM_UI_FLAG_FULLSCREEN;
//		decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		
		mDecorView = getWindow().getDecorView();
		hideSystemUI();
		
		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
//		mSystemUiHider = SystemUiHider.getInstance(this, contentView,
//				HIDER_FLAGS);
//		mSystemUiHider.setup();
//		mSystemUiHider
//				.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
//					// Cached values.
//					int mControlsHeight;
//					int mShortAnimTime;
//
//					@Override
//					@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//					public void onVisibilityChange(boolean visible) {
//						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//							// If the ViewPropertyAnimator API is available
//							// (Honeycomb MR2 and later), use it to animate the
//							// in-layout UI controls at the bottom of the
//							// screen.
//							if (mControlsHeight == 0) {
//								mControlsHeight = controlsView.getHeight();
//							}
//							if (mShortAnimTime == 0) {
//								mShortAnimTime = getResources().getInteger(
//										android.R.integer.config_shortAnimTime);
//							}
//							controlsView
//									.animate()
//									.translationY(visible ? 0 : mControlsHeight)
//									.setDuration(mShortAnimTime);
//						} else {
//							// If the ViewPropertyAnimator APIs aren't
//							// available, simply show or hide the in-layout UI
//							// controls.
//							controlsView.setVisibility(visible ? View.VISIBLE
//									: View.GONE);
//						}
//
//						if (visible && AUTO_HIDE) {
//							// Schedule a hide().
//							delayedHide(AUTO_HIDE_DELAY_MILLIS);
//						}
//					}
//				});
//
//		// Set up the user interaction to manually show or hide the system UI.
//		contentView.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
////				if (TOGGLE_ON_CLICK) {
////					mSystemUiHider.toggle();
////				} else {
////					mSystemUiHider.show();
////				}
//			}
//		});
//
//		mSystemUiHider.toggle();
//		mSystemUiHider.hide();
		
		// Upon interacting with UI controls, delay any scheduled hide()
		// operations to prevent the jarring behavior of controls going away
		// while interacting with the UI.
//		findViewById(R.id.dummy_button).setOnTouchListener(
//				mDelayHideTouchListener);

	}

	
	// This snippet hides the system bars.
	private void hideSystemUI() {
	    // Set the IMMERSIVE flag.
	    // Set the content to appear under the system bars so that the content
	    // doesn't resize when the system bars hide and show.
	    mDecorView.setSystemUiVisibility(
	            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
	            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
	            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
	            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
	            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
	            | View.SYSTEM_UI_FLAG_IMMERSIVE);
	}

	// This snippet shows the system bars. It does this by removing all the flags
	// except for the ones that make the content appear under the system bars.
	private void showSystemUI() {
	    mDecorView.setSystemUiVisibility(
	            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
	            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
	            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
	}
	private void dealWithCallBack() {
		Intent intent = getIntent();
		Uri data = intent.getData();

		if (data != null) {

			Log.i(TAG, "uri:" + data == null ? "null" : data.toString());
			Log.i(TAG, "retrive is running...");
			Log.i(TAG, "token:" + Constants.token);
			PocketHandler pocketHandler = new PocketHandler();
			pocketHandler.dealWithCallBack();

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	//deal with the ear-phone unpluged...
	private class NoisyAudioStreamReceiver extends BroadcastReceiver {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	        if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
	            // Pause the playback
	        }
	    }
	}

	private IntentFilter intentFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);

	private AudioManager am;

	protected RemoteControlReceiver remoteControlReceiver;

	private OnAudioFocusChangeListener afChangeListener;

	private void startPlayback() {
//	    registerReceiver(myNoisyAudioStreamReceiver(), intentFilter);
	}

	private void stopPlayback() {
//	    unregisterReceiver(myNoisyAudioStreamReceiver);
	}
	
	public void startRadio(View view) {
		
		am = (AudioManager) Utils.context.getSystemService(Context.AUDIO_SERVICE);
		
		
		if (am.isBluetoothA2dpOn()) {
		    // Adjust output for Bluetooth.
		} else if (am.isSpeakerphoneOn()) {
		    // Adjust output for Speakerphone.
		} else if (am.isWiredHeadsetOn()) {
		    // Adjust output for headsets
		} else { 
		    // If audio plays and noone can hear it, is it still playing?
		}

		afChangeListener = new OnAudioFocusChangeListener() {
		   

			public void onAudioFocusChange(int focusChange) {
		    	if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
		            // Lower the volume
		        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
		            // Pause playback
		        } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
		            // Resume playback 
		        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
//		            am.unregisterMediaButtonEventReceiver(remoteControlReceiver);
		            am.abandonAudioFocus(afChangeListener);
		            // Stop playback
		        }
		    }
		};
		
		// Request audio focus for playback
		int result = am.requestAudioFocus(afChangeListener,
		                                 // Use the music stream.
		                                 AudioManager.STREAM_MUSIC,
		                                 // Request permanent focus.
		                                 AudioManager.AUDIOFOCUS_GAIN);
		   
		if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
//		    am.unregisterMediaButtonEventReceiver(remoteControlReceiver);
		    // Start playback.
		}

		Log.i(TAG, "play...");
		
		radio = new PrivateRadio();
		radio.play();

	}

	public void stopRadio(View view) {

		Log.i(TAG, "stop...");
		
		am.abandonAudioFocus(afChangeListener);
		radio.stop();
		

	}
	
	public void likeIt(View view) {

		Log.i(TAG, "likt it...");
		
		
		

	}
	
	public void unlikeIt(View view) {

		Log.i(TAG, "unlike it...");
		
		radio.nextChannel();
		radio.play();

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
				com.rainasmoon.privateradio.sourcechanel.localmedia.Constants.MY_PICK_SONG_URI = uri;
				
			}
		}
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
//		delayedHide(100);
	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (AUTO_HIDE) {
//				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
			return false;
		}
	};

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
//			mSystemUiHider.hide();
		}
	};

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}

}
