<%@ include file="../include.jsp"%>
    
<table border="2" cellspacing="2" cellpadding="5">
	<tr>
		<th>Company</th>
		<th>Address</th>
		<th>Country</th>
		<th>Creation date</th>
		<th>Last update</th>
		<th>Contacts</th>
		<th>Tickets</th>
		<th>Licences</th>
		<th>Partner</th>
		<th>Enabled</th>
	</tr>
	<c:forEach items="${list}" var="client">
		<tr class="entity">
			<td>
				<a href="<c:url value ="/client/view.html?id=${client.id}" />">
					<c:out value="${client.name}"/>
				</a>
			</td>
			<td>
				<c:out value="${client.address}"/>
			</td>
			<td>
				<c:out value="${client.country}"/>
			</td>
			<td>
				<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${client.creationDate}"/>
			</td>
			<td>
				<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${client.lastUpdate}"/>
			</td>
			<td>
				<c:out value="${client.contacts}"/>
			</td>
			<td>
				<c:out value="${client.tickets}"/>
			</td>
			<td>
				<c:out value="${client.licences}"/>
			</td>
			<td>
				<c:out value="${client.partner}"/>
			</td>
			<td>
				<c:out value="${client.enabled}"/>
			</td>
			<td>
				<a href="<c:url value ="/client/edit.html?id=${client.id}" />" title="Edit">
					<img src="<c:url value ="/img/Edit_16x16.png" />" alt="edit" />
				</a>
			</td>
			<c:set var="path" value="<%=request.getContextPath() %>" />
			<c:set var="action" value="${path}/client/delete.html?id=${client.id}" />
			<td>
				<input 
					src="<c:url value ="/img/Delete_16x16.png" />" 
					alt="delete" 
					type="image" 
					title="Delete" 
					onclick="deleteObject('Are you sure you want to delete this client?', '${client}', '${action}')" 
				/>
			</td>
		</tr>
	</c:forEach>
</table>
