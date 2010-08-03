<%@ include file="../include.jsp"%>
    
<table border="2" cellspacing="2" cellpadding="5">
	<tr>
		<th>Name</th>
		<th>Company</th>
		<th>Tickets</th>
		<th>Enabled</th>
	</tr>
	<c:forEach items="${list}" var="contact">
		<tr class="entity">
			<td>
				<a href="<c:url value ="/contact/view.html?id=${contact.id}" />">
					<c:out value="${contact.name}"/>
				</a>
			</td>
			<td>
				<c:out value="${contact.company}"/>
			</td>
			<td>
				<c:out value="${contact.tickets}"/>
			</td>
			<td>
				<c:out value="${contact.enabled}"/>
			</td>
			<td>
				<a href="<c:url value ="/contact/edit.html?id=${contact.id}" />" title="Edit">
					<img border="0" src="<c:url value ="/img/Edit_16x16.png" />" alt="edit" />
				</a>
			</td>
			<c:set var="path" value="<%=request.getContextPath() %>" />
			<c:set var="action" value="${path}/contact/delete.html?id=${contact.id}" />
			<td>
				<input 
					src="<c:url value ="/img/Delete_16x16.png" />" 
					alt="delete" 
					type="image" 
					title="Delete" 
					onclick="deleteObject('Are you sure you want to delete this contact?', '${contact}', '${action}')" 
				/>
			</td>
		</tr>
	</c:forEach>
</table>
