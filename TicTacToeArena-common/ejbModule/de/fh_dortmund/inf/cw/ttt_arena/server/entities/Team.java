package de.fh_dortmund.inf.cw.ttt_arena.server.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@NamedQueries({
	@NamedQuery(name = "Team.all", query = "SELECT t FROM Team t"),
	@NamedQuery(name = "Team.findbyname", query = "SELECT t FROM Team t WHERE t.name = :name"),
	@NamedQuery(name = "Team.allnbr", query = "SELECT COUNT(t) FROM Team t")
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
	
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="team")
	private Set<Player> players = new HashSet<>();
	
	public Team() {	}
	
	public Set<Player> getPlayers() {
		return players;
	}

	public void addPlayer(Player player) {
		player.setTeam(this);
		this.players.add(player);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
