<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="box width-800">
	<div class="icon">
		 <img src="<c:url value="/images/compte.png"/>" alt="compte" title="compte" />
	</div>
	
	<h3>Opérations du compte "${compte.libelle}" (solde courant : <span<c:if test="${compte.solde < 0}"> class="decouvert"</c:if>>${compte.solde}€</span>)</h3>
	
	<jsp:include page="/WEB-INF/pages/utils/messages.jsp" />
	
	<form action="<c:url value="/private/compte/${compte.id}.htm"></c:url>" method="post" class="filter">
		<select name="filter_month" id="filter_month">
			<c:forEach items="${listemois}" var="mois" varStatus="status">
				<c:if test="${mois != ''}">
					<option value="${status.index}"<c:if test="${moiscourant == status.index}"> selected="selected"</c:if>>${mois}</option>
				</c:if>
			</c:forEach>
		</select>
		
		<select name="filter_year" id="filter_year">
			<c:forEach begin="${anneecourante-2}" end="${anneecourante}" var="annee">
				<option value="${annee}"<c:if test="${anneeselectionnee == annee}"> selected="selected"</c:if>>${annee}</option>
			</c:forEach>
		</select>
		
		<input type="submit" value="Afficher" />
	</form>
	
	<p>Ci-dessous, la liste des opérations de ce compte pour le mois de ${listemois[moiscourant]} ${anneeselectionnee}.</p>
	<p>Les opérations par carte sont regroupées en une seule ligne dans le sous-total.</p>
	
	<table>
		<thead>
			<tr>
				<th width="30%">Date</th>
				<th width="30%">Libelle</th>
				<th width="20%">Type</th>
				<th width="10%">Débit</th>
				<th width="10%">Crédit</th>
			</tr>
		</thead>
	
		<tbody>
			<c:choose>
				<c:when test="${fn:length(operations) > 0}">
					<% int i = 0; %>
					<c:forEach items="${operations}" var="operation">
						<tr class="line<%=i++ % 2%>">
							<td><fmt:formatDate value="${operation.dateOp}" type="date" /></td>
							<td>${operation.libelle}</td>
							<td>${operation.type}</td>
							<td align="right" class="decouvert">
								<c:if test="${operation.montant < 0}"><fmt:formatNumber maxFractionDigits="2">${operation.montant}</fmt:formatNumber>€</c:if>
							</td>
							<td align="right">
								<c:if test="${operation.montant >= 0}"><fmt:formatNumber maxFractionDigits="2">${operation.montant}</fmt:formatNumber>€</c:if>
							</td>
						</tr>
					</c:forEach>
				</c:when>
				
				<c:otherwise>
					<tr class="line0">
						<td colspan="5" class="empty">Aucune opération</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
		
		<tfoot>
			<tr>
				<td colspan="3">Opérations</td>
				<td align="right" class="decouvert"><c:if test="${soustotal < 0}" ><fmt:formatNumber maxFractionDigits="2">${soustotal}</fmt:formatNumber>€</c:if></td>
				<td align="right" ><c:if test="${soustotal >= 0}"><fmt:formatNumber maxFractionDigits="2">${soustotal}</fmt:formatNumber>€</c:if></td>
			</tr>
			<tr>
				<td colspan="3"><a href="<c:url value="/private/compte/${compte.id}/operations/carte.htm"/>">Opérations par carte</a></td>
				<td align="right" class="decouvert"><c:if test="${soustotalCarte < 0}"><fmt:formatNumber maxFractionDigits="2">${soustotalCarte}</fmt:formatNumber>€</c:if></td>
				<td align="right"><c:if test="${soustotalCarte >= 0}"><fmt:formatNumber maxFractionDigits="2">${soustotalCarte}</fmt:formatNumber>€</c:if></td>
			</tr>
			<tr>
				<td colspan="3">Total des opérations de ${listemois[moiscourant]} ${anneeselectionnee}</td>
				<td align="right" class="total decouvert"><c:if test="${total<0}" ><fmt:formatNumber maxFractionDigits="2">${total}</fmt:formatNumber>€</c:if></td>
				<td align="right" class="total"><c:if test="${total>=0}"><fmt:formatNumber maxFractionDigits="2">${total}</fmt:formatNumber>€</c:if></td>
			</tr>
		</tfoot>
	</table>
</div>
