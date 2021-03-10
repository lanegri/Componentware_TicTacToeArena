package de.fh_dortmund.inf.cw.ttt_arena.client.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import de.fh_dortmund.inf.cw.ttt_arena.client.shared.GameHandler;
//import common.Player;
import de.fh_dortmund.inf.cw.ttt_arena.client.shared.ServiceHandler;
import de.fh_dortmund.inf.cw.ttt_arena.client.shared.TeamSessionHandler;
import de.fh_dortmund.inf.cw.ttt_arena.client.shared.TicTacToeArenaHandler;
import de.fh_dortmund.inf.cw.ttt_arena.server.shared.ClientNotification;

public class GameArena implements Observer{
		
	static char[] arena;
		
	private ServiceHandler serviceHandler;
	private TicTacToeArenaHandler tttHandler;
	private GameHandler notificationHandler;
	private TeamSessionHandler teamSessionHandler;
	
	public GameArena() {
		this.serviceHandler = ServiceHandlerHelper.getInstance().getServiceHandler();
		this.tttHandler = ServiceHandlerHelper.getInstance().getTicTacToeArenaHandler();
		this.notificationHandler = ServiceHandlerHelper.getInstance().getClientNotificationHandler();
		this.teamSessionHandler = ServiceHandlerHelper.getInstance().getTeamSessionHandler();
				
		this.serviceHandler.addObserver(this);
	}

	
	/**
	 *  Spielfeld ausgeben
	 */
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
	
	public void executeGame() throws Exception{
		Scanner input = new Scanner(System.in);

//		System.out.println("Logen Sie sich oder registrieren Sie sich um den Arena zu betreten! \n 1 == Login 2 == Registrieren");
//		int action = input.nextInt();
//		input.nextLine();
		System.out.println("Geben Sie Ihren Benutzername ein!");
		String username = input.nextLine();
		
//		switch(action) {
//			case 1:
//				teamSessionHandler.login(username);
//				break;
//			case 2:
//				teamSessionHandler.registerTeam(username);
				teamSessionHandler.login(username);
				if(teamSessionHandler.getNumberOfOnlinePlayers() > 1)
					tttHandler.startGame();
//				break;
//			default:
//				break;
//		}
		
		do{
			System.out.println("Spielen");
			int x = input.nextInt();
			
//			this.feld = tttHandler.play(feld, x, y, PLAYER_X);
			notificationHandler.play(x);
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
	
	@Override
	public void update(Observable o, Object arg) {
	    if (arg instanceof ClientNotification) {
	    	ClientNotification notification = (ClientNotification)arg;
	    	String strMessage = "";
	    	DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	    	
//	      switch (notification.getType()) {
//	        case REGISTER:
//	          break;
//	        case LOGIN:
////	        	List<String> onlineTeams = teamSessionHandler.getOnlineTeamsNames();
////	        		        	
////	        	if(onlineTeams.size() == 2) {
////	        		System.out.print("Partie beginnt: " + dateFormat.format(notification.getDate()) + " Uhr. \n Teams: ");
////		  			for(String team: teamSessionHandler.getOnlineTeamsNames()) {
////		  				System.out.print(team + " ");
////		  			}
////		  			System.out.println();
////	        	}else {
////	        		System.out.println("warten aug gegner ...");
////	        	}
//	          	
//	  			break;
//	        case LOGOUT:
//	          strMessage = String.valueOf(notification.getPlayer()) + " hat sich um " + dateFormat.format(notification.getDate()) + " Uhr abgemeldet.";
////	          updateUserList();
//	          break;
//	        case TOKEN:
//	          strMessage = String.valueOf(notification.getPlayer()) + " um " + dateFormat.format(notification.getDate()) + " Uhr:\n";
//	          try {
//				tttHandler.set(notification.getIndex(), notification.getPlayer(), notification.getToken());
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	          arena = tttHandler.getArena();
//	          System.out.println("Play: " + strMessage);
//	          print();
//	          break;
//	        case STATISTIC:
//	          strMessage = "Statistik um " + dateFormat.format(notification.getDate()) + " Uhr:\n";
////	          strMessage = String.valueOf(strMessage) + notification.getText() + "\n";
//	          break;
//			case DISCONNECT:
//				break;
//			case WIN:
//	          strMessage = "Statistik um " + dateFormat.format(notification.getDate()) + " Uhr:\n";
//	          strMessage = String.valueOf(strMessage) + notification.getPlayer() + " hat gewonnen\n";
//	          System.out.println(strMessage);
//	          break;
//			case DRAW:
//				System.out.println("Unentschieden");
//				break;
//			default:
//				break;
//	      } 
	      
	    	switch (notification.getType()) {
    		case REGISTER:
    			System.out.println("Neue Registrierung");
    			
    			break;
    		case LOGIN:
	        	System.out.println(notification.getPlayer() + " hat den Arena betreten");
    			
	  			break;
    		case LOGOUT:
	          System.out.println(notification.getPlayer() + " hat um " + dateFormat.format(notification.getDate()) + " den Arena verlassen.");
	       
	          
	          break;
    		case TOKEN:
    			strMessage = String.valueOf(notification.getPlayer()) + " um " + dateFormat.format(notification.getDate()) + " Uhr:\n";
    			
				try {
					tttHandler.set(notification.getIndex(), notification.getPlayer(), notification.getToken());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			arena = tttHandler.getArena();
    			
				System.out.println("Play: " + strMessage);
				print();
				
    			break;
	        case STATISTIC:
	        	strMessage = "Statistik um " + dateFormat.format(notification.getDate()) + " Uhr:\n";
	          
	          
	          break;
			case DISCONNECT:
				System.out.println("Disconnect");
				
				break;
			case WIN:
			  strMessage = String.valueOf(strMessage) + notification.getPlayer() + " hat gewonnen\n";
	          System.out.println(strMessage);
	         
	          break;
			case DRAW:
				System.out.println("Unentschieden");
				
				break;
			case TIMER:
				System.out.println("Timer");
				
				break;
			default:
				break;
    	} 
	    } 
	  }
	
}
