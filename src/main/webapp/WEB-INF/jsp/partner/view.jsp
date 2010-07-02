<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Partner</title>
	</head>
	<body>

		<h1>Partner #<c:out value="${partner.id}"/> - <c:out value="${partner.name}"/></h1>
	
		<div>
			Company : ${partner.name}<br/>
			Address : ${partner.address}<br/>
			Country : ${partner.country}<br/>
		</div>

		<br/>
		<a href="../partner.html">Partner page</a><br/>
		<a href="../index.html">Home page</a><br/>

	</body>
</html>