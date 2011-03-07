<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
	$(document).ready(function () {
		$('#username').focus();
	});
</script>

<div class="box width-400">
	<form method="post" action="<c:url value="/login.do"/>">
		<jsp:include page="/WEB-INF/pages/utils/messages.jsp" />
		
		<div class="row">
			<label for="username">Identifiant :</label>
			<input class="input" id="username" name="username" value="" type="text" />
		</div>
		<div class="row">
			<label for="password">Mot de passe :</label>
			<input class="input" id="password" name="password" type="password" />
		</div>
		
		<div class="buttons">
			<input value="Valider" type="submit" />
		</div>
	</form>
</div>