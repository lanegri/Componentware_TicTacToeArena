package de.fh_dortmund.inf.cw.shop.client.test;

import org.junit.BeforeClass;
import org.junit.Test;

import de.fh_dortmund.inf.cw.ttt_arena.client.ServiceHandlerImpl;


public class TicTacToeArenaTest {
	
	private static ServiceHandlerImpl serviceHandler;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		serviceHandler = ServiceHandlerImpl.getInstance();
	}

	@Test
	public void tom_test() {
		
	}

}
