<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
	<head>
		<title>User</title>
		<link type="text/css" media="all" rel="stylesheet" href="<c:url value="/css/style.css" />"/>
	</head>
	<body>

		<div class="header">
			<jsp:include page="../menu.jsp" />
	    </div>
		<h1>User - Search page</h1>
	
		<form:form modelAttribute="userQuery">
			Login : <form:input path="login"/>
			<form:radiobutton path="loginMatchMode" value="true" label="Exact"/>
			<form:radiobutton path="loginMatchMode" value="false" label="Substring"/>
			<br/>
			Name : <form:input path="name"/>
			<form:radiobutton path="nameMatchMode" value="true" label="Exact"/>
			<form:radiobutton path="nameMatchMode" value="false" label="Substring"/>
			<br/>
			Email : <form:input path="email"/>
			<form:radiobutton path="emailMatchMode" value="true" label="Exact"/>
			<form:radiobutton path="emailMatchMode" value="false" label="Substring"/>
			<br/>
			Title : <form:input path="title"/>
			<form:radiobutton path="titleMatchMode" value="true" label="Exact"/>
			<form:radiobutton path="titleMatchMode" value="false" label="Substring"/>
			<br/>
			Access level : 
			<form:select path="accessLevel">
				<form:option value="select" label="-- select --"/>
				<c:forEach begin="0" end="10" var="i">
					<form:option value="${i}"/>
				</c:forEach>
			</form:select>
			<br/>
			<form:radiobutton path="accessLevelCompOp" value="0" label="&lt;"/>
			<form:radiobutton path="accessLevelCompOp" value="1" label="&le;"/>
			<form:radiobutton path="accessLevelCompOp" value="2" label="="/>
			<form:radiobutton path="accessLevelCompOp" value="3" label="&ge;"/>
			<form:radiobutton path="accessLevelCompOp" value="4" label="&gt;"/>
			<br/>
			<input type="submit" value="Search">
		</form:form>

	</body>
</html>