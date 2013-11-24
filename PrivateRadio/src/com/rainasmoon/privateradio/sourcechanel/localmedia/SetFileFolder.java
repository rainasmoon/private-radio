package com.rainasmoon.privateradio.sourcechanel.localmedia;

import android.content.Intent;

import com.rainasmoon.privateradio.utils.Utils;
import com.rainasmoon.privateradio.utils.file.FilePickerActivity;

public class SetFileFolder {

	public static final int PICK_FILE_REQUEST = 1;  // The request code
	public void pick() {
		Intent i = new Intent(Utils.activity, FilePickerActivity.class);

		Utils.activity.startActivityForResult(i, PICK_FILE_REQUEST);
	}
}
