package de.fh_dortmund.inf.cw.ttt_arena.client.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
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
	//Team 1
	@Test 
	public void test_01_register_team_1() throws Exception {
		int teams = team_1.getNumberOfRegisteredTeams();
		team_1.register("Black Panther");
		team_2.register("King Lion");
		assertEquals(teams + 2, team_1.getNumberOfRegisteredTeams());
	}
	
	
	/**
	 *  Team einloggen und Team Name zurück liefern
	 *  Prüfen ob Anzahl online Teams auf 1 erhöht wird
	 * @throws Exception
	 */
	@Test 
	public void test_02_login_team_1() throws Exception {
		int onlineTeams = team_1.getNumberOfOnlineTeams();
		
		team_1.login("Black Panther");
		assertTrue("Black Panther".equals(team_1.getTeamName()));
		
		team_2.login("King Lion");
		assertTrue("King Lion".equals(team_2.getTeamName()));
		
		System.out.println("\"" + team_1.getTeamName() + "\" hat sich eingeloggt" );
		System.out.println("\"" + team_2.getTeamName() + "\" hat sich eingeloggt" );
		
		assertEquals(onlineTeams + 2, team_1.getNumberOfOnlineTeams());
	}
	
//	@AfterClass
//	void clear() throws Exception {
//		team_1.delete("Black Panther");
//		team_1.delete("King Lion");
//	}
	
	/**
	 * Current Team ausloggen
	 * @throws Exception
	 */
//	@Test 
//	public void test_03_logout() throws Exception {
//		serviceHandler.logout();
//		assertNull(serviceHandler.getTeamName());
//	}
	
	/**
	 *  Team löschen und Anzahl registrierten Teams prüfen (-1)
	 * @throws Exception
	 */
//	@Test 
//	public void test_05_delete() throws Exception {
//		int usersbefore = serviceHandler.getNumberOfRegisteredTeams();
//		serviceHandler.delete("King Lion");
//		assertEquals(usersbefore - 1, serviceHandler.getNumberOfRegisteredTeams());
//	}
	
	public static ServiceHandlerImpl getTeam_1() {
		return team_1;
	}

	public static void setTeam_1(ServiceHandlerImpl team_1) {
		TeamSessionTest.team_1 = team_1;
	}

	public static ServiceHandlerImpl getTeam_2() {
		return team_2;
	}

	public static void setTeam_2(ServiceHandlerImpl team_2) {
		TeamSessionTest.team_2 = team_2;
	}
}
