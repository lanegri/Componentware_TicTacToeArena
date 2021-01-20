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
import de.fh_dortmund.inf.cw.ttt_arena.server.shared.ClientNotificationType;
import de.fh_dortmund.inf.cw.ttt_arena.server.shared.PlayerToken;

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
		teamManangement.sendTopic(ClientNotificationType.REGISTER, username);
	}

	@Override
	public void login(String username) throws Exception {
		
		if(getNumberOfOnlineTeams() < 2) {
			
			currentteam = teamManangement.login(username);
			if(currentteam != null && !currentteam.isLoggedIn()) {
				
				if(getNumberOfOnlineTeams() == 0) {
					currentteam.setToken(PlayerToken.PLAYER_X);
				}else {
					currentteam.setToken(PlayerToken.PLAYER_O);
				}
				
				currentteam.setLoggedIn(true);
				currentteam.setLastLogin(new Date());
				
				teamManangement.sendTopic(ClientNotificationType.LOGIN, currentteam.getName());
				
			}
//			else if(currentteam != null && currentteam.isLoggedIn()){
//				if(currentteam.getLastLogin() != null)
//					disconnect();
//			}
			else {
				throw new Exception("Team mit deisen Name wurde leider nicht gefunden!");
			}
			
			
		}else {
			disconnect();
		}
	}

	@Override
	@Remove
	public void logout() {
		teamManangement.logout(currentteam);
	}

	@Override
	public void disconnect() {
		teamManangement.disconnect(currentteam);
	}

	@Override
	@Remove(retainIfException = true)
	public void delete(String name) throws Exception {
		if(currentteam.getName().trim().equals(name.trim())) {
			teamManangement.delete(currentteam);
			teamManangement.sendTopic(ClientNotificationType.LOGOUT, currentteam.getName());
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

	@Override
	public char getToken() {
		return currentteam.getToken();
	}

}
