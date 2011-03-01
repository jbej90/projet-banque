package com.excilys.projet.banque.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.excilys.projet.banque.dao.api.CarteDAO;
import com.excilys.projet.banque.model.Carte;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.model.TypeCarte;

@Repository("carteDao")
public class CarteDAOImpl extends HibernateDaoSupport implements CarteDAO {

	@Autowired
	public CarteDAOImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Carte> findAll() {
		return getHibernateTemplate().find("From Carte");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Carte> findAllByCompte(Compte compte) {
		return getHibernateTemplate().find("From Carte where compte_fk = ?", compte.getId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Carte> findAllByType(TypeCarte type) {
		return getHibernateTemplate().find("From Carte where type = ?", type);
	}

	@Override
	public Carte findById(int idCarte) {
		return (Carte) getHibernateTemplate().find("From Carte ca left join fetch ca.compte where ca.id=?", idCarte).get(0);
	}

	@Override
	public void save(Carte carte) {
		getHibernateTemplate().save(carte);
	}

}
