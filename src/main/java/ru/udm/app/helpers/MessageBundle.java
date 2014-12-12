package ru.udm.app.helpers;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MessageBundle {

	@Value("${message.count}")
	int messageCount;

	private int currentPos;

	@PostConstruct
	public void init() {
		currentPos = 0;
	}

	public String getNext() {
		if (currentPos < messageCount) {
			return "Message " + (currentPos++);
		} else {
			return null;
		}
	}
}
