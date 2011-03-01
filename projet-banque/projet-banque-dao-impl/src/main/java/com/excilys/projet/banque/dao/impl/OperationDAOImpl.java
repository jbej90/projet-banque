package com.excilys.projet.banque.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
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

	// public String dateToString(Date date) {
	//
	// DateTime dt = new DateTime(date);
	// int premierJour = dt.dayOfMonth().withMinimumValue().getDayOfMonth();
	// int dernierJour = dt.dayOfMonth().withMaximumValue().getDayOfMonth();
	// int moisCourant = dt.getMonthOfYear();
	// int anneeCourante = dt.getYear();
	// String dateChaineDeb = premierJour + "-" + moisCourant + "-" +
	// anneeCourante + " , " + moisCourant);
	//
	//
	//
	// return anneeCourante+"-"+der
	// }

	@SuppressWarnings("unchecked")
	@Override
	public List<Operation> findAllByMoisByCompte(Date date, Compte compte) {
		DateTime dt = new DateTime(date);
		int premierJour = dt.dayOfMonth().withMinimumValue().getDayOfMonth();
		int dernierJour = dt.dayOfMonth().withMaximumValue().getDayOfMonth();
		int moisCourant = dt.getMonthOfYear();
		int anneeCourante = dt.getYear();
		String dateChaineDeb = anneeCourante + "-" + moisCourant + "-" + premierJour;
		String dateChaineFin = anneeCourante + "-" + moisCourant + "-" + dernierJour;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		Date dateFin = null, dateDeb = null;
		try {
			dateDeb = sdf.parse(dateChaineDeb);
			dateFin = sdf.parse(dateChaineFin);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return getHibernateTemplate().find("From Operation o where DATE_OP between ? and ? and compte_fk = ?", dateDeb, dateFin, compte.getId());
	}

	// @SuppressWarnings("unchecked")
	// @Override
	// public List<Operation> findAllByMoisByCompteAndByType(Date date, Compte
	// compte, Type type) {
	// DateTime dt = new DateTime(date);
	// int premierJour = dt.dayOfMonth().withMinimumValue().getDayOfMonth();
	// int dernierJour = dt.dayOfMonth().withMaximumValue().getDayOfMonth();
	// int moisCourant = dt.getMonthOfYear();
	// int anneeCourante = dt.getYear();
	// String dateChaineDeb = anneeCourante + "-" + moisCourant + "-" +
	// premierJour;
	// String dateChaineFin = anneeCourante + "-" + moisCourant + "-" +
	// dernierJour;
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
	// Date dateFin = null, dateDeb = null;
	// try {
	// dateDeb = sdf.parse(dateChaineDeb);
	// dateFin = sdf.parse(dateChaineFin);
	// } catch (ParseException e) {
	// e.printStackTrace();
	// }
	//
	// return
	// getHibernateTemplate().find("From Operation o where DATE_OP between ? and ? and compte_fk = ?",
	// dateDeb, dateFin, compte.getId());
	// }

}
