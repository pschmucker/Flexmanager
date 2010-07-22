<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include.jsp"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Users</title>
		<script type="text/javascript" src="<c:url value ="/js/script.js" />"></script>
		<link type="text/css" media="all" rel="stylesheet" href="<c:url value="/css/style.css" />"/>
	</head>
	<body>
	
		<div class="header">
			<jsp:include page="menu.jsp" />
	    </div>
		<div>
			<table border="2" cellspacing="2" cellpadding="5">
				<tr>
		    		<th>Id</th>
		    		<th>Login</th>
		    		<th>Name</th>
		    		<th>Enabled</th>
				</tr>
			    <c:forEach items="${list}" var="user">
			    	<tr>
			    		<td><a href="user/view.html?id=${user.id}"><c:out value="${user.id}"/></a></td>
			    		<td><a href="user/view.html?id=${user.id}"><c:out value="${user.login}"/></a></td>
			    		<td><c:out value="${user.name}"/></td>
			    		<td><c:out value="${user.enabled}"/></td>
			    		<td><a href="user/edit.html?id=${user.id}"><img border="0" src="<c:url value ="/img/Edit_16x16.png" />" /></a></td>
			    		<td><input src="<c:url value ="/img/Delete_16x16.png" />" type="image" onclick="deleteObject('Are you sure you want to delete this user?', '${user}', 'user/delete.html?id=${user.id}')" /></td>
			    	</tr>
			    </c:forEach>
			</table>
	    </div>
	    <br/>
	    <a href="user/add.html">Add new user</a>
	
	    <br/>
	    <a href="user/search.html">Search user</a>
	
	    <br/>
	    <a href="index.html">Home</a>
	
	</body>
</html>