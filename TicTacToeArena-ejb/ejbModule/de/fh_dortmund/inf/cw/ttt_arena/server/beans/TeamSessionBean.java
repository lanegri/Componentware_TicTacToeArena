package de.fh_dortmund.inf.cw.ttt_arena.server.beans;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import de.fh_dortmund.inf.cw.ttt_arena.server.beans.interfaces.TeamSessionLocal;
import de.fh_dortmund.inf.cw.ttt_arena.server.beans.interfaces.TeamSessionRemote;
import de.fh_dortmund.inf.cw.ttt_arena.server.entities.Player;
import de.fh_dortmund.inf.cw.ttt_arena.server.entities.Team;
import de.fh_dortmund.inf.cw.ttt_arena.server.entities.TeamStatistic;

@Stateful
public class TeamSessionBean implements TeamSessionLocal, TeamSessionRemote {
	
	private Team currentteam;
	@EJB
	private TeamManagementBean teamManangement;
	
	public TeamSessionBean() {}
	
	@Override
	public String getTeamName() {
		return currentteam.getName();
	}

	@Override
	public void register(String username) throws Exception {
		teamManangement.register(username);
//		management.sendTopic(ChatMessageType.REGISTER, userName);
	}

	@Override
	public void login(String userName) throws Exception {
		currentteam = teamManangement.login(userName);
		if(currentteam != null && !currentteam.isLoggedIn()) {
			currentteam.setLoggedIn(true);
			currentteam.setLastLogin(new Date());
//			management.sendTopic(ChatMessageType.LOGIN, currentUser.getUsername());
			
		}else if(currentteam != null && currentteam.isLoggedIn()){
			if(currentteam.getLastLogin() != null)
				teamManangement.disconnect(currentteam);
		}else {
			throw new Exception("Name falsch");
		}
	}

	@Override
	@Remove
	public void logout() {
		teamManangement.logout(currentteam);
	}

	@Override
	public void disconnect() {

	}

	@Override
	@Remove(retainIfException = true)
	public void delete(String name) throws Exception {
		if(currentteam.getName().trim().equals(name.trim())) {
			teamManangement.delete(currentteam);
//			teamManangement.sendTopic(ChatMessageType.LOGOUT, currentteam.getUsername());
		}else {
			throw new Exception("Ihre angegebenen Name ist falsch");
		}
	}

	@Override
	public List<String> getOnlineTeams() {
		return teamManangement.getOnlineTeams();
	}

	@Override
	public int getNumberOfRegisteredTeams() {
		return teamManangement.getNumberOfRegisteredTeams();
	}

	@Override
	public int getNumberOfOnlineTeams() {
		// TODO Auto-generated method stub
		return teamManangement.getNumberOfOnlineTeams();
	}

	@Override
	public Team getTeam() {
		return currentteam;
	}

	@Override
	public TeamStatistic getTeamStatistic() {
		return teamManangement.getTeamStatistic(currentteam);
	}

	@Override
	public List<Player> getTeammates() {
		// TODO Auto-generated method stub
		return null;
	}

}
