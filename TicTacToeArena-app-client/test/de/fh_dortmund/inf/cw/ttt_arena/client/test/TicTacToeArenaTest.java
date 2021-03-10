package de.fh_dortmund.inf.cw.ttt_arena.client.test;

import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CountDownLatch;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.fh_dortmund.inf.cw.ttt_arena.client.ServiceHandlerImpl;
import de.fh_dortmund.inf.cw.ttt_arena.server.shared.ClientNotification;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TicTacToeArenaTest implements Observer{
	
	private static ServiceHandlerImpl team_1, team_2;
	private CountDownLatch observerLatch;

	static char[] arena;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}
	
	@Before
	public void setUp() {
		team_1 = new ServiceHandlerImpl();
		team_2 = new ServiceHandlerImpl();
		
		team_1.addObserver(this);
		team_2.addObserver(this);
		observerLatch = new CountDownLatch(1);
	}
	
	@After
	public void resetPlayer() {
		team_1.deleteObserver(this);
		team_2.deleteObserver(this);
		team_1.logout();
		team_2.logout();
	}
	
	
	/**
	 *  Spieler (X) gewinnt das Spiel Diagonal
	 * @return true
	 * @throws Exception 
	 */
	@Test(timeout=60000)
	public void test_06_player_X_win() throws Exception {
		assertTrue("Panther One".equals(player_X_win()));
	}
	
	/**
	 *  Spieler (O) gewinnt das Spiel auf zweite Spalte
	 * @return true
	 * @throws Exception 
	 */
	@Test(timeout=60000)
	public void test_07_player_O_win() throws Exception {
		assertTrue("Lion Two".equals(player_O_win()));
	}
	
	/**
	 *  Unentschiden, da das Spielfeld voll ist
	 * @return false  
	 * @throws Exception 
	 */
	@Test(timeout=60000)
	public void test_08_draw_full() throws Exception {
		draw();
	}

	
	public String player_X_win() throws Exception {
		
		//Spieler einloggen
		team_1.login("Panther One");
		team_2.login("Lion One");
		System.out.println("\n ---------- Partie 1 ----------");
		team_1.startGame();

		team_1.play(1);
		team_2.play(2);
		team_1.play(5);
		team_2.play(4);
		team_1.play(9);
		team_2.play(3);
	    
	    return team_2.finishAsVictory(team_1.getCurrentPlayer().getNickname());
	}
	
	public String player_O_win() throws Exception {
				
		//Spieler einloggen
		team_1.login("Panther Two");
		team_2.login("Lion Two");
		System.out.println("\n ---------- Partie 2 ----------");
		team_2.startGame();

		team_1.play(1);
		team_2.play(2);
		team_1.play(3);
		team_2.play(5);
		team_1.play(4);
		team_2.play(8);
		team_1.play(8);
		
		return team_1.finishAsVictory(team_2.getCurrentPlayer().getNickname());
	}
	
	public boolean draw() throws Exception {
				
		//Spieler einloggen
		team_1.login("Black Panther OWNER");
		team_2.login("King Lion OWNER");
		System.out.println("\n ---------- Partie 3 ----------");
		team_1.startGame();

		team_1.play(1);
		team_2.play(3); 
		team_1.play(2); 
		team_2.play(4); 
		team_1.play(6); 
		team_2.play(5); 
		team_1.play(7); 
		team_2.play(8); 
		team_1.play(9);
		team_2.play(1);
				
	    return team_1.isFull();
	}
	
	public void print() {
		int i = 0;
		System.out.println( "   +-----+-----+----+");
		for(char c: arena) {
			System.out.print("   | " + c);
			
			i++;
			if((i % 3) == 0) 
				System.out.println("  |" + "\n"  + "   +-----+-----+----+");
			
		}
	}

	@Override
	public void update(Observable o, Object arg) {

		if (arg instanceof ClientNotification) {
	    	ClientNotification notification = (ClientNotification)arg;
	    	DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	    	switch (notification.getType()) {
	    		case REGISTER:
	    			printlog(o, "Neue Registrierung");
	    			break;
	    		case LOGIN:
	    			printlog(o, notification.getPlayer() + " hat den Arena betreten");
		  			break;
	    		case LOGOUT:
	    			printlog(o, notification.getPlayer() + " hat um " + dateFormat.format(notification.getDate()) + " den Arena verlassen.");
		          break;
	    		case TOKEN:
//	    			System.out.println("Token");
	    			((ServiceHandlerImpl) o).set(notification.getIndex(), notification.getPlayer(), notification.getToken());
	    			break;
		        case STATISTIC:
		          printlog(o, "Statistik um " + dateFormat.format(notification.getDate()) + " Uhr:\n");
		          break;
				case DISCONNECT:
					printlog(o, "Disconnect");
					break;
				case WIN:
					arena = ((ServiceHandlerImpl) o).getArena();
					print();
					printlog(o, notification.getPlayer() + " hat gewonnen\n");
					System.out.println("WON");
		          break;
				case DRAW:
					arena = ((ServiceHandlerImpl) o).getArena();
					print();
					printlog(o, "Unentschieden");
					System.out.println("DRAW");
					break;
				case TIMER:
					System.out.println("Timer");
					break;
				default:
					break;
	    	} 
	    	observerLatch.countDown();
	    } 
	}
	
	private void printlog(Object o, String param) {
		System.out.println("[" + o + "] " + param);
	}
}
