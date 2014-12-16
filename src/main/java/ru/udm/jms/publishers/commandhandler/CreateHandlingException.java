package ru.udm.jms.publishers.commandhandler;

import javax.jms.Message;

import org.springframework.stereotype.Component;

@Component
public class CreateHandlingException extends GenericHandler<String> {

	private final static String COMMAND_NAME = "exc:";

	@Override
	Message handle(Message message, String command) throws HandlerException {
		LOGGER.debug("Create exception handler started");
		if (getValue(message, command, COMMAND_NAME) != null) {
			throw new HandlerException("You called me and I come!");
		}
		return message;
	}

	@Override
	String parseValue(String value) {
		return value;
	}

}
