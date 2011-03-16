package com.excilys.projet.banque.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.excilys.projet.banque.dao.api.CompteDAO;
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

	@SuppressWarnings("unchecked")
	@Override
	public Compte findById(int idCompte) {
		Assert.isTrue(idCompte > 0, "L'id du Compte ne peut être inférieur ou égal à 0.");

		List<Compte> lesCompte = getHibernateTemplate().find("From Compte where id=?", idCompte);
		if (lesCompte.size() == 0) {
			return null;
		}
		return lesCompte.get(0);
	}

	@Override
	public void save(Compte compte) {
		Assert.notNull(compte, "Le compte ne peut être null.");

		getHibernateTemplate().save(compte);
	}

	@Override
	public void update(Compte compte) {
		Assert.notNull(compte, "Le compte ne peut être null.");

		getHibernateTemplate().update(compte);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Compte> findAllByIdClient(int idClient) {
		Assert.isTrue(idClient > 0, "L'id du Client ne peut être inférieur ou égal à 0.");
		return getHibernateTemplate().find("From Compte  where client.id=?", idClient);
		// return
		// getHibernateTemplate().find("From Compte left join fetch cp.client where cl.id=?",
		// idClient);
	}

}
