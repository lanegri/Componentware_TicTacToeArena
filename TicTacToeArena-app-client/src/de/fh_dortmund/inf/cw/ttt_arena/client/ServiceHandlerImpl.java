package de.fh_dortmund.inf.cw.ttt_arena.client;

import java.util.List;

import javax.ejb.Singleton;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import de.fh_dortmund.inf.cw.ttt_arena.client.shared.GameHandler;
import de.fh_dortmund.inf.cw.ttt_arena.client.shared.ServiceHandler;
import de.fh_dortmund.inf.cw.ttt_arena.client.shared.TeamSessionHandler;
import de.fh_dortmund.inf.cw.ttt_arena.client.shared.TicTacToeArenaHandler;
import de.fh_dortmund.inf.cw.ttt_arena.server.beans.interfaces.TeamSessionRemote;
import de.fh_dortmund.inf.cw.ttt_arena.server.beans.interfaces.TicTacToeArenaRemote;
import de.fh_dortmund.inf.cw.ttt_arena.server.entities.Player;
import de.fh_dortmund.inf.cw.ttt_arena.server.shared.ClientNotification;

@Singleton
public class ServiceHandlerImpl extends ServiceHandler implements TeamSessionHandler, MessageListener, GameHandler, TicTacToeArenaHandler {
	
	private JMSContext jmsContext;
	private Topic observerTopic;
	private Queue NotificationQueue;
	
	private static ServiceHandlerImpl instance;
	private Context ctx;
	private TeamSessionRemote teamsession;
	private TicTacToeArenaRemote arena;
	
	public ServiceHandlerImpl() {
		try {
			ctx = new InitialContext();
			teamsession = (TeamSessionRemote) ctx.lookup("java:global/TicTacToeArena-ear/TicTacToeArena-ejb/TeamSessionBean!de.fh_dortmund.inf.cw.ttt_arena.server.beans.interfaces.TeamSessionRemote");
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
	
	public void initializeJMSConnections() {
		try {
			//common
			ConnectionFactory connectionFactory = (ConnectionFactory) ctx.lookup("java:comp/DefaultJMSConnectionFactory");
			jmsContext = connectionFactory.createContext();
			
			//Topic
			observerTopic = (Topic) ctx.lookup("java:global/jms/ObserverTopic");
			jmsContext.createConsumer(observerTopic).setMessageListener(this);
			NotificationQueue = (Queue) ctx.lookup("java:global/jms/NotificationQueue");			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void registerTeam(String name) throws Exception {
		teamsession.registerTeam(name);
	}

	@Override
	public void login(String name) throws Exception {
		teamsession.login(name);
		
		//Initialize Connections
		this.initializeJMSConnections();
	}

	@Override
	public void logout() {
		teamsession.logout();
	}

	@Override
	public void disconnect() {
		teamsession.disconnect();
	}

	@Override
	public void delete(String name) throws Exception {
		teamsession.delete(name);
	}

	@Override
	public int getNumberOfRegisteredTeams() {
		return teamsession.getNumberOfRegisteredTeams();
	}
	
	@Override
	public List<String> getOnlinePlayersNames() {
		return null;
	}

	@Override
	public String getPlayerName() {
		return teamsession.getPlayerName();
	}

	@Override
	public int getNumberOfOnlinePlayers() {
		return teamsession.getNumberOfOnlinePlayers();
	}

	@Override
	public void addPlayer(String nickname) throws Exception {
		teamsession.addPlayer(nickname);
	}

	@Override
	public Player getCurrentPlayer() {
		return teamsession.getCurrentPlayer();
	}

//	@Override
//	public Player getPlayerByNick(String nickname) {
//		return teamsession.getPlayerByNick(nickname);
//	}
	
	@Override
	public void startGame() {
		arena.startGame();
	}
	
	@Override
	public void set(int index, String name, char token) {
		try {
			arena.set(index, name, token);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean isFull() {
		return arena.isFull();
	}

	@Override
	public String finishAsVictory(String name) {
		return arena.finishAsVictory(name);
	}
	
	@Override
	public void play(int i) {
		try {
			TextMessage notification = jmsContext.createTextMessage();
			notification.setIntProperty("index", i);
			notification.setStringProperty("player", getCurrentPlayer().getNickname());
			notification.setStringProperty("token", "" + getCurrentPlayer().getToken());
						
			jmsContext.createProducer().send(NotificationQueue, notification);
			
		} catch (JMSException e) {
			System.err.println("Error while notfy Observer via Topic" + e.getMessage());
		}
		
	}

	@Override
	public void onMessage(Message message) {
		
		ObjectMessage o = (ObjectMessage)message;
		try {
			
			if(message.getJMSDestination().equals(observerTopic)) {
				
				if(o.getObject() instanceof ClientNotification) {
					setChanged();
					notifyObservers((ClientNotification) o.getObject());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int getNumberOfTeamPlayers() {
		return this.teamsession.getNumberOfTeamPlayers();
	}

	@Override
	public char[] getArena() {
		return this.arena.getArena();
	}
}
