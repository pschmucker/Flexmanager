<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
	<head>
		<title>Spring Application</title>
		<link type="text/css" media="all" rel="stylesheet" href="<c:url value="/css/style.css" />" />
		<script type="text/javascript" src="<c:url value ="/js/script.js" />"></script>
	</head>
	<body>
		<div id="header">
			<%@ include file="menu.jsp"%>
		</div>
		<div id="content">
			<h1>10 Last tickets</h1>
			<c:set var="list" value="${last}" />
			<div>
				<%@ include file="ticket/list.jsp"%>
		    </div>

			<h1>Urgent tickets</h1>
			<c:set var="list" value="${urgent}" />
			<div>
				<%@ include file="ticket/list.jsp"%>
		    </div>

			<h1>Unassigned tickets</h1>
			<c:set var="list" value="${unassigned}" />
			<div>
				<%@ include file="ticket/list.jsp"%>
		    </div>
			<br/>
			<a href="index/bootstrap.html">Bootstrap</a>
		</div>
	</body>
</html>