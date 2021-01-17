package de.fh_dortmund.inf.cw.ttt_arena.client.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.fh_dortmund.inf.cw.ttt_arena.client.ServiceHandlerImpl;


public class TicTacToeArenaTest {
	
	private static ServiceHandlerImpl serviceHandler;
	
	private static final char PLAYER_X = 'X';
	private static final char PLAYER_O = 'O';
	private static final char NOPLAYER =  ' ';
	static char[][] feld;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		serviceHandler = ServiceHandlerImpl.getInstance();
		feld = new char[3][3];
	}
	
	/**
	 * SetUp: Spielfeld leeren und neues Spiel starten
	 */
	@Before
	public void fillArena(){
		for(int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				if(i == 3 && j == 2){
					feld[i][j] = '_';
				}
				else{
					feld[i][j] = NOPLAYER;
				}				
			}
		}
	}
	
	/**
	 *  Spieler (X) gewinnt das Spiel auf die erste Spalte
	 * @return true
	 */
	@Test
	public void test001_playerX_win() {
		assertTrue(playerX_win());
	}
	
	/**
	 *  Spieler (O) gewinnt das Spiel auf Diagonalen
	 * @return true
	 */
	@Test
	public void test002_playerO_win() {
		assertTrue(playerO_win());
	}
	
	/**
	 *  Unentschiden, da das Spielfeld voll ist
	 * @return false
	 */
	@Test
	public void test003_draw_full() {
		assertTrue(draw_full());
	}
	
	public boolean playerX_win() {
		feld = serviceHandler.play(feld, 0, 0, PLAYER_X);
		feld = serviceHandler.play(feld, 0, 1, PLAYER_O); 
		feld = serviceHandler.play(feld, 1, 0, PLAYER_X);
		feld = serviceHandler.play(feld, 2, 2, PLAYER_O);
		feld = serviceHandler.play(feld, 2, 0, PLAYER_X);
		
	    return serviceHandler.isWin(feld, PLAYER_X);
	}
	
	public boolean playerO_win() {
		feld = serviceHandler.play(feld, 0, 1, PLAYER_X);
		feld = serviceHandler.play(feld, 0, 0, PLAYER_O); 
		feld = serviceHandler.play(feld, 1, 2, PLAYER_X);
		feld = serviceHandler.play(feld, 1, 1, PLAYER_O);
		feld = serviceHandler.play(feld, 2, 0, PLAYER_X);
		feld = serviceHandler.play(feld, 2, 2, PLAYER_O);
		
	    return serviceHandler.isWin(feld, PLAYER_O);
	}
	
	public boolean draw_full() {
		feld = serviceHandler.play(feld, 0, 0, PLAYER_X);
		feld = serviceHandler.play(feld, 1, 0, PLAYER_O); 
		feld = serviceHandler.play(feld, 2, 0, PLAYER_X);
		feld = serviceHandler.play(feld, 2, 2, PLAYER_O);
		feld = serviceHandler.play(feld, 1, 1, PLAYER_X);
		feld = serviceHandler.play(feld, 0, 1, PLAYER_O);
		feld = serviceHandler.play(feld, 2, 1, PLAYER_X);
		feld = serviceHandler.play(feld, 1, 2, PLAYER_O);
		feld = serviceHandler.play(feld, 0, 2, PLAYER_X);
		
	    return serviceHandler.isFull(feld);
	}
	
}
