package de.fh_dortmund.inf.cw.ttt_arena.client.ui;

import java.util.Scanner;

//import tictactoe.Spieler;

public class GameArena {
	
	char[][] feld; 
	int z = 1, s = 1;
	//Spieler player;
	
	public GameArena(){
		feld = new char[3][3];
	}
	
	public void spielfeldAusgaben(){
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
	public void startGame(){
		for(int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				System.out.print("   | " + feld[i][j]);
			}
			System.out.println("  |" + "\n"  + "   -------------------");
		}
	}
//	public void sign1(int x1, int y1, Spieler p){
//		if(feld[x1][y1] == ' ')
//			feld[x1][y1] = p.getSpieler1();
//		else
//			System.out.println("Feld schon besetzt");
//	}
//	public void sign2(int x2, int y2, Spieler p){
//		if(feld[x2][y2] == ' ')
//			feld[x2][y2] = p.getSpieler2();
//		else
//			System.out.println("Feld ist schon besetzt");
//		//while et message
//	}
	
	public boolean gewonnen(){
		boolean won = true;
		
		for(int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				if(feld[i][j] == 'X'){
					if(!(i == j)){
						won = false;
					}
				}
				else if(feld[i][j] == 'O'){
					won = true;
				}
			}
		}
		return won;
	}
	
	public static void main(String[] args) {
		
		GameArena champs = new GameArena();
//		Spieler spieler = new Spieler('X', 'O');
		
		
		Scanner sc = new Scanner(System.in);
		
		//int z = 1;
		//System.out.println(z++);
		System.out.println("   -------------------");
		champs.spielfeldAusgaben();
		champs.startGame();
		
		do{
			System.out.println("Spieler1 geben Sie ihre Koordinaten ein\n X");
			int x = sc.nextInt();
			System.out.println("Y");
			int y = sc.nextInt();
//			champs.sign1(x, y, spieler);
			champs.startGame();
			System.out.println("Spieler2 geben Sie ihre Koordinaten ein\n X");
			int x2 = sc.nextInt();
			System.out.println("Y");
			int y2 = sc.nextInt();
//			champs.sign2(x2, y2, spieler);
			champs.startGame();
		}while(true);//(!champs.gewonnen());
}
}
