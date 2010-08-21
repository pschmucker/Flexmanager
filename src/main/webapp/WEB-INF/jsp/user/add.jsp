<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
	<head>
		<title>New user</title>
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
		<form:form modelAttribute="user">
		
			<form:errors path="*" cssClass="error"/>
		
			<table border="0" cellspacing="0" cellpadding="5">
				<tr>
					<td align="right" width="40%">Login :</td>
					<td width="60%">
						<form:input path="login"/>
					</td>
				</tr>
				
				<tr>
					<td align="right" width="40%">Password :</td>
					<td width="60%">
						<form:password path="password"/>
					</td>
				</tr>
				
				<tr>
					<td align="right" width="40%">Name :</td>
					<td width="60%">
						<form:input path="name"/>
					</td>
				</tr>
				
				<tr>
					<td align="right" width="40%">Email :</td>
					<td width="60%">
						<form:input path="email"/>
					</td>

				</tr>
				
				<tr>
					<td align="right" width="40%">Title :</td>
					<td width="60%">
						<form:input path="title"/>
					</td>

				</tr>
				
				<tr>
					<td align="right" width="40%">Access level :</td>
					<td width="60%">
						<form:select path="accessLevel">
							<c:forEach begin="0" end="10" var="i">
								<form:option value="${i}"></form:option>
							</c:forEach>
						</form:select>
					</td>

				</tr>
				
				<tr>
					<td align="right" width="40%">Roles :</td>
					<td width="60%">
						<form:select path="roles">
							<form:options items="${roles}" itemValue="id" itemLabel="name"/>
						</form:select>
					</td>
				</tr>
				
				<tr>
					<td align="right" width="40%">Email notification :</td>
					<td width="60%">
						<form:checkbox path="emailNotificationEnabled"/>
					</td>
				</tr>
			</table>
			<br>
			<input type="submit" value="Create">
		</form:form>
	
	</body>
</html>