package com.excilys.projet.banque.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import com.excilys.projet.banque.dao.api.ClientDAO;
import com.excilys.projet.banque.dao.api.HibernateUtil;
import com.excilys.projet.banque.dao.api.exceptions.EmptyClientsException;
import com.excilys.projet.banque.dao.api.exceptions.UnknownClientException;
import com.excilys.projet.banque.model.Client;

public class ClientDAOImpl implements ClientDAO {

	private Session session;
	private Transaction tx;

	public List<Client> findAll() throws EmptyClientsException {
		session = (Session) HibernateUtil.currentSession();
		Query query = session.createQuery("From Client");
		if (query.list().isEmpty())
			throw new EmptyClientsException();
		return query.list();
	}

	public Client findById(int idClient) throws UnknownClientException {
		session = (Session) HibernateUtil.currentSession();
		Query query = session.createQuery("From Client where id=:id");
		query.setInteger("id", idClient);
		if (query.list().isEmpty())
			throw new UnknownClientException();
		return (Client) query.list().get(0);
	}

	public void save(Client client) {
		Session session = (Session) HibernateUtil.currentSession();
		tx = session.beginTransaction();
		session.save(client);
		tx.commit();
		HibernateUtil.closeSession();
	}
}
