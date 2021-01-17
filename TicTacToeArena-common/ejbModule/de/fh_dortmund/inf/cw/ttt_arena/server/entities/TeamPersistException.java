package de.fh_dortmund.inf.cw.ttt_arena.server.entities;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class TeamPersistException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public TeamPersistException(String message) {
		super(message);
	}
}
