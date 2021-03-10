package de.fh_dortmund.inf.cw.ttt_arena.server.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.fh_dortmund.inf.cw.ttt_arena.server.entities.Player;
import de.fh_dortmund.inf.cw.ttt_arena.server.entities.Team;
import de.fh_dortmund.inf.cw.ttt_arena.server.shared.ClientNotification;
import de.fh_dortmund.inf.cw.ttt_arena.server.shared.ClientNotificationType;
import de.fh_dortmund.inf.cw.ttt_arena.server.shared.PlayerRole;

@Stateless
public class TeamManagementBean {
	
	@Inject
	private JMSContext context;
	@Resource(lookup = "java:global/jms/ObserverTopic")
	private Topic observerTopic;
	
	@PersistenceContext(unitName = "TictactoearenaDB")
	private EntityManager entityManager;
		
	public Team register(String name) throws Exception { 
		TypedQuery<Team> query = entityManager.createNamedQuery("Team.all", Team.class);
		
		List<Team> teams = query.getResultList();
		for(Team t: teams) {
			if(t.getName().equals(name)) {
				throw new Exception("Team mit dieser Name ist schon vorhanden");
			}
		}

		Player owner = new Player();
		owner.setNickname(name + " OWNER");
		owner.setRole(PlayerRole.OWNER);
		
		Team team = new Team();
		team.setName(name);
		team.addPlayer(owner);
		
		entityManager.persist(team);
		entityManager.flush();
		entityManager.clear();
		
		sendTopic(ClientNotificationType.REGISTER, name);
		
		return team;
	}
	
	public Player getPlayerByNick(String nickname) {
		TypedQuery<Player> query = entityManager.createNamedQuery("Player.findbynickname", Player.class);
		query.setParameter("nickname", nickname);
		Player player = query.getSingleResult();
		return player;
	}
	
	public void addPlayer(Player player, String nickname) throws Exception {
		
		if(player.isOwner()  && player.isLoggedIn()) {
			
			TypedQuery<Player> player_query = entityManager.createNamedQuery("Player.all", Player.class);
			List<Player> players = player_query.getResultList();
			
			for(Player p: players) {
				if(p.getNickname().equals(nickname)) {
					throw new Exception("Spieler mit dieser Spitzname ist schon vorhanden");
				}
			}
			
			TypedQuery<Team> query = entityManager.createNamedQuery("Team.findbyname", Team.class);
			query.setParameter("name", player.getTeam().getName());
			
			Team team = query.getSingleResult();
			
			Player newPlayer = new Player();
			newPlayer.setNickname(nickname);
			newPlayer.setRole(PlayerRole.MEMBER);
			
			team.addPlayer(newPlayer);
			entityManager.merge(team);
			entityManager.flush();
			entityManager.clear();
		}else {
			throw new Exception("Current Player hat kein Zugriff auf diese Feature");
		}
	}
	
	public Player login(String nickname) {
		Player currentPlayer = null;
		
		TypedQuery<Player> query = entityManager.createNamedQuery("Player.findbynickname", Player.class);
		query.setParameter("nickname", nickname);
		
		currentPlayer = query.getSingleResult();
				
		return currentPlayer;
	}
	
	public void logout(Player player) {
		player.setLoggedIn(false);

		player.setToken((char) 0);

		entityManager.merge(player);
		entityManager.flush();
		
		sendTopic(ClientNotificationType.LOGOUT, player.getNickname());
	}

	public void disconnect(Player player) {
		sendTopic(ClientNotificationType.DISCONNECT, player.getNickname());
	}

	public void delete(Player player) {
		entityManager.remove(player);
		entityManager.flush();
	}

	public int getNumberOfRegisteredTeams() {
		TypedQuery<Number> query = entityManager.createNamedQuery("Team.allnbr", Number.class);
		
		return query.getSingleResult().intValue();
	}
	
	public List<String> getOnlinePlayersNames() {
		TypedQuery<String> query = entityManager.createNamedQuery("Player.online", String.class);
		
		List<String> onlineUsers = query.getResultList();
		
		return new ArrayList<String>(onlineUsers);
	}

	public int getNumberOfRegisteredPlayers() {
		TypedQuery<Number> query = entityManager.createNamedQuery("Player.allnbr", Number.class);
		
		return query.getSingleResult().intValue();
	}

	public int getNumberOfOnlinePlayers() {
		TypedQuery<Number> query = entityManager.createNamedQuery("Player.onlinenbr", Number.class);
		
		return query.getSingleResult().intValue();
	}
	
	public List<Player> getTeammates() {
		return null;
	}
	
	public int getNumberOfTeamPlayers(int id) {
		TypedQuery<Number> query = entityManager.createNamedQuery("Player.allnbrTeam", Number.class);
		query.setParameter("id", id);
		return query.getSingleResult().intValue();
	}
		
	public boolean isAnyPlayerOnline() {
		
		return (getNumberOfOnlinePlayers() > 0) ? true : false;
	}
	
	public void sendTopic(ClientNotificationType notificationType, String player) {
		try {
			
			ClientNotification notification = new ClientNotification(notificationType, player, new Date());
			
			ObjectMessage  om = context.createObjectMessage();
			om.setObject(notification);
			context.createProducer().send(observerTopic, om);
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
