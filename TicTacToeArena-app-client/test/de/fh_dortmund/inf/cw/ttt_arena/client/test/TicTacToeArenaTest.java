package de.fh_dortmund.inf.cw.ttt_arena.client.test;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.fh_dortmund.inf.cw.ttt_arena.client.ServiceHandlerImpl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TicTacToeArenaTest {
	
//	private static ServiceHandlerImpl team_1, team_2;
//	
//	private static final char PLAYER_X = 'X';
//	private static final char PLAYER_O = 'O';
//	private static final char NOPLAYER =  ' ';
	static char[][] feld;
		
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		feld = new char[3][3];
	}
	
	/**
	 * SetUp: Spielfeld leeren und neues Spiel starten
	 */
	@Before
	public void fillArena(){
		System.out.println("\nNeues Partie");
		for(int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				if(i == 3 && j == 2){
					feld[i][j] = '_';
				}
				else{
					feld[i][j] = ' ';
				}				
			}
		}
	}
	
	/**
	 *  Spielfeld ausgeben
	 */
	public void showArena(){
		System.out.println( "   +-----+-----+----+");
		for(int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				System.out.print("   | " + feld[i][j]);
			}
			System.out.println("  |" + "\n"  + "   +-----+-----+----+");
		}
		
	}
	/**
	 *  Spieler (X) gewinnt das Spiel auf die erste Spalte
	 * @return true
	 */
	@Test
	public void test_03_playerX_win() {
		assertTrue(playerX_win());
	}
	
	/**
	 *  Spieler (O) gewinnt das Spiel auf Diagonalen
	 * @return true
	 */
	@Test
	public void test_04_playerO_win() {
		assertTrue(playerO_win());
	}
	
	/**
	 *  Unentschiden, da das Spielfeld voll ist
	 * @return false
	 */
	@Test
	public void test_05_draw_full() {
		assertTrue(draw_full());
	}

	
	public boolean playerX_win() {
		feld = TeamSessionTest.getTeam_1().play(feld, 0, 0, TeamSessionTest.getTeam_1().getToken()); showArena();
		feld = TeamSessionTest.getTeam_2().play(feld, 0, 1, TeamSessionTest.getTeam_2().getToken()); showArena();
		feld = TeamSessionTest.getTeam_1().play(feld, 1, 0, TeamSessionTest.getTeam_1().getToken()); showArena();
		feld = TeamSessionTest.getTeam_2().play(feld, 2, 2, TeamSessionTest.getTeam_2().getToken()); showArena();
		feld = TeamSessionTest.getTeam_1().play(feld, 2, 0, TeamSessionTest.getTeam_1().getToken()); showArena();
	    
		System.out.println("Team Black Panther hat gewonnen \n Feld wird gelert");

	    return TeamSessionTest.getTeam_1().isWin(feld, TeamSessionTest.getTeam_1().getToken());
	}
	
	public boolean playerO_win() {
		feld = TeamSessionTest.getTeam_1().play(feld, 0, 1, TeamSessionTest.getTeam_1().getToken()); showArena();
		feld = TeamSessionTest.getTeam_2().play(feld, 0, 0, TeamSessionTest.getTeam_2().getToken()); showArena();
		feld = TeamSessionTest.getTeam_1().play(feld, 1, 2, TeamSessionTest.getTeam_1().getToken()); showArena();
		feld = TeamSessionTest.getTeam_2().play(feld, 1, 1, TeamSessionTest.getTeam_2().getToken()); showArena();
		feld = TeamSessionTest.getTeam_1().play(feld, 2, 0, TeamSessionTest.getTeam_1().getToken()); showArena();
		feld = TeamSessionTest.getTeam_2().play(feld, 2, 2, TeamSessionTest.getTeam_2().getToken()); showArena();
		
		System.out.println("Team King Lion hat gewonnen \n Feld wird gelert");
		
	    return TeamSessionTest.getTeam_2().isWin(feld, TeamSessionTest.getTeam_2().getToken());
	}
	
	public boolean draw_full() {
		feld = TeamSessionTest.getTeam_1().play(feld, 0, 0, TeamSessionTest.getTeam_1().getToken()); showArena();
		feld = TeamSessionTest.getTeam_2().play(feld, 1, 0, TeamSessionTest.getTeam_2().getToken()); showArena();
		feld = TeamSessionTest.getTeam_1().play(feld, 2, 0, TeamSessionTest.getTeam_1().getToken()); showArena();
		feld = TeamSessionTest.getTeam_2().play(feld, 2, 2, TeamSessionTest.getTeam_2().getToken()); showArena();
		feld = TeamSessionTest.getTeam_1().play(feld, 1, 1, TeamSessionTest.getTeam_1().getToken()); showArena();
		feld = TeamSessionTest.getTeam_2().play(feld, 0, 1, TeamSessionTest.getTeam_2().getToken()); showArena();
		feld = TeamSessionTest.getTeam_1().play(feld, 2, 1, TeamSessionTest.getTeam_1().getToken()); showArena();
		feld = TeamSessionTest.getTeam_2().play(feld, 1, 2, TeamSessionTest.getTeam_2().getToken()); showArena();
		feld = TeamSessionTest.getTeam_1().play(feld, 0, 2, TeamSessionTest.getTeam_1().getToken()); showArena();
		
		System.out.println("Unentschieden");
		
	    return TeamSessionTest.getTeam_1().isFull(feld);
	}
	
}
