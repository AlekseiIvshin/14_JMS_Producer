package ru.udm.app;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.udm.app.helpers.MessageBundle;
import ru.udm.jms.publishers.Publisher;
import ru.udm.jms.queue.Producer;

public class AppProducer {

	private static Logger LOGGER = LoggerFactory
			.getLogger(AppProducer.class);
	
	private ApplicationContext context;

	public static void main(String[] args) {
		director(new AppProducer());
	}

	public static void director(AppProducer app) {
		app.init();
		app.director();
	}

	private void director() {
		Producer producer = context.getBean(Producer.class);
		Publisher publisher = context.getBean(Publisher.class);
		MessageBundle messageBundle = context.getBean(MessageBundle.class);

		try (Scanner sc = new Scanner(System.in)) {
			String line = "";
			while ((line = sc.next()) != null && !line.isEmpty()) {
				String[] command = line.split(":", 2);
				LOGGER.debug("Command:{}",command);
				switch (command[0]) {
				case "publish":
					publisher.publish(command[1]);
					break;
				case "send":
					producer.send(command[1]);
					break;
				case "sendFromBundle":
					String messageFromBundle;
					if((messageFromBundle = messageBundle.getNext())!=null){
						producer.send(messageFromBundle);
					}
					break;
				case "exit":
					close();
					System.exit(1);
				default:
					break;
				}
			}
		}
	}

	private void init() {
		context = new ClassPathXmlApplicationContext("beans.xml");

	}

	private void close() {
		if (context != null) {
			((AbstractApplicationContext) context).close();
		}
	}

}
