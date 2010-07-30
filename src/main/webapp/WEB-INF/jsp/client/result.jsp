<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include.jsp"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Result</title>
		<link type="text/css" media="all" rel="stylesheet" href="<c:url value="/css/style.css" />"/>
		<script type="text/javascript" src="<c:url value ="/js/script.js" />"></script>
	</head>
	<body>
	
		<div class="header">
			<jsp:include page="../menu.jsp" />
	    </div>
		<h1>Clients found</h1>
		${fn:length(result)} result<c:if test="${fn:length(result) > 1}">s</c:if> found :
		<c:set var="list" value="${result}" />
		<div>
			<%@ include file="list.jsp"%>
	    </div>
	
	</body>
</html>