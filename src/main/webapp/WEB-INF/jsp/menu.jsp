<%@ include file="include.jsp"%>

<ul id="menu">
	<li>
		<a href="<c:url value ="/home.html" />">Home</a>
	</li>
	<li>
		<a href="<c:url value ="/partner.html" />">Partner</a>
		<ul class="submenu">
			<li><a href="<c:url value="/partner/add.html" />">Add</a></li>
			<li><a href="<c:url value ="/partner/search.html" />">Search</a></li>
		</ul>
	</li>
	<li>
		<a href="<c:url value ="/client.html" />">Client</a>
		<ul class="submenu">
			<li><a href="<c:url value ="/client/add.html" />">Add</a></li>
			<li><a href="<c:url value ="/client/search.html" />">Search</a></li>
		</ul>
	</li>
	<li>
		<a href="<c:url value ="/product.html" />">Product</a>
		<ul class="submenu">
			<li><a href="<c:url value ="/product/add.html" />">Add</a></li>
			<li><a href="<c:url value ="/product/search.html" />">Search</a></li>
		</ul>
	</li>
	<li>
		<a href="<c:url value ="/licence.html" />">Licence</a>
		<ul class="submenu">
			<li><a href="<c:url value ="/licence/add.html" />">Add</a></li>
			<li><a href="<c:url value ="/licence/search.html" />">Search</a></li>
		</ul>
	</li>
	<li>
		<a href="<c:url value ="/contact.html" />">Contact</a>
		<ul class="submenu">
			<li><a href="<c:url value ="/contact/add.html" />">Add</a></li>
			<li><a href="<c:url value ="/contact/search.html" />">Search</a></li>
		</ul>
	</li>
	<li>
		<a href="<c:url value ="/ticket.html" />">Ticket</a>
		<ul class="submenu">
			<li><a href="<c:url value ="/ticket/add.html" />">Add</a></li>
			<li><a href="<c:url value ="/ticket/search.html" />">Search</a></li>
		</ul>
	</li>
	<li>
		<a href="<c:url value ="/user.html" />">User</a>
		<ul class="submenu">
			<li><a href="<c:url value ="/user/add.html" />">Add</a></li>
			<li><a href="<c:url value ="/user/search.html" />">Search</a></li>
		</ul>
	</li>
	<li id="authentication">
		User : <sec:authentication property="principal.username"/>
		<a href="<c:url value ="/j_spring_security_logout" />" title="Logout">
			<img src="<c:url value ="/img/logout.png" />" alt="logout" width="16" height="16" border="0"/>
		</a>
	</li>
</ul>
