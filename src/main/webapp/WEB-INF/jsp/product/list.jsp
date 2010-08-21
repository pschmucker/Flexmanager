<%@ include file="../include.jsp"%>
    
<table border="2" cellspacing="2" cellpadding="5">
	<thead>
		<tr>
			<th>Name</th>
			<th>Version</th>
			<th>Description</th>
			<th>Creation date</th>
			<th>Last update</th>
			<th>Tickets</th>
			<th>Licences</th>
			<th>Enabled</th>
			<th colspan="2" />
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${list}" var="product">
			<tr class="entity">
				<td>
					<a href="<c:url value ="/product/view.html?id=${product.id}" />">
						<c:out value="${product.name}"/>
					</a>
				</td>
				<td>
					<c:out value="${product.version}"/>
				</td>
				<td>
					<c:out value="${product.description}"/>
				</td>
				<td>
					<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${product.creationDate}"/>
				</td>
				<td>
					<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${product.lastUpdate}"/>
				</td>
				<td>
					<c:out value="${product.tickets}"/>
				</td>
				<td>
					<c:out value="${product.licences}"/>
				</td>
				<td>
					<c:out value="${product.enabled}"/>
				</td>
				<td>
					<a href="<c:url value ="/product/edit.html?id=${product.id}" />" title="Edit">
						<img src="<c:url value ="/img/Edit_16x16.png" />" alt="edit" />
					</a>
				</td>
				<c:set var="path" value="<%=request.getContextPath() %>" />
				<c:set var="action" value="${path}/product/delete.html?id=${product.id}" />
				<td>
					<input 
						src="<c:url value ="/img/Delete_16x16.png" />" 
						alt="delete" 
						type="image" 
						title="Delete" 
						onclick="deleteObject('Are you sure you want to delete this product?', '${product}', '${action}')" 
					/>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
