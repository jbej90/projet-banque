<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<h2>Compte n°${compte.id}: ${compte.libelle} (solde: <span<c:if test="${compte.solde < 0}"> class="decouvert"</c:if>>${compte.solde}</span>)</h2>

<div class="box width-800">
	<h3>Opérations du mois en cours</h3>
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
								<c:if test="${operation.montant < 0}">${operation.montant}€</c:if>
							</td>
							<td align="right">
								<c:if test="${operation.montant >= 0}">${operation.montant}€</c:if>
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
				<td align="right" class="decouvert"><c:if test="${soustotal < 0}" >${soustotal}€</c:if></td>
				<td align="right" ><c:if test="${soustotal >= 0}">${soustotal}€</c:if></td>
			</tr>
			<tr>
				<td colspan="3"><a href="<c:url value="/private/compte-carte-${compte.id}.htm"/>">Opérations par carte</a></td>
				<td align="right" class="decouvert"><c:if test="${soustotalCarte < 0}">${soustotalCarte}€</c:if></td>
				<td align="right"><c:if test="${soustotalCarte >= 0}">${soustotalCarte}€</c:if></td>
			</tr>
			<tr>
				<td colspan="3">Total des opérations</td>
				<td align="right" class="decouvert"><c:if test="${total<0}" ><fmt:formatNumber maxFractionDigits="2">${total}</fmt:formatNumber>€</c:if></td>
				<td align="right" ><c:if test="${total>=0}"><fmt:formatNumber maxFractionDigits="2">${total}</fmt:formatNumber>€</c:if></td>
				
			</tr>
		</tfoot>
	</table>
</div>