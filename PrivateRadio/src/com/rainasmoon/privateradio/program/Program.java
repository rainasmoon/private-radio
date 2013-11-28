package com.rainasmoon.privateradio.program;

import com.rainasmoon.privateradio.utils.Utils;

public interface Program extends Play {

	
	public void play();
	public void stop();
	public boolean isCompleted();
	
	public void getDuriation();
	
	public boolean isText();
	public boolean isAudio();
	public boolean isVedio();
	
	public String getDescription();
}
