package de.fh_dortmund.inf.cw.ttt_arena.server.beans.interfaces;

public interface TicTacToeArena {
	public char[][] play(char[][] feld, int i, int j, char player);
	public boolean isFull(char[][] feld);
	public boolean playerWinOnRow(char[][] feld, int[][] reihe, char sp);
	public char isWin(char[][] feld);
	public boolean isWin(char[][] feld, char player);
}
