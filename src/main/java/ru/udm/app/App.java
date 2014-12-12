package ru.udm.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.udm.jms.producer.Producer;


public class App {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		Producer producer = ctx.getBean(Producer.class);
		producer.simpleSend();

	}

}
