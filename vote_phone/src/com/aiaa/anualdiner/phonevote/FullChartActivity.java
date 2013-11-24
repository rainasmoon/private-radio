package com.aiaa.anualdiner.phonevote;

import java.util.HashMap;
import java.util.Map;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;


import com.aiaa.anualdiner.phonevote.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class FullChartActivity extends Activity {
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
	
	private ChartView view;
	private LinearLayout layout;
	
	private static Map<String,ChartView> views = new HashMap<String, ChartView>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_full_chart);
		setupActionBar();

		final View controlsView = findViewById(R.id.fullscreen_content_controls);
		final View contentView = findViewById(R.id.chart_layout);

		layout = (LinearLayout) findViewById(R.id.chart_layout);

		init(Configuration.ORIENTATION_PORTRAIT);
		
		Result.initTeams();

		for (String teamName : Result.getAllTeamsName()) {
			view = new ChartView(this, Result.getTeams().get(teamName)
					.getVoteNumber(), teamName);
			views.put(teamName, view);
			layout.addView(view, new LayoutParams(100, LayoutParams.FILL_PARENT));

		}
		
		
		thread.start();
		
		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
		mSystemUiHider = SystemUiHider.getInstance(this, contentView,
				HIDER_FLAGS);
		mSystemUiHider.setup();
		mSystemUiHider
				.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
					// Cached values.
					int mControlsHeight;
					int mShortAnimTime;

					@Override
					@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
					public void onVisibilityChange(boolean visible) {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
							// If the ViewPropertyAnimator API is available
							// (Honeycomb MR2 and later), use it to animate the
							// in-layout UI controls at the bottom of the
							// screen.
							if (mControlsHeight == 0) {
								mControlsHeight = controlsView.getHeight();
							}
							if (mShortAnimTime == 0) {
								mShortAnimTime = getResources().getInteger(
										android.R.integer.config_shortAnimTime);
							}
							controlsView
									.animate()
									.translationY(visible ? 0 : mControlsHeight)
									.setDuration(mShortAnimTime);
						} else {
							// If the ViewPropertyAnimator APIs aren't
							// available, simply show or hide the in-layout UI
							// controls.
							controlsView.setVisibility(visible ? View.VISIBLE
									: View.GONE);
						}

						if (visible && AUTO_HIDE) {
							// Schedule a hide().
							delayedHide(AUTO_HIDE_DELAY_MILLIS);
						}
					}
				});

		// Set up the user interaction to manually show or hide the system UI.
		contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TOGGLE_ON_CLICK) {
					mSystemUiHider.toggle();
				} else {
					mSystemUiHider.show();
				}
			}
		});

		// Upon interacting with UI controls, delay any scheduled hide()
		// operations to prevent the jarring behavior of controls going away
		// while interacting with the UI.
		findViewById(R.id.toggle_pause).setOnTouchListener(
				mDelayHideTouchListener);


	}
	
	public void onToggleClicked(View view) {

		boolean on = ((ToggleButton) view).isChecked();

		if (on) {
			Result.isPaused = true;
//			pause();
		} else {
			Result.isPaused = false;
//			counter();
		}
	}
	private void pause() {}
	private void counter() {
		TextView tt = (TextView) findViewById(R.id.text_counter); 
		MyCount myCount = new MyCount(30000,1000, tt);
		myCount.start();
	}
	
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		delayedHide(100);
	}
	
	@Override
    protected void onStart() {
        super.onStart();
        Log.d("wh:", "wh:on satrt");
    }
    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
        Log.d("wh:", "wh:on Resume");
    }
    
    @Override
    public void onConfigurationChanged (Configuration newConfig) {
    	super.onConfigurationChanged(newConfig);
    	Log.d("wh:", "wh: config changeed.");
    	
    	
    	init(newConfig.orientation);
    }

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			// TODO: If Settings has multiple levels, Up should navigate up
			// that hierarchy.
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
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
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
			return false;
		}
	};

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			mSystemUiHider.hide();
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
	
	private void init(int oriantation) {
		 int screenWidth,screenHeight; 
		 WindowManager windowManager = getWindowManager();
		 Display display = windowManager.getDefaultDisplay(); 
		 screenWidth = display.getWidth();
		 screenHeight = display.getHeight();


		 Result.SCREEN_WIDTH = screenWidth;
		 Result.SCREEN_HEIGHT = screenHeight;
		 
		 double start_rate = 0.725;		 
		 double start_rate_oriantial = 0.8;
		 double index_rate = 560;
		 
		 if (oriantation == Configuration.ORIENTATION_PORTRAIT) {
			 Result.START_POINT = (int)(start_rate * Result.SCREEN_HEIGHT);
		 }
		 else {
			 Result.START_POINT = (int)(start_rate_oriantial * Result.SCREEN_HEIGHT);
		 }
		 
		 Log.d("wh:", "wh: start point:" + Result.START_POINT);
		 
		 Result.PAINT_WEIGHT = Result.SCREEN_HEIGHT/index_rate;
		 Log.d("wh:", "wh: paint weight:" + Result.PAINT_WEIGHT);
		 
		 Log.d("wh:", "width:" + screenWidth + " height:" + screenHeight);
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);			
			for(String teamName : Result.getAllTeamsName()) {
				if (teamName != null && views.get(teamName) != null) {
					views.get(teamName).setHight(Result.getTeams().get(teamName).getVoteNumber(), Result.getTeams().get(teamName).isNumberOne());
				}
			}
		}
	};
	
	private Thread thread = new Thread() {
		@Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
				try {					
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.err.println("InterruptedException！线程关闭");
					this.interrupt();
				}
			}
		}
	};
	
}
