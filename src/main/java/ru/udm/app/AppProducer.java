package ru.udm.app;


public interface AppProducer {

	void publish(String command);
	void sendToQueue(String command);
	void close();
}
