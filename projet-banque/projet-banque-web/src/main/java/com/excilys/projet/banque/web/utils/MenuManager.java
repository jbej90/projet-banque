package com.excilys.projet.banque.web.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

@Component
public class MenuManager {
	private List<MenuItem> items;
	private MenuItem itemCourant;
	
	
	public MenuManager() {
		items = new LinkedList<MenuItem>();
	}
	public MenuManager(List<Map<String, String>> items) {
		init(items);
	}
	
	
	public void init(List<Map<String, String>> items) {
		this.items = new LinkedList<MenuItem>();
		
		// Parcours la liste de map passé par spring depuis la conf XMl, et créé une liste de MenuItem
		int id = 0;
		for (Map<String, String> item : items) {
			this.items.add(new MenuItem(id++, item.get("title"), item.get("url"), item.get("pattern")));
		}
	}

	public void setUri(String uri) {
		String patterns[];
		// Parcours la liste des items et vérifie les patterns pour la sélection de l'item courant
		for (MenuItem item : items) {
			// Parcours le ou les patterns pour chaque item
			patterns = item.getPattern().split(";");
			for (String pattern : patterns) {
				// Si ça correspond, on change l'item courant en pensant à déselectionner l'ancien 
				if (PatternMatchUtils.simpleMatch(pattern, uri)) {
					if (itemCourant != null) {
						itemCourant.setSelected(false);
					}
					item.setSelected(true);
					itemCourant = item;
				}
			}
		}
	}
	
	
	
	public List<MenuItem> getItems() {
		return items;
	}
	
	public MenuItem getItemCourant() {
		return itemCourant;
	}
}