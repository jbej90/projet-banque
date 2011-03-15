package com.excilys.projet.banque.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.excilys.projet.banque.dao.api.OperationDAO;
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
		Assert.isTrue(idOperation>0, "L'id d'Opération ne peut être inférieur ou égal à 0.");

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

	@SuppressWarnings("unchecked")
	public List<Operation> findAllByIdCompte(int idCompte, Type[] types, EtatOperation[] etats, Date dateDebut, Date dateFin) {

		Assert.notNull(idCompte, "L'id du compte ne peut être null.");
		Assert.notNull(dateDebut, "La date de début null.");
		Assert.notNull(dateFin, "La date de fin ne peut être null.");
		Assert.isTrue(idCompte>0, "Un id de compte ne peut être inférieur ou égal à 0.");
		Assert.isTrue(!dateDebut.after(dateFin), "La date de début ne peut être après la date de fin.");
		
		// Construction de la clause WHERE
		String requete = "From Operation o left join fetch o.compte c where c.id = ? and";
		if (types != null && types.length > 0) {
			requete += " o.type in ('" + StringUtils.arrayToDelimitedString(types, "', '") + "') and";
		}
		if (etats != null && etats.length > 0) {
			requete += " o.etat in ('" + StringUtils.arrayToDelimitedString(etats, "', '") + "') and";
		}
		requete += " o.dateOp between ? and ? order by o.dateOp desc";
		
		return getHibernateTemplate().find(requete, idCompte, dateDebut, dateFin);
	}

	@SuppressWarnings("unchecked")
	public List<Operation> findAllByIdClient(int idClient, Type[] types, EtatOperation[] etats, Date dateDebut, Date dateFin) {

		Assert.notNull(idClient, "L'id du client ne peut être null.");
		Assert.notNull(dateDebut, "La date de début null.");
		Assert.notNull(dateFin, "La date de fin ne peut être null.");
		Assert.isTrue(idClient>0, "Un id de compte ne peut être inférieur ou égal à 0.");
		Assert.isTrue(!dateDebut.after(dateFin), "La date de début ne peut être après la date de fin.");
		
		// Construction de la requete
		String requete = "From Operation o left join fetch o.compte c left join fetch c.client ci where ci.id = ? and";
		if (types != null && types.length > 0) {
			requete += " o.type in ('" + StringUtils.arrayToDelimitedString(types, "', '") + "') and";
		}
		if (etats != null && etats.length > 0) {
			requete += " etat in ('" + StringUtils.arrayToDelimitedString(etats, "', '") + "') and";
		}
		requete += " o.dateOp between ? and ? order by o.dateOp desc";
		
		return getHibernateTemplate().find(requete, idClient, dateDebut, dateFin);
	}
	
	@Override
	public void save(Operation operation) {
		Assert.notNull(operation, "L'operation ne peut être null.");
		
		getHibernateTemplate().save(operation);
	}

	@Override
	public void update(Operation operation) {
		Assert.notNull(operation, "L'operation ne peut être null.");
		
		getHibernateTemplate().update(operation);
	}
	
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Operation> findAllByCompte(Compte compte) {
//		return getHibernateTemplate().find("From Operation o where compte_fk = ? order by o.dateOp desc", compte.getId());
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Operation> findAllByCarte(Carte carte) {
//		return getHibernateTemplate().find("From Operation o where carte_fk = ? order by o.dateOp desc", carte.getId());
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Operation> findAllByClient(Date date, Client client) {
//		DateTime dateDebut = CalculDateMois.calculDateTimeDebutMois(date);
//		DateTime dateFin = CalculDateMois.calculDateTimeFinMois(date);
//		return getHibernateTemplate().find("From Operation o left join fetch o.compte c where o.dateOp between ? and ? and c.client.id = ? order by o.dateOp desc",
//				dateDebut.toDate(), dateFin.toDate(), client.getId());
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Operation> findAllByClientByType(Date date, Client client, Type type) {
//		DateTime dateDebut = CalculDateMois.calculDateTimeDebutMois(date);
//		DateTime dateFin = CalculDateMois.calculDateTimeFinMois(date);
//		return getHibernateTemplate().find(
//				"From Operation o left join fetch o.compte c left join fetch c.client ci where o.dateOp between ? and ? and ci.id = ? and o.type = ? order by o.dateOp desc",
//				dateDebut.toDate(), dateFin.toDate(), client.getId(), type);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Operation> findAllByMoisByCompte(Date date, Compte compte) {
//		DateTime dateDebut = CalculDateMois.calculDateTimeDebutMois(date);
//		DateTime dateFin = CalculDateMois.calculDateTimeFinMois(date);
//		return getHibernateTemplate().find("From Operation o where o.dateOp between ? and ? and compte_fk = ? order by o.dateOp desc", dateDebut.toDate(), dateFin.toDate(),
//				compte.getId());
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Operation> findAllByMoisByCompteAndByType(Date date, Compte compte, Type type) {
//		DateTime dateDebut = CalculDateMois.calculDateTimeDebutMois(date);
//		DateTime dateFin = CalculDateMois.calculDateTimeFinMois(date);
//		return getHibernateTemplate().find("From Operation o where o.dateOp between ? and ? and compte_fk = ? and type=? order by o.dateOp desc", dateDebut.toDate(),
//				dateFin.toDate(), compte.getId(), type);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Operation> findAllByMoisByCompteAndByTypes(Date date, Compte compte, List<Type> types) {
//		DateTime dateDebut = CalculDateMois.calculDateTimeDebutMois(date);
//		DateTime dateFin = CalculDateMois.calculDateTimeFinMois(date);
//		List<Operation> lesOperations = new ArrayList<Operation>();
//		
//		if (types.size() != 0) {
//			String lesTypesEnString = "'" + StringUtils.arrayToDelimitedString(types.toArray(), "', '") + "'";
//			String req = "From Operation o where o.dateOp between ? and ? and compte_fk = ? and type in (" + lesTypesEnString + ") order by o.dateOp desc";
//			lesOperations = getHibernateTemplate().find(req, dateDebut.toDate(), dateFin.toDate(), compte.getId());
//		}
//		return lesOperations;
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Operation> findAllByMoisByCompteAndNotInTypes(Date date, Compte compte, List<Type> types) {
//		DateTime dateDebut = CalculDateMois.calculDateTimeDebutMois(date);
//		DateTime dateFin = CalculDateMois.calculDateTimeFinMois(date);
//
//		String lesTypesEnString = "";
//		if (types.size() != 0) {
//			lesTypesEnString = "and type not in ('" + StringUtils.arrayToDelimitedString(types.toArray(), "', '") + "')";
//		}
//
//		String req = "From Operation o where o.dateOp between ? and ? and compte_fk = ? " + lesTypesEnString + " order by o.dateOp desc";
//		return getHibernateTemplate().find(req, dateDebut.toDate(), dateFin.toDate(), compte.getId());
//	}

}
