<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="box width-800">
	<div class="icon">
		 <img src="<c:url value="/images/compte.png"/>" alt="compte" title="compte" />
	</div>
	
	<h3>Opérations par carte du compte "<a href="<c:url value="/private/compte/${compte.id}.htm"/>">${compte.libelle}</a>"</h3>
	
	<jsp:include page="/WEB-INF/pages/utils/messages.jsp" />
	
	<form id="form" action="<c:url value="/private/compte/${compte.id}/operations/carte.htm"></c:url>" method="post" class="filter">
		<jsp:include page="/WEB-INF/template/module-calendar.jsp" />
		
		<p>Ci-dessous, la liste des opérations par carte de ce compte pour le mois de ${listemois[moiscourant]} ${anneeselectionnee}.</p>
		
		<table>
			<thead>
				<tr>
					<th width="30%">Date<input type="hidden" id="datepicker" name="datepicker" /></th>
					<th width="30%">Libelle</th>
					<th width="20%">Type</th>
					<th width="10%">Débit</th>
					<th width="10%">Crédit</th>
				</tr>
			</thead>
		
			<tbody>
				<c:choose>
					<c:when test="${fn:length(operationscarte) > 0}">
						<% int i = 0; %>
						<c:forEach items="${operationscarte}" var="operation">
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
					<td align="right" class="total decouvert"><c:if test="${totalcarte<0}" ><fmt:formatNumber maxFractionDigits="2">${totalcarte}</fmt:formatNumber>€</c:if></td>
					<td align="right" class="total"><c:if test="${totalcarte>=0}"><fmt:formatNumber maxFractionDigits="2">${totalcarte}</fmt:formatNumber>€</c:if></td>
				</tr>
			</tfoot>
		</table>
	</form>
</div>