<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${fn:length(toolbar.tools) > 0}">
	<div id="toolbar">
		<c:forEach var="tool" items="${toolbar.tools}">
			<a href="<c:url value="${tool.url}"/>">
				<img src="<c:url value="${tool.img}"/>" alt="${tool.title}" title="${tool.title}" />
			</a>
		</c:forEach>
	</div>
</c:if>
