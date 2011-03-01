<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<h2>Compte n°${compte.id}: ${compte.libelle} </h2>

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
			<%
				int i = 0;
			%>
			<c:forEach items="${operations}" var="operation">
				<tr class="line<%=i++ % 2%>">
					<td><fmt:formatDate value="${operation.dateOp}" type="both" /></td>
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
		</tbody>
	
		<tfoot>
			<tr>
				<td colspan="3">Total des opérations</td>
				<td align="right" class="decouvert"><c:if test="${total<0}" >${total}€</c:if></td>
				<td align="right" ><c:if test="${total>=0}">${total}€</c:if></td>
			</tr>
		</tfoot>
	</table>
</div>