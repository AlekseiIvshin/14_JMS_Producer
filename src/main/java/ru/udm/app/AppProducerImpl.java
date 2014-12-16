package ru.udm.app;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import ru.udm.jms.queue.Producer;
import ru.udm.jms.topic.Publisher;

@Component
public class AppProducerImpl implements AppProducer {

	private static Logger LOGGER = LoggerFactory
			.getLogger(AppProducerImpl.class);

	@Autowired
	private ApplicationContext context;
	@Autowired
	private Publisher publisher;
	@Autowired
	private Producer producer;

	@Override
	public void publish(String command) {
		LOGGER.debug("Publish in topic. Command = '{}'", command);
		publisher.publish(command);
	}

	@Override
	public void sendToQueue(String command) {
		LOGGER.debug("Send to queue. Command = '{}'", command);
		producer.send(command);
	}

	@Override
	public void sendBatchToQueue(List<String> commands) {
		LOGGER.debug("Send batch to queue. Command count = '{}'",
				commands.size());
		producer.sendBatch(commands);

	}

}
