package ru.udm.app;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppProducerMain {

	private static Logger LOGGER = LoggerFactory
			.getLogger(AppProducerMain.class);

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"beans.xml");
		AppProducer app = context.getBean(AppProducer.class);
		direct(app);
		((AbstractApplicationContext)context).close();
	}

	public static void direct(AppProducer app) {
		try (Scanner sc = new Scanner(System.in)) {
			String line = "";
			while ((line = sc.nextLine()) != null) {
				String[] command = line.split(":{1}\\s?", 2);
				switch (command[0]) {
				case "publish":
					app.publish(command[1]);
					break;
				case "send":
					app.sendToQueue(command[1]);
					break;
				case "exit":
					app.close();
					System.exit(1);
					break;
				default:
					break;
				}
			}
		}
	}

}
