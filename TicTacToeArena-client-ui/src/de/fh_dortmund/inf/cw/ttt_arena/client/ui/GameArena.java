package de.fh_dortmund.inf.cw.ttt_arena.client.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import de.fh_dortmund.inf.cw.ttt_arena.client.shared.ClientNotificationHandler;
//import common.Player;
import de.fh_dortmund.inf.cw.ttt_arena.client.shared.ServiceHandler;
import de.fh_dortmund.inf.cw.ttt_arena.client.shared.TicTacToeArenaHandler;
import de.fh_dortmund.inf.cw.ttt_arena.server.shared.Notification;
import de.fh_dortmund.inf.cw.ttt_arena.server.shared.NotificationType;

public class GameArena implements Observer{
	
	public static final char PLAYER_X = 'X';
	public static final char PLAYER_O = 'O';
	public static final char NOPLAYER =  ' ';
	
	char[][] feld;
	String strMessage = "";
	
	Scanner sc = new Scanner(System.in);
	
	private ServiceHandler serviceHandler;
	private TicTacToeArenaHandler tttHandler;
	private ClientNotificationHandler notificationHandler;
	
	public GameArena() {
		this.serviceHandler = ServiceHandlerHelper.getInstance().getServiceHandler();
		this.tttHandler = ServiceHandlerHelper.getInstance().getTicTacToeArenaHandler();
		this.notificationHandler = ServiceHandlerHelper.getInstance().getNotificationHandler();
		
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
	
	public void showText(){
		
		
		do{
			System.out.println("Txt");
			String text = sc.nextLine();
			notificationHandler.sendNotification(text);
//			ChatPanel.this.chatMessageHandler.sendChatMessage(ChatPanel.this.txtInput.getText());
			System.out.println(strMessage);
		}while(true);
	}
	
	public void update(Observable o, Object arg) {
	    if (arg instanceof Notification) {
	    	Notification notification = (Notification)arg;
	      
	      DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	      switch (notification.getType()) {
	        case REGISTER:
//	          updateUserList();
	          break;
	        case LOGIN:
	          strMessage = String.valueOf(notification.getSender()) + " hat sich um " + dateFormat.format(notification.getDate()) + " Uhr angemeldet.";
//	          updateUserList();
	          break;
	        case LOGOUT:
	          strMessage = String.valueOf(notification.getSender()) + " hat sich um " + dateFormat.format(notification.getDate()) + " Uhr abgemeldet.";
//	          updateUserList();
	          break;
	        case TEXT:
	          strMessage = String.valueOf(notification.getSender()) + " um " + dateFormat.format(notification.getDate()) + " Uhr:\n";
	          strMessage = String.valueOf(strMessage) + "  " + notification.getText();
	          System.out.println(strMessage);
	          break;
	        case STATISTIC:
	          strMessage = "Statistik um " + dateFormat.format(notification.getDate()) + " Uhr:\n";
	          strMessage = String.valueOf(strMessage) + notification.getText() + "\n";
	          break;
			case DISCONNECT:
				break;
			default:
				break;
	      } 
	      
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
