<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr" lang="fr" dir="ltr">
	<head>
		<title>Projet banque - Erreur ${code}</title>
		
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		
		<link rel="icon" type="image/png" href="<c:url value="/images/favicon.png"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/style/style.css"/>" />
	</head>
	
	<body>
		<div id="container">
			<div id="header">
				<div id="logo">
					<img src="<c:url value="/images/login.gif"/>" alt="login" title="login" />
				</div>
				<div id="title">Erreur ${code}</div>
				
				<div class="clearer"></div>
			</div>
			
			<div id="sub-header">
				<div id="home">
					<a href="<c:url value="/"/>">Retour à l'accueil</a>
				</div>
			</div>
		
			<div id="body">
				<div id="content">
					<jsp:include page="/WEB-INF/pages/utils/messages.jsp" />
				</div>
			</div>
			
			<div id="footer">
				<jsp:include page="footer.jsp" />
			</div>
		</div>
	</body>
</html>