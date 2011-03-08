package com.excilys.projet.banque.web.ajax;

import java.util.LinkedList;
import java.util.List;

import com.excilys.projet.banque.model.Operation;

/**
 * <p>
 * POJO utilisé uniquement pour la sérialisation en JSON.
 * </p>
 * <p>
 * Cette classe est utilisée pour les méthodes AJAX et ne doit donc envoyer que les attributs nécessaires.
 * </p>
 * <p>
 * Elle contient une liste d'opération ainsi que les sous-totaux et totaux
 * </p>
 * 
 * @author excilys
 *
 */
public class AjaxOperations {

	private List<AjaxOperation>	operations;
	private float				sousTotalCarte;
	private float				sousTotal;
	private float				total;

	public AjaxOperations(List<Operation> operations, float sousTotalCarte, float sousTotal) {
		this.operations = new LinkedList<AjaxOperation>();
		for (Operation op : operations) {
			this.operations.add(new AjaxOperation(op.getDateOp(), op.getLibelle(), op.getType(), op.getMontant()));
		}

		setSousTotalCarte(sousTotalCarte);
		setSousTotal(sousTotal);
	}

	public List<AjaxOperation> getOperations() {
		return operations;
	}

	public void setOperations(List<AjaxOperation> operations) {
		this.operations = operations;
	}

	public float getSousTotalCarte() {
		return sousTotalCarte;
	}

	public void setSousTotalCarte(float sousTotalCarte) {
		this.sousTotalCarte = sousTotalCarte;
		this.total = sousTotalCarte + sousTotal;
	}

	public float getSousTotal() {
		return sousTotal;
	}

	public void setSousTotal(float sousTotal) {
		this.sousTotal = sousTotal;
		this.total = sousTotalCarte + sousTotal;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}
}
