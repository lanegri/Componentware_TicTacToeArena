package de.fh_dortmund.inf.cw.ttt_arena.client.shared;

import java.util.Observable;

public abstract class ServiceHandler extends Observable{
	public static ServiceHandler getInstance() {
	    throw new UnsupportedOperationException("Singleton-Pattern is not implemented yet.");
	}
}
