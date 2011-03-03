package com.excilys.projet.banque.web.utils;


public class MenuItem {
	private int id;
	private String title;
	private String url;
	private String pattern;
	private boolean selected;
	
	
	
	public MenuItem() {}
	public MenuItem(int id, String title, String url) {
		super();
		this.id = id;
		this.title = title;
		this.url = url;
	}
	public MenuItem(int id, String title, String url, String pattern) {
		this(id, title, url);
		this.pattern = pattern;
	}
	public MenuItem(int id, String title, String url, String pattern, boolean selected) {
		this(id, title, url, pattern);
		this.selected = selected;
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
	
	public String getPattern() {
		return pattern;
	}
	
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
