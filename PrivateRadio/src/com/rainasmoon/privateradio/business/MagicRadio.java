package com.rainasmoon.privateradio.business;

public interface MagicRadio {

	public void play();
	public void pause();
	public void stop();
	public void start();
	public void nextChannel();// that we don't like.
	public void preChannel();
	//i prefer to create the MagicRadio as simple as possible.
	//the other things the program will take care.
	public void generateProgramSchedule();
	
}
