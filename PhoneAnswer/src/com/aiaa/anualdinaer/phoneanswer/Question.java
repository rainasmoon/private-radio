package com.aiaa.anualdinaer.phoneanswer;

import java.util.ArrayList;
import java.util.List;

import com.aiaa.anualdinaer.sms.MessageItem;

public class Question {

	private String answers;
	private List<MessageItem> winners;
	private List<String> existWinnerPhones;
	private List<MessageItem> legalMsgs;
	private List<MessageItem> allMsgs;
	
	
	public Question(String answer) {
		this.answers = answer;
		winners = new ArrayList<MessageItem>();
		existWinnerPhones = new ArrayList<String>();
		legalMsgs = new ArrayList<MessageItem>();
		allMsgs = new ArrayList<MessageItem>();
	}
	
	public void addToWiners(MessageItem item) {
		addToLegalMsgs(item);
		if (!checkExistWinner(item)) {
			winners.add(item);
			existWinnerPhones.add(item.getPhone());
		}
		
	}

	public void addToLegalMsgs(MessageItem item) {
		addToAllMsgs(item);
		legalMsgs.add(item);
	}

	public void addToAllMsgs(MessageItem item) {
		allMsgs.add(item);		
	}
	
	private boolean checkExistWinner(MessageItem item) {
		if (existWinnerPhones.contains(item.getPhone())) {
			return true;
		}
		return false;
	}
	
	public String getAnswers() {
		return answers;
	}
	public void setAnswers(String answers) {
		this.answers = answers;
	}
	public List<MessageItem> getWinners() {
		return winners;
	}
	public void setWinners(List<MessageItem> winners) {
		this.winners = winners;
	}
	public List<MessageItem> getLegalMsgs() {
		return legalMsgs;
	}
	public void setLegalMsgs(List<MessageItem> legalMsgs) {
		this.legalMsgs = legalMsgs;
	}
	public List<MessageItem> getAllMsgs() {
		return allMsgs;
	}
	public void setAllMsgs(List<MessageItem> allMsgs) {
		this.allMsgs = allMsgs;
	}

}
