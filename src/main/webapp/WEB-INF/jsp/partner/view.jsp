<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Partner</title>
		<link type="text/css" media="all" rel="stylesheet" href="<c:url value="/css/style.css" />"/>
	</head>
	<body>

		<div class="header">
			<jsp:include page="../menu.jsp" />
	    </div>
		<h1>Partner #<c:out value="${partner.id}"/> - <c:out value="${partner.name}"/></h1>
	
		<div>
			Company : ${partner.name}<br/>
			Address : ${partner.address}<br/>
			Country : ${partner.country}<br/>
		</div>

	</body>
</html>