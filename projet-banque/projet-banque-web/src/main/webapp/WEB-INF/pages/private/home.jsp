<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<p>Bienvenue, ${client.prenom} ${client.nom}</p>
					
<table class="box width-500">
	<thead>
		<tr>
			<th width="50%">Libelle</th>
			<th width="50%">Solde courant</th>
		</tr>
	</thead>
	
	<tbody>
		<% int i=0; %>
		<c:forEach items="${comptes}" var="compte">
			<tr class="line<%= i++ % 2 %>">
				<td><a href="<c:url value="/private/compte-${compte.id}.htm"/>">${compte.libelle}</a></td>
				<td align="right"<c:if test="${compte.solde < 0}"> class="decouvert"</c:if>>${compte.solde}€</td>
			</tr>
		</c:forEach>
	</tbody>
	
	<tfoot>
		<tr>
			<td></td>
			<td align="right"<c:if test="${total < 0}"> class="decouvert"</c:if>>${total}€</td>
		</tr>
	</tfoot>
</table>
