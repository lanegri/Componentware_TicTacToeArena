package de.fh_dortmund.inf.cw.ttt_arena.client;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import de.fh_dortmund.inf.cw.ttt_arena.client.shared.ServiceHandler;
import de.fh_dortmund.inf.cw.ttt_arena.client.shared.TicTacToeArenaHandler;
import de.fh_dortmund.inf.cw.ttt_arena.server.beans.interfaces.TicTacToeArenaRemote;

public class ServiceHandlerImpl extends ServiceHandler implements TicTacToeArenaHandler {
	
	private static ServiceHandlerImpl instance;
	private Context ctx;
	private TicTacToeArenaRemote arena;
	
	private ServiceHandlerImpl() {
		try {
			ctx = new InitialContext();
			arena = (TicTacToeArenaRemote) ctx.lookup("java:global/TicTacToeArena-ear/TicTacToeArena-ejb/TicTacToeArenaBean!de.fh_dortmund.inf.cw.ttt_arena.server.beans.interfaces.TicTacToeArenaRemote");
		} catch (NamingException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static ServiceHandlerImpl getInstance() {
		if (instance == null) {
			instance = new ServiceHandlerImpl();
		}
		return instance;
	}
	

	@Override
	public char[][] play(char[][] feld, int i, int j, char player) {
		return arena.play(feld, i, j, player);
	}

	@Override
	public boolean isFull(char[][] feld) {
		return arena.isFull(feld);
	}

	@Override
	public boolean playerWinOnRow(char[][] feld, int[][] reihe, char sp) {
		return arena.playerWinOnRow(feld, reihe, sp);
	}

	@Override
	public char isWin(char[][] feld) {
		return arena.isWin(feld);
	}

	@Override
	public boolean isWin(char[][] feld, char player) {
		return arena.isWin(feld, player);
	}

}
