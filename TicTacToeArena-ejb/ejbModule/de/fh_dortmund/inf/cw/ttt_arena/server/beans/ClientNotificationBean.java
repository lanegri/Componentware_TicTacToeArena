package de.fh_dortmund.inf.cw.ttt_arena.server.beans;

import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.jms.Topic;

import de.fh_dortmund.inf.cw.ttt_arena.server.shared.ClientNotification;
import de.fh_dortmund.inf.cw.ttt_arena.server.shared.ClientNotificationType;

@MessageDriven(mappedName = "java:global/jms/NotificationQueue", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
	})
public class ClientNotificationBean implements MessageListener {
	
	@Inject
	private JMSContext jmsContext;
	@Resource(lookup = "java:global/jms/ObserverTopic")
	private Topic observerTopic;

	@Override
	public void onMessage(Message message) {
		try {
			
			TextMessage textMessage = (TextMessage) message;
			int index = textMessage.getIntProperty("index");
			String player = textMessage.getStringProperty("player");
			char token = textMessage.getStringProperty("token").charAt(0);
			
			ClientNotification notification = new ClientNotification(ClientNotificationType.TOKEN, player, new Date());
			notification.setToken(token);
			notification.setIndex(index);
			
			ObjectMessage  om = jmsContext.createObjectMessage();
			om.setObject(notification);
			jmsContext.createProducer().send(observerTopic, om);
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
