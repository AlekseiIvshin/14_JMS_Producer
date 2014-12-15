package ru.udm.jms.publishers.commandhandler;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageHandler {

	private static Logger LOGGER = LoggerFactory
			.getLogger(MessageHandler.class);

	@PostConstruct
	public void init() {
		LOGGER.debug("Handlers count = {}", handlers.size());
	}

	@Autowired
	List<CommandHandler> handlers;
	
	public Message handle(Message message,String command){
		for(CommandHandler h: handlers){
			try {
				message = h.handle(message, command);
			} catch (JMSException e) {
				LOGGER.error("Handle error",e);
			} catch (RuntimeException e) {}
		}
		return message;
	}
}
