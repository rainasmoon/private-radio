package com.rainasmoon.privateradio.program;

public class SilenceProgram implements Program {
	
	private long silenceTime;

	public SilenceProgram(long time) {
		silenceTime = time;
	}
	
	public SilenceProgram() {
		silenceTime = 5000;
	}
	
	
	@Override
	public void getDuration() {
		// TODO Auto-generated method stub

	}

	@Override
	public long iid() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void play() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isCompleted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void getDuriation() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isText() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAudio() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isVedio() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getSilenceTime() {
		return silenceTime;
	}

	public void setSilenceTime(long silenceTime) {
		this.silenceTime = silenceTime;
	}

}
