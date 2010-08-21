<%@ include file="../include.jsp"%>
    
<table border="2" cellspacing="2" cellpadding="5">
	<tr>
		<th>Login</th>
		<th>Name</th>
		<th>Email</th>
		<th>Title</th>
		<th>Access level</th>
		<th>Creation date</th>
		<th>Last update</th>
		<th>Last login</th>
		<th>Roles</th>
		<th>Tickets</th>
		<th>Enabled</th>
	</tr>
	<c:forEach items="${list}" var="user">
		<tr class="entity">
			<td>
				<a href="<c:url value ="/user/view.html?id=${user.id}" />">
					<c:out value="${user.login}"/>
				</a>
			</td>
			<td>
				<c:out value="${user.name}"/>
			</td>
			<td>
				<c:out value="${user.email}"/>
			</td>
			<td>
				<c:out value="${user.title}"/>
			</td>
			<td>
				<c:out value="${user.accessLevel}"/>
			</td>
			<td>
				<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${user.creationDate}"/>
			</td>
			<td>
				<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${user.lastUpdate}"/>
			</td>
			<td>
				<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${user.lastLogin}"/>
			</td>
			<td>
				<c:out value="${user.roles}"/>
			</td>
			<td>
				<c:out value="${user.assignedTickets}"/>
			</td>
			<td>
				<c:out value="${user.enabled}"/>
			</td>
			<td>
				<a href="<c:url value ="/user/edit.html?id=${user.id}" />" title="Edit">
					<img src="<c:url value ="/img/Edit_16x16.png" />" alt="edit" />
				</a>
			</td>
			<c:set var="path" value="<%=request.getContextPath() %>" />
			<c:set var="action" value="${path}/user/delete.html?id=${user.id}" />
			<td>
				<input 
					src="<c:url value ="/img/Delete_16x16.png" />" 
					alt="delete" 
					type="image" 
					title="Delete" 
					onclick="deleteObject('Are you sure you want to delete this user?', '${user}', '${action}')" 
				/>
			</td>
		</tr>
	</c:forEach>
</table>
