<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="flexcom.casehistory.ticket.entity.*"%>
<%@ include file="../include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Contact</title>
		<link type="text/css" media="all" rel="stylesheet" href="<c:url value="/css/style.css" />"/>
	</head>
	<body>
		<div class="header">
			<jsp:include page="../menu.jsp" />
	    </div>
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
		
	</body>
</html>