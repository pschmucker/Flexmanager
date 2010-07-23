<%@ include file="../include.jsp"%>
    
<table border="2" cellspacing="2" cellpadding="5">
	<tr>
		<th>Id</th>
		<th>Name</th>
		<th>Version</th>
		<th>Description</th>
		<th>Enabled</th>
	</tr>
	<c:forEach items="${list}" var="product">
		<tr>
			<td><a href="<c:url value ="/product/view.html?id=${product.id}" />"><c:out value="${product.id}"/></a></td>
			<td><a href="<c:url value ="/product/view.html?id=${product.id}" />"><c:out value="${product.name}"/></a></td>
			<td><c:out value="${product.version}"/></td>
			<td><c:out value="${product.description}"/></td>
			<td><c:out value="${product.enabled}"/></td>
			<td><a href="<c:url value ="/product/edit.html?id=${product.id}" />"><img border="0" src="<c:url value ="/img/Edit_16x16.png" />" /></a></td>
			<c:set var="path" value="<%=request.getContextPath() %>" />
			<c:set var="action" value="${path}/product/delete.html?id=${product.id}" />
			<td><input src="<c:url value ="/img/Delete_16x16.png" />" type="image" onclick="deleteObject('Are you sure you want to delete this product?', '${product}', '${action}')" /></td>
		</tr>
	</c:forEach>
</table>
