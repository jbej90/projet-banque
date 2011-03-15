package com.excilys.projet.banque.web.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.excilys.projet.banque.web.utils.Message.MessageType;

/**
 * Pile de messages utilisée pour stocker les messages d'erreurs et de validation. Ces messages sont dépilés automatiquement quand on les affiches pour les afficher. Cette pile est
 * spécifique à <b>chaque session</b>.
 * 
 * @author excilys
 * 
 */
public class MessageStack {

	public final static String					DEFAULT_DOMAIN	= "default";
	private Map<String, LinkedList<Message>>	messages;

	private MessageStack() {
		messages = new HashMap<String, LinkedList<Message>>();
		messages.put(DEFAULT_DOMAIN, new LinkedList<Message>());
	}

	public static MessageStack getInstance(HttpServletRequest request) {
		MessageStack messages = (MessageStack) request.getSession().getAttribute("messages");
		if (messages == null) {
			messages = new MessageStack();
			request.getSession().setAttribute("messages", messages);
		}
		return messages;
	}

	public void addInfo(String message) {
		addMessage(message, DEFAULT_DOMAIN, MessageType.INFO);
	}

	public void addInfo(String message, String domaine) {
		addMessage(message, domaine, MessageType.INFO);
	}

	public void addWarning(String message) {
		addMessage(message, DEFAULT_DOMAIN, MessageType.WARNING);
	}

	public void addWarning(String message, String domaine) {
		addMessage(message, domaine, MessageType.WARNING);
	}

	public void addError(String message) {
		addMessage(message, DEFAULT_DOMAIN, MessageType.ERROR);
	}

	public void addError(String message, String domaine) {
		addMessage(message, domaine, MessageType.ERROR);
	}

	private void addMessage(String message, String domaine, MessageType type) {
		LinkedList<Message> messagesDomaine;
		if (!messages.keySet().contains(domaine)) {
			messagesDomaine = new LinkedList<Message>();
			messages.put(domaine, messagesDomaine);
		}
		else {
			messagesDomaine = messages.get(domaine);
		}
		messagesDomaine.add(new Message(message, type));
	}

	public int getSize(String domaine) {
		if (messages.get(domaine) != null) {
			return messages.get(domaine).size();
		}
		return 0;
	}

	public List<Message> getMessages(String domaine) {
		LinkedList<Message> tmp = new LinkedList<Message>(messages.get(domaine));
		messages.remove(domaine);
		return tmp;
	}

	public List<Message> getMessages() {
		LinkedList<Message> tmp = new LinkedList<Message>(messages.get(DEFAULT_DOMAIN));
		messages.remove(DEFAULT_DOMAIN);
		return tmp;
	}

//	public List<Message> getMessages() {
//		List<Message> tmp = new LinkedList<Message>();
//
//		Set<String> domaines = messages.keySet();
//		for (String domaine : domaines) {
//			tmp.addAll(messages.get(domaine));
//		}
//
//		messages.clear();
//		return tmp;
//	}

	public Message getLastMessage(String domaine) {
		return messages.get(domaine).size() > 0 ? messages.get(domaine).remove(0) : null;
	}

	public String getDefaultDomain() {
		return DEFAULT_DOMAIN;
	}
}
