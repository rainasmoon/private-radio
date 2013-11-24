package com.aiaa.anualdiner.phonevote;

import java.util.List;

import com.aiaa.anualdiner.phonevote.sms.MessageItem;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListIllegalMsgActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ListView listView = new ListView(this);
		List<MessageItem> illegalMsg = Result.getIllegalMsg();
		String[] illegalMsgStrArray = new String[illegalMsg.size()];
		for (int i = 0; i < illegalMsgStrArray.length; i++) {
			illegalMsgStrArray[i] = illegalMsg.get(i).getMaskPhone() + ":"
					+ illegalMsg.get(i).getBody();
		}
		listView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1,
				illegalMsgStrArray));
		setContentView(listView);

	}

}
