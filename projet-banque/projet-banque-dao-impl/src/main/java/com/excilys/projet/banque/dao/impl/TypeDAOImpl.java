package com.excilys.projet.banque.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.excilys.projet.banque.dao.api.TypeDAO;
import com.excilys.projet.banque.model.Type;

public class TypeDAOImpl extends HibernateDaoSupport implements TypeDAO {

	@Override
	public List<Type> findAll() {
		return getHibernateTemplate().find("from Type");
	}

	@Override
	public Type findById(int id) {
		return (Type)getHibernateTemplate().find("from Type t where t.id = ?", id);
	}

	@Override
	public void save(Type type) {
		getHibernateTemplate().save(type);
	}

}
