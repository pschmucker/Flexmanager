<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Edit contact</title>
		<style>
			.error {
				color: red;
				font-style: bold;
			}
		</style> 
	</head>
	<body>
		<form:form modelAttribute="contact">
		
			<form:errors path="*" cssClass="error"/>
		
			<table border="0" cellspacing="0" cellpadding="5">
				<tr>
					<td align="right" width="40%">Name :</td>
					<td width="60%">
						<form:input path="name"/>
					</td>
				</tr>
				
				<tr>
					<td align="right" width="40%">Company :</td>
					<td width="60%">
						<form:select path="company">
							<form:options items="${companies}" itemValue="id" itemLabel="name"/>
						</form:select>
					</td>
				</tr>
			</table>
			<br>
			<input type="submit" value="Update">
			<input type="button" value="Cancel" onclick="history.go(-1)">
		</form:form>
	
	</body>
</html>