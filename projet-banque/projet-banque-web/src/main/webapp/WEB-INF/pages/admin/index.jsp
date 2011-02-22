<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
		<title>Projet banque</title>
		
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		 
		<link rel="icon" type="image/png" href="<c:url value="/images/favicon.png"/>" />
		<link rel=stylesheet type="text/css" href="<c:url value="/style/style.css"/>" />
    </head>
    
    <body>
		<div id="container">
			<div id="header">
				<div id="logo">
					<img src="<c:url value="/images/login.gif"/>" alt="login" title="login" />
				</div>
				<div id="title">Administration</div>
				
				<div class="clearer"></div>
			</div>
			
			<div id="sub-header">
				<a href="<c:url value="/j_spring_security_logout"/>">Déconnexion</a>
			</div>
		
			<div id="body">
				<div id="content">
					<p>Options d'administration</p>
				</div>
			</div>
			
			<div id="footer">
				Projet banque version 0.0.1-SNAPSHOT
			</div>
		</div>
    </body>
</html>
