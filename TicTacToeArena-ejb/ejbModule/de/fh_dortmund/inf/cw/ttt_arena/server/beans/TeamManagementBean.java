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
import de.fh_dortmund.inf.cw.ttt_arena.server.entities.TeamStatistic;
import de.fh_dortmund.inf.cw.ttt_arena.server.shared.ClientNotification;
import de.fh_dortmund.inf.cw.ttt_arena.server.shared.ClientNotificationType;

@Stateless
public class TeamManagementBean {
	
	@Inject
	private JMSContext context;
	@Resource(lookup = "java:global/jms/ObserverTopic")
	private Topic observerTopic;
	
	@PersistenceContext(unitName = "TictactoearenaDB")
	private EntityManager entityManager;
	
	public TeamManagementBean() {}
	
	public Team register(String name) throws Exception {
		TypedQuery<Team> query = entityManager.createNamedQuery("Team.all", Team.class);
		
		List<Team> teams = query.getResultList();
		for(Team t: teams) {
			if(t.getName().equals(name)) {
				throw new Exception("Team mit dieser Name ist schon vorhanden");
			}
		}
		
		TeamStatistic stat = new TeamStatistic();
		stat.setLogins(0);
		stat.setLogouts(0);
		
		Team team = new Team();
		team.setName(name);
		team.setStatistic(stat);
//		user.setCreatedAt(new Date());
		
		entityManager.persist(team);
		entityManager.flush();
		
		sendTopic(ClientNotificationType.REGISTER, name);
		
		return team;
	}

	public Team login(String name) {
		Team currentTeam = null;
		TeamStatistic stat;
		
		TypedQuery<Team> query = entityManager.createNamedQuery("Team.all", Team.class);
		
		List<Team> teams = query.getResultList();
		
		for(Team team: teams) {
			if(team.getName().trim().equals(name.trim())) {

				currentTeam = team;
				
				stat = currentTeam.getStatistic();
				stat.setLastLogin(new Date());
				int logins = stat.getLogins();
				logins++;
				stat.setLogins(logins);
				
				currentTeam.setStatistic(stat);
			}
		}
				
		return currentTeam;
	}
	
	public void logout(Team team) {
		TeamStatistic stat = team.getStatistic();
		team.setLoggedIn(false);
		char c = 0;
		team.setToken(c);
		int logouts = stat.getLogouts();
		logouts++;
		stat.setLogouts(logouts);
		entityManager.merge(team);
		entityManager.flush();
		
		sendTopic(ClientNotificationType.LOGOUT, team.getName());
	}

	public void disconnect(Team team) {
		sendTopic(ClientNotificationType.DISCONNECT, team.getName());
	}

	public void delete(Team team) {
		team = entityManager.merge(team);
		entityManager.remove(team);
		entityManager.flush();
	}

	public List<String> getOnlineTeams() {
		TypedQuery<String> query = entityManager.createNamedQuery("Team.online", String.class);
		
		List<String> onlineUsers = query.getResultList();
		
		return new ArrayList<String>(onlineUsers);
	}

	public int getNumberOfRegisteredTeams() {
		TypedQuery<Number> query = entityManager.createNamedQuery("Team.allnbr", Number.class);
		
		return query.getSingleResult().intValue();
	}

	public int getNumberOfOnlineTeams() {
		TypedQuery<Number> query = entityManager.createNamedQuery("Team.onlinenbr", Number.class);
		
		return query.getSingleResult().intValue();
	}

 
	public TeamStatistic getTeamStatistic(Team team) {
		TypedQuery<Team> query = entityManager.createNamedQuery("Team.all", Team.class);
		
		List<Team> teams = query.getResultList();
		
		for(Team t: teams) {
			if(t.getName().trim().equals(team.getName().trim())) {
				team = t;
			}
		}
		return team.getStatistic();
	}
	
	public List<Player> getTeammates() {
		return null;
	}
	
	public boolean isAnyUseronline() {
		
		return (getNumberOfOnlineTeams() > 0) ? true : false;
	}
	
	public void sendTopic(ClientNotificationType notificationType, String sender) {
		try {
			
			ClientNotification notification = new ClientNotification(notificationType, sender, new Date());
			
			ObjectMessage  om = context.createObjectMessage();
			om.setObject(notification);
			context.createProducer().send(observerTopic, om);
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
