<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include.jsp"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Tickets</title>
		<script type="text/javascript" src="<c:url value ="/js/script.js" />"></script>
	</head>
	<body>
	
		<h1>Ticket list</h1>
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
		    		<th>Users</th>
		    		<th>Enabled</th>
				</tr>
			    <c:forEach items="${list}" var="ticket">
			    	<tr>
			    		<td><a href="ticket/view.html?id=${ticket.id}"><c:out value="${ticket.id}"/></a></td>
			    		<td><a href="ticket/view.html?id=${ticket.id}"><c:out value="${ticket.title}"/></a></td>
			    		<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${ticket.creationDate}"/></td>
			    		<td><c:out value="${ticket.status}"/></td>
			    		<td><c:out value="${ticket.priority}"/></td>
			    		<td><a href="client/view.html?id=${ticket.client.id}"><c:out value="${ticket.client.name}"/></a></td>
			    		<td><a href="product/view.html?id=${ticket.product.id}"><c:out value="${ticket.product.name}"/></a></td>
			    		<td><c:out value="${ticket.usersInCharge}"/></td>
			    		<td><c:out value="${ticket.enabled}"/></td>
			    		<td><a href="ticket/edit.html?id=${ticket.id}"><img border="0" src="<c:url value ="/img/Edit_16x16.png" />" /></a></td>
			    		<td><input src="<c:url value ="/img/Delete_16x16.png" />" type="image" onclick="deleteObject('Are you sure you want to delete this ticket?', '${ticket}', 'ticket/delete.html?id=${ticket.id}')" /></td>
			    	</tr>
			    </c:forEach>
			</table>
	    </div>
	    <br/>
	    <a href="ticket/add.html">Add new ticket</a>
	
	    <br/>
	    <a href="ticket/search.html">Search ticket</a>
	
	    <br/>
	    <a href="index.html">Home</a>
	    
	</body>
</html>