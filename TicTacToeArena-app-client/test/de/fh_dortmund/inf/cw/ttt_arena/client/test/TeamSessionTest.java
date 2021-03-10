package de.fh_dortmund.inf.cw.ttt_arena.client.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ejb.NoSuchEJBException;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.fh_dortmund.inf.cw.ttt_arena.client.ServiceHandlerImpl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TeamSessionTest {
	
	private static ServiceHandlerImpl team_1, team_2;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		team_1 = new ServiceHandlerImpl();
		team_2 = new ServiceHandlerImpl();
	}
	
	/**
	 *  Zwei Teams anlegen und Prüfen dass
	 *  Anzahl Teams auf 2 erhöt wird
	 * @throws Exception
	 */
	@Test 
	public void test_01_register_team_1_2() throws Exception {
		int teams = team_1.getNumberOfRegisteredTeams();
		team_1.registerTeam("Black Panther");
		team_2.registerTeam("King Lion");
		assertEquals(teams + 2, team_1.getNumberOfRegisteredTeams());
	}
	
	@Test 
	public void test_02_login_Admins() throws Exception {
		int onlinePlayers = team_1.getNumberOfOnlinePlayers();
		
		team_1.login("Black Panther OWNER");
		assertTrue("Black Panther OWNER".equals(team_1.getPlayerName()));
		
		team_2.login("King Lion OWNER");
		assertTrue("King Lion OWNER".equals(team_2.getPlayerName()));
		
		assertEquals(onlinePlayers + 2, team_1.getNumberOfOnlinePlayers());
	}
	
	@Test 
	public void test_03_add_players_to_team_1() throws Exception {
		team_1.addPlayer("Panther One");
		team_1.addPlayer("Panther Two");
		assertEquals(3, team_1.getNumberOfTeamPlayers());
	}
	
	@Test 
	public void test_04_add_players_to_team_2() throws Exception {
		team_2.addPlayer("Lion One");
		team_2.addPlayer("Lion Two");
		assertEquals(3, team_2.getNumberOfTeamPlayers());
	}
	
	@Test(expected = NoSuchEJBException.class)
	public void test_05_clear_logins() throws Exception {
		team_1.logout();
		team_2.logout();
		team_1.getCurrentPlayer();
		team_2.getCurrentPlayer();
	}
	
}
