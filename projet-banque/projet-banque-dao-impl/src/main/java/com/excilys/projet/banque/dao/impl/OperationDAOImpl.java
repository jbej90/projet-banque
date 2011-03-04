package com.excilys.projet.banque.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.excilys.projet.banque.dao.api.OperationDAO;
import com.excilys.projet.banque.dao.utils.CalculDateMois;
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
		List<Operation> lesOperations = getHibernateTemplate().find("From Operation o left join fetch o.compte left join fetch o.carte where o.id=?", idOperation);
		if (lesOperations.isEmpty()) {
			return null;
		}
		return lesOperations.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Operation> findAllByMoisByCompte(Date date, Compte compte) {
		DateTime dateDebut = CalculDateMois.calculDateTimeDebutMois(date);
		DateTime dateFin = CalculDateMois.calculDateTimeFinMois(date);
		return getHibernateTemplate().find("From Operation o where DATE_OP between ? and ? and compte_fk = ?", dateDebut.toDate(), dateFin.toDate(), compte.getId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Operation> findAllByMoisByCompteAndByType(Date date, Compte compte, Type type) {
		DateTime dateDebut = CalculDateMois.calculDateTimeDebutMois(date);
		DateTime dateFin = CalculDateMois.calculDateTimeFinMois(date);
		return getHibernateTemplate().find("From Operation o where DATE_OP between ? and ? and compte_fk = ? and type=?", dateDebut.toDate(), dateFin.toDate(), compte.getId(),
				type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Operation> findAllByMoisByCompteAndByTypes(Date date, Compte compte, List<Type> types) {
		DateTime dateDebut = CalculDateMois.calculDateTimeDebutMois(date);
		DateTime dateFin = CalculDateMois.calculDateTimeFinMois(date);
		List<Operation> lesOperations = new ArrayList<Operation>();
		String lesTypesEnString = "";
		if (types.size() != 0) {
			lesTypesEnString = "and type in (";
			int compteur = 0;
			for (Type unType : types) {

				lesTypesEnString += "'" + unType + "'";
				if (types.size() - 1 != compteur) {
					lesTypesEnString += ",";
				}
				compteur++;
			}
			lesTypesEnString += ")";

			String req = "From Operation o where DATE_OP between ? and ? and compte_fk = ?  " + lesTypesEnString;
			System.out.println(req);

			lesOperations = getHibernateTemplate().find(req, dateDebut.toDate(), dateFin.toDate(), compte.getId());

		}
		return lesOperations;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Operation> findAllByMoisByCompteAndNotInTypes(Date date, Compte compte, List<Type> types) {
		DateTime dateDebut = CalculDateMois.calculDateTimeDebutMois(date);
		DateTime dateFin = CalculDateMois.calculDateTimeFinMois(date);

		String lesTypesEnString = "";
		if (types.size() != 0) {
			lesTypesEnString = "and type not in (";
			int compteur = 0;
			for (Type unType : types) {

				lesTypesEnString += "'" + unType + "'";
				if (types.size() - 1 != compteur) {
					lesTypesEnString += ",";
				}
				compteur++;
			}
			lesTypesEnString += ")";
		}
		String req = "From Operation o where DATE_OP between ? and ? and compte_fk = ?  " + lesTypesEnString;
		System.out.println(req);

		return getHibernateTemplate().find(req, dateDebut.toDate(), dateFin.toDate(), compte.getId());
	}


	@Override
	public void save(Operation operation) {
		getHibernateTemplate().save(operation);
	}
	
	@Override
	public void update(Operation operation) {
		getHibernateTemplate().update(operation);
	}
}
