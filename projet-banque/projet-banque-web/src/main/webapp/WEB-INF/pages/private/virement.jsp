<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script type="text/javascript">
	function filtrercompte(idCompte) {
		$.getJSON(
			'<c:url value="/private/ajax/comptes.htm"/>', {
				client: ${idclient},
				compte: idCompte
			}, function(data) {
				$('#compte_dest').empty();
				$.each(data, function (index, value) {
					$('#compte_dest').append('<option value="'+value.id+'">'+value.libelle+'</option>');
				}
			);
		});
	}
	
	$(document).ready(function () {
		filtrercompte($('#compte_dest').val());
	});
</script>

<div class="box">
	<div class="icon">
		 <img src="<c:url value="/images/virement.png"/>" alt="virement" title="virement" />
	</div>
	
	<h3>Effectuer un virement</h3>
	
	<p>Sélectionnez votre compte source et le compte destination et indiquez le montant à transféfer.</p>

	<form method="post" action="<c:url value="/private/virement.do"/>">
		<jsp:include page="/WEB-INF/pages/utils/messages.jsp" />
		
		<div class="row">
			<label for="compte_src">Source :</label>
			<select name="compte_src" id="compte_src" onchange="filtrercompte(this.value)">
				<c:forEach items="${comptes}" var="compte">
					<option value="${compte.id}"<c:if test="${compte.id == compte_src}"> selected="selected"</c:if>>${compte.libelle} (${compte.solde}€)</option>
				</c:forEach>
			</select>
		</div>
		<div class="row">
			<label for="compte_dest">Destination :</label>
			<select name="compte_dest" id="compte_dest">
				<c:forEach items="${comptes}" var="compte">
					<option value="${compte.id}"<c:if test="${compte.id == compte_dest}"> selected="selected"</c:if>>${compte.libelle} (${compte.solde}€)</option>
				</c:forEach>
			</select>
		</div>
		<div class="row">
			<label for="montant">Montant :</label>
			<input class="input" id="montant" name="montant" type="text" value="${montant}" />€
		</div>
		
		<div class="buttons">
			<input value="Valider" type="submit" />
		</div>
	</form>
</div>

<div class="separator"></div>

<div class="box">
	<h3>Historique de mes virements</h3>
	
	<p>Ce tableau présente la liste de vos virements.</p>
	
	<table>
		<thead>
			<tr>
				<th width="30%">Date</th>
				<th width="50%">Libelle</th>
				<th width="20%">Montant</th>
			</tr>
		</thead>
	
		<tbody>
			<c:choose>
				<c:when test="${fn:length(virements) > 0}">
					<% int i = 0; %>
					<c:forEach items="${virements}" var="operation">
						<tr class="line<%=i++ % 2%>">
							<td><fmt:formatDate value="${operation.dateOp}" type="both" /></td>
							<td>${operation.libelle}</td>
							<td align="right"<c:if test="${operation.montant < 0}"> class="decouvert"</c:if>>${operation.montant}€</td>
						</tr>
					</c:forEach>
				</c:when>
					
				<c:otherwise>
					<tr class="line0">
						<td colspan="3" class="empty">Aucun historique</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
</div>