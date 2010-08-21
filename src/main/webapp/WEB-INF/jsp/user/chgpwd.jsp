<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
	<head>
		<title>Change password</title>
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
	    
		<form:form modelAttribute="chgPwdCommand">
		
			<form:errors path="*" cssClass="error"/>
		
			<table border="0" cellspacing="0" cellpadding="5">
				<tr>
					<td align="right" width="40%">Old password :</td>
					<td width="60%">
						<form:password path="oldPassword" />
					</td>
				</tr>
				
				<tr>
					<td align="right" width="40%">New password :</td>
					<td width="60%">
						<form:password path="newPassword" />
					</td>
				</tr>
				
				<tr>
					<td align="right" width="40%">Confirm password :</td>
					<td width="60%">
						<form:password path="confirmPassword" />
					</td>
				</tr>
			</table>
			<br>
			
			<form:hidden path="userId" />
			
			<input type="submit" value="Change password">
		</form:form>
	
	</body>
</html>