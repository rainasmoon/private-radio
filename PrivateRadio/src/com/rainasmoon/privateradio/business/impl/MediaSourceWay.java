package com.rainasmoon.privateradio.business.impl;


public enum MediaSourceWay {
	SELECT_LOCAL_MEDIA(1, "Picker Media"), INTERNET_MEDIA(2,
			"Internet Song"), RSS(3, "RSS"), POCKET(4, "Pocket"), WEIBO(5,
			"Weibo"), LOCAL_MEDIA(6, "Local Media"), LOCAL_FOLDER(0,
			"Local Folder");

	private int channelId;
	private String channelName;

	private MediaSourceWay(int id, String name) {
		channelId = id;
		channelName = name;
	}

	@Override
	public String toString() {
		return channelName;
	}

	private MediaSourceWay getChannel(int id) {

		switch (id) {
		case 0:
			return LOCAL_FOLDER;
		case 1:
			return SELECT_LOCAL_MEDIA;
		case 2:
			return INTERNET_MEDIA;
		case 3:
			return RSS;
		case 4:
			return POCKET;
		case 5:
			return WEIBO;
		case 6:
			return LOCAL_MEDIA;

		}
		return null;
	}

	public MediaSourceWay nextMediaSource() {
		if (channelId == MediaSourceWay.values().length - 1) {
			return LOCAL_MEDIA;
		}
		return getChannel(channelId + 1);
	}

	public static MediaSourceWay initMediaSource() {
		
		return LOCAL_FOLDER;
	}
}