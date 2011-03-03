package com.excilys.projet.banque.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.excilys.projet.banque.dao.api.AuthDAO;
import com.excilys.projet.banque.model.Auth;

@Repository("authDao")
public class AuthDAOImpl extends HibernateDaoSupport implements AuthDAO {

	@Autowired
	public AuthDAOImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Auth> findAll() {
		return getHibernateTemplate().find("From Auth");
	}

	@Override
	public Auth findById(int idAuth) {
		return (Auth) getHibernateTemplate().find("From Auth a left join fetch a.client where a.id=" + idAuth).get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Auth findByLogin(String login) {
		List<Auth> lesAuth = getHibernateTemplate().find("From Auth a left join fetch a.client where a.login=?", login);
		if (lesAuth.isEmpty()) {
			return null;
		}
		return lesAuth.get(0);
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
