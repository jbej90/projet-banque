package com.excilys.projet.banque.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

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
		Assert.isTrue(idClient>0, "L'id du Client ne peut être inférieur ou égal à 0.");
		
		return (Client) getHibernateTemplate().find("From Client c left join fetch c.comptes where c.id=?", idClient).get(0);
	}

	@Override
	public Client findByLogin(String login) {
		Assert.hasText(login, "Le login ne peut être vide.");
		Assert.notNull(login, "Le login ne peut être null.");
		
		return (Client) getHibernateTemplate().find("select distinct c From Client c left join fetch c.auth a left join fetch c.comptes where a.login = ?", login).get(0);
	}

	@Override
	public void save(Client client) {
		Assert.notNull(client, "Le Client ne peut être null.");
		
		getHibernateTemplate().save(client);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Client> findByNom(String nom) {
		Assert.hasText(nom, "Le nom ne peut être vide.");
		Assert.notNull(nom, "Le nom ne peut être null.");
		
		return getHibernateTemplate().find("From Client where nom=?", nom);
	}
}
