<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
	</head>
	
	<body>
		${test}
		<a href="${pageContext.request.contextPath}/test.html?test=plop">Cliquez pour un test</a>
		
		<form method="post" action="${pageContext.request.contextPath}/j_spring_security_check">
			Identifiant  :<input name="j_username" value="" type="text" value="admin" />
			Mot de passe :<input name="j_password" type="password" value="admin" />
			<input value="Valider" type="submit" />
		</form>
	</body>
</html>