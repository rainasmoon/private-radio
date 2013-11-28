package com.rainasmoon.privateradio.sourcechanel.localmedia;

import java.io.IOException;

import com.rainasmoon.privateradio.business.impl.MediaPlayHandler;
import com.rainasmoon.privateradio.utils.Utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class LocalMediaHandler {
	
	java.util.logging.Logger log = java.util.logging.Logger.getLogger("wh:");
	
	private ContentResolver contentResolver;
	
	public LocalMediaHandler(ContentResolver contentResolver) {
		this.contentResolver = contentResolver;
	}
	
	public LocalMediaHandler() {
		this(Utils.contentResolver);
	}

	public long getBanchOfLocalMedia() {

		Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		log.info("uri: " + uri);

		Cursor cursor = contentResolver.query(uri, null, null, null, null);

		if (cursor == null) {
			log.info("query failed");

		} else if (!cursor.moveToFirst()) {
			log.info("no media");
		} else {
			int titleColumn = cursor
					.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
			int idColumn = cursor
					.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
			do {
				long thisId = cursor.getLong(idColumn);
				String thisTitle = cursor.getString(titleColumn);
				// ...process entry...
				log.info("play: " + thisTitle);
				
					return thisId;
				
				
			} while (cursor.moveToNext());
		}
		return 0;
	}
}
