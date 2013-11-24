package com.aiaa.anualdinaer.phoneanswer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Message;

import com.aiaa.anualdinaer.sms.MessageItem;
import com.aiaa.anualdinaer.sms.ResponseMessage;
import com.aiaa.anualdinaer.sms.SMSSendHandler;

public class Result {
	
	public static String ADMIN = "admin";
	public static String FILTER = "";

	public static List<String> answers;
	public static int currentGame;

	public static Map<String, Question> allGames;

	public static int NUM_WINERS = 5;
	public static int NUM_GAMERS = 50;
	
	public static String BEGIN_HOUR = "17";
	
	public static SMSSendHandler sendHandler;
	public static boolean isPaused;

	static {
		answers = new ArrayList<String>();
		allGames = new HashMap<String, Question>();
		sendHandler = new SMSSendHandler();
	}

	public static void init() {
		for (int i = 0; i < answers.size(); i++) {			
			String answer = answers.get(i).trim();
			Question question = new Question(answer);
			allGames.put(""+i, question);
		}
	}
	
	public static void exec_msg(List<MessageItem> msgs) {
		for(int i = 0; i < msgs.size(); i++) {
			exec_msg(msgs.get(i), null);
		}
	}

	synchronized public static void exec_msg(MessageItem item, Context mContext) {
		String str = item.getBody();
		Question q = allGames.get(""+currentGame);
		
		if (str.equalsIgnoreCase(q.getAnswers())
				&& q.getWinners().size() < NUM_WINERS) {
			// right answer and within the timetable
			q.addToWiners(item);
			//send win			
			sendHandler.sendMessage(ResponseMessage.createWinMsg(item, q.getWinners().size(), ""+ (currentGame+1)));

		} 
		else if (str.equalsIgnoreCase(q.getAnswers())
				&& q.getLegalMsgs().size() < NUM_GAMERS) {
			//right answer but too late.
			q.addToLegalMsgs(item);
			//send too late
			sendHandler.sendMessage(ResponseMessage.createWinButLateMsg(item, q.getLegalMsgs().size(), ""+ (currentGame+1)));
		}
		else if (!str.equalsIgnoreCase(q.getAnswers())
				&& q.getLegalMsgs().size() < NUM_GAMERS) {
			//wrong but legal.
			q.addToLegalMsgs(item);	
			//send wrong
			sendHandler.sendMessage(ResponseMessage.createLossMsg(item, q.getLegalMsgs().size(), ""+ (currentGame+1)));
		}
		else {
			//other w/o msg.
			q.addToAllMsgs(item);
			//igrone
		}

	}

	synchronized public static void exec_admin_msg(MessageItem item) {
		
		sendHandler.sendMessage(ResponseMessage.createAdminMsg(answers));
	}

	public static List<String> getAllWiners() {
		Question q = allGames.get("" + currentGame);
		if (q == null) {
			return new ArrayList<String>();
		}
		List<MessageItem> winners = q.getWinners();
		List<String> result = new ArrayList<String>();
		for(MessageItem item : winners) {
			result.add(item.getMaskPhone() + " : " + item.getMaskDate() +" : " + item.getBody());
		}
		
		return result;
	}
	
	public static List<String> getAllMsg() {
		Question q = allGames.get("" + currentGame);
		if (q == null) {
			return new ArrayList<String>();
		}
		List<MessageItem> msgs = q.getAllMsgs();
		List<String> result = new ArrayList<String>();
		for(MessageItem item : msgs) {
			result.add(item.getMaskPhone() + " : " + item.getMaskDate() +" : " + item.getBody());
		}
		
		return result;
	}
	
	public static String getCurrentGame() {
		if (allGames == null || allGames.get(""+currentGame) == null) {
			return "no games";
		}
		return "" + (currentGame + 1);
	}
	
	public static String getCurrentAnswer() {
		if (allGames == null || allGames.get(""+currentGame) == null) {
			return "no games";
		} 
		return allGames.get(""+currentGame).getAnswers();
	}
	
	public static String getCurrentWinnersNum() {
		if (allGames == null || allGames.get(""+currentGame) == null) {
			return "no games";
		} 
		return "" + allGames.get(""+currentGame).getWinners().size();
	}
	
	public static String getCurrentMsgNum() {
		if (allGames == null || allGames.get(""+currentGame) == null) {
			return "no games";
		} 
		return "" + allGames.get(""+currentGame).getAllMsgs().size();
	}
	
	public static List<String> getAllAnswers() {

		return answers;
	}

	public static void addAnswer(String answer) {
		answers.add(answer);

		Result.init();
	}

	public static void clear() {
		answers = new ArrayList<String>();
		allGames = new HashMap<String, Question>();
		currentGame = 0;
		init();

	}

}
