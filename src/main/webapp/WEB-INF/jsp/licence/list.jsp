<%@ include file="../include.jsp"%>
    
<table border="2" cellspacing="2" cellpadding="5">
	<thead>
		<tr>
			<th>Key</th>
			<th>Creation date</th>
			<th>Beginning date</th>
			<th>Expiration date</th>
			<th>Last update</th>
			<th>Client</th>
			<th>Product</th>
			<th>Maintenance</th>
			<th>Enabled</th>
			<th colspan="2" />
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${list}" var="licence">
			<tr class="entity">
				<td>
					<a href="<c:url value ="/licence/view.html?id=${licence.id}" />">
						<c:out value="${licence.licenceKey}"/>
					</a>
				</td>
				<td>
					<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${licence.creationDate}"/>
				</td>
				<td>
					<fmt:formatDate pattern="dd/MM/yyyy" value="${licence.beginningDate}"/>
				</td>
				<td>
					<fmt:formatDate pattern="dd/MM/yyyy" value="${licence.expirationDate}"/>
				</td>
				<td>
					<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${licence.lastUpdate}"/>
				</td>
				<td>
					<a href="<c:url value ="/client/view.html?id=${licence.client.id}" />">
						<c:out value="${licence.client.name}"/>
					</a>
				</td>
				<td>
					<a href="<c:url value ="/product/view.html?id=${licence.product.id}" />">
						<c:out value="${licence.product.name}"/>
					</a>
				</td>
				<td>
					<c:out value="${licence.maintenance}"/>
				</td>
				<td>
					<c:out value="${licence.enabled}"/>
				</td>
				<td>
					<a href="<c:url value ="/licence/edit.html?id=${licence.id}" />" title="Edit">
						<img src="<c:url value ="/img/Edit_16x16.png" />" alt="edit" />
					</a>
				</td>
				<c:set var="path" value="<%=request.getContextPath() %>" />
				<c:set var="action" value="${path}/licence/delete.html?id=${licence.id}" />
				<td>
					<input 
						src="<c:url value ="/img/Delete_16x16.png" />" 
						alt="delete" 
						type="image" 
						title="Delete" 
						onclick="deleteObject('Are you sure you want to delete this licence?', '${licence}', '${action}')" 
					/>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
