package com.rainasmoon.privateradio.business.impl;

import java.util.Timer;
import java.util.TimerTask;

import com.rainasmoon.privateradio.business.PlayHandlerImpl;
import com.rainasmoon.privateradio.utils.Utils;

public class SilenceHandler {

	private static SilenceHandler silenceHandler;
	private PlayHandlerImpl playHandlerImpl;
	
	private SilenceHandler(PlayHandlerImpl playHandlerImpl) {
		this.playHandlerImpl = playHandlerImpl;
	}

	public static SilenceHandler instanceSilenceHandler(PlayHandlerImpl playHandlerImpl) {
		if (silenceHandler == null) {
			silenceHandler = new SilenceHandler(playHandlerImpl);
		}
		return silenceHandler;
	}

	public void shutup(long silenceTime) {
		
		Utils.log.info("try to silence:" + silenceTime);
		//
		Timer timer = new Timer();
		// 超时TIMEOUT退出
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				playHandlerImpl.next();
			}
		}, silenceTime);
		
	}
}
