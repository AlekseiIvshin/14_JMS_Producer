package ru.udm.jms.queue;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.jms.DeliveryMode;
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
import org.springframework.transaction.annotation.Transactional;

import ru.udm.jms.publishers.commandhandler.HandlerException;
import ru.udm.jms.publishers.commandhandler.MessageHandler;

@Component
public class Producer {
	private static Logger LOGGER = LoggerFactory.getLogger(Producer.class);

	@Autowired
	MessageHandler messageHandler;

	@Autowired
	@Qualifier("jmsProducerTemplate")
	JmsTemplate jmsTemplate;

	@Autowired
	@Qualifier("defaultDestination")
	Queue destination;

	@PostConstruct
	public void init() {
		LOGGER.debug("Producer: {}", jmsTemplate.getDeliveryMode());
	}

	public void send(final String command) {
		this.jmsTemplate.send(destination, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				LOGGER.debug("send to queue '{}' message '{}'",
						destination.getQueueName(), command);
				Message message = session.createTextMessage();
				message.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);
				try {
					return messageHandler.handle(message, command);
				} catch (HandlerException e) {
					throw new  JMSException(e.getMessage());
				}
			}
		});
	}

	@Transactional(rollbackFor = JMSException.class)
	public void sendBatch(final List<String> commands) {
		for (final String cmd : commands) {
			this.jmsTemplate.send(destination, new MessageCreator() {

				@Override
				public Message createMessage(Session session)
						throws JMSException {
					LOGGER.debug("publish to queue '{}' message '{}'",
							destination.getQueueName(), commands);
					Message message = session.createTextMessage();
					message.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);
					try {
						return messageHandler.handle(message, cmd);
					} catch (HandlerException e) {
						LOGGER.error("Handle error",e);
						session.rollback();
						throw new  JMSException(e.getMessage());
					}
				}
			});
		}
	}
}
