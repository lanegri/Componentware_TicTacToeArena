package de.fh_dortmund.inf.cw.ttt_arena.server.beans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import de.fh_dortmund.inf.cw.ttt_arena.server.beans.interfaces.TicTacToeArenaLocal;
import de.fh_dortmund.inf.cw.ttt_arena.server.beans.interfaces.TicTacToeArenaRemote;

@Stateful
public class TicTacToeArenaBean implements TicTacToeArenaLocal, TicTacToeArenaRemote, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private char[] arena = new char[9];
	@EJB
	ArenaBean arenaBean;
	
	@Override
	public void startGame() {
		fillArena();
//		arenaBean.startTimer();
	}
	
	@Override
	public void set(int index, String name, char token) throws Exception {
		arenaBean.set(arena, index, name, token);
	}
	
   /**
    * Finishes the game as a Victory
    * @param player
    */
	@Override
	public String finishAsVictory(String name){
		return arenaBean.finishAsVictory(name);
    }
	
	@Override
	public boolean isFull() {
		return arenaBean.isFull(arena);
	}
    
    public void fillArena() {		
		for(@SuppressWarnings("unused") char c: arena) {
			c = 0;
		}
	}

	@Override
	public char[] getArena() {
		return this.arena;
	}
}
