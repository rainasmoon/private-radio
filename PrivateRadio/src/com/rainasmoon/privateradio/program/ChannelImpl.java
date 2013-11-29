package com.rainasmoon.privateradio.program;

import java.util.ArrayList;
import java.util.List;

public class ChannelImpl implements Channel {
	
	private String channelName;
	private ChannelImpl subChannel;
	private List<Program> programs;

	public ChannelImpl(String channelName) {
		this.channelName = channelName;
	}
	
	public void addProgram(Program p) {
		if (programs == null) {
			programs = new ArrayList<Program> ();
		}
		programs.add(p);
	}
	
	@Override
	public String getChannelName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Program> listProgramMenu() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void generateProgramSchedule() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Program> getPrograms() {
		
		return programs;
	}

	@Override
	public void addAllPrograms(List<Program> programs) {
		if (programs == null) {
			programs = new ArrayList<Program> ();
		}
		programs.addAll(programs);
	}

}
