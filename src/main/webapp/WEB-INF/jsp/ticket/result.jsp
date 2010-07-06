<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include.jsp"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Result</title>
	</head>
	<body>
	
		<h1>Tickets found</h1>
		${fn:length(result)} result<c:if test="${fn:length(result) > 1}">s</c:if> found :
		<div>
			<table border="2" cellspacing="2" cellpadding="5">
				<tr>
		    		<th>Id</th>
		    		<th>Title</th>
		    		<th>Creation date</th>
		    		<th>Status</th>
		    		<th>Priority</th>
		    		<th>Client</th>
		    		<th>Product</th>
		    		<th>Build</th>
		    		<th>Contact</th>
				</tr>
			    <c:forEach items="${result}" var="ticket">
			    	<tr>
			    		<td><a href="view.html?id=${ticket.id}"><c:out value="${ticket.id}"/></a></td>
			    		<td><a href="view.html?id=${ticket.id}"><c:out value="${ticket.title}"/></a></td>
			    		<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${ticket.creationDate}"/></td>
			    		<td><c:out value="${ticket.status}"/></td>
			    		<td><c:out value="${ticket.priority}"/></td>
			    		<td><a href="../client/view.html?id=${ticket.client.id}"><c:out value="${ticket.client.name}"/></a></td>
			    		<td><a href="../product/view.html?id=${ticket.product.id}"><c:out value="${ticket.product.name}"/></a></td>
			    		<td><c:out value="${ticket.build}"/></td>
			    		<td><a href="../contact/view.html?id=${ticket.contact.id}"><c:out value="${ticket.contact.name}"/></a></td>
			    		<td><a href="edit.html?id=${ticket.id}">Edit</a></td>
			    		<td><a href="delete.html?id=${ticket.id}">Delete</a></td>
			    	</tr>
			    </c:forEach>
			</table>
	    </div>
	
	    <br/>
	    <a href="../index.html">Home</a>
	
	</body>
</html>