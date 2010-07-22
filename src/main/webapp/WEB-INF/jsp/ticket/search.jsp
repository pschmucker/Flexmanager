<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Ticket</title>
		<link type="text/css" media="all" rel="stylesheet" href="<c:url value="/css/style.css" />"/>
	</head>
	<body>

		<div class="header">
			<jsp:include page="../menu.jsp" />
	    </div>
		<h1>Ticket - Search page</h1>
	
		<form:form modelAttribute="ticketQuery">
			Title : <form:input path="title"/>
			<form:radiobutton path="titleMatchMode" value="true" label="Exact"/>
			<form:radiobutton path="titleMatchMode" value="false" label="Substring"/>
			<br/>
			Status : 
			<form:select path="statusId">
				<form:option value="select" label="-- select --"/>
				<form:options items="${statuses}" itemValue="id" itemLabel="name"/>
			</form:select>
			<br/>
			Priority : 
			<form:select path="priorityId">
				<form:option value="select" label="-- select --"/>
				<form:options items="${priorities}" itemValue="id" itemLabel="name"/>
			</form:select>
			<br/>
			Client :
			<form:select path="clientId">
				<form:option value="select" label="-- select --"/>
				<form:options items="${clients}" itemValue="id" itemLabel="name"/>
			</form:select>
			<br/>
			Product :
			<form:select path="productId">
				<form:option value="select" label="-- select --"/>
				<form:options items="${products}" itemValue="id" itemLabel="name"/>
			</form:select>
			<br/>
			Contact :
			<form:select path="contactId">
				<form:option value="select" label="-- select --"/>
				<form:options items="${contacts}" itemValue="id" itemLabel="name"/>
			</form:select>
			<br/>
			<input type="submit" value="Search">
		</form:form>

		<br/>
		<a href="../ticket.html">Ticket page</a><br/>
		<a href="../index.html">Home page</a><br/>

	</body>
</html>