package com.excilys.projet.banque.web.utils;

public class WebUtils {

	/** prefix pour les requetes et retour concernant la partie privée */
	public static final String	BASE_DIR_PRIVATE		= "private/";
	/** prefix pour les requetes et retour concernant la partie ajax privée */
	public static final String	BASE_DIR_PRIVATE_AJAX	= "private/ajax/";
	/** prefix pour les requetes et retour concernant la partie admin */
	public static final String	BASE_DIR_ADMIN			= "admin/";
	/** prefix pour les requetes et retour concernant la partie publique */
	public static final String	BASE_DIR_HOME			= "";

	/** Suffix des URI à mapper pour l'affichage de page */
	public static final String	URL_SUFFIX_PAGE			= ".htm";
	/** Suffix des URI à mapper pour les pages de validation de formulaire */
	public static final String	URL_SUFFIX_FORM			= ".do";
	/** Suffix des URI à mapper pour les requetes Ajax */
	public static final String	URL_SUFFIX_AJAX			= ".ajax";
	/** Suffix des URI à mapper pour la génération de fichier xls */
	public static final String	URL_SUFFIX_XLS			= ".xls";

	public static String getFormatPageUri(String Uri) {
		return Uri + URL_SUFFIX_PAGE;
	}

	public static String getFormatFormUri(String Uri) {
		return Uri + URL_SUFFIX_FORM;
	}

	public static String getFormatAjaxUri(String Uri) {
		return Uri + URL_SUFFIX_AJAX;
	}

	public static String getFormatXlsUri(String Uri) {
		return Uri + URL_SUFFIX_XLS;
	}
}
