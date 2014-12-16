package ru.udm.app;

import java.util.List;

public interface AppProducer {

	/**
	 * Publish text in command in topic.
	 */
	void publish(String command);

	/**
	 * Send text in command to queue.
	 */
	void sendToQueue(String command);

	/**
	 * Create batch of commands and send to queue.
	 */
	void sendBatchToQueue(List<String> commands);

}
