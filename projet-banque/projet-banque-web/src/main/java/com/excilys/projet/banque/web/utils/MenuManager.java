package com.excilys.projet.banque.web.utils;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

/**
 * <p>
 * Gestionnaire de menu permettant de :
 * </p>
 * <ul>
 * <li>parser le fichier de configuration des menus</li>
 * <li>vérifier la concordance entre le pattern des items et l'URI pour savoir quel item sélectionner</li>
 * </ul>
 * 
 * @author excilys
 * 
 */
@Component
public class MenuManager {

	/** Liste des items du menu */
	private List<MenuItem>	items;
	/** Item courant */
	private MenuItem		itemCourant;

	public MenuManager() {
		items = new LinkedList<MenuItem>();
	}

	public MenuManager(List<MenuItem> items) {
		this.items = items;
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
