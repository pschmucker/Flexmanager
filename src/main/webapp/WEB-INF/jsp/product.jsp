<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include.jsp"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Products</title>
		<script type="text/javascript" src="<c:url value ="/js/script.js" />"></script>
	</head>
	<body>
	
		<h1>Product list</h1>
		<div>
			<table border="2" cellspacing="2" cellpadding="5">
				<tr>
		    		<th>Id</th>
		    		<th>Name</th>
		    		<th>Version</th>
		    		<th>Description</th>
		    		<th>Enabled</th>
				</tr>
			    <c:forEach items="${list}" var="product">
			    	<tr>
			    		<td><a href="product/view.html?id=${product.id}"><c:out value="${product.id}"/></a></td>
			    		<td><a href="product/view.html?id=${product.id}"><c:out value="${product.name}"/></a></td>
			    		<td><c:out value="${product.version}"/></td>
			    		<td><c:out value="${product.description}"/></td>
			    		<td><c:out value="${product.enabled}"/></td>
			    		<td><a href="product/edit.html?id=${product.id}"><img border="0" src="<c:url value ="/img/Edit_16x16.png" />" /></a></td>
			    		<td><input src="<c:url value ="/img/Delete_16x16.png" />" type="image" onclick="deleteObject('Are you sure you want to delete this product?', '${product}', 'product/delete.html?id=${product.id}')" /></td>
			    	</tr>
			    </c:forEach>
			</table>
	    </div>
	    <br/>
	    <a href="product/add.html">Add new product</a>
	
	    <br/>
	    <a href="product/search.html">Search product</a>
	
	    <br/>
	    <a href="index.html">Home</a>
	
	</body>
</html>