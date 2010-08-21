<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
	<head>
		<title>Client</title>
		<link type="text/css" media="all" rel="stylesheet" href="<c:url value="/css/style.css" />"/>
	</head>
	<body>

		<div class="header">
			<jsp:include page="../menu.jsp" />
	    </div>
		<h1>Client - Search page</h1>
	
		<form:form modelAttribute="companyQuery">
			Company : <form:input path="companyName"/><br/>
			<form:radiobutton path="matchMode" value="true" label="Exact"/>
			<form:radiobutton path="matchMode" value="false" label="Substring"/>
			<br/>
			<input type="submit" value="Search">
		</form:form>

	</body>
</html>