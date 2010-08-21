<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
	<head>
		<title>Access denied</title>
	</head>
	<body>
		<div>
			Vous n'avez pas les droits nécessaires pour accéder à cette page !
			<br/>
			<a href="<c:url value="/index.html" />">Home</a>
		</div>
	</body>
</html>