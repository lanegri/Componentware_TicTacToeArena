package de.fh_dortmund.inf.cw.ttt_arena.client.ui;

import java.util.Scanner;

//import common.Player;
import de.fh_dortmund.inf.cw.ttt_arena.client.shared.ServiceHandler;
import de.fh_dortmund.inf.cw.ttt_arena.client.shared.TicTacToeArenaHandler;

public class GameArena {
	
	public static final char PLAYER_X = 'X';
	public static final char PLAYER_O = 'O';
	public static final char NOPLAYER =  ' ';
	char[][] feld;
	Scanner sc = new Scanner(System.in);
	
	private ServiceHandler serviceHandler;
	private TicTacToeArenaHandler tttHandler;
	
	public GameArena() {
		this.serviceHandler = ServiceHandlerHelper.getInstance().getServiceHandler();
		this.tttHandler = ServiceHandlerHelper.getInstance().getTicTacToeArenaHandler();
		
		feld = new char[3][3];
	}
	
	/**
	 * Spielfeld einmalig ausfüllen
	 */
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
	
	public void executeGame(){
//		showArena();
		fillArena();
		
		do{
			System.out.println("Spieler1 (X)");
			int x = sc.nextInt();
			int y = sc.nextInt();
			
			this.feld = tttHandler.play(feld, x, y, PLAYER_X);
			if(tttHandler.isWin(this.feld, PLAYER_X)) {
				System.out.println(PLAYER_X + " hat gewonnen");
				break;
			}
			showArena();
			System.out.println("Spieler2 (O)");
			int x2 = sc.nextInt();
			int y2 = sc.nextInt();
			
			this.feld = tttHandler.play(feld, x2, y2, PLAYER_O);
			if(tttHandler.isWin(this.feld, PLAYER_O)) {
				System.out.println(PLAYER_O + " hat gewonnen");
				break;
			}
			showArena();
		}while(!tttHandler.isFull(this.feld));
	}
	
}
