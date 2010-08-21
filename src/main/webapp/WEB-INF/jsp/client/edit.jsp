<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
	<head>
		<title>Edit client</title>
		<style>
			.error {
				color: red;
				font-style: bold;
			}
		</style> 
		<link type="text/css" media="all" rel="stylesheet" href="<c:url value="/css/style.css" />"/>
	</head>
	<body>

		<div class="header">
			<jsp:include page="../menu.jsp" />
	    </div>
		<form:form modelAttribute="client">
		
			<form:errors path="*" cssClass="error"/>
		
			<table border="0" cellspacing="0" cellpadding="5">
				<tr>
					<td align="right" width="40%">Name :</td>
					<td width="60%">
						<form:input path="name"/>
					</td>
				</tr>
				
				<tr>
					<td align="right" width="40%">Address :</td>
					<td width="60%">
						<form:textarea path="address"/>
					</td>
				</tr>
				
				<tr>
					<td align="right" width="40%">Country :</td>
					<td width="60%">
						<form:input path="country"/>
					</td>

				</tr>
			</table>
			<br>
			<input type="submit" value="Update">
		</form:form>
	
	</body>
</html>