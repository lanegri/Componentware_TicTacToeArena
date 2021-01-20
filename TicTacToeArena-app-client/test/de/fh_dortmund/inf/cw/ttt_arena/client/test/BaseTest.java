package de.fh_dortmund.inf.cw.ttt_arena.client.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import de.fh_dortmund.inf.cw.ttt_arena.client.ServiceHandlerImpl;

public abstract class BaseTest {

private static ServiceHandlerImpl team_1, team_2;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		team_1 = ServiceHandlerImpl.getInstance();
		team_2 = ServiceHandlerImpl.getInstance();
	}
	
	//Team 1
	@Test 
	public void test_01_register_team_1() throws Exception {
		int teams = team_1.getNumberOfRegisteredTeams();
		team_1.register("Black Panther");
		team_2.register("King Lion");
		assertEquals(teams + 2, team_1.getNumberOfRegisteredTeams());
	}

}
