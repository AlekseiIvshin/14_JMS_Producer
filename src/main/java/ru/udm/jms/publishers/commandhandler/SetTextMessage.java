package ru.udm.jms.publishers.commandhandler;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SetTextMessage extends CommandHandler {

	private static Logger LOGGER = LoggerFactory
			.getLogger(SetTextMessage.class);

	private final static String COMMAND_NAME = "text:";

	@Override
	public Message handle(Message message, String command) throws JMSException {
		if (message instanceof TextMessage) {
			String value = getValue(message, command, COMMAND_NAME);
			LOGGER.debug("Handle message: message text = '{}'", value);
			((TextMessage) message).setText(value);
		}
		return message;
	}

	@Override
	String parseValue(String value) {
		return value;
	}

}