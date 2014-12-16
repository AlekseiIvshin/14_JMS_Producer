package ru.udm.jms.publishers.commandhandler;

import javax.jms.JMSException;
import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SetMessageType extends GenericHandler<String> {
	private static Logger LOGGER = LoggerFactory
			.getLogger(SetMessageType.class);

	private final static String COMMAND_NAME = "type:";

	@Override
	Message handle(Message message, String command) throws HandlerException {
		String type = getValue(message, command, COMMAND_NAME);
		LOGGER.debug("Handle message: type = {}", type);
		try {
			message.setJMSType(type);
			message.setStringProperty("type", type);
		} catch (JMSException e) {

			throw new HandlerException(e.getMessage());
		}
		return message;
	}

	@Override
	String parseValue(String value) {
		return value;
	}

}
