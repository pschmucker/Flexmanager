<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="flexcom.casehistory.ticket.entity.*" %>
<%@ include file="../include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Ticket history</title>
		<link type="text/css" media="all" rel="stylesheet" href="<c:url value="/css/style.css" />"/>
	</head>
	<body>

		<div class="header">
			<jsp:include page="../menu.jsp" />
	    </div>
		<h1>Ticket #<c:out value="${events[0].ticket.id}"/> - <c:out value="${events[0].ticket.title}"/></h1>
		<h2>History</h2>
		<div>
			<table border="2" cellspacing="2" cellpadding="5">
				<tr>
		    		<th>Date</th>
		    		<th>Operation</th>
		    		<th>Author</th>
				</tr>
			    <c:forEach items="${events}" var="event">
			    	<tr>
			    		<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${event.date}"/></td>
			    		<td><c:out value="${event.action}"/></td>
			    		<td><a href="../user/view.html?id=${event.author.id}"><c:out value="${event.author.name}"/></a></td>
			    	</tr>
			    </c:forEach>
			</table>
	    </div>

		<br/>
		<a href="view.html?id=${events[0].ticket.id}">Back to ticket</a><br/>
		
	</body>
</html>