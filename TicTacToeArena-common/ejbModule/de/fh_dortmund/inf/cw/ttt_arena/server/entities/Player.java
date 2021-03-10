package de.fh_dortmund.inf.cw.ttt_arena.server.entities;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import de.fh_dortmund.inf.cw.ttt_arena.server.shared.PlayerRole;

@NamedQueries({
	@NamedQuery(name = "Player.all", query = "SELECT p FROM Player p"),
	@NamedQuery(name = "Player.findbynickname", query = "SELECT p FROM Player p WHERE p.nickname = :nickname"),
	@NamedQuery(name = "Player.allnbr", query = "SELECT COUNT(p) FROM Player p"),
	@NamedQuery(name = "Player.allnbrTeam", query = "SELECT COUNT(p) FROM Player p WHERE p.team.id = :id"),
	@NamedQuery(name = "Player.online", query = "SELECT p.nickname FROM Player p WHERE p.loggedIn = 1"),
	@NamedQuery(name = "Player.onlinenbr", query = "SELECT COUNT(p) FROM Player p WHERE p.loggedIn = 1")
})
@Entity
public class Player extends EntitiesInfo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Basic(optional = false)
	@Column(nullable = false)
	private String nickname;
	private char token;
	@ManyToOne(cascade=CascadeType.ALL)
	private Team team;
	@Column
	private boolean loggedIn = false;
	
	private PlayerRole role;
//	@Column
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date lastLogin;
	
	public Player() {}
	
	public int getId() {
		return id;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
//
//	public Date getLastLogin() {
//		return lastLogin;
//	}
//
//	public void setLastLogin(Date lastLogin) {
//		this.lastLogin = lastLogin;
//	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public char getToken() {
		return token;
	}

	public void setToken(char token) {
		this.token = token;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public PlayerRole getRole() {
		return this.role;
	}
	
	public void setRole(PlayerRole role) {
		this.role = role;
	}
	
	public boolean isOwner() {
		return this.role == PlayerRole.OWNER;
	}
}
