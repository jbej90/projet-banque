package com.excilys.projet.banque.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.excilys.projet.banque.dao.api.OperationDAO;
import com.excilys.projet.banque.model.Carte;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.model.Operation;
import com.excilys.projet.banque.model.Type;

@Repository("operationDao")
public class OperationDAOImpl extends HibernateDaoSupport implements OperationDAO {

	@Autowired
	public OperationDAOImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Operation> findAll() {
		return getHibernateTemplate().find("From Operation");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Operation> findAllByType(Type type) {
		System.out.println(type);
		return getHibernateTemplate().find("From Operation o where type = ?", type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Operation> findAllByCompte(Compte compte) {
		return getHibernateTemplate().find("From Operation i where compte_fk = ?", compte.getId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Operation> findAllByCarte(Carte carte) {
		return getHibernateTemplate().find("From Operation i where carte_fk = ?", carte.getId());
	}

	@Override
	public Operation findById(int idOperation) {
		return (Operation) getHibernateTemplate().find("From Operation o left join fetch o.compte left join fetch o.carte where o.id=?", idOperation).get(0);
	}

	@Override
	public void save(Operation operation) {
		getHibernateTemplate().save(operation);
	}

}
