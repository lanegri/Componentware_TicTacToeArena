package de.fh_dortmund.inf.cw.ttt_arena.server.entities;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries({
	@NamedQuery(name = "Player.all", query = "SELECT p FROM Player p"),
	@NamedQuery(name = "Player.login", query = "SELECT p FROM Player p WHERE p.username = :username"),
	@NamedQuery(name = "Player.allnbr", query = "SELECT COUNT(p) FROM Player p"),
	@NamedQuery(name = "Player.online", query = "SELECT p.username FROM Player p WHERE p.loggedIn = 1"),
	@NamedQuery(name = "Player.onlinenbr", query = "SELECT COUNT(p) FROM Player p WHERE p.loggedIn = 1")
})
@Entity
public class Player extends EntitiesInfo{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	public static final char PLAYER_X = 'X';
//	public static final char PLAYER_O = 'O';
//	public static final char NOPLAYER =  ' ';
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Basic(optional = false)
	@Column(nullable = false)
	private String username;
	
	@Column
	private boolean loggedIn = false;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;
	
	public Player() {}
	
	public Player(int id, String username) { 
		this.id = id;
		this.username = username;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
}
