package com.rainasmoon.privateradio.program;

import java.util.List;


public interface Channel {

	public String getChannelName();
	public List<Program> listProgramMenu();
	
	
	public void generateProgramSchedule();
}
