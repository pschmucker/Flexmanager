<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Client</title>
		<link type="text/css" media="all" rel="stylesheet" href="<c:url value="/css/style.css" />"/>
	</head>
	<body>

		<div class="header">
			<jsp:include page="../menu.jsp" />
	    </div>
		<h1>Client #<c:out value="${client.id}"/> - <c:out value="${client.name}"/></h1>
	
		<div>
			Company : ${client.name}<br/>
			Address : ${client.address}<br/>
			Country : ${client.country}<br/>
		</div>

	</body>
</html>