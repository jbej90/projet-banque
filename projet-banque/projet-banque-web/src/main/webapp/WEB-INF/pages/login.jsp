<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!--<form method="post" action="<c:url value="/login.form"/>">-->
<!--	Identifiant  :<input class="input" name="username" value="" type="text" />-->
<!--	Mot de passe :<input class="input" name="password" type="password" />-->
<!--	<input value="Valider" type="submit" />-->
<!--</form>-->

<form method="post" action="<c:url value="/j_spring_security_check"/>" class="box">
	<jsp:include page="utils/messages.jsp" />
	
	<div class="row">
		<label for="username">Identifiant :</label>
		<input class="input" id="username" name="j_username" value="" type="text" />
	</div>
	<div class="row">
		<label for="password">Mot de passe :</label>
		<input class="input" id="password" name="j_password" type="password" />
	</div>
	
	<div class="buttons">
		<input value="Valider" type="submit" />
	</div>
</form>