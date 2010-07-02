<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="flexcom.casehistory.ticket.entity.*"%>
<%@ include file="../include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Contact</title>
	</head>
	<body>
		<h1>Contact #<c:out value="${contact.id}"/> - <c:out value="${contact.name}"/></h1>
	
		<div>
			Name : ${contact.name}<br/>
			Company :
			<%
				Company c = ((Contact) request.getAttribute("contact")).getCompany();
				out.write("<a href=\"../" + c.getClass().getSimpleName().toLowerCase() + "/view.html?id=" + c.getId() + "\">" + c.getName() + "</a>");
			%>
			<br/>
		</div>
		
		<br/>
		<a href="../contact.html">Contact page</a><br/>
		<a href="../index.html">Home page</a><br/>
		
	</body>
</html>