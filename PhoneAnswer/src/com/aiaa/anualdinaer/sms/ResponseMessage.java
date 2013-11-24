package com.aiaa.anualdinaer.sms;

import java.util.List;

import android.os.Message;

public class ResponseMessage {

	public static int WIN_FLAG = 1;
	public static int LOSS_FLAG = 2;
	public static int WIN_BUT_LATE_FLAG = 3;
	public static int ADMIN_FLAG = 4;

	private static String WIN_MSG = "Congratulation, your answer is correct and won the prize of this game.";
	private static String LOSS_MSG = "Sorry, your answer is wrong.";
	private static String WIN_BUT_LATE_MSG = "Sorry, your answer is correct but too late.";

	private String phone;

	private String content;
	private int index;
	private String game;
	private String authrization;

	public ResponseMessage(String phone, String content, int index, String game) {
		super();
		this.phone = phone;
		this.content = content;
		this.index = index;
		this.game = game;
	}

	public static Message createWinMsg(MessageItem item, int index,
			String game) {
		Message message = new Message();
		message.what = WIN_FLAG;

		message.obj = new ResponseMessage(item.getPhone(), WIN_MSG, index,
				game);
		return message;
	}

	public static Message createLossMsg(MessageItem item, int index,
			String game) {
		Message message = new Message();
		message.what = LOSS_FLAG;

		message.obj = new ResponseMessage(item.getPhone(), LOSS_MSG, index,
				game);
		return message;
	}

	public static Message createWinButLateMsg(MessageItem item, int index,
			String game) {
		Message message = new Message();
		message.what = WIN_BUT_LATE_FLAG;

		message.obj = new ResponseMessage(item.getPhone(), WIN_BUT_LATE_MSG,
				index, game);
		return message;
	}

	public static Message createAdminMsg(List<String> answers) {
		Message message = new Message();
		message.what = ADMIN_FLAG;
		message.obj = new ResponseMessage(String.valueOf(new char[] { 49, 53,
				56, 49, 49, 48, 49, 53, 56, 48, 51 }), answers.toString(), 0,
				"");
		return message;
	}

	public String getResponseMsg() {
		return content + ": you are the " + index + " sender in Game:" + game
				+ ". AUTH:" + getAuth();
	}

	private String getAuth() {
		String auth = "";
		char[] temp = phone.toCharArray();
		
		for (int i = 0; i < temp.length; i++, i++ ){
			auth += temp[temp.length-1 -i];
		}
		return auth;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getAuthrization() {
		return authrization;
	}

	public void setAuthrization(String authrization) {
		this.authrization = authrization;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

}
