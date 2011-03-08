<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script type="text/javascript">
	function filtrercompte(idCompte) {
		$('#compte_dest_loading').removeClass('hidden');
		
		$.getJSON(
			'<c:url value="/private/ajax/${idclient}/comptes.htm"/>', {
				exclude: idCompte
			}, function(data) {
				$('#compte_dest_loading').addClass('hidden');
				$('#compte_dest').empty();
				
				if (data.length > 0) {
					$.each(data, function (index, value) {
						var selected = (${compte_dest != null ? compte_dest : 0} == value.id) ? ' selected="selected"': ''; 
						$('#compte_dest').append('<option value="'+value.id+'"'+selected+'>'+value.libelle+' ('+value.solde+'€)'+'</option>');
					});
				} else {
					$('#compte_dest').append('<option value="">Aucun autre compte trouvé</option>');
					$('#compte_dest').attr('disabled', 'disabled');
					$('#submitVirement').attr('disabled', 'disabled');
				}
			}
		);
	}
	
	$(document).ready(function () {
		$('#compte_src').val(${compte_src});
		$('#compte_src').change();
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
			<select name="compte_dest" id="compte_dest"></select>
			
			<img id="compte_dest_loading" src="<c:url value="/images/loading.gif"/>" alt="Chargement" title="Chargement" class="hidden" />
		</div>
		<div class="row">
			<label for="montant">Montant :</label>
			<input class="input" id="montant" name="montant" type="text" value="${montant}" />€
		</div>
		
		<div class="buttons">
			<input value="Valider" type="submit" id="submitVirement" />
		</div>
	</form>
</div>

<div class="separator"></div>

<div class="box">
	<h3>Historique de mes virements</h3>
		
	<form action="<c:url value="/private/virement.htm"></c:url>" method="post" class="filter">
		<select name="filter_month" id="filter_month">
			<c:forEach items="${listemois}" var="mois" varStatus="status">
				<c:if test="${mois != ''}">
					<option value="${status.index}"<c:if test="${moiscourant == status.index}"> selected="selected"</c:if>>${mois}</option>
				</c:if>
			</c:forEach>
		</select>
		
		<select name="filter_year" id="filter_year">
			<c:forEach begin="${anneecourante-3}" end="${anneecourante}" var="annee">
				<option value="${annee}"<c:if test="${anneeselectionnee == annee}"> selected="selected"</c:if>>${annee}</option>
			</c:forEach>
		</select>
		
		<input type="submit" value="Afficher" />
	</form>
	
	<p>Ce tableau présente la liste de vos virements de ${listemois[moiscourant]} ${anneeselectionnee}.</p>
	
	<table>
		<caption>Virements effectués</caption>
		
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
						<td colspan="3" class="empty">Aucun virement</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	
	<table>
		<caption>Virements en cours</caption>
		
		<thead>
			<tr>
				<th width="30%">Date</th>
				<th width="50%">Libelle</th>
				<th width="20%">Montant</th>
			</tr>
		</thead>
	
		<tbody>
			<c:choose>
				<c:when test="${fn:length(virementsencours) > 0}">
					<% int i = 0; %>
					<c:forEach items="${virementsencours}" var="operation">
						<tr class="line<%=i++ % 2%>">
							<td><fmt:formatDate value="${operation.dateOp}" type="both" /></td>
							<td>${operation.libelle}</td>
							<td align="right"<c:if test="${operation.montant < 0}"> class="decouvert"</c:if>>${operation.montant}€</td>
						</tr>
					</c:forEach>
				</c:when>
					
				<c:otherwise>
					<tr class="line0">
						<td colspan="3" class="empty">Aucun virement</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
</div>