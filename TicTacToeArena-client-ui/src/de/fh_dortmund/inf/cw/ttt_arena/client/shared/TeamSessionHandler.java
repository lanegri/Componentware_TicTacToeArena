package de.fh_dortmund.inf.cw.ttt_arena.client.shared;

import java.util.List;

import de.fh_dortmund.inf.cw.ttt_arena.server.entities.Player;
import de.fh_dortmund.inf.cw.ttt_arena.server.entities.Team;
import de.fh_dortmund.inf.cw.ttt_arena.server.entities.TeamStatistic;

public interface TeamSessionHandler {
	public String getTeamName();
	public void register(String username) throws Exception;
	public void login(String username) throws Exception;
	public void logout();
	public void disconnect();
	public void delete(String name) throws Exception;
	public List<String> getOnlineTeams();
	public int getNumberOfRegisteredTeams();
	public int getNumberOfOnlineTeams();
	public Team getTeam();
	public List<Player> getTeammates();
	public TeamStatistic getTeamStatistic();
}
