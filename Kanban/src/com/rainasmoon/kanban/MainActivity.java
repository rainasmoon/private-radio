package com.rainasmoon.kanban;

import java.util.Set;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	private DataSet dataSet;
	private LinearLayout backlogLayout;
	private LinearLayout planLayout;
	private LinearLayout inProcessLayout;
	private LinearLayout completedLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.action_add:
			addItem();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		LayoutInflater factory = LayoutInflater.from(this);
		final View textEntryView = factory.inflate(R.layout.dialog_add_item,
				null);

		return new AlertDialog.Builder(this)
				.setIconAttribute(android.R.attr.alertDialogIcon)
				.setTitle(R.string.app_name)
				.setView(textEntryView)
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {

								EditText editText = (EditText) textEntryView
										.findViewById(R.id.itemname);
								String teamName = editText.getText().toString();

								/* User clicked OK so do some stuff */
								dataSet.addBacklog(teamName);
								putData(backlogLayout, teamName);
								saveData();

								editText.setText("");
							}
						})
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {

								/* User clicked cancel so do some stuff */
							}
						}).create();
	}

	private void addItem() {

		showDialog(0);

	}

	private void init() {
		// init the 4 pannels' recevie listenner.
		backlogLayout = (LinearLayout) findViewById(R.id.kanban_backlog);
		backlogLayout.setOnDragListener(new MyDragListener());
		planLayout = (LinearLayout) findViewById(R.id.kanban_plan);
		planLayout.setOnDragListener(new MyDragListener());
		inProcessLayout = (LinearLayout) findViewById(R.id.kanban_in_process);
		inProcessLayout.setOnDragListener(new MyDragListener());
		completedLayout = (LinearLayout) findViewById(R.id.kanban_completed);
		completedLayout.setOnDragListener(new MyDragListener());

		// get storage data
		dataSet = DataKeeper
				.readData(MainActivity.this.getApplicationContext());

		if (dataSet == null || dataSet.isEmpty()) {
			Log.i("wh", "init test data...");
			dataSet = new DataSet();
			initTestData();
		}

		// put data

		putData(backlogLayout, dataSet.getBacklog());
		putData(planLayout, dataSet.getPlan());
		putData(inProcessLayout, dataSet.getInProcess());
		putData(completedLayout, dataSet.getCompleted());

	}

	private void initTestData() {
		DataKeeper.clear(MainActivity.this.getApplicationContext());
		dataSet = new DataSet();
		dataSet.addBacklog("赚100W");
		dataSet.addBacklog("珠峰");
		dataSet.addBacklog("打孩子");

		dataSet.addPlan("读书计划");

		dataSet.addInProcess("漫步华尔街");

		dataSet.addCompleted("游泳");

		DataKeeper
				.writeData(MainActivity.this.getApplicationContext(), dataSet);
	}

	private void putData(LinearLayout pannel, Set<String> set) {
		if (set == null) {
			return;
		}
		for (String s : set) {
			pannel.addView(createItem(s));
		}

	}

	private void putData(LinearLayout pannel, String value) {
		if (value == null) {
			return;
		}

		pannel.addView(createItem(value));

	}

	private View createItem(String string) {
		TextView textView = new TextView(this);
		textView.setTextSize(20);
		textView.setText(string);
		textView.setOnTouchListener(new MyTouchListener());

		return textView;
	}

	private final class MyTouchListener implements OnTouchListener {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				ClipData data = ClipData.newPlainText("", "");
				DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
						view);
				view.startDrag(data, shadowBuilder, view, 0);
				view.setVisibility(View.INVISIBLE);
				return true;
			} else {
				return false;
			}
		}
	}

	private final class MyDragListener implements OnDragListener {

		Drawable enterShape = getResources().getDrawable(
				R.drawable.shape_droptarget);
		Drawable normalShape = getResources().getDrawable(R.drawable.shape);

		@Override
		public boolean onDrag(View v, DragEvent event) {

			switch (event.getAction()) {
			case DragEvent.ACTION_DRAG_STARTED:
				// do nothing
				break;
			case DragEvent.ACTION_DRAG_ENTERED:
				v.setBackgroundDrawable(enterShape);
				break;
			case DragEvent.ACTION_DRAG_EXITED:
				v.setBackgroundDrawable(normalShape);
				break;
			case DragEvent.ACTION_DROP:
				// Dropped, reassign View to ViewGroup
				View view = (View) event.getLocalState();
				ViewGroup owner = (ViewGroup) view.getParent();
				owner.removeView(view);
				LinearLayout container = (LinearLayout) v;
				container.addView(view);

				view.setVisibility(View.VISIBLE);
				// rearage the data.
				if (owner.getId() != v.getId()) {
					rearangeData(view, owner, v);
					saveData();
				}

				break;
			case DragEvent.ACTION_DRAG_ENDED:
				v.setBackgroundDrawable(normalShape);
			default:
				break;
			}
			return true;
		}

		private void rearangeData(View view, ViewGroup owner, View v) {
			String item = ((TextView) view).getText().toString();

			if (owner.getId() == backlogLayout.getId()) {
				dataSet.removeBacklog(item);

			} else if (owner.getId() == planLayout.getId()) {
				dataSet.removePlan(item);

			} else if (owner.getId() == inProcessLayout.getId()) {
				dataSet.removeInProcess(item);
			} else if (owner.getId() == completedLayout.getId()) {
				dataSet.removeCompleted(item);
			}

			if (v.getId() == backlogLayout.getId()) {
				dataSet.addBacklog(item);
			} else if (v.getId() == planLayout.getId()) {
				dataSet.addPlan(item);
			} else if (v.getId() == inProcessLayout.getId()) {
				dataSet.addInProcess(item);
			} else if (v.getId() == completedLayout.getId()) {
				dataSet.addCompleted(item);
			}

		}
	}

	public void saveData() {
		Log.i("wh", "save data:" + dataSet);
		DataKeeper.clear(MainActivity.this.getApplicationContext());
		DataKeeper
				.writeData(MainActivity.this.getApplicationContext(), dataSet);

	}

}
