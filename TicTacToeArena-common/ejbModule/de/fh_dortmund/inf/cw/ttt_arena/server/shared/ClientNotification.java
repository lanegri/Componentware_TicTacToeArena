package de.fh_dortmund.inf.cw.ttt_arena.server.shared;

import java.io.Serializable;
import java.util.Date;

public class ClientNotification implements Serializable {

	
	private static final long serialVersionUID = 3987031742488973869L;
	
	private ClientNotificationType type;
	private String sender;
//	private String text;
	
	private int row, line;
	private char token;
	private Date date;
	
	public ClientNotification(ClientNotificationType type, String sender, Date date) {
		this.type = type;
		this.setSender(sender);
		this.setDate(date);
	}
	
	public ClientNotificationType getType() {
		return type;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

//	public String getText() {
//		return text;
//	}
//
//	public void setText(String text) {
//		this.text = text;
//	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public char getToken() {
		return token;
	}

	public void setToken(char token) {
		this.token = token;
	}
}
