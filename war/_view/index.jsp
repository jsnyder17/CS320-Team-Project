<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Login</title>
		<link rel="stylesheet" type="text/css" href="index.css">
	</head>
	
	<body>
	  <div align="center">
	  	<h1>===================================</h1>
	  	<h1>Meme Quest</h1>
	  	<h1>===================================</h1>
	 	<form action="${pageContext.servletContext.contextPath}/index" method="post">
		<table style="width: 80%">
			<tr>
				<th>User name</th>
				<th><input type="text" name="username" value="${username}" /></th>
			</tr>
		
			<tr>
				<th>Password</th>
				<th><input type="password" name="password" value="${password}"/></th>
			</tr>
		</table>
			<input type="Submit" name="login" value="Login!!">
			<input type="Submit" name="new" value="New User">
		</form>
		
		<c:if test="${! empty errorMessage}">
				<div class="statusMessage">${errorMessage}</div>
		</c:if>
	 </div>
	</body>
</html>