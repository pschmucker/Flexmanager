<%@ include file="../include.jsp"%>
    
<table border="2" cellspacing="2" cellpadding="5">
	<tr>
		<th>Id</th>
		<th>Login</th>
		<th>Name</th>
		<th>Enabled</th>
	</tr>
	<c:forEach items="${list}" var="user">
		<tr>
			<td><a href="<c:url value ="/user/view.html?id=${user.id}" />"><c:out value="${user.id}"/></a></td>
			<td><a href="<c:url value ="/user/view.html?id=${user.id}" />"><c:out value="${user.login}"/></a></td>
			<td><c:out value="${user.name}"/></td>
			<td><c:out value="${user.enabled}"/></td>
			<td><a href="<c:url value ="/user/edit.html?id=${user.id}" />"><img border="0" src="<c:url value ="/img/Edit_16x16.png" />" /></a></td>
			<c:set var="path" value="<%=request.getContextPath() %>" />
			<c:set var="action" value="${path}/user/delete.html?id=${user.id}" />
			<td><input src="<c:url value ="/img/Delete_16x16.png" />" type="image" onclick="deleteObject('Are you sure you want to delete this user?', '${user}', '${action}')" /></td>
		</tr>
	</c:forEach>
</table>
