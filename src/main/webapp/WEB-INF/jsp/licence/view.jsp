<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Licence detail</title>
		<link type="text/css" media="all" rel="stylesheet" href="<c:url value="/css/style.css" />"/>
	</head>
	<body>

		<div class="header">
			<jsp:include page="../menu.jsp" />
	    </div>
		<h1>Licence #<c:out value="${licence.id}"/> - <c:out value="${licence.licenceKey}"/></h1>
	
		<div>
			From <fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${licence.beginningDate}"/><br/>
			To <fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${licence.expirationDate}"/><br/>
			Maintenance : ${licence.maintenance}<br/>
		</div>
		<h2>Client</h2>
		<div>
			Company : <a href="../client/view.html?id=${licence.client.id}">${licence.client.name}</a><br/>
			Address : ${licence.client.address}<br/>
		</div>
		<h2>Product</h2>
		<div>
			Product : <a href="../product/view.html?id=${licence.product.id}">${licence.product.name} ${licence.product.version}</a><br/>
		</div>
		
	</body>
</html>