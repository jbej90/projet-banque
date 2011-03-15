package com.excilys.projet.banque.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

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
		Assert.isTrue(idAuth>0, "L'id Auth ne peut être inférieur ou égal à 0.");
		
		return (Auth) getHibernateTemplate().find("From Auth a left join fetch a.client where a.id=" + idAuth).get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Auth findByLogin(String login) {
		Assert.hasText(login, "Le login ne peut être vide.");
		Assert.notNull(login, "Le login ne peut être null.");
		
		List<Auth> lesAuth = getHibernateTemplate().find("From Auth a left join fetch a.client where a.login=?", login);
		if (lesAuth.isEmpty()) {
			return null;
		}
		return lesAuth.get(0);
	}

	@Override
	public void save(Auth auth) {
		Assert.notNull(auth, "Auth ne peut être null.");
		
		MessageDigestPasswordEncoder m = new MessageDigestPasswordEncoder("SHA-1");
		auth.setPassword(m.encodePassword(auth.getPassword(), ""));
		getHibernateTemplate().save(auth);
	}

	@Override
	public Auth findAuthByIdClient(int idClient) {
		Assert.isTrue(idClient>0, "L'id du Client ne peut être inférieur ou égal à 0.");
		
		return (Auth) getHibernateTemplate().find("From Auth a left join fetch a.client where a.client.id=?", idClient).get(0);
	}

}
