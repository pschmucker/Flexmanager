<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Licence</title>
		<link type="text/css" media="all" rel="stylesheet" href="<c:url value="/css/style.css" />"/>
	</head>
	<body>

		<div class="header">
			<jsp:include page="../menu.jsp" />
	    </div>
		<h1>Licence - Search page</h1>
	
		<form:form modelAttribute="licenceQuery">
			Key : <form:input path="key"/>
			<form:radiobutton path="keyMatchMode" value="true" label="Exact"/>
			<form:radiobutton path="keyMatchMode" value="false" label="Substring"/>
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
			Maintenance :
			<form:select path="maintenanceId">
				<form:option value="select" label="-- select --"/>
				<form:options items="${maintenances}" itemValue="id" itemLabel="name"/>
			</form:select>
			<br/>
			<input type="submit" value="Search">
		</form:form>

	</body>
</html>