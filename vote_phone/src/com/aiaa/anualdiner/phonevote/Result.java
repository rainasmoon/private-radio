package com.aiaa.anualdiner.phonevote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.aiaa.anualdiner.phonevote.sms.MessageItem;

public class Result {

	public static double PAINT_WEIGHT = 1.5;
	public static int PERCENT_100_WEIGHT = 300;

	public static String FILTER  = "";
	
	public static String ADMIN = "admin";
	
	private static Map<String,Team> teams;
	private static List<MessageItem> illegalMsg;
	private static List<String> allTeamsName;
	private static List<String> vipPhones;

	private static Map<String, List<Team>> checkMsgs; 
	public static int MAX_VOTE_NUM = 1;
	
	public static int VIP_WEIGHT = 10;

	public static boolean enableMultiVote;
	
	private static int max_vote;

	public static int START_POINT = 475;
	
	public static int SCREEN_WIDTH = 480;
	public static int SCREEN_HEIGHT = 854;

	public static int PAINT_WIDTH = 35;

	public static int PAINT_DISTANCE = 5;
	
	public static String BEGIN_HOUR = "17";	
	public static boolean isPaused;
	
	static {
		teams = new HashMap<String, Team>();
		illegalMsg = new ArrayList<MessageItem>();
		allTeamsName = new ArrayList<String>();
		vipPhones = new ArrayList<String>();
		checkMsgs = new HashMap<String, List<Team>>();
	}
	
	public static void initTeams() {		
		allTeamsName = new ArrayList(teams.keySet());	
		
	}
	
	public static void exec_msg(List<MessageItem> msgs) {
		for(int i = 0; i < msgs.size(); i++) {
			exec_msg(msgs.get(i), null);
		}
	}

	synchronized public static void exec_msg(MessageItem msg, Context context) {		 
		String msgString = msg.getBody().trim();
		String[] msgArray = msgString.split(" ");
				
		if(msgArray.length >= 1) {
			String token = msgArray[0].trim();
			
			if (teams.get(token) != null && check_if_legal_msg(context, teams.get(token), msg)) {
				if (msg != null && msg.getPhone() != null && isVip(msg.getPhone())) {
					//vip
					teams.get(token).addVipMsg(msg, VIP_WEIGHT);
				}
				else {
					teams.get(token).addMsg(msg);
				}
				
			}else {
				Log.d("wh:", "illeal msg:" + msg);
				illegalMsg.add(msg);
			}
			if (msgArray.length >= 2 && context != null) {
				String text = "";
				for (int i = 1; i < msgArray.length; i++) {
					text = text + " " + msgArray[i];
				}
				
				text = msg.getMaskPhone() + ": "+text;
				toast(context, text);
			}
		}
		else{
			illegalMsg.add(msg);
		}
		setFirst();
	}
	
	synchronized public static void exec_admin_msg(MessageItem msg) {		 
		String msgString = msg.getBody().trim();
		String[] msgArray = msgString.split(" ");
				
		if(msgArray.length >= 2) {
			String token = msgArray[0].trim();			
			if (teams.get(token) != null) {
				    int funny = 5;
				    try {
				    	funny = Integer.parseInt(msgArray[1]);
				    }
				    catch(Exception e) {}
					teams.get(token).addVipMsg(null, funny);
								
			}
		}
		setFirst();
	}
	
		
	public static Map<String, Team> getTeams() {
		return teams;
	}

	public static List<MessageItem> getIllegalMsg() {
		return illegalMsg;
	}

	public static List<String> getAllTeamsName() {
		return allTeamsName;
	}

	public static List<String> getAllVipPhones() {
		return vipPhones;
	}
	
	public static void addTeam(String teamName) {
		Team team = new Team(teamName);
		teams.put(teamName, team);
		initTeams();
	}
	
	public static void addVipPhone(String phoneNumber) {
		vipPhones.add(phoneNumber);
		
	}

	public static void clear() {
		teams = new HashMap<String, Team>();
		illegalMsg = new ArrayList<MessageItem>();
		allTeamsName = new ArrayList<String>();
		vipPhones = new ArrayList<String>();
		initTeams();
		
	}
	
	private static void setFirst() {
		if (teams == null || teams.values() == null) {
			return;
		}
		for (Team t : teams.values()) {
			if (t == null) {
				continue;
			}
			t.setNumberOne(false);
			if (t.getVoteNumber() >= max_vote) {
				max_vote = t.getVoteNumber();
				t.setNumberOne(true);
			}
		}
	}
	
	private static boolean isVip(String phone) {
		if (phone == null) {
			return false;
		}
		for (String vip : vipPhones) {
			if (phone.endsWith(vip)) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean check_if_legal_msg(Context context, Team team, MessageItem msg) {
		
		if (checkMsgs.get(msg.getPhone()) == null) {
			checkMsgs.put(msg.getPhone(),new ArrayList<Team>());
		}
		if (checkMsgs.get(msg.getPhone()).size() >= Result.MAX_VOTE_NUM) {
			Log.w("wh:", msg.getMaskPhone() + " has expried max vote num");
			toast(context, msg.getMaskPhone() + " has expried max vote num");
			return false;
		}
		if (!Result.enableMultiVote && checkMsgs.get(msg.getPhone()).contains(team)){
			Log.w("wh:", msg.getMaskPhone() + " has aready voted to this team");
			toast(context, msg.getMaskPhone() + " has aready voted to this team");
			return false;
		}
		checkMsgs.get(msg.getPhone()).add(team);
		return true;
	}
	
	private static void toast(Context context, String text) {

		if (context != null && text != null) {
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
	}

	
}
