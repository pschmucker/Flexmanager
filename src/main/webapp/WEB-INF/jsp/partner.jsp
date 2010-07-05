<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include.jsp"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Partners</title>
		<script type="text/javascript" src="<c:url value ="/js/script.js" />"></script>
	</head>
	<body>
	
		<h1>Partner list</h1>
		<div>
			<table border="2" cellspacing="2" cellpadding="5">
				<tr>
		    		<th>Id</th>
		    		<th>Company</th>
		    		<th>Enabled</th>
				</tr>
			    <c:forEach items="${list}" var="partner">
			    	<tr>
			    		<td><a href="partner/view.html?id=${partner.id}"><c:out value="${partner.id}"/></a></td>
			    		<td><a href="partner/view.html?id=${partner.id}"><c:out value="${partner.name}"/></a></td>
			    		<td><c:out value="${partner.enabled}"/></td>
			    		<td><a href="partner/edit.html?id=${partner.id}" ><img border="0" src="<c:url value ="/img/Edit_16x16.png" />" /></a></td>
			    		<td><input src="<c:url value ="/img/Delete_16x16.png" />" type="image" onclick="deleteObject('Are you sure you want to delete this partner?', '${partner}', 'partner/delete.html?id=${partner.id}')" /></td>
			    	</tr>
			    </c:forEach>
			</table>
	    </div>
	    <br/>
	    <a href="partner/add.html">Add new partner</a>
	
	    <br/>
	    <a href="partner/search.html">Search partner</a>
	
	    <br/>
	    <a href="index.html">Home</a>
	
	</body>
</html>