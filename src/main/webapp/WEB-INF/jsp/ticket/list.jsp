<%@ include file="../include.jsp"%>
    
<table border="2" cellspacing="2" cellpadding="5">
	<tr>
		<th>N°</th>
		<th>Title</th>
		<th>Creation date</th>
		<th>Last update</th>
		<th>Status</th>
		<th>Priority</th>
		<th>Description</th>
		<th>Client</th>
		<th>Product</th>
		<th>Build</th>
		<th>Contact</th>
		<th>Responsables</th>
		<th>Enabled</th>
	</tr>
	<c:forEach items="${list}" var="ticket">
		<tr>
			<td>
				<a href="<c:url value="/ticket/view.html?id=${ticket.id}" />">
					<c:out value="${ticket.id}"/>
				</a>
			</td>
			<td>
				<a href="<c:url value="/ticket/view.html?id=${ticket.id}" />">
					<c:out value="${ticket.title}"/>
				</a>
			</td>
			<td>
				<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${ticket.creationDate}"/>
			</td>
			<td>
				<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${ticket.lastUpdate}"/>
			</td>
			<td>
				<c:out value="${ticket.status}"/>
			</td>
			<td>
				<c:out value="${ticket.priority}"/>
			</td>
			<td>
				<c:out value="${ticket.description}"/>
			</td>
			<td>
				<a href="<c:url value="/client/view.html?id=${ticket.client.id}" />">
					<c:out value="${ticket.client.name}"/>
				</a>
			</td>
			<td>
				<a href="<c:url value="/product/view.html?id=${ticket.product.id}" />">
					<c:out value="${ticket.product.name}"/>
				</a>
			</td>
			<td>
				<c:out value="${ticket.build}"/>
			</td>
			<td>
				<c:out value="${ticket.contact}"/>
			</td>
			<td>
				<c:out value="${ticket.usersInCharge}"/>
			</td>
			<td>
				<c:out value="${ticket.enabled}"/>
			</td>
			<td>
				<a href="<c:url value="/ticket/edit.html?id=${ticket.id}" />">
					<img border="0" src="<c:url value ="/img/Edit_16x16.png" />" />
				</a>
			</td>
			<c:set var="path" value="<%=request.getContextPath() %>" />
			<c:set var="action" value="${path}/ticket/delete.html?id=${ticket.id}" />
			<td>
				<input 
					src="<c:url 
					value ="/img/Delete_16x16.png" />" 
					type="image" 
					onclick="deleteObject('Are you sure you want to delete this ticket?', '${ticket}', '${action}')" 
				/>
			</td>
		</tr>
	</c:forEach>
</table>
