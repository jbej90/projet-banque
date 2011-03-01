package com.excilys.projet.banque.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.excilys.projet.banque.dao.api.CompteDAO;
import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;

@Repository("compteDao")
public class CompteDAOImpl extends HibernateDaoSupport implements CompteDAO {

	@Autowired
	public CompteDAOImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Compte> findAll() {
		return getHibernateTemplate().find("From Compte");
	}

	@Override
	public Compte findById(int idCompte) {
		return (Compte) getHibernateTemplate().find("From Compte where id=?", idCompte).get(0);
	}

	@Override
	public void save(Compte compte) {
		getHibernateTemplate().save(compte);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Compte> findAllByClient(Client client) {
		return getHibernateTemplate().find("From Compte where client.id=?", client.getId());
	}

}
