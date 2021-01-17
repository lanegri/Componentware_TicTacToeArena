package de.fh_dortmund.inf.cw.ttt_arena.server.entities;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Statistic implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int logins;
	private int logouts;
	private int games;
	
	public Statistic() {}
	
	public int getLogins() {
		return logins;
	}
	public void setLogins(int logins) {
		this.logins = logins;
	}
	public int getLogouts() {
		return logouts;
	}
	public void setLogouts(int logouts) {
		this.logouts = logouts;
	}

	public int getGames() {
		return games;
	}

	public void setGames(int games) {
		this.games = games;
	}
	
	
}
