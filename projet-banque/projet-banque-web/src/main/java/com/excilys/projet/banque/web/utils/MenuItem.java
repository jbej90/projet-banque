package com.excilys.projet.banque.web.utils;


public class MenuItem {
	private int id;
	private String title;
	private String url;
	
	
	public MenuItem() {

	}
	public MenuItem(int id, String title, String url) {
		super();
		this.id = id;
		this.title = title;
		this.url = url;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
}
