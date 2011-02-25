package com.excilys.projet.banque.dao.api;

import java.util.List;

import com.excilys.projet.banque.model.Type;

public interface TypeDAO {

	List<Type> findAll();
	
	Type findById(int id);
	
	void save(Type type);

}
