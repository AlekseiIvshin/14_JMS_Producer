package ru.udm.jms.publishers;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class Publisher {
	private static Logger LOGGER = LoggerFactory
			.getLogger(Publisher.class);

	@Autowired
	@Qualifier("jmsTopicTemplate")
	JmsTemplate jmsTemplate;

	@Autowired
	@Qualifier("topicDestination")
	Topic destination;
	
	public void publish(final String message){
		this.jmsTemplate.send(destination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				LOGGER.debug("publish to topic '{}' message '{}'",destination.getTopicName(),message);
				return session.createTextMessage(message);
			}
		});
	}
}
