<%@ include file="../include.jsp"%>

<table border="2" cellspacing="2" cellpadding="5">
	<tr>
		<th>Company</th>
		<th>Address</th>
		<th>Country</th>
		<th>Creation date</th>
		<th>Last update</th>
		<th>Contacts</th>
		<th>Clients</th>
		<th>Enabled</th>
	</tr>
	<c:forEach items="${list}" var="partner">
		<tr class="entity">
			<td>
				<a href="<c:url value ="/partner/view.html?id=${partner.id}" />">
					<c:out value="${partner.name}"/>
				</a>
			</td>
			<td>
				<c:out value="${partner.address}"/>
			</td>
			<td>
				<c:out value="${partner.country}"/>
			</td>
			<td>
				<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${partner.creationDate}"/>
			</td>
			<td>
				<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${partner.lastUpdate}"/>
			</td>
			<td>
				<c:out value="${partner.contacts}"/>
			</td>
			<td>
				<c:out value="${partner.clients}"/>
			</td>
			<td>
				<c:out value="${partner.enabled}"/>
			</td>
			<td>
				<a href="<c:url value ="/partner/edit.html?id=${partner.id}" />" title="Edit">
					<img src="<c:url value ="/img/Edit_16x16.png" />" alt="edit" />
				</a>
			</td>
			<td>
				<c:set var="path" value="<%=request.getContextPath() %>" />
				<c:set var="action" value="${path}/partner/delete.html?id=${partner.id}" />
				<input 
					src="<c:url value ="/img/Delete_16x16.png" />" 
					alt="delete" 
					type="image" 
					title="Delete" 
					onclick="deleteObject('Are you sure you want to delete this partner?', '${partner}', '${action}')" 
				/>
			</td>
		</tr>
	</c:forEach>
</table>
