package de.fh_dortmund.inf.cw.ttt_arena.server.shared;

import java.io.Serializable;
import java.util.Date;

public class ClientNotification implements Serializable {

	
	private static final long serialVersionUID = 3987031742488973869L;
	
	private ClientNotificationType type;
	private String player;
	private int index;
	private char token;
	private Date date;
	
	public ClientNotification(ClientNotificationType type, String player, Date date) {
		this.type = type;
		this.setPlayer(player);
		this.setDate(date);
	}
	
	public ClientNotificationType getType() {
		return type;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public char getToken() {
		return token;
	}

	public void setToken(char token) {
		this.token = token;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
