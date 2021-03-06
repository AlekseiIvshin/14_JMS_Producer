package ru.udm.jms.publishers.commandhandler;

import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class GenericHandler<T> {

	protected static Logger LOGGER = LoggerFactory
			.getLogger(GenericHandler.class);
	protected String COMMAND_END = ";";

	abstract Message handle(Message message, String command)
			throws HandlerException;

	abstract T parseValue(String value);

	T getValue(Message message, String command, String commandCallName){
		int from = command.indexOf(commandCallName);
		if(from<0){
			throw new IllegalArgumentException(commandCallName);
		}
		int to = command.indexOf(COMMAND_END, from + commandCallName.length());
		if (to > command.length() || to < 0) {
			LOGGER.debug("Command '{}': command end('{}') to found",
					commandCallName, COMMAND_END);
			throw new IllegalArgumentException(commandCallName);
		}
		String value = command.substring(from + commandCallName.length(), to);
		LOGGER.debug("Command='{}' commandCall='{}' pos=[{},{}] result='{}'",command,commandCallName,from,to,value);
		return parseValue(value);
	}
}
