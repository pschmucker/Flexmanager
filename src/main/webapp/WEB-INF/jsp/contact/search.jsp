<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Contact</title>
	</head>
	<body>

		<h1>Contact - Search page</h1>
	
		<form:form modelAttribute="contactQuery">
			Name : <form:input path="contactName"/>
			<form:radiobutton path="matchMode" value="true" label="Exact"/>
			<form:radiobutton path="matchMode" value="false" label="Substring"/>
			<br/>
			<input type="submit" value="Search">
		</form:form>

		<br/>
		<a href="../contact.html">Contact page</a><br/>
		<a href="../index.html">Home page</a><br/>

	</body>
</html>