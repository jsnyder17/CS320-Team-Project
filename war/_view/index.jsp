<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Login</title>
	</head>
	
	<body>
		<c:if test="${! empty errorMessage}">
				<div class="error">${errorMessage}</div>
		</c:if>
		
	  <div align="center">
	 	<form action="${pageContext.servletContext.contextPath}/index" method="post">
		<table style="width: 80%">
			<tr>
				<th>User name</th>
				<th><input type="text" name="userName" value="${userName}" /></th>
			</tr>
		
			<tr>
				<th>Password</th>
				<th><input type="password" name="password" value="${password}"/></th>
			</tr>
		</table>
			<input type="Submit" name="login" value="Login!!">
		</form>
	 </div>
	</body>
</html>