package com.aiaa.anualdiner.phonevote.sms;

import java.util.Date;

public class MessageItem implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private int type;
	private int protocol;
	private String name;
	private String phone;
	private String body;
	private Date date;

	public MessageItem() {
	}

	public MessageItem(String name, String phoneNumber, String smsbody,
			Date date, int type) {
		super();
		this.name = name;
		this.phone = phoneNumber;
		this.body = smsbody;
		this.date = date;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {

		this.type = type;
	}

	public int getProtocol() {

		return protocol;
	}

	public void setProtocol(int protocol) {
		this.protocol = protocol;
	}

	public String getPhone() {

		return phone;
	}

	public void setPhone(String phone) {

		this.phone = phone;
	}

	public String getBody() {

		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String toString() {

		return "id = " + id + ";" + "type = " + type + ";" + "protocol = "
				+ protocol + ";" + "phone = " + phone + ";" + "body = " + body;
	}

	public String getStandardPhone11() {
		if (getPhone() == null) {
			return "null";
		}
		if (getPhone().length() >= 11) {
			String maskPhone = getPhone().substring(getPhone().length() - 11,
					getPhone().length());
			return maskPhone;
		}
		return getPhone();
	}
	
	public String getMaskPhone() {
		if (getPhone() == null) {
			return "null";
		}
		if (getPhone().length() >= 11) {
			String maskPhone = getPhone().substring(getPhone().length() - 11,
					getPhone().length());
			return maskPhone.substring(0, 3) + "****"
					+ maskPhone.substring(7, 11);
		}
		return getPhone();
	}

}