<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<p>Compte numéro ${compte.id}</p>

<c:forEach var="operation" items="${operations}">
	${operation.libelle}
</c:forEach>
