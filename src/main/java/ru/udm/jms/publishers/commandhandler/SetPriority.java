package ru.udm.jms.publishers.commandhandler;

import javax.jms.JMSException;
import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SetPriority extends CommandHandler {

	private static Logger LOGGER = LoggerFactory.getLogger(SetPriority.class);

	private String COMMAND_NAME = "pr:";

	@Override
	public Message handle(Message message, String command) throws JMSException {
		int priority = getValue(message, command, COMMAND_NAME);
		LOGGER.debug("Handle message: priority = {}", priority);
		message.setJMSPriority(priority);
		return message;
	}

	@Override
	Integer parseValue(String value) {
		return Integer.valueOf(value);
	}

}