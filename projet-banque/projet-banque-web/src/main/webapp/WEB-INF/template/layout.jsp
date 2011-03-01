<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr" lang="fr" dir="ltr">
	<head>
		<title>Projet banque - <tiles:insertAttribute name="title" ignore="true" /></title>
		
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
				<h1><tiles:insertAttribute name="title" /></h1>
				
				<div class="clearer"></div>
			</div>
			
			<div id="sub-header">
				<tiles:insertAttribute name="menu" />
				<div id="home">
					<tiles:insertAttribute name="sub-header" />
				</div>
				<div class="clearer"></div>
			</div>
		
			<div id="body">
				<div id="content">
					<tiles:insertAttribute name="content" />
				</div>
			</div>
			
			<div id="footer">
				<jsp:include page="part-footer.jsp" />
			</div>
		</div>
	</body>
</html>