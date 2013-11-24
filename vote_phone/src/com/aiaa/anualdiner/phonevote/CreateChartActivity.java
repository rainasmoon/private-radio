package com.aiaa.anualdiner.phonevote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aiaa.anualdiner.phonevote.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;

public class CreateChartActivity extends Activity {

	private ChartView view;
	private LinearLayout layout;
	
	private static Map<String,ChartView> views = new HashMap<String, ChartView>();

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_chart);
		layout = (LinearLayout) findViewById(R.id.chart_layout);

		init();
		
		Result.initTeams();

		for (String teamName : Result.getAllTeamsName()) {
			view = new ChartView(this, Result.getTeams().get(teamName)
					.getVoteNumber(), teamName);
			views.put(teamName, view);
			layout.addView(view, new LayoutParams(100, LayoutParams.FILL_PARENT));

		}
		
		thread.start();

	}
	
	private void init() {
		 int screenWidth,screenHeight; 
		 WindowManager windowManager = getWindowManager();
		 Display display = windowManager.getDefaultDisplay(); 
		 screenWidth = display.getWidth();
		 screenHeight = display.getHeight();
	 
		 double start_rate = 0.825;		
		 
		 Result.START_POINT = 480;
		 
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