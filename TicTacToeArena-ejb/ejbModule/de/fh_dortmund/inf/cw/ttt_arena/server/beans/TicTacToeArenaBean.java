package de.fh_dortmund.inf.cw.ttt_arena.server.beans;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.fh_dortmund.inf.cw.ttt_arena.server.beans.interfaces.TicTacToeArenaLocal;
import de.fh_dortmund.inf.cw.ttt_arena.server.beans.interfaces.TicTacToeArenaRemote;
import de.fh_dortmund.inf.cw.ttt_arena.server.entities.Team;
import de.fh_dortmund.inf.cw.ttt_arena.server.entities.TeamStatistic;
import de.fh_dortmund.inf.cw.ttt_arena.server.shared.Notification;
import de.fh_dortmund.inf.cw.ttt_arena.server.shared.NotificationType;


@Stateful
public class TicTacToeArenaBean implements TicTacToeArenaLocal, TicTacToeArenaRemote {
	
	
	
	@Override
	public char[][] play(char[][] feld, int i, int j, char player) {
		
		
		if(feld[i][j] == ' ')
			feld[i][j] = player;
		else
			System.out.println("Feld ist schon besetzt");
			
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
	public boolean playerWinOnRow(char[][] feld, int[][] reihe, char player) {
		char p0 = feld[reihe[0][0]][reihe[0][1]];
	    char p1 = feld[reihe[1][0]][reihe[1][1]];
	    char p2 = feld[reihe[2][0]][reihe[2][1]];
	    return p0 == player && p1 == player && p2 == player;
	}

	@Override
	public char isWin(char[][] feld) {
		if(isWin(feld, 'X')) return 'X';
	     if(isWin(feld, 'O')) return 'O';
	     return '-';
	}

	@Override
	public boolean isWin(char[][] feld, char player) {
		for(int[][] siegesReihe : SIEGESREIHEN) {
	        if(playerWinOnRow(feld, siegesReihe, player)) {
	            return true;
	        }
	     }
	     return false;
	}
	
}
