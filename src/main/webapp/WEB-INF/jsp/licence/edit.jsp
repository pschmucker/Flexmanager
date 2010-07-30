<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Edit licence</title>
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
		<form:form modelAttribute="licence">
		
			<form:errors path="*" cssClass="error"/>
		
			<table border="0" cellspacing="0" cellpadding="5">
				<tr>
					<td align="right" width="40%">Key :</td>
					<td width="60%">
						<form:input path="licenceKey"/>
					</td>
				</tr>
				
				<tr>
					<td align="right" width="40%">Expiration date :</td>
					<td width="60%">
						<form:input path="expirationDate"/>
					</td>
				</tr>
				
				<tr>
					<td align="right" width="40%">Maintenance :</td>
					<td width="60%">
						<form:select path="maintenance">
							<form:options items="${maintenances}" itemValue="id" itemLabel="name"/>
						</form:select>
					</td>
				</tr>
			</table>
			<br>
			
			<form:hidden path="creationDate"/>
			<form:hidden path="client"/>
			<form:hidden path="product"/>
			
			<input type="submit" value="Update">
		</form:form>
	
	</body>
</html>