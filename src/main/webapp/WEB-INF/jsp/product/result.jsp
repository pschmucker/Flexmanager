<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include.jsp"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Result</title>
	</head>
	<body>
	
		<h1>Products found</h1>
		${fn:length(result)} result<c:if test="${fn:length(result) > 1}">s</c:if> found :
		<div>
			<table border="2" cellspacing="2" cellpadding="5">
				<tr>
		    		<th>Id</th>
		    		<th>Name</th>
		    		<th>Version</th>
		    		<th>Description</th>
				</tr>
			    <c:forEach items="${result}" var="product">
			    	<tr>
			    		<td><a href="view.html?id=${product.id}"><c:out value="${product.id}"/></a></td>
			    		<td><a href="view.html?id=${product.id}"><c:out value="${product.name}"/></a></td>
			    		<td><c:out value="${product.version}"/></td>
			    		<td><c:out value="${product.description}"/></td>
			    		<td><a href="edit.html?id=${product.id}">Edit</a></td>
			    		<td><a href="delete.html?id=${product.id}">Delete</a></td>
			    	</tr>
			    </c:forEach>
			</table>
	    </div>
	
	    <br/>
	    <a href="../index.html">Home</a>
	
	</body>
</html>