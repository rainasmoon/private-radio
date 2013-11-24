package com.aiaa.anualdiner.phonevote;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.aiaa.anualdiner.phonevote.sms.MessageItem;

public class Team {

	private String name;
	private int voteNumber;
	private List<MessageItem> voteMsgs;
	private boolean isNumberOne;
	
	
	public Team() {
		voteMsgs = new ArrayList<MessageItem>();
	}
		
	public Team(String name) {
		this();
		this.name = name;
	}
	
	public void addMsg(MessageItem msg) {		
		voteNumber++;		
		if (msg != null) {
			voteMsgs.add(msg);
		}
	}
	
	public void addVipMsg(MessageItem msg, int weight) {		
		voteNumber += weight;		
		if (msg != null) {
			voteMsgs.add(msg);
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Team) {
			return this.name != null && this.name.equalsIgnoreCase(((Team) o).getName());
		}
		return false;
		
	}
	
	@Override
	public int hashCode() {
		return name == null ? 0 : name.hashCode();
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getVoteNumber() {
		return voteNumber;
	}
	public void setVoteNumber(int voteNumber) {
		this.voteNumber = voteNumber;
	}
	public List<MessageItem> getVoteMsgs() {
		return voteMsgs;
	}
	public void setVoteMsgs(List<MessageItem> voteMsgs) {
		this.voteMsgs = voteMsgs;
	}

	public boolean isNumberOne() {
		return isNumberOne;
	}

	public void setNumberOne(boolean isNumberOne) {
		this.isNumberOne = isNumberOne;
	}

	
	
}
