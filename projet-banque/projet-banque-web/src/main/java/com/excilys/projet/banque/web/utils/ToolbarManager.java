package com.excilys.projet.banque.web.utils;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * <p>
 * Gestionnaire de toolbar
 * </p>
 * 
 * @author excilys
 * 
 */
@Component
public class ToolbarManager {

	/** Liste des items de la toolbar */
	private List<ToolItem>	tools;

	public ToolbarManager() {
		tools = new LinkedList<ToolItem>();
	}

	public void addTool(ToolItem tool) {
		tools.add(tool);
	}
	
	public List<ToolItem> getTools() {
		return tools;
	}
}
