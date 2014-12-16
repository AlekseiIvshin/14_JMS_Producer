package ru.udm.jms.publishers.commandhandler;

import javax.jms.JMSException;
import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SetExpireTime extends GenericHandler<Long> {

	private static Logger LOGGER = LoggerFactory.getLogger(SetExpireTime.class);

	private final static String COMMAND_NAME = "ttl:";

	@Override
	public Message handle(Message message, String command) throws HandlerException {

		Long times = getValue(message, command, COMMAND_NAME);
		LOGGER.debug("Handle message: expire time = {}", times);
		try {
			message.setJMSExpiration(times);
		} catch (JMSException e) {
			throw new HandlerException(e.getMessage());
		}
		return message;
	}

	@Override
	Long parseValue(String value) {
		return Long.valueOf(value);
	}

}
