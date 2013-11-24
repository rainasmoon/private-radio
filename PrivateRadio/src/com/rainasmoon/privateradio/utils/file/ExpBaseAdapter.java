package com.rainasmoon.privateradio.utils.file;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.rainasmoon.privateradio.R;
import com.rainasmoon.privateradio.R.id;
import com.rainasmoon.privateradio.R.layout;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExpBaseAdapter extends BaseAdapter {

	private Context mContext;

	private List mItems = new ArrayList();

	private LinearLayout layout, layout_more;

	public ExpBaseAdapter(Context context) {
		mContext = context;

	}

	public void addItem(FileData it) {
		mItems.add(it);

	}

	public FileData getItem(int it) {
		return (FileData) mItems.get(it);

	}

	public int getCount() {
		return mItems.size();

	}

	public long getItemId(int arg0) {
		return arg0;

	}

	public boolean isAFile(int arg0) {
		return getItem(arg0).isAFile();

	}
	public boolean isAFolder(int arg0) {
		return getItem(arg0).isAFolder();

	}

	public void clearItems() {
		mItems.clear();

	}

	public View getView(int arg0, View arg1, ViewGroup arg2) {

		LayoutInflater inflate = (LayoutInflater) mContext
				.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);

		arg1 = (LinearLayout) inflate.inflate(R.layout.list_file, null);

		TextView fm_text_01 = (TextView) arg1.findViewById(R.id.text_view_1);
		fm_text_01.setText(getItem(arg0).getName());
		
		/*
		 * if (getItem(arg0).equals(".")){
		 * 
		 * }
		 */

		return arg1;

	}

}
