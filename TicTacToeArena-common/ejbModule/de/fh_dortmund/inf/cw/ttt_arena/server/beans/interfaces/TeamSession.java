package de.fh_dortmund.inf.cw.ttt_arena.server.beans.interfaces;

import java.util.List;

import de.fh_dortmund.inf.cw.ttt_arena.server.entities.Player;

public interface TeamSession {
	
	public void registerTeam(String name) throws Exception; //Team
	public void addPlayer(String nickname) throws Exception;
	
	public String getPlayerName();
	public Player getCurrentPlayer();
	public int getNumberOfOnlinePlayers();
	public List<String> getOnlinePlayersNames();

	public void login(String nickname) throws Exception; //Player
	public void logout();
	public void disconnect();
	public void delete(String name) throws Exception;
		 	
	public int getNumberOfRegisteredTeams();
	public int getNumberOfTeamPlayers();	
	
}
