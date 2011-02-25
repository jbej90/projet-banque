package com.excilys.projet.banque.web.utils;

import java.util.LinkedList;
import java.util.List;


public class Menu {
	private List<MenuItem> items;
	private int itemSelected;
	
	
	
	public Menu() {
		this.items = new LinkedList<MenuItem>();
	}
	public Menu(List<MenuItem> items) {
		super();
		this.items = items;
	}
	
	
	public void addItem(MenuItem item) {
		items.add(item);
	}
	
	public void removeItem(int index) {
		items.remove(index);
	}

	
	public List<MenuItem> getItems() {
		return items;
	}

	public int getItemSelected() {
		return itemSelected;
	}

	public void setItemSelected(int itemSelected) {
		this.itemSelected = itemSelected;
	}
}
