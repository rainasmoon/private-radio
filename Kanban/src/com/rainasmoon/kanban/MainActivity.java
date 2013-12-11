package com.rainasmoon.kanban;



import android.app.Activity;
import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;



public class MainActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		View textView = findViewById(R.id.test_drag);
		
		textView.setOnTouchListener(new MyTouchListener());
		

		findViewById(R.id.kanban_backlog).setOnDragListener(new MyDragListener());
		findViewById(R.id.kanban_plan).setOnDragListener(new MyDragListener());
		findViewById(R.id.kanban_in_process).setOnDragListener(new MyDragListener());
		findViewById(R.id.kanban_completed).setOnDragListener(new MyDragListener());
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private final class MyTouchListener implements OnTouchListener {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
		      if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
		        ClipData data = ClipData.newPlainText("", "");
		        DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
		        view.startDrag(data, shadowBuilder, view, 0);
//		        view.setVisibility(View.INVISIBLE);
		        return true;
		      } else {
		        return false;
		      }
		    }
	  }

	  class MyDragListener implements OnDragListener {
		  
		    Drawable enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
		    Drawable normalShape = getResources().getDrawable(R.drawable.shape);

	    @Override
	   
	    	 public boolean onDrag(View v, DragEvent event) {

	       	      switch (event.getAction()) {
	       	      case DragEvent.ACTION_DRAG_STARTED:
	       	        // do nothing
	       	    	Log.i("wh", "start.");
	       	        break;
	       	      case DragEvent.ACTION_DRAG_ENTERED:
	       	    	Log.i("wh", "enter.");
	       	        v.setBackgroundDrawable(enterShape);
	       	        break;
	       	      case DragEvent.ACTION_DRAG_EXITED:
	       	    	Log.i("wh", "exit.");
	       	        v.setBackgroundDrawable(normalShape);
	       	        break;
	       	      case DragEvent.ACTION_DROP:
	       	        // Dropped, reassign View to ViewGroup
	       	    	Log.i("wh", "drop.");
	       	        View view = (View) event.getLocalState();
	       	        ViewGroup owner = (ViewGroup) view.getParent();
	       	        owner.removeView(view);
	       	     LinearLayout container = (LinearLayout) v;
	       	        container.addView(view);
	       	     Log.i("wh", "drop."+view.getContext());
	       	        view.setVisibility(View.VISIBLE);
	       	        break;
	       	      case DragEvent.ACTION_DRAG_ENDED:
	       	    	  Log.i("wh", "end.");
	       	        v.setBackgroundDrawable(normalShape);
	       	      default:
	       	        break;
	       	      }
	       	      return true;
	            }
	   	  }

}
