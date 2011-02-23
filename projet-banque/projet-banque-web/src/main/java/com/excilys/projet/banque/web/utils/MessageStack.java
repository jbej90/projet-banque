package com.excilys.projet.banque.web.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.excilys.projet.banque.web.utils.Message.MessageType;

public class MessageStack {
	private List<Message> messages;


	private MessageStack() {
		messages = new ArrayList<Message>();
	}
	public static MessageStack getInstance(HttpServletRequest request) {
		MessageStack messages = (MessageStack)request.getSession().getAttribute("messages");
		if (messages == null) {
			messages = new MessageStack();
			request.getSession().setAttribute("messages", messages);
		}
		return messages;
	}


	public void addInfo(String message) {
		addMessage(message, MessageType.INFO);
	}
	public void addWarning(String message) {
		addMessage(message, MessageType.WARNING);
	}
	public void addError(String message) {
		addMessage(message, MessageType.ERROR);
	}
	private void addMessage(String message, MessageType type) {
		messages.add(new Message(message, type));
	}



	public int getSize() {
		return messages.size();
	}
	public List<Message> getMessages() {
		List<Message> tmp = new ArrayList<Message>(messages);
		messages.clear();
		return tmp;
	}
	public Message getLastMessage() {
		return messages.size() > 0 ? messages.remove(0) : null;
	}
}
