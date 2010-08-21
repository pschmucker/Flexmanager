<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
	<head>
		<title>Connexion</title>
	</head>
	<body>
		<div>
			<form action="j_spring_security_check" method="post">
				Identifiant  : 
				<input name="j_username" value="" type="text" />
				Mot de passe : 
				<input name="j_password" type="password" />
				<input value="Valider" type="submit" />
			</form>
		</div>
	</body>
</html>