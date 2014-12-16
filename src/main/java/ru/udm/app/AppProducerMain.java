package ru.udm.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppProducerMain {

	private static String AUTO_COMMAND = "text:Some text";
	private static long AUTO_TIME_WORK = 1000 * 60 * 60; // 1 hour
	private static long AUTO_STEP = 1000;

	private static Logger LOGGER = LoggerFactory
			.getLogger(AppProducerMain.class);

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"beans.xml");
		AppProducer app = context.getBean(AppProducer.class);
		direct(app);
		((AbstractApplicationContext) context).close();
	}

	public static void direct(AppProducer app) {
		try (Scanner sc = new Scanner(System.in)) {
			String line = "";
			boolean exit = false;
			while (!exit && (line = sc.nextLine()) != null) {
				String[] command = line.split(":{1}\\s?", 2);
				switch (command[0]) {
				case "publish":
					app.publish(command[1]);
					break;
				case "send":
					app.sendToQueue(command[1]);
					break;
				case "sendbatch":
					LOGGER.info("Start create batch of command. Enter 'end' for build batch.");
					List<String> commands = new ArrayList<String>();
					while ((line = sc.nextLine()) != null
							&& !line.trim().equalsIgnoreCase("end")) {
						commands.add(line);
					}
					LOGGER.info("Batch created.");
					app.sendBatchToQueue(commands);
					break;
				case "exit":
					exit = true;
					break;
				case "auto":
					long startWork = System.currentTimeMillis();
					long endWork = startWork + AUTO_TIME_WORK;
					long current;
					while ((current = System.currentTimeMillis()) < endWork) {
						String cmd = AUTO_COMMAND + current + ";";
						app.publish(cmd);
						try {
							Thread.sleep(AUTO_STEP);
						} catch (InterruptedException e) {}

					}

					break;
				default:
					break;
				}
			}
		}
	}
}
