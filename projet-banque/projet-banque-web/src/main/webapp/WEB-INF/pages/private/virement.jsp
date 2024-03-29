<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
	function filtrercompte(idCompte) {
		$('#compte_dest_loading').removeClass('hidden');
		
		$.getJSON(
			'<c:url value="/private/ajax/comptes.htm"/>', {
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

<div class="box width-800">
	<div class="icon">
		 <img src="<c:url value="/images/virement.png"/>" alt="virement" title="virement" />
	</div>
	
	<h3>Effectuer un virement</h3>
	
	<p>Sélectionnez votre compte source et le compte destination et indiquez le montant à transféfer.</p>
	
	<form method="post" action="<c:url value="/private/virement.do"/>">
		<jsp:include page="/WEB-INF/modules/messages.jsp" />
		
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
			<script type="text/javascript">
				//<![CDATA[
					document.write('<select name="compte_dest" id="compte_dest"></select>');
				//]]>
			</script>
			<noscript>
				<select name="compte_dest" id="compte_dest">
					<c:forEach items="${comptes}" var="compte">
						<option value="${compte.id}"<c:if test="${compte.id == compte_dest}"> selected="selected"</c:if>>${compte.libelle} (${compte.solde}€)</option>
					</c:forEach>
				</select>
			</noscript>
			
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
		
	<form id="form" action="<c:url value="/private/virement.htm"></c:url>" method="post">
		<p>Ce tableau présente la liste de vos virements du mois sélectionné.</p>
		
		<jsp:include page="/WEB-INF/modules/messages.jsp">
			<jsp:param name="domaine" value="filter" />
		</jsp:include>
		
		<table id="operations">
			<caption>
				<jsp:include page="/WEB-INF/modules/calendar.jsp" />
			</caption>
			
			<thead>
				<tr>
					<th width="30%">Date</th>
					<th width="50%">Libelle</th>
					<th width="20%">Montant</th>
				</tr>
			</thead>
		
			<tbody>
				<tr class="caption">
					<td colspan="3">Virements effectués</td>
				</tr>
				
				<c:choose>
					<c:when test="${fn:length(virements) > 0}">
						<c:forEach items="${virements}" var="operation" varStatus="status">
							<tr class="line${status.index % 2}">
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

				<tr class="caption">
					<td colspan="3">Virements en cours</td>
				</tr>
				
				<c:choose>
					<c:when test="${fn:length(virementsencours) > 0}">
						<c:forEach items="${virementsencours}" var="operation" varStatus="status">
							<tr class="line${status.index % 2}">
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
	</form>
</div>