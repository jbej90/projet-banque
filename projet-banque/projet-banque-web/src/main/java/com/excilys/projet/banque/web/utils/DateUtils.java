package com.excilys.projet.banque.web.utils;

import java.util.Calendar;
import java.util.Locale;

public class DateUtils {

	public static Calendar getMonthYearFilter(Calendar cal) {
		return getMonthYearFilter(cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
	}

	public static Calendar getMonthYearFilter(int month, int year) throws NumberFormatException {
		Calendar cal = Calendar.getInstance(Locale.FRANCE);

		if (month < 0 || month > 11) {
			throw new NumberFormatException("Mois hors limite");
		}

		// Bride l'historique à 3 années coulantes (de mois à mois) en arrière
		if (year < cal.get(Calendar.YEAR) - 3 || (year == cal.get(Calendar.YEAR) - 3 && month < cal.get(Calendar.MONTH))
				|| (year == cal.get(Calendar.YEAR) && month > cal.get(Calendar.MONTH)) || year > cal.get(Calendar.YEAR)) {
			throw new NumberFormatException("Année hors limite");
		}

		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.YEAR, year);

		return cal;
	}
}
