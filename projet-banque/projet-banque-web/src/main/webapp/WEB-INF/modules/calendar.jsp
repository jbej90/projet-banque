<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<script type="text/javascript">
		//<![CDATA[
		document.write('<strong>${listemois[moiscourant]} ${anneeselectionnee}</strong> ');
		document.write('<input type="hidden" id="datepicker" name="datepicker" />');
		
		$(function() {
			$("#datepicker").datepicker({
				defaultDate: "01/${moiscourant+1}/${anneeselectionnee}"
			}).change(function() {
				$("#form").submit();
			});
		});
		//]]>
	</script>
	
	<noscript>
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
	</noscript>
