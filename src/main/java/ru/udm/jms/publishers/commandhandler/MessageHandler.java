package ru.udm.jms.publishers.commandhandler;

import java.util.List;

import javax.annotation.PostConstruct;
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
	List<GenericHandler<?>> handlers;
	
	public Message handle(Message message,String command) throws HandlerException{
		for(GenericHandler<?> h: handlers){
			try {
				message = h.handle(message, command);
			}  catch (RuntimeException e) {}
		}
		return message;
	}
}
