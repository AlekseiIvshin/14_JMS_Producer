package ru.udm.jms.publishers.commandhandler;

import javax.jms.JMSException;
import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SetExpireTime extends CommandHandler {

	private static Logger LOGGER = LoggerFactory.getLogger(SetExpireTime.class);

	private final static String COMMAND_NAME = "ttl:";

	@Override
	public Message handle(Message message, String command) throws JMSException {

		Long times = getValue(message, command, COMMAND_NAME);
		LOGGER.debug("Handle message: expire time = {}", times);
		message.setJMSExpiration(times);
		return message;
	}

	@Override
	Long parseValue(String value) {
		return Long.valueOf(value);
	}

}
