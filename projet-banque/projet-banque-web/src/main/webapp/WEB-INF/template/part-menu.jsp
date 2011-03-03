<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${menu != null}">
	<ul id="menu">
		<c:forEach var="item" items="${menu.items}">
			<li class="item<c:if test="${item.id == 0}"> first</c:if><c:if test="${item.selected}"> selected</c:if>">
				<a href="<c:url value="${item.url}" />" title="${item.title}">${item.title}</a>
			</li>
		</c:forEach>
	</ul>
</c:if>
