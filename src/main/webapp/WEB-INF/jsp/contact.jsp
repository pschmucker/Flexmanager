<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include.jsp"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Contacts</title>
		<script type="text/javascript" src="<c:url value ="/js/script.js" />"></script>
		<link type="text/css" media="all" rel="stylesheet" href="<c:url value="/css/style.css" />"/>
	</head>
	<body>
	
		<div class="header">
			<jsp:include page="menu.jsp" />
	    </div>
		<div>
			<jsp:include page="contact/list.jsp" />
	    </div>
	
	</body>
</html>