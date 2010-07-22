<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include.jsp"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Result</title>
		<link type="text/css" media="all" rel="stylesheet" href="<c:url value="/css/style.css" />"/>
	</head>
	<body>
	
		<div class="header">
			<jsp:include page="../menu.jsp" />
	    </div>
		<h1>Licences found</h1>
		${fn:length(result)} result<c:if test="${fn:length(result) > 1}">s</c:if> found :
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
				</tr>
			    <c:forEach items="${result}" var="licence">
			    	<tr>
			    		<td><a href="view.html?id=${licence.id}"><c:out value="${licence.id}"/></a></td>
			    		<td><a href="view.html?id=${licence.id}"><c:out value="${licence.licenceKey}"/></a></td>
			    		<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${licence.creationDate}"/></td>
			    		<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${licence.expirationDate}"/></td>
			    		<td><a href="../client/view.html?id=${licence.client.id}"><c:out value="${licence.client.name}"/></a></td>
			    		<td><a href="../product/view.html?id=${licence.product.id}"><c:out value="${licence.product.name}"/></a></td>
			    		<td><c:out value="${licence.maintenance}"/></td>
			    		<td><a href="edit.html?id=${licence.id}">Edit</a></td>
			    		<td><a href="delete.html?id=${licence.id}">Delete</a></td>
			    	</tr>
			    </c:forEach>
			</table>
	    </div>
	
	    <br/>
	    <a href="../index.html">Home</a>
	
	</body>
</html>