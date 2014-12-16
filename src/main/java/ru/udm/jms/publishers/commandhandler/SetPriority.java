package ru.udm.jms.publishers.commandhandler;

import javax.jms.JMSException;
import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SetPriority extends GenericHandler<Integer> {

	private static Logger LOGGER = LoggerFactory.getLogger(SetPriority.class);

	private String COMMAND_NAME = "pr:";

	@Override
	public Message handle(Message message, String command) throws HandlerException {
		int priority = getValue(message, command, COMMAND_NAME);
		LOGGER.debug("Handle message: priority = {}", priority);
		try {
			message.setJMSPriority(priority);
		} catch (JMSException e) {
			throw new HandlerException(e.getMessage());
		}
		return message;
	}

	@Override
	Integer parseValue(String value) {
		return Integer.valueOf(value);
	}

}