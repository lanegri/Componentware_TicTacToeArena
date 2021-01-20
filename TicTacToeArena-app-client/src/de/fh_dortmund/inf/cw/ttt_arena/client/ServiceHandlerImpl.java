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

import de.fh_dortmund.inf.cw.ttt_arena.client.shared.ClientNotificationHandler;
import de.fh_dortmund.inf.cw.ttt_arena.client.shared.ServiceHandler;
import de.fh_dortmund.inf.cw.ttt_arena.client.shared.TeamSessionHandler;
import de.fh_dortmund.inf.cw.ttt_arena.client.shared.TicTacToeArenaHandler;
import de.fh_dortmund.inf.cw.ttt_arena.server.beans.interfaces.TeamSessionRemote;
import de.fh_dortmund.inf.cw.ttt_arena.server.beans.interfaces.TicTacToeArenaRemote;
import de.fh_dortmund.inf.cw.ttt_arena.server.entities.Player;
import de.fh_dortmund.inf.cw.ttt_arena.server.entities.Team;
import de.fh_dortmund.inf.cw.ttt_arena.server.entities.TeamStatistic;
import de.fh_dortmund.inf.cw.ttt_arena.server.shared.ClientNotification;
import de.fh_dortmund.inf.cw.ttt_arena.server.shared.PlayerToken;

@Singleton
public class ServiceHandlerImpl extends ServiceHandler implements TeamSessionHandler, MessageListener, ClientNotificationHandler, TicTacToeArenaHandler {
	
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
	public String getTeamName() {

		return teamsession.getTeamName();
	}

	@Override
	public void register(String name) throws Exception {
		teamsession.register(name);
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
	public List<String> getOnlineTeams() {
		return teamsession.getOnlineTeams();
	}

	@Override
	public int getNumberOfRegisteredTeams() {
		return teamsession.getNumberOfRegisteredTeams();
	}

	@Override
	public int getNumberOfOnlineTeams() {
		return teamsession.getNumberOfOnlineTeams();
	}

	@Override
	public Team getTeam() {
		return teamsession.getTeam();
	}
	
	@Override
	public char getToken() {
		return teamsession.getToken();
	}
	
	@Override
	public List<Player> getTeammates() {
		return teamsession.getTeammates();
	}

	@Override
	public TeamStatistic getTeamStatistic() {
		return teamsession.getTeamStatistic();
	}

	
	@Override
	public char[][] play(char[][] feld, int i, int j, char token) {
		return arena.play(feld, i, j, token);
	}

//	@Override
	public boolean isFull(char[][] feld) {
		return arena.isFull(feld);
	}

//	@Override
//	public boolean playerWinOnRow(char[][] feld, int[][] reihe, char sp) {
//		return arena.playerWinOnRow(feld, reihe, sp);
//	}

//	@Override
//	public char isWin(char[][] feld) {
//		return arena.isWin(feld, getTeamName());
//	}

	@Override
	public boolean isWin(char[][] feld, char player) {
		return arena.isWin(feld, player);
	}

	@Override
	public void sendNotification(int i, int j) {
		try {
			TextMessage notification = jmsContext.createTextMessage();
			notification.setStringProperty("MESSAGE_SENDER", getTeamName());
			notification.setIntProperty("row", i);
			notification.setIntProperty("line", j);
			notification.setStringProperty("token", "" + getToken());
						
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


}
