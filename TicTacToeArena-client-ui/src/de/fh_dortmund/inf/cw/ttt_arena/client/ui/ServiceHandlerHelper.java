package de.fh_dortmund.inf.cw.ttt_arena.client.ui;

import de.fh_dortmund.inf.cw.ttt_arena.client.shared.ServiceHandler;
import de.fh_dortmund.inf.cw.ttt_arena.client.shared.TicTacToeArenaHandler;

public class ServiceHandlerHelper {
	
	private static ServiceHandlerHelper instance;
	  
	private ServiceHandler serviceHandler;
	  
	private TicTacToeArenaHandler tttHandler;
	
	private ServiceHandlerHelper() { 
		serviceHandler = ServiceHandler.getInstance();
		tttHandler = (TicTacToeArenaHandler) serviceHandler;
	}
	
	public static ServiceHandlerHelper getInstance() {
		if (instance == null) {
			instance = new ServiceHandlerHelper();
		}
		return instance;
	}
	
	public ServiceHandler getServiceHandler() {
		return serviceHandler;
		
	}
	
	public TicTacToeArenaHandler getUserSessionHandler() {
		return tttHandler;
	}
}
