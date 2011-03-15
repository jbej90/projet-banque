package com.excilys.projet.banque.web.utils;

/**
 * Classe de définition d'un item du menu. Elle comprend toutes les informations de ceux-ci : id, titre, url, pattern
 * 
 * @author excilys
 * 
 */
public class MenuItem {

	private String	title		= "";
	private String	url			= "";
	private String	pattern		= "";
	private boolean	selected	= false;

	public MenuItem() {
	}

	public MenuItem(String title, String url) {
		super();
		this.title = title;
		this.url = url;
	}

	public MenuItem(String title, String url, String pattern) {
		this(title, url);
		this.pattern = pattern;
	}

	public MenuItem(String title, String url, String pattern, boolean selected) {
		this(title, url, pattern);
		this.selected = selected;
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
