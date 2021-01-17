package de.fh_dortmund.inf.cw.ttt_arena.server.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries({
	@NamedQuery(name = "Team.all", query = "SELECT t FROM Team t"),
	@NamedQuery(name = "Team.login", query = "SELECT t FROM Team t WHERE t.name = :name"),
	@NamedQuery(name = "Team.allnbr", query = "SELECT COUNT(t) FROM Team t"),
	@NamedQuery(name = "Team.online", query = "SELECT t.name FROM Team t WHERE t.loggedIn = 1"),
	@NamedQuery(name = "Team.onlinenbr", query = "SELECT COUNT(t) FROM Team t WHERE t.loggedIn = 1")
})
@Entity
public class Team extends EntitiesInfo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Basic(optional = false)
	@Column(nullable = false)
	private String name;
	@Column
	private boolean loggedIn = false;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;
	
//	private List<Player> players;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "STATISTIC_ID", referencedColumnName = "id", unique = true)
	private TeamStatistic statistic;
	
	public Team() {}
	
//	public List<Player> getPlayers() {
//		return players;
//	}
//
//	public void addPlayer(Player player) {
//		this.players.add(player);
//	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TeamStatistic getStatistic() {
		return statistic;
	}

	public void setStatistic(TeamStatistic statistic) {
		this.statistic = statistic;
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
