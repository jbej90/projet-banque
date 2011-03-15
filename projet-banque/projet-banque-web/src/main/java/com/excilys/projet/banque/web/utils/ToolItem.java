package com.excilys.projet.banque.web.utils;

/**
 * Classe de définition d'un item de la toolbar. Elle comprend toutes les informations de ceux-ci : id, titre, image, url, pattern
 * 
 * @author excilys
 * 
 */
public class ToolItem {

	private String	title;
	private String	url;
	private String	img;
	private String	alt;
	private String	action;

	public ToolItem() {
	}

	public ToolItem(String title, String url, String img, String alt, String action) {
		super();
		this.title = title;
		this.url = url;
		this.img = img;
		this.alt = alt;
		this.action = action;
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

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
