<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>New licence</title>
		<style>
			.error {
				color: red;
				font-style: bold;
			}
		</style> 
	</head>
	<body>

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
					<td align="right" width="40%">Date d'expiration :</td>
					<td width="60%">
						<form:input path="expirationDate"/>
					</td>
				</tr>
				
				<tr>
					<td align="right" width="40%">Client :</td>
					<td width="60%">
						<form:select path="client">
							<form:options items="${clients}" itemValue="id" itemLabel="name"/>
						</form:select>
					</td>
				</tr>
				
				<tr>
					<td align="right" width="40%">Produit :</td>
					<td width="60%">
						<form:select path="product">
							<form:options items="${products}" itemValue="id" itemLabel="name"/>
						</form:select>
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
			<input type="submit" value="Create">
			<input type="button" value="Cancel" onclick="history.go(-1)">
		</form:form>
	
	</body>
</html>