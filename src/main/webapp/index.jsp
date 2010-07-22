<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="WEB-INF/jsp/include.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Spring Application</title>
		<link type="text/css" media="all" rel="stylesheet" href="<c:url value="/css/style.css" />"/>
	</head>
	<body>
		<div id="header">
			<%@ include file="WEB-INF/jsp/menu.jsp"%>
		</div>
		<div>
			<br/><br/>
			<a href="j_spring_security_logout">Déconnexion</a>
			<br/>
			<a href="index.html?bootstrap=true">Bootstrap</a>
		</div>
	</body>
</html>