package com.excilys.projet.banque.model;

public class Auth {
	private int id;
	private String login;
	private Client client;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
}
