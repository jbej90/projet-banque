package com.excilys.projet.banque.service.api;

import java.util.Date;
import java.util.List;

import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.model.EtatOperation;
import com.excilys.projet.banque.model.Operation;
import com.excilys.projet.banque.model.Type;

public interface OperationService {

	public final static Type[]			TYPES_NON_CARTE	= { Type.VIREMENT_EXT, Type.VIREMENT_INT, Type.DEPOT, Type.RETRAIT };
	public final static Type[]			TYPES_CARTE		= { Type.OP_CARTE_DIFF, Type.OP_CARTE_IMM };
	public final static Type[]			TYPES_VIREMENT	= { Type.VIREMENT_EXT, Type.VIREMENT_INT };
	public final static Type[]			TYPES_ESPECE	= { Type.DEPOT, Type.RETRAIT };

	public final static EtatOperation[]	ETATS_EN_COURS	= { EtatOperation.EN_COURS };
	public final static EtatOperation[]	ETATS_EFFECTUE	= { EtatOperation.EFFECTUE };
	public final static EtatOperation[]	ETATS_REFUSE	= { EtatOperation.REFUSE };

	void effectuerVirementInterne(Compte compteEmetteur, Compte compteDestinataire, float montant);

	Operation recupererOperation(int id);

	List<Operation> recupererOperationsClient(int client, Date date);

	List<Operation> recupererOperationsClient(int client, Date date, Type[] types);

	List<Operation> recupererOperationsClient(int client, Date date, Type[] types, EtatOperation[] etats);

	List<Operation> recupererOperationsClientCarte(int client, Date date, EtatOperation[] etats);

	List<Operation> recupererOperationsClientNonCarte(int client, Date date, EtatOperation[] etats);

	List<Operation> recupererOperationsCompte(int compte, Date date);

	List<Operation> recupererOperationsCompte(int compte, Date date, Type[] types);

	List<Operation> recupererOperationsCompte(int compte, Date date, Type[] types, EtatOperation[] etats);

	List<Operation> recupererOperationsCompteCarte(int compte, Date date, EtatOperation[] etats);

	List<Operation> recupererOperationsCompteNonCarte(int compte, Date date, EtatOperation[] etats);

	float totalOperations(List<Operation> operations);

}
