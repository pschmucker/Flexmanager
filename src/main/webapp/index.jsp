<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="WEB-INF/jsp/include.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Spring Application</title>
		<link type="text/css" media="all" rel="stylesheet" href="<c:url value="/css/style.css" />"/>
	</head>
	<body>
		<h1>Welcome !</h1>
		<div>
			<ul id="menu">
				<li><a href="partner.html">Partner</a></li>
				<li><a href="client.html">Client</a></li>
				<li><a href="product.html">Product</a></li>
				<li><a href="licence.html">Licence</a></li>
				<li><a href="contact.html">Contact</a></li>
				<li><a href="ticket.html">Ticket</a></li>
				<li><a href="user.html">User</a></li>
			</ul>
		</div>
		<div>
			<br/><br/><br/>
			<a href="j_spring_security_logout">Déconnexion</a>
			<br/>
			<a href="index.html?bootstrap=true">Bootstrap</a>
		</div>
	</body>
</html>