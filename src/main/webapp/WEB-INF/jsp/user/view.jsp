<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>User</title>
		<link type="text/css" media="all" rel="stylesheet" href="<c:url value="/css/style.css" />"/>
	</head>
	<body>
		<div class="header">
			<jsp:include page="../menu.jsp" />
	    </div>
		<h1>User #<c:out value="${user.id}"/> - <c:out value="${user.name}"/></h1>
	
		<div>
			Login : ${user.login}<br/>
			Password : ${user.password}<br/>
		</div>
		<div>
			Name : ${user.name}<br/>
			Email : ${user.email}<br/>
			Title : ${user.title}<br/>
			Access level : ${user.accessLevel}<br/>
			Roles : ${user.roles}<br/>
		</div>
		
		<h2>Assigned tickets</h2>
		
		<table border="2">
			<c:forEach items="${user.assignedTickets}" var="ticket">
				<tr>
					<td>
						<a href="../ticket/view.html?id=${ticket.id}"><c:out value="${ticket}"/></a><br/>
					</td>
					<td>
						<input type="button" value="Remove" onclick="window.location = 'view/remove.html?id=${user.id}&ticketId=${ticket.id}'">
					</td>
				</tr>
			</c:forEach>
		</table>
		<br/>
		
		<form:form modelAttribute="ticketIdCommand">

			<form:select path="ticketId">
				<form:options items="${tickets}" itemValue="id" itemLabel="title"/>
			</form:select>
		
			<input type="submit" value="Assign">
		</form:form>
		
		<br/>
		<a href="../user.html">User page</a><br/>
		<a href="../index.html">Home page</a><br/>
		
	</body>
</html>