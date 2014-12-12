package ru.udm.jms.producer;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class Producer {

	@Autowired
	@Qualifier("jmsProducerTemplate")
	JmsTemplate jmsTemplate;

	@Value("${jms.producer.queue}")
	String destinationName;

	// @Value("connectionFactory")
	// public void setConnectionFactory(ConnectionFactory cf){
	// this.jmsTemplate = new JmsTemplate(cf);
	// }
	//

	public void simpleSend() {
		this.jmsTemplate.send(destinationName, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				return session
						.createTextMessage("Hello from Spring jms template");
			}
		});
	}
}
