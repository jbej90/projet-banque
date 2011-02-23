package com.excilys.projet.banque.web.utils;

public class Message {
	public enum MessageType {
		INFO("info"), ERROR("error"), WARNING("warning");
		
		/** L'attribut qui contient la valeur associé à l'enum */
		private final String value;
		
		/** Le constructeur qui associe une valeur à l'enum */
		private MessageType(String value) {
			this.value = value;
		}
		
		/** La méthode accesseur qui renvoit la valeur de l'enum */
		public String getValue() {
			return this.value;
		}
	};
	
	private String message;
	private MessageType type;
	
	
	public Message(String message, MessageType type) {
		this.message = message;
		this.type = type;
	}

	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTypeValue() {
		return type.getValue();
	}
	public MessageType getType() {
		return type;
	}
	public void setType(MessageType type) {
		this.type = type;
	}
}