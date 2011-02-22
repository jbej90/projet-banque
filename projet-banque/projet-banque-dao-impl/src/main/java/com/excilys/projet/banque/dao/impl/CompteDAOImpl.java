package com.excilys.projet.banque.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import com.excilys.projet.banque.dao.api.CompteDAO;
import com.excilys.projet.banque.dao.api.HibernateUtil;
import com.excilys.projet.banque.dao.api.exceptions.UnknownCompteException;
import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;

public class CompteDAOImpl implements CompteDAO {

	private Session session;
	private Transaction tx;

	@Override
	public List<Compte> findAll() {
		session = (Session) HibernateUtil.currentSession();
		Query query = session.createQuery("From Compte");
		return query.list();
	}

	@Override
	public Compte findById(int idCompte) throws UnknownCompteException {
		session = (Session) HibernateUtil.currentSession();
		Query query = session.createQuery("From Compte where id=:id");
		query.setInteger("id", idCompte);
		if (query.list().isEmpty())
			throw new UnknownCompteException();
		return (Compte) query.list().get(0);
	}

	@Override
	public void save(Compte compte) {
		session = (Session) HibernateUtil.currentSession();
		tx = session.beginTransaction();
		session.save(compte);
		tx.commit();
		HibernateUtil.closeSession();
	}

	@Override
	public List<Compte> findAllByClient(Client client) {
		session = (Session) HibernateUtil.currentSession();
		Query query = session.createQuery("From Compte where client.id=:id");
		query.setInteger("id", client.getId());
		return query.list();
	}

}
