<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include.jsp"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Contacts</title>
		<script type="text/javascript" src="<c:url value ="/js/script.js" />"></script>
	</head>
	<body>
	
		<h1>Contact list</h1>
		<div>
			<table border="2" cellspacing="2" cellpadding="5">
				<tr>
		    		<th>Id</th>
		    		<th>Name</th>
		    		<th>Enabled</th>
				</tr>
			    <c:forEach items="${list}" var="contact">
			    	<tr>
			    		<td><a href="contact/view.html?id=${contact.id}"><c:out value="${contact.id}"/></a></td>
			    		<td><a href="contact/view.html?id=${contact.id}"><c:out value="${contact.name}"/></a></td>
			    		<td><c:out value="${contact.enabled}"/></td>
			    		<td><a href="contact/edit.html?id=${contact.id}"><img border="0" src="<c:url value ="/img/Edit_16x16.png" />" /></a></td>
			    		<td><input src="<c:url value ="/img/Delete_16x16.png" />" type="image" onclick="deleteObject('Are you sure you want to delete this contact?', '${contact}', 'contact/delete.html?id=${contact.id}')" /></td>
			    	</tr>
			    </c:forEach>
			</table>
	    </div>
	    <br/>
	    <a href="contact/add.html">Add new contact</a>
	
	    <br/>
	    <a href="contact/search.html">Search contact</a>
	
	    <br/>
	    <a href="index.html">Home</a>
	
	</body>
</html>