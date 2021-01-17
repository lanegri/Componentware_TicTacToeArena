package de.fh_dortmund.inf.cw.ttt_arena.client.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.fh_dortmund.inf.cw.ttt_arena.client.ServiceHandlerImpl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TeamSessionTest {
	
	private static ServiceHandlerImpl serviceHandler;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		serviceHandler = ServiceHandlerImpl.getInstance();
	}
	
	/**
	 *  Zwei Teams anlegen und Prüfen dass
	 *  Anzahl Teams auf 2 erhöt wird
	 * @throws Exception
	 */
	@Test 
	public void test_01_register() throws Exception {
		int teams = serviceHandler.getNumberOfRegisteredTeams();
		serviceHandler.register("Black Panther");
		serviceHandler.register("King Lion");
		assertEquals(teams + 2, serviceHandler.getNumberOfRegisteredTeams());
	}
	
	/**
	 *  Team einloggen und Team Name zurück liefern
	 *  Prüfen ob Anzahl online Teams auf 1 erhöht wird
	 * @throws Exception
	 */
	@Test 
	public void test_02_login() throws Exception {
		int onlineTeams = serviceHandler.getNumberOfOnlineTeams();
		serviceHandler.login("Black Panther");
		assertNotNull(serviceHandler.getTeamName());
		System.out.println(serviceHandler.getTeamName());
		assertEquals(onlineTeams + 1, serviceHandler.getNumberOfOnlineTeams());
	}
	
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

}
