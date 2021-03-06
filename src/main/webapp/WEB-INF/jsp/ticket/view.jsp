<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="flexcom.casehistory.ticket.entity.*" %>
<%@ include file="../include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Ticket detail</title>
		<link type="text/css" media="all" rel="stylesheet" href="<c:url value="/css/style.css" />"/>
	</head>
	<body>

		<div class="header">
			<jsp:include page="../menu.jsp" />
	    </div>
		<h1>Ticket #<c:out value="${ticket.id}"/> - <c:out value="${ticket.title}"/></h1>
	
		<div align="right">
			<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${ticket.creationDate}"/>
		</div>
		<h2>Client</h2>
		<div>
			Company : <a href="../client/view.html?id=${ticket.client.id}">${ticket.client.name}</a><br/>
			Address : ${ticket.client.address}<br/>
		</div>
		<h2>Contact</h2>
		<div>
			Name : <a href="../contact/view.html?id=${ticket.contact.id}">${ticket.contact.name}</a>
			(<%
				Company c = ((Ticket) request.getAttribute("ticket")).getContact().getCompany();
				out.write("<a href=\"../" + c.getClass().getSimpleName().toLowerCase() + "/view.html?id=" + c.getId() + "\">" + c.getName() + "</a>");
			%>)<br/>
		</div>
		<h2>Product</h2>
		<div>
			Product : <a href="../product/view.html?id=${ticket.product.id}">${ticket.product.name} ${ticket.product.version}</a><br/>
			Build : ${ticket.build}<br/>
		</div>
		<h2>Informations</h2>
		<div>
			Priority : ${ticket.priority}<br/>
			Status : ${ticket.status}<br/>
		</div>
		<div>
			Description : ${ticket.description}<br/>
		</div>
		<br/>
		
		<h2>Notes</h2>
		<div>
			<table border="2" cellspacing="2" cellpadding="5">
				<tr>
		    		<th>Id</th>
		    		<th>Note</th>
		    		<th>Author</th>
		    		<th>Date</th>
		    		<th>Attachment</th>
				</tr>
			    <c:forEach items="${notes}" var="note">
			    	<tr>
			    		<td><c:out value="${note.id}"/></td>
			    		<td><c:out value="${note.note}"/></td>
			    		<td><a href="../user/view.html?id=${note.author.id}"><c:out value="${note.author.name}"/></a></td>
			    		<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${note.creationDate}"/></td>
			    		<td><c:out value="${note.attachment}"/></td>
			    	</tr>
			    </c:forEach>
			</table>
	    </div>
	    
	    <form:form modelAttribute="note" enctype="multipart/form-data">
		
			<form:errors path="*" cssClass="error"/>
		
			<table border="0" cellspacing="0" cellpadding="5">
				<tr>
					<td align="right" width="40%">Note :</td>
					<td width="60%">
						<form:textarea path="note"/>
					</td>
				</tr>
			</table>
			
			<form:hidden path="author"/>
			<form:hidden path="ticket"/>
			
			<input type="file" name="file"/>
			
			<br><br>
			<input type="submit" value="Add note">
		</form:form>
		
		<br/>
		<a href="events.html?id=${ticket.id}">History</a><br/>
	    
	</body>
</html>