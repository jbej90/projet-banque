<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="domaine" scope="request">
	<c:out value="${param.domaine}" default="${sessionScope['messages'].defaultDomain}" />
</c:set>

<c:if test="${sessionScope['messages'].getSize(domaine) > 0}">
	<ul id="messages">
		<c:forEach var="message" items="${sessionScope['messages'].getMessages(domaine)}">
			<li class="${message.typeValue}">${message.message}</li>
		</c:forEach>
	</ul>
</c:if>