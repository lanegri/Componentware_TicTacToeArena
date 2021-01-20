package de.fh_dortmund.inf.cw.ttt_arena.server.beans;

import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Topic;

import de.fh_dortmund.inf.cw.ttt_arena.server.beans.interfaces.TicTacToeArenaLocal;
import de.fh_dortmund.inf.cw.ttt_arena.server.beans.interfaces.TicTacToeArenaRemote;
import de.fh_dortmund.inf.cw.ttt_arena.server.shared.ClientNotification;
import de.fh_dortmund.inf.cw.ttt_arena.server.shared.ClientNotificationType;

@Stateless
public class TicTacToeArenaBean implements TicTacToeArenaLocal, TicTacToeArenaRemote {
	
	@Inject
	private JMSContext context;
	@Resource(lookup = "java:global/jms/ObserverTopic")
	private Topic observerTopic;
	
	@Override
	public char[][] play(char[][] feld, int i, int j, char token) {
		
//		if(isFull(feld)) {
//			sendTopic(ClientNotificationType.DRAW, "");
//		}else {
			if(feld[i][j] == ' ')
				feld[i][j] = token;
			else
				System.out.println("Feld ist schon besetzt");
		
//			if(isWin(feld, token)) {
//				sendTopic(ClientNotificationType.WIN, "");
//			}
//		}
		
		
		
		return feld;
	}

	@Override
	public boolean isFull(char[][] feld) {
		int belegt = 0;
	    for(int i = 0; i < 3; i++) {
	        for(int j = 0; j < 3; j ++) {
	            if(' ' != feld[i][j]) {
	                belegt = belegt + 1;
	            }
	        }
	    }
	    return 9 == belegt; 
	}
	
	final static int [][][] SIEGESREIHEN = {
		// Zeilen
		{{0, 0}, {0, 1}, {0, 2}},
		{{1, 0}, {1, 1}, {1, 2}},
		{{2, 0}, {2, 1}, {2, 2}},
		// Spalten
		{{0, 0}, {1, 0}, {2, 0}},
		{{0, 1}, {1, 1}, {2, 1}},
		{{0, 2}, {1, 2}, {2, 2}},
		// Diagonalen
		{{0, 0}, {1, 1}, {2, 2}},
		{{2, 0}, {1, 1}, {0, 2}}
	};
	
	@Override
	public boolean playerWinOnRow(char[][] feld, int[][] reihe, char token) {
		char p0 = feld[reihe[0][0]][reihe[0][1]];
	    char p1 = feld[reihe[1][0]][reihe[1][1]];
	    char p2 = feld[reihe[2][0]][reihe[2][1]];
	    return p0 == token && p1 == token && p2 == token;
	}

//	@Override
//	public char isWin(char[][] feld, String team) {
//		if(isWin(feld, 'X', team)) return 'X';
//	     if(isWin(feld, 'O', team)) return 'O';
//	     return '-';
//	}

	@Override
	public boolean isWin(char[][] feld, char token) {
		for(int[][] siegesReihe : SIEGESREIHEN) {
	        if(playerWinOnRow(feld, siegesReihe, token)) {
	            return true;
	        }
	     }
	     return false;
	}
	
	public void sendTopic(ClientNotificationType notificationType, String sender) {
		try {
			
			ClientNotification notification = new ClientNotification(notificationType, sender, new Date());
			
			ObjectMessage  om = context.createObjectMessage();
			om.setObject(notification);
			context.createProducer().send(observerTopic, om);
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
