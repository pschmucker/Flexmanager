<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include.jsp"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Result</title>
	</head>
	<body>
	
		<h1>Partners found</h1>
		${fn:length(result)} result<c:if test="${fn:length(result) > 1}">s</c:if> found :
		<div>
			<table border="2" cellspacing="2" cellpadding="5">
				<tr>
		    		<th>Id</th>
		    		<th>Company</th>
				</tr>
			    <c:forEach items="${result}" var="partner">
			    	<tr>
			    		<td><a href="view.html?id=${partner.id}"><c:out value="${partner.id}"/></a></td>
			    		<td><a href="view.html?id=${partner.id}"><c:out value="${partner.name}"/></a></td>
			    		<td><a href="edit.html?id=${partner.id}">Edit</a></td>
			    		<td><a href="delete.html?id=${partner.id}">Delete</a></td>
			    	</tr>
			    </c:forEach>
			</table>
	    </div>
	
	    <br/>
	    <a href="../index.html">Home</a>
	
	</body>
</html>