package de.fh_dortmund.inf.cw.ttt_arena.client.shared;

public interface TicTacToeArenaHandler {
	public void startGame();
	public void set(int index, String name, char token) throws Exception;
	public boolean isFull();
	public String finishAsVictory(String name);
	public char[] getArena();
}