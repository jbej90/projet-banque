package com.excilys.projet.banque.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.excilys.projet.banque.dao.api.ClientDAO;
import com.excilys.projet.banque.model.Client;

@Repository("clientDao")
public class ClientDAOImpl extends HibernateDaoSupport implements ClientDAO {

	@Autowired
	public ClientDAOImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Client> findAll() {
		return getHibernateTemplate().find("From Client");
	}

	@Override
	public Client findById(int idClient) {
		return (Client) getHibernateTemplate().find("From Client c left join fetch c.comptes where c.id=?", idClient).get(0);
	}

//	@Override
	public Client findByUsername(String username) {
		return (Client) getHibernateTemplate().find("select distinct c From Client c left join fetch c.auth a left join fetch c.comptes where a.login = ?", username).get(0);
	}

	@Override
	public void save(Client client) {
		getHibernateTemplate().save(client);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Client> findByNom(String nom) {
		return getHibernateTemplate().find("From Client where nom=?", nom);
	}
}
