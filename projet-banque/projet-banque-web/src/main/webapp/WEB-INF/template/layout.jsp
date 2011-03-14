<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr" lang="fr" dir="ltr">
	<head>
		<title>Projet banque - <tiles:insertAttribute name="title" ignore="true" /></title>
		
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		
		<link rel="icon" type="image/png" href="<c:url value="/images/favicon.png"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/style/style.css"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/style/jquery-ui.css"/>" />
		
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.min.js"></script>
		<script type="text/javascript" src="<c:url value="/javascript/jquery-ui.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/javascript/jquery-ui.datepicker-fr.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/javascript/jquery-ui.monthpicker.js"/>"></script>
		
		<script type="text/javascript">
			$.datepicker.setDefaults({
				minDate: '-3Y', 
				maxDate: 0,
				showAnim: 'fold',
				showOn: 'button',
				buttonImage: '<c:url value="/images/calendar_month.png"/>',
				buttonImageOnly: true
			});
		</script>
	</head>
	
	<body>
		<div id="container">
			<div id="header">
				<div id="logo">
					<a href="<c:url value="/"/>">
						<img src="<c:url value="/images/login.gif"/>" alt="login" title="login" />
					</a>
				</div>
				<h1><tiles:insertAttribute name="title" /></h1>
				
				<div class="clearer"></div>
			</div>
			
			<div id="sub-header">
				<tiles:insertAttribute name="menu" />
				<div id="navigation">
					<tiles:insertAttribute name="sub-header" />
				</div>
				<div class="clearer"></div>
			</div>
		
			<div id="body">
				<c:choose>
					<c:when test="${fn:length(toolbar.tools) == 0}">
						<div id="content">
							<tiles:insertAttribute name="content" />
						</div>
					</c:when>
					
					<c:otherwise>
						<div id="content-with-tools">
							<tiles:insertAttribute name="content" />
							<tiles:insertAttribute name="toolbar" />
						</div>
					</c:otherwise>
				</c:choose>
				
				<div class="clearer"></div>
			</div>
			
			<div id="footer">
				<jsp:include page="part-footer.jsp" />
			</div>
		</div>
	</body>
</html>