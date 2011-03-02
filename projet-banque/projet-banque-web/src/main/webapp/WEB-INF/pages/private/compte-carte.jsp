<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<h2>Opération par carte du compte n°${compte.id}: ${compte.libelle} </h2>

<div class="box width-800">
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
				<c:when test="${fn:length(operationsCarte) > 0}">
					<% int i = 0; %>
					<c:forEach items="${operationsCarte}" var="operation">
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
				<td colspan="3">Total des opérations</td>
				<td align="right" class="decouvert"><c:if test="${totalCarte<0}" >${totalCarte}€</c:if></td>
				<td align="right" ><c:if test="${totalCarte>=0}">${totalCarte}€</c:if></td>
			</tr>
		</tfoot>
	</table>
</div>