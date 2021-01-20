package de.fh_dortmund.inf.cw.ttt_arena.server.beans;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.fh_dortmund.inf.cw.ttt_arena.server.entities.Team;
import de.fh_dortmund.inf.cw.ttt_arena.server.entities.TeamStatistic;
import de.fh_dortmund.inf.cw.ttt_arena.server.shared.ClientNotification;
import de.fh_dortmund.inf.cw.ttt_arena.server.shared.ClientNotificationType;
import de.fh_dortmund.inf.cw.ttt_arena.server.shared.PlayerToken;

@MessageDriven(mappedName = "java:global/jms/NotificationQueue", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
	})
public class ClientNotificationBean implements MessageListener {
	
	@Inject
	private JMSContext jmsContext;
	@Resource(lookup = "java:global/jms/ObserverTopic")
	private Topic observerTopic;
	
	@Resource(name = "spielzeit")
	private int spielzeit;
	private List<Team> teams;
	@PersistenceContext(unitName = "TictactoearenaDB")
	private EntityManager entityManager;
	
	@EJB
	private TeamManagementBean teamManangement;
	
	@PostConstruct
	public void init() {
		TypedQuery<Team> query = entityManager.createNamedQuery("Team.all", Team.class);
		
		 teams = query.getResultList();
	}

	
	@Override
	public void onMessage(Message message) {
		try {
			
			TextMessage textMessage = (TextMessage) message;
			int i = textMessage.getIntProperty("row");
			int j = textMessage.getIntProperty("line");
			
			ClientNotification notification;
			notification = new ClientNotification(ClientNotificationType.TOKEN, textMessage.getStringProperty("MESSAGE_SENDER"), new Date());
			notification.setToken(textMessage.getStringProperty("token").charAt(0));
			notification.setRow(i);
			notification.setLine(j);
			
			ObjectMessage  om = jmsContext.createObjectMessage();
			om.setObject(notification);
			jmsContext.createProducer().send(observerTopic, om);
			
			
//			for(Team t: teams) {
//				if(t.getName().equals(textMessage.getStringProperty("MESSAGE_SENDER"))) {
//					
//					TeamStatistic stat = t.getStatistic();
//					int messages = stat.getMessages();
//					messages++;
//					stat.setMessages(messages);
//					entityManager.merge(stat);
//					entityManager.flush();
//				}
//			}
			
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
