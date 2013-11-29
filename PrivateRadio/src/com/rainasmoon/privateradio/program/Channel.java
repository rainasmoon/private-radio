package com.rainasmoon.privateradio.program;

import java.util.List;


public interface Channel {

	public String getChannelName();
	public List<Program> listProgramMenu();
	
	
	public void generateProgramSchedule();
	public void addProgram(Program textProgram);
	public List<Program> getPrograms();
	public void addAllPrograms(List<Program> programs);
}
