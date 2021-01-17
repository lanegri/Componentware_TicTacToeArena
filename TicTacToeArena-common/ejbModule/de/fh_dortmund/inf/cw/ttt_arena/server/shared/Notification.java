package de.fh_dortmund.inf.cw.ttt_arena.server.shared;

import java.io.Serializable;
import java.util.Date;

public class Notification implements Serializable {

	
	private static final long serialVersionUID = 3987031742488973869L;
	
	private NotificationType type;
	private String sender;
	private String text;
	
	private int row, line;
	
	private Date date;
	
	public Notification(NotificationType type, String sender, String text, Date date) {
		this.type = type;
		this.setSender(sender);
		this.setText(text);
		this.setDate(date);
	}
	
	public NotificationType getType() {
		return type;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

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
}
