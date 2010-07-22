<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include.jsp"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Result</title>
		<link type="text/css" media="all" rel="stylesheet" href="<c:url value="/css/style.css" />"/>
	</head>
	<body>
	
		<div class="header">
			<jsp:include page="../menu.jsp" />
	    </div>
		<h1>Users found</h1>
		${fn:length(result)} result<c:if test="${fn:length(result) > 1}">s</c:if> found :
		<div>
			<table border="2" cellspacing="2" cellpadding="5">
				<tr>
		    		<th>Id</th>
		    		<th>Login</th>
		    		<th>Name</th>
				</tr>
			    <c:forEach items="${result}" var="user">
			    	<tr>
			    		<td><a href="view.html?id=${user.id}"><c:out value="${user.id}"/></a></td>
			    		<td><a href="view.html?id=${user.id}"><c:out value="${user.login}"/></a></td>
			    		<td><c:out value="${user.name}"/></td>
			    		<td><a href="edit.html?id=${user.id}">Edit</a></td>
			    		<td><a href="delete.html?id=${user.id}">Delete</a></td>
			    	</tr>
			    </c:forEach>
			</table>
	    </div>
	
	    <br/>
	    <a href="../index.html">Home</a>
	
	</body>
</html>