package com.excilys.projet.banque.dao.api;

import java.util.List;

import com.excilys.projet.banque.model.Auth;

public interface AuthDAO {

	List<Auth> findAll();

	Auth findById(int idAuth);

	Auth findByLogin(String login);

	void save(Auth auth);

	Auth findAuthByIdClient(int idClient);

}
