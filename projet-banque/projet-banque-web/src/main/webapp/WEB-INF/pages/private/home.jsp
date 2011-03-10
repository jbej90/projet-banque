<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		
<div class="box width-800">
	<div class="icon">
		 <img src="<c:url value="/images/home.png"/>" alt="home" title="home" />
	</div>
	
	<h3>Bienvenue ${client.prenom} ${client.nom}</h3>
	
	<p>Vous trouverez ci-dessous la liste de vos comptes et leur solde courant.</p>
	<p>Cliquez sur le libellé d'un compte pour voir le détail de ses opérations.</p>
	
	<table>
		<thead>
			<tr>
				<th width="50%">Libelle</th>
				<th width="25%">Solde courant</th>
				<th width="25%">Solde prévisionnel</th>
			</tr>
		</thead>
		
		<tbody>
			<% int i=0; %>
			<c:forEach items="${comptes}" var="compte">
				<tr class="line<%= i++ % 2 %>">
					<td><a href="<c:url value="/private/compte/${compte.id}.htm"/>">${compte.libelle}</a></td>
					<td align="right"<c:if test="${compte.solde < 0}"> class="decouvert"</c:if>><fmt:formatNumber maxFractionDigits="2">${compte.solde}</fmt:formatNumber>€</td>
					<td align="right"<c:if test="${compte.soldePrevisionnel < 0}"> class="decouvert"</c:if>><fmt:formatNumber maxFractionDigits="2">${compte.soldePrevisionnel}</fmt:formatNumber>€</td>
				</tr>
			</c:forEach>
		</tbody>
		
		<tfoot>
			<tr>
				<td></td>
				<td align="right" class="total<c:if test="${total < 0}"> decouvert</c:if>"><fmt:formatNumber maxFractionDigits="2">${total}</fmt:formatNumber>€</td>
				<td></td>
			</tr>
		</tfoot>
	</table>
</div>