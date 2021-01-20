package de.fh_dortmund.inf.cw.ttt_arena.client.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import de.fh_dortmund.inf.cw.ttt_arena.client.shared.ClientNotificationHandler;
//import common.Player;
import de.fh_dortmund.inf.cw.ttt_arena.client.shared.ServiceHandler;
import de.fh_dortmund.inf.cw.ttt_arena.client.shared.TeamSessionHandler;
import de.fh_dortmund.inf.cw.ttt_arena.client.shared.TicTacToeArenaHandler;
import de.fh_dortmund.inf.cw.ttt_arena.server.shared.ClientNotification;
import de.fh_dortmund.inf.cw.ttt_arena.server.shared.ClientNotificationType;

public class GameArena implements Observer{
		
	char[][] feld;
		
	private ServiceHandler serviceHandler;
	private TicTacToeArenaHandler tttHandler;
	private ClientNotificationHandler notificationHandler;
	private TeamSessionHandler teamSessionHandler;
	
	public GameArena() {
		this.serviceHandler = ServiceHandlerHelper.getInstance().getServiceHandler();
		this.tttHandler = ServiceHandlerHelper.getInstance().getTicTacToeArenaHandler();
		this.notificationHandler = ServiceHandlerHelper.getInstance().getClientNotificationHandler();
		this.teamSessionHandler = ServiceHandlerHelper.getInstance().getTeamSessionHandler();
		
		feld = new char[3][3];
		
		this.serviceHandler.addObserver(this);
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
	
	
	public void executeGame() throws Exception{
		Scanner input = new Scanner(System.in);

		System.out.println("Logen Sie sich oder registrieren Sie sich um den Arena zu betreten! \n 1 == Login 2 == Registrieren");
		int action = input.nextInt();
		input.nextLine();
		System.out.println("Geben Sie Ihren Benutzername ein!");
		String username = input.nextLine();
		
		switch(action) {
			case 1:
				teamSessionHandler.login(username);
				break;
			case 2:
				teamSessionHandler.register(username);
				teamSessionHandler.login(username);
				break;
			default:
				break;
		}
		
//		showArena();
		fillArena();
		
		do{
			System.out.println("Spielen");
			int x = input.nextInt();
			int y = input.nextInt();
			
//			this.feld = tttHandler.play(feld, x, y, PLAYER_X);
			notificationHandler.sendNotification(x, y);
//			if(tttHandler.isWin(this.feld, PLAYER_X)) {
//				System.out.println(PLAYER_X + " hat gewonnen");
//				break;
//			}
//			showArena();
//			System.out.println("Spieler2 (O)");
//			int x2 = sc.nextInt();
//			int y2 = sc.nextInt();
//			
//			this.feld = tttHandler.play(feld, x2, y2, PLAYER_O);
//			if(tttHandler.isWin(this.feld, PLAYER_O)) {
//				System.out.println(PLAYER_O + " hat gewonnen");
//				break;
//			}
//			showArena();
		}while(true);
		
	}
	
	public void update(Observable o, Object arg) {
	    if (arg instanceof ClientNotification) {
	    	ClientNotification notification = (ClientNotification)arg;
	    	String strMessage = "";
	    	DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	    	
	      switch (notification.getType()) {
	        case REGISTER:
	          break;
	        case LOGIN:
	        	List<String> onlineTeams = teamSessionHandler.getOnlineTeams();
	        		        	
	        	if(onlineTeams.size() == 2) {
	        		System.out.print("Partie beginnt: " + dateFormat.format(notification.getDate()) + " Uhr. \n Teams: ");
		  			for(String team: teamSessionHandler.getOnlineTeams()) {
		  				System.out.print(team + " ");
		  			}
		  			System.out.println();
	        	}else {
	        		System.out.println("warten aug gegner ...");
	        	}
	          	
	  			break;
	        case LOGOUT:
	          strMessage = String.valueOf(notification.getSender()) + " hat sich um " + dateFormat.format(notification.getDate()) + " Uhr abgemeldet.";
//	          updateUserList();
	          break;
	        case TOKEN:
	          strMessage = String.valueOf(notification.getSender()) + " um " + dateFormat.format(notification.getDate()) + " Uhr:\n";
//	          feld[notification.getRow()][notification.getLine()] = notification.getToken();
	          feld = tttHandler.play(feld, notification.getRow(), notification.getLine(), notification.getToken());
	          System.out.println(strMessage);
	          showArena();
	          break;
	        case STATISTIC:
	          strMessage = "Statistik um " + dateFormat.format(notification.getDate()) + " Uhr:\n";
//	          strMessage = String.valueOf(strMessage) + notification.getText() + "\n";
	          break;
			case DISCONNECT:
				break;
			case WIN:
	          strMessage = "Statistik um " + dateFormat.format(notification.getDate()) + " Uhr:\n";
	          strMessage = String.valueOf(strMessage) + notification.getSender() + " hat gewonnen\n";
	          System.out.println(strMessage);
	          break;
			case DRAW:
				System.out.println("Unentschieden");
				break;
			default:
				break;
	      } 
	      
	    } 
	  }
	
}
