package com.excilys.projet.banque.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

import com.excilys.projet.banque.dao.api.AuthDAO;
import com.excilys.projet.banque.model.Auth;

public class AuthDAOImpl extends HibernateDaoSupport implements AuthDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Auth> findAll() {
		return getHibernateTemplate().find("From Auth");
	}

	@Override
	public Auth findById(int idAuth) {
		return (Auth) getHibernateTemplate().find("From Auth a left join fetch a.client where a.id=" + idAuth).get(0);
	}

	@Override
	public Auth findByLogin(String login) {
		return (Auth) getHibernateTemplate().find("From Auth a left join fetch a.client where a.login=?", login).get(0);
	}

	@Override
	public void save(Auth auth) {
		MessageDigestPasswordEncoder m = new MessageDigestPasswordEncoder("SHA-1");
		auth.setPassword(m.encodePassword(auth.getPassword(), ""));
		getHibernateTemplate().save(auth);
	}

	@Override
	public Auth findAuthByIdClient(int idClient) {
		return (Auth) getHibernateTemplate().find("From Auth a left join fetch a.client where a.client.id=?", idClient).get(0);
	}

}
