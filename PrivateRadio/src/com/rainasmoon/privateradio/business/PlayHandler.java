package com.rainasmoon.privateradio.business;

import com.rainasmoon.privateradio.program.Program;

public interface PlayHandler {

	public boolean isLiveNow();

	public void init();
	public void playProgram(Program program);
	public boolean isCompleted();
	public void onCompletedListenner();
	public void stop();
	
}
