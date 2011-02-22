package com.excilys.projet.banque.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import com.excilys.projet.banque.dao.api.ClientDAO;
import com.excilys.projet.banque.dao.api.HibernateUtil;
import com.excilys.projet.banque.dao.api.exceptions.UnknownClientException;
import com.excilys.projet.banque.model.Client;

public class ClientDAOImpl implements ClientDAO {

	private Session session;
	private Transaction tx;

	@Override
	public List<Client> findAll() {
		session = (Session) HibernateUtil.currentSession();
		Query query = session.createQuery("From Client");
		return query.list();
	}

	@Override
	public Client findById(int idClient) throws UnknownClientException {
		session = (Session) HibernateUtil.currentSession();
		Query query = session.createQuery("From Client where id=:id");
		query.setInteger("id", idClient);
		if (query.list().isEmpty())
			throw new UnknownClientException();
		return (Client) query.list().get(0);
	}

	@Override
	public void save(Client client) {
		session = (Session) HibernateUtil.currentSession();
		tx = session.beginTransaction();
		session.save(client);
		tx.commit();
		HibernateUtil.closeSession();
	}

	@Override
	public List<Client> findByNom(String nom) {
		session = (Session) HibernateUtil.currentSession();
		Query query = session.createQuery("From Client where nom=:nom");
		query.setString("nom", nom);
		return query.list();
	}
}
