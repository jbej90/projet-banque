package com.excilys.projet.banque.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.excilys.projet.banque.dao.api.OperationDAO;
import com.excilys.projet.banque.dao.api.exceptions.DAOException;
import com.excilys.projet.banque.dao.utils.CalculDateMois;
import com.excilys.projet.banque.model.Carte;
import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.model.EtatOperation;
import com.excilys.projet.banque.model.Operation;
import com.excilys.projet.banque.model.Type;

@Repository("operationDao")
public class OperationDAOImpl extends HibernateDaoSupport implements OperationDAO {

	@Autowired
	public OperationDAOImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Operation findById(int idOperation) {

		List<Operation> lesOperations = getHibernateTemplate().find("From Operation o left join fetch o.compte left join fetch o.carte where o.id = ?", idOperation);
		if (lesOperations.isEmpty()) {
			return null;
		}
		return lesOperations.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Operation> findAll() {
		return getHibernateTemplate().find("From Operation order by dateOp desc");
	}
	
	
	

	public List<Operation> findAllByCompte(int idCompte, Type[] types, EtatOperation[] etats, Date dateDebut, Date dateFin) throws DAOException {
		// Test de pré-taitement
		if (idCompte <= 0) {
			throw new DAOException("Id compte incorrect");
		}
		if (dateDebut.after(dateFin)) {
			throw new DAOException("La date de début ne peut être après la date de fin");
		}
		
		// Construction de la clause WHERE
		String requete = "From Operation o left join fetch o.compte c where c.id = ?";
		if (types != null && types.length > 0) {
			requete += " type in ('"+StringUtils.arrayToDelimitedString(types, "', '")+"') and";
		}
		if (etats != null && etats.length > 0) {
			requete += " etat in ('"+StringUtils.arrayToDelimitedString(etats, "', '")+"') and";
		}
		requete += " o.dateOp between ? and ?";
		
		return getHibernateTemplate().find(requete, idCompte, dateDebut, dateFin);
	}
	
	public List<Operation> findAllByClient(int idClient, Type[] types, EtatOperation[] etats, Date dateDebut, Date dateFin) throws DAOException {
		// Test de pré-taitement
		if (idClient <= 0) {
			throw new DAOException("Id client incorrect");
		}
		if (dateDebut.after(dateFin)) {
			throw new DAOException("La date de début ne peut être après la date de fin");
		}

		// Construction de la requete
		String requete = "From Operation o left join fetch o.compte c left join fetch c.client ci where ci.id = ?";
		if (types != null && types.length > 0) {
			requete += " type in ('"+StringUtils.arrayToDelimitedString(types, "', '")+"') and";
		}
		if (etats != null && etats.length > 0) {
			requete += " etat in ('"+StringUtils.arrayToDelimitedString(etats, "', '")+"') and";
		}
		requete += " o.dateOp between ? and ?";
		
		return getHibernateTemplate().find(requete, dateDebut, dateFin);
	}
	
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Operation> findAllByCompte(Compte compte) {
		return getHibernateTemplate().find("From Operation o where compte_fk = ? order by o.dateOp desc", compte.getId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Operation> findAllByCarte(Carte carte) {
		return getHibernateTemplate().find("From Operation o where carte_fk = ? order by o.dateOp desc", carte.getId());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Operation> findAllByClient(Date date, Client client) {
		DateTime dateDebut = CalculDateMois.calculDateTimeDebutMois(date);
		DateTime dateFin = CalculDateMois.calculDateTimeFinMois(date);
		return getHibernateTemplate().find("From Operation o left join fetch o.compte c where o.dateOp between ? and ? and c.client.id = ? order by o.dateOp desc", dateDebut.toDate(), dateFin.toDate(), client.getId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Operation> findAllByClientByType(Date date, Client client, Type type) {
		DateTime dateDebut = CalculDateMois.calculDateTimeDebutMois(date);
		DateTime dateFin = CalculDateMois.calculDateTimeFinMois(date);
		return getHibernateTemplate().find("From Operation o left join fetch o.compte c left join fetch c.client ci where o.dateOp between ? and ? and ci.id = ? and o.type = ? order by o.dateOp desc", dateDebut.toDate(), dateFin.toDate(), client.getId(), type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Operation> findAllByMoisByCompte(Date date, Compte compte) {
		DateTime dateDebut = CalculDateMois.calculDateTimeDebutMois(date);
		DateTime dateFin = CalculDateMois.calculDateTimeFinMois(date);
		return getHibernateTemplate().find("From Operation o where o.dateOp between ? and ? and compte_fk = ? order by o.dateOp desc", dateDebut.toDate(), dateFin.toDate(), compte.getId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Operation> findAllByMoisByCompteAndByType(Date date, Compte compte, Type type) {
		DateTime dateDebut = CalculDateMois.calculDateTimeDebutMois(date);
		DateTime dateFin = CalculDateMois.calculDateTimeFinMois(date);
		return getHibernateTemplate().find("From Operation o where o.dateOp between ? and ? and compte_fk = ? and type=? order by o.dateOp desc", dateDebut.toDate(), dateFin.toDate(), compte.getId(),
				type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Operation> findAllByMoisByCompteAndByTypes(Date date, Compte compte, List<Type> types) {
		DateTime dateDebut = CalculDateMois.calculDateTimeDebutMois(date);
		DateTime dateFin = CalculDateMois.calculDateTimeFinMois(date);
		List<Operation> lesOperations = new ArrayList<Operation>();
		
		if (types.size() != 0) {
			String lesTypesEnString = "'"+StringUtils.arrayToDelimitedString(types.toArray(), "', '")+"'";
			String req = "From Operation o where o.dateOp between ? and ? and compte_fk = ? and type in ("+lesTypesEnString+") order by o.dateOp desc";
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
			lesTypesEnString = "and type not in ('"+StringUtils.arrayToDelimitedString(types.toArray(), "', '")+"')";
		}
		
		String req = "From Operation o where o.dateOp between ? and ? and compte_fk = ? "+lesTypesEnString+" order by o.dateOp desc";
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
