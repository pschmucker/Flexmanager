<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Product</title>
	</head>
	<body>

		<h1>Product - Search page</h1>
	
		<form:form modelAttribute="productQuery">
			Name : <form:input path="name"/>
			<form:radiobutton path="nameMatchMode" value="true" label="Exact"/>
			<form:radiobutton path="nameMatchMode" value="false" label="Substring"/>
			<br/>
			Version : <form:input path="version"/>
			<form:radiobutton path="versionMatchMode" value="true" label="Exact"/>
			<form:radiobutton path="versionMatchMode" value="false" label="Substring"/>
			<br/>
			<input type="submit" value="Search">
		</form:form>

		<br/>
		<a href="../product.html">Product page</a><br/>
		<a href="../index.html">Home page</a><br/>

	</body>
</html>