<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${sessionScope['messages'].size > 0}">
	<ul id="messages">
		<c:forEach var="message" items="${sessionScope['messages'].messages}">
			<li class="${message.typeValue}">${message.message}</li>
		</c:forEach>
	</ul>
</c:if>