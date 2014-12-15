package ru.udm.app;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import ru.udm.jms.publishers.Publisher;
import ru.udm.jms.queue.Producer;

@Component
public class AppProducerImpl implements AppProducer {

	private static Logger LOGGER = LoggerFactory
			.getLogger(AppProducerImpl.class);

	@Autowired
	private ApplicationContext context;
	private Publisher publisher;
	private Producer producer;
	
	@PostConstruct
	private void init() {
		LOGGER.debug("AppProducer start working!");
		producer = context.getBean(Producer.class);
		publisher =context.getBean(Publisher.class);
	}
	

	@Override
	public void close() {
		LOGGER.debug("Close application");
		if (context != null) {
			((AbstractApplicationContext) context).close();
		}
	}


	@Override
	public void publish(String command) {
		publisher.publish(command);
	}


	@Override
	public void sendToQueue(String command) {
		LOGGER.debug("Send to queue. Command = '{}'",command);
		producer.send(command);
	}
	
	

}
