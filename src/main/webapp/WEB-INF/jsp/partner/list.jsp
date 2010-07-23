<%@ include file="../include.jsp"%>

<table border="2" cellspacing="2" cellpadding="5">
	<tr>
		<th>Id</th>
		<th>Company</th>
		<th>Enabled</th>
	</tr>
	<c:forEach items="${list}" var="partner">
		<tr>
			<td>
				<a href="<c:url value ="/partner/view.html?id=${partner.id}" />">
					<c:out value="${partner.id}"/>
				</a>
			</td>
			<td>
				<a href="<c:url value ="/partner/view.html?id=${partner.id}" />">
					<c:out value="${partner.name}"/>
				</a>
			</td>
			<td>
				<c:out value="${partner.enabled}"/>
			</td>
			<td>
				<a href="<c:url value ="/partner/edit.html?id=${partner.id}" />">
					<img border="0" src="<c:url value ="/img/Edit_16x16.png" />" />
				</a>
			</td>
			<td>
				<c:set var="path" value="<%=request.getContextPath() %>" />
				<c:set var="action" value="${path}/partner/delete.html?id=${partner.id}" />
				<input 
					src="<c:url value ="/img/Delete_16x16.png" />" 
					type="image" 
					onclick="deleteObject('Are you sure you want to delete this partner?', '${partner}', '${action}')" 
				/>
			</td>
		</tr>
	</c:forEach>
</table>