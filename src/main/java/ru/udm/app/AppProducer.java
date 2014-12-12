package ru.udm.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.udm.app.helpers.MessageBundle;
import ru.udm.jms.queue.Producer;

public class AppProducer {

	public static void main(String[] args) {
		new AppProducer().init();
	}

	public void init() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		Producer producer = ctx.getBean(Producer.class);
		MessageBundle messageBundle = ctx.getBean(MessageBundle.class);
		String message;
		while ((message = messageBundle.getNext()) != null) {
			producer.send(message);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}

		((AbstractApplicationContext) ctx).close();
	}

}
