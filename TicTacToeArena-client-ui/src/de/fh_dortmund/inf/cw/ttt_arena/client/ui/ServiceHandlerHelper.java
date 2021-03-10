package de.fh_dortmund.inf.cw.ttt_arena.client.ui;

import de.fh_dortmund.inf.cw.ttt_arena.client.shared.GameHandler;
import de.fh_dortmund.inf.cw.ttt_arena.client.shared.ServiceHandler;
import de.fh_dortmund.inf.cw.ttt_arena.client.shared.TicTacToeArenaHandler;
import de.fh_dortmund.inf.cw.ttt_arena.client.shared.TeamSessionHandler;

import java.util.Set;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;

public class ServiceHandlerHelper {
	
	private static ServiceHandlerHelper instance;
	  
	private ServiceHandler serviceHandler;
	  
	private TicTacToeArenaHandler tttHandler;
	private TeamSessionHandler teamSessionHandler;
	private GameHandler notificationHandler;
	
//	private ServiceHandlerHelper() { 
//		serviceHandler = ServiceHandler.getInstance();
//		tttHandler = (TicTacToeArenaHandler) serviceHandler;
//	}
	
	private ServiceHandlerHelper() {
	    Reflections reflections = new Reflections(new Object[] { ClasspathHelper.forJavaClassPath() });
	    if (this.serviceHandler == null) {
	      Set<Class<? extends ServiceHandler>> set = reflections.getSubTypesOf(ServiceHandler.class);
	      if (set.size() > 2)
	        throw new RuntimeException("There are more than one Service-Handler implemented."); 
	      for (Class<? extends ServiceHandler> tempHandler : set) {
	        if (this.serviceHandler == null)
	          try {
	            this.serviceHandler = (ServiceHandler)tempHandler.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
	          } catch (Exception ex) {
	            throw new RuntimeException("Error while getting the Service-Handler-Instance.");
	          }  
	      } 
	      if (this.serviceHandler == null)
	        throw new RuntimeException("There is no Service-Handler implemented."); 
	    } 
	    Set<Class<? extends TicTacToeArenaHandler>> handlers = reflections.getSubTypesOf(TicTacToeArenaHandler.class);
	    if (handlers.size() > 1)
	      throw new RuntimeException("There are more than one TicTacToeArena-Handler implemented."); 
	    if (handlers.size() > 0) {
	      Class<? extends TicTacToeArenaHandler> tempHandler = handlers.iterator().next();
	      if (ServiceHandler.class.isAssignableFrom(tempHandler)) {
	        try {
	          this.tttHandler = (TicTacToeArenaHandler)tempHandler.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
	        } catch (Exception ex) {
	          throw new RuntimeException("Error while getting the TicTacToeArena-Handler-Instance.");
	        } 
	      } else {
	        System.out.println("TicTacToeArena-Handler must implement Service-Handler.");
	      } 
	    } 
	    
	    Set<Class<? extends TeamSessionHandler>> ushandlers = reflections.getSubTypesOf(TeamSessionHandler.class);
	    if (ushandlers.size() > 1)
	      throw new RuntimeException("There are more than one TicTacToeArena-Handler implemented."); 
	    if (ushandlers.size() > 0) {
	      Class<? extends TeamSessionHandler> tempHandler = ushandlers.iterator().next();
	      if (ServiceHandler.class.isAssignableFrom(tempHandler)) {
	        try {
	          this.teamSessionHandler = (TeamSessionHandler)tempHandler.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
	        } catch (Exception ex) {
	          throw new RuntimeException("Error while getting the UserSessionHandler-Handler-Instance.");
	        } 
	      } else {
	        System.out.println("UserSessionHandler-Handler must implement Service-Handler.");
	      } 
	    } 
	    
	    Set<Class<? extends GameHandler>> nothandlers = reflections.getSubTypesOf(GameHandler.class);
	    if (nothandlers.size() > 1)
	      throw new RuntimeException("There are more than one TicTacToeArena-Handler implemented."); 
	    if (nothandlers.size() > 0) {
	      Class<? extends GameHandler> tempHandler = nothandlers.iterator().next();
	      if (ServiceHandler.class.isAssignableFrom(tempHandler)) {
	        try {
	          this.notificationHandler = (GameHandler)tempHandler.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
	        } catch (Exception ex) {
	          throw new RuntimeException("Error while getting the UserSessionHandler-Handler-Instance.");
	        } 
	      } else {
	        System.out.println("UserSessionHandler-Handler must implement Service-Handler.");
	      } 
	    } 
	  }
	
	public static ServiceHandlerHelper getInstance() {
		if (instance == null) {
			instance = new ServiceHandlerHelper();
		}
		return instance;
	}
	
	public ServiceHandler getServiceHandler() {
		return this.serviceHandler;
		
	}
	
	public TeamSessionHandler getTeamSessionHandler() {
	    return this.teamSessionHandler;
    }
	
	public TicTacToeArenaHandler getTicTacToeArenaHandler() {
		return this.tttHandler;
	}
	
	public GameHandler getClientNotificationHandler() {
	    return this.notificationHandler;
	}
}
