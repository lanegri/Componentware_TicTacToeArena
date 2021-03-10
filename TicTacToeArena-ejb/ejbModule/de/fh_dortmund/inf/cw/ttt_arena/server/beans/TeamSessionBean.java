package de.fh_dortmund.inf.cw.ttt_arena.server.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import de.fh_dortmund.inf.cw.ttt_arena.server.beans.interfaces.TeamSessionLocal;
import de.fh_dortmund.inf.cw.ttt_arena.server.beans.interfaces.TeamSessionRemote;
import de.fh_dortmund.inf.cw.ttt_arena.server.entities.Player;
import de.fh_dortmund.inf.cw.ttt_arena.server.shared.ClientNotificationType;
import de.fh_dortmund.inf.cw.ttt_arena.server.shared.PlayerToken;

@Stateful
public class TeamSessionBean implements TeamSessionLocal, TeamSessionRemote {
	
	private Player currentplayer;
	@EJB
	private TeamManagementBean teamManangement;
		
	@Override
	public String getPlayerName() {
		return currentplayer.getNickname();
	}

	@Override
	public void registerTeam(String name) throws Exception {
		teamManangement.register(name);
		teamManangement.sendTopic(ClientNotificationType.REGISTER, name);
	}

	@Override
	public void login(String nickname) throws Exception {
		
		if(getNumberOfOnlinePlayers() < 2) {
			
			currentplayer = teamManangement.login(nickname);
			if(currentplayer != null && !currentplayer.isLoggedIn()) {
				
				if(!teamManangement.isAnyPlayerOnline()) {
					currentplayer.setToken(PlayerToken.PLAYER_X);
				}else {
					currentplayer.setToken(PlayerToken.PLAYER_O);
				}
				
				currentplayer.setLoggedIn(true);
				
				teamManangement.sendTopic(ClientNotificationType.LOGIN, currentplayer.getNickname());
				
			} else {
				throw new Exception("Spieler mit deisen Name wurde leider nicht gefunden oder Sie sing schon eingeloggt!");
			}
			
			
		}else {
			disconnect();
		}
	}

	@Override
	@Remove()
	public void logout() {
		teamManangement.logout(currentplayer);
		currentplayer = null;
	}

	@Override
	public void disconnect() {
		teamManangement.disconnect(currentplayer);
	}

	@Override
	@Remove(retainIfException = true)
	public void delete(String nickname) throws Exception {
		if(currentplayer.getNickname().trim().equals(nickname.trim())) {
			teamManangement.delete(currentplayer);
			teamManangement.sendTopic(ClientNotificationType.LOGOUT, currentplayer.getNickname());
		}else {
			throw new Exception("Ihre angegebenen Name ist falsch");
		}
	}

	@Override
	public int getNumberOfRegisteredTeams() {
		return teamManangement.getNumberOfRegisteredTeams();
	}

	@Override
	public List<String> getOnlinePlayersNames() {
		return null;
	}

	@Override
	public Player getCurrentPlayer() {
		return this.currentplayer;
	}

	@Override
	public void addPlayer(String nickname) throws Exception {
		teamManangement.addPlayer(this.currentplayer, nickname);
	}

	@Override
	public int getNumberOfOnlinePlayers() {
		return teamManangement.getNumberOfOnlinePlayers();
	}
	
	@Override
	public int getNumberOfTeamPlayers() {
		return this.teamManangement.getNumberOfTeamPlayers(this.currentplayer.getTeam().getId());
	}
}
