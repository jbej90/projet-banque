 package com.excilys.projet.banque.dao.utils;

import java.util.Date;

import org.joda.time.DateTime;

public class CalculDateMois {

	public static DateTime calculDateTimeDebutMois(Date date) {
		DateTime dt = new DateTime(date);
		int premierJour = dt.dayOfMonth().withMinimumValue().getDayOfMonth();

		int moisCourant = dt.getMonthOfYear();
		int anneeCourante = dt.getYear();

		return new DateTime(anneeCourante, moisCourant, premierJour, 0, 0, 0, 0);
	}

	public static DateTime calculDateTimeFinMois(Date date) {
		DateTime dt = new DateTime(date);
		int moisCourant = dt.getMonthOfYear();
		int anneeCourante = dt.getYear();
		int dernierJour = dt.dayOfMonth().withMaximumValue().getDayOfMonth();
		return new DateTime(anneeCourante, moisCourant, dernierJour, 0, 0, 0, 0);
	}
}
