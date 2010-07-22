<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Product</title>
		<link type="text/css" media="all" rel="stylesheet" href="<c:url value="/css/style.css" />"/>
	</head>
	<body>

		<div class="header">
			<jsp:include page="../menu.jsp" />
	    </div>
		<h1>Product #<c:out value="${product.id}"/> - <c:out value="${product.name}"/></h1>
	
		<div>
			Name : ${product.name}<br/>
			Version : ${product.version}<br/>
			Description : ${product.description}<br/>
		</div>

		<br/>
		<a href="../product.html">Product page</a><br/>
		<a href="../index.html">Home page</a><br/>

	</body>
</html>