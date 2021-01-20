package de.fh_dortmund.inf.cw.ttt_arena.server.beans.interfaces;

public interface TicTacToeArena {
	public char[][] play(char[][] feld, int i, int j, char token);
	public boolean isFull(char[][] feld);
	public boolean playerWinOnRow(char[][] feld, int[][] reihe, char token);
//	public char isWin(char[][] feld, String team);
	public boolean isWin(char[][] feld, char token);
}
