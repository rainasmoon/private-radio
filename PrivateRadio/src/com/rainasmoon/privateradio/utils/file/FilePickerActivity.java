package com.rainasmoon.privateradio.utils.file;

import java.io.File;

import com.rainasmoon.privateradio.R;
import com.rainasmoon.privateradio.R.id;
import com.rainasmoon.privateradio.R.layout;
import com.rainasmoon.privateradio.R.string;
import com.rainasmoon.privateradio.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;

public class FilePickerActivity extends Activity implements OnClickListener {

	private ListView lv2;

	private File mCurrentDirectory = Environment.getExternalStorageDirectory();

	private ExpBaseAdapter ap;

	private String fileEndings[] = { "avi", "3gp", "mp3", "mp4" };

	

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setTitle(R.string.app_name);

		setContentView(R.layout.list_file);

		lv2 = (ListView) findViewById(R.id.list_view_1);

		lv2.setCacheColorHint(0x00000000);

		ap = new ExpBaseAdapter(this);

		lv2.setAdapter(ap);

		ListView.OnItemClickListener lv2click = new ListView.OnItemClickListener() {

			public void onItemClick(AdapterView parent, View view,
					int position, long id) {
				// "_id", "ext_number", "name","ann", "intro"
				
				String mPath = "";

				if (ap.isAFolder((int) id)) {
					String s1 = ap.getItem((int) id).getName();

					if (s1.equals("..")) {
						mPath = mCurrentDirectory.getParent();

					} else {
						mPath = mCurrentDirectory.getPath() + "/" + s1 + "/";

					}

					mCurrentDirectory = new File(mPath);

					listFile(mCurrentDirectory);

				} else {

					Bundle bundle = new Bundle();

					bundle.putString("filename", mCurrentDirectory.getPath()
							+ "/" + ap.getItem((int) id).getName());

					Utils.log.info("the select file is:" + mCurrentDirectory.getPath()
							+ "/" + ap.getItem((int) id).getName());
					
					Intent mIntent = new Intent();

					mIntent.putExtras(bundle);

					setResult(RESULT_OK, mIntent);

					finish();

				}

			}

		};

		listFile(mCurrentDirectory);

		lv2.setOnItemClickListener(lv2click);

	}

	private void listFile(File aDirectory) {

		ap.clearItems();

		ap.notifyDataSetChanged();

		lv2.postInvalidate();

		Log.v("vodone", "mpath=" + aDirectory.getPath());

		// if (aDirectory==null)return;

		//TODO: 
		if (!aDirectory.getPath().equals("/sdcard")) {

			FileData fd = new FileData("..", FileData.TYPE_FOLDER);
			
			ap.addItem(fd);

		}

		for (File f : aDirectory.listFiles()) {

			if (f.isDirectory()) {

				FileData fd = new FileData(f);
		
				ap.addItem(fd);

			} else {

				if (checkEnds(f.getName().toLowerCase())) {

					FileData fd = new FileData(f.getName(), FileData.TYPE_FILE);
					
					ap.addItem(fd);

				}

			}

		}

		ap.notifyDataSetChanged();

		lv2.postInvalidate();

	}

	private boolean checkEnds(String checkItsEnd) {

		for (String aEnd : fileEndings) {
			if (checkItsEnd.endsWith(aEnd))

				return true;

		}

		return false;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// TODO: when you click

		Utils.log.info("you click on something...");

	}

}
