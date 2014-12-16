package ru.udm.jms.topic;

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

import ru.udm.jms.publishers.commandhandler.HandlerException;
import ru.udm.jms.publishers.commandhandler.MessageHandler;

@Component
public class Publisher {
	private static Logger LOGGER = LoggerFactory.getLogger(Publisher.class);

	@Autowired
	MessageHandler messageHandler;

	@Autowired
	@Qualifier("jmsTopicTemplate")
	JmsTemplate jmsTemplate;

	@Autowired
	@Qualifier("topicDestination")
	Topic destination;

	public void publish(final String command) {
		this.jmsTemplate.send(destination, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				LOGGER.debug("publish in topic '{}' command '{}'",
						destination.getTopicName(), command);
				Message message = session.createTextMessage();
				try {
					return messageHandler.handle(message, command);
				} catch (HandlerException e) {
					throw new JMSException(e.getMessage());
				}
			}
		});
	}

}
