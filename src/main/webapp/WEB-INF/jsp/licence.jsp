<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include.jsp"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Licences</title>
		<script type="text/javascript" src="<c:url value ="/js/script.js" />"></script>
		<link type="text/css" media="all" rel="stylesheet" href="<c:url value="/css/style.css" />"/>
	</head>
	<body>
	
		<div class="header">
			<jsp:include page="menu.jsp" />
	    </div>
		<div>
			<table border="2" cellspacing="2" cellpadding="5">
				<tr>
		    		<th>Id</th>
		    		<th>Key</th>
		    		<th>Creation date</th>
		    		<th>Expiration date</th>
		    		<th>Client</th>
		    		<th>Product</th>
		    		<th>Maintenance</th>
		    		<th>Enabled</th>
				</tr>
			    <c:forEach items="${list}" var="licence">
			    	<tr>
			    		<td><a href="licence/view.html?id=${licence.id}"><c:out value="${licence.id}"/></a></td>
			    		<td><a href="licence/view.html?id=${licence.id}"><c:out value="${licence.licenceKey}"/></a></td>
			    		<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${licence.creationDate}"/></td>
			    		<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${licence.expirationDate}"/></td>
			    		<td><a href="client/view.html?id=${licence.client.id}"><c:out value="${licence.client.name}"/></a></td>
			    		<td><a href="product/view.html?id=${licence.product.id}"><c:out value="${licence.product.name}"/></a></td>
			    		<td><c:out value="${licence.maintenance}"/></td>
			    		<td><c:out value="${licence.enabled}"/></td>
			    		<td><a href="licence/edit.html?id=${licence.id}"><img border="0" src="<c:url value ="/img/Edit_16x16.png" />" /></a></td>
			    		<td><input src="<c:url value ="/img/Delete_16x16.png" />" type="image" onclick="deleteObject('Are you sure you want to delete this licence?', '${licence}', 'licence/delete.html?id=${licence.id}')" /></td>
			    	</tr>
			    </c:forEach>
			</table>
	    </div>
	    <br/>
	    <a href="licence/add.html">Add new licence</a>
	
	    <br/>
	    <a href="licence/search.html">Search licence</a>
	
	    <br/>
	    <a href="index.html">Home</a>
	    
	</body>
</html>