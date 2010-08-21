<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
	<head>
		<title>New licence</title>
		<style>
			.error {
				color: red;
				font-style: bold;
			}
		</style>
		<link type="text/css" media="all" rel="stylesheet" href="<c:url value="/js/dijit/themes/tundra/tundra.css" />"/>
		<script type="text/javascript" src="<c:url value ="/js/dojo/dojo.js" />"></script>
		<script type="text/javascript" src="<c:url value ="/js/datepicker.js" />"></script>
		<script type="text/javascript">
			initDatePicker("beginningDate", new Date());
			initDatePicker("expirationDate", new Date());
		</script>
		<link type="text/css" media="all" rel="stylesheet" href="<c:url value="/css/style.css" />"/>
	</head>
	<body class="tundra">

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
					<td align="right" width="40%">Date de début :</td>
					<td width="60%">
						<spring:bind path="beginningDate">
							<input id="beginningDate" type="text" />
						</spring:bind>
					</td>
				</tr>
				
				<tr>
					<td align="right" width="40%">Date d'expiration :</td>
					<td width="60%">
						<spring:bind path="expirationDate">
							<input id="expirationDate" type="text" />
						</spring:bind>
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
		</form:form>
	
	</body>
</html>