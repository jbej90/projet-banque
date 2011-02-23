<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr" lang="fr" dir="ltr">
    <head>
		<title>Projet banque</title>
		
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
				<div id="title">Résumé de mes comptes</div>
				
				<div class="clearer"></div>
			</div>
			
			<div id="sub-header">
				<a href="<c:url value="/j_spring_security_logout"/>">Déconnexion</a>
			</div>
		
			<div id="body">
				<div id="content">
					<p>Bienvenue, ${client.prenom} ${client.nom}</p>
					
					<table class="box">
						<thead>
							<tr>
								<th>Libelle</th>
								<th>Solde courant</th>
							</tr>
						</thead>
						
						<tbody>
							<% int i=0; %>
							<c:forEach items="${comptes}" var="compte">
								<tr class="line<%= i++ % 2 %>">
									<td><a href="<c:url value="/private/compte-${compte.id}.htm"/>">${compte.libelle}</a></td>
									<td align="right"<c:if test="${compte.solde < 0}"> class="decouvert"</c:if>>${compte.solde}€</td>
								</tr>
							</c:forEach>
						</tbody>
						
						<tfoot>
							<tr>
								<td></td>
								<td align="right"<c:if test="${client.totalSoldeComptes < 0}"> class="decouvert"</c:if>>${client.totalSoldeComptes}€</td>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
			
			<div id="footer">
				Projet banque version 0.0.1-SNAPSHOT
				<div id="w3c">
					<a href="http://validator.w3.org/check?uri=referer">
						<img src="http://www.w3.org/Icons/valid-xhtml10" alt="Valid XHTML 1.0 Transitional" height="31" width="88" />
					</a>
				</div>
			</div>
		</div>
    </body>
</html>
