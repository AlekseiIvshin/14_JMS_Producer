package ru.udm.jms.queue;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class Producer {
	private static Logger LOGGER = LoggerFactory
			.getLogger(Producer.class);

	@Autowired
	@Qualifier("jmsProducerTemplate")
	JmsTemplate jmsTemplate;

	@Autowired
	@Qualifier("defaultDestination")
	Queue destination;

	public void send(final String messageText) {
		this.jmsTemplate.send(destination, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {

				LOGGER.debug("publish to queue '{}' message '{}'",destination.getQueueName(),messageText);
				return session.createTextMessage(this.getClass().getName()
						+ " send message '" + messageText + "' timestamp:"
						+ System.currentTimeMillis());
			}
		});
	}
}
