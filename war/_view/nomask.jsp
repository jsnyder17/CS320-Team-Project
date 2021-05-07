<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
        <head>
        	<title>You Lose!</title>
            <link rel="stylesheet" type="text/css" href="losescreen.css">
        </head>

        <body>
        	<form action="${pageContext.servletContext.contextPath}/nomask"
        	method="post">
			<div class ="center">
                <h1>Shoulda put on the mask...</h1>
			</div>
			<iframe width="1100" height="600" src="https://letmegooglethat.com/?q=How+to+put+on+mask%3F&l=1" title="You Died Player" frameborder="0" allow="autoplay; picture-in-picture" allowfullscreen></iframe>
			
			<div class ="center">
				<input type="Submit", name="Restart", class ="button", onclick="alertFunction()", value="Restart"></input>
				
				<script>
					function alertFunction() {
						alert("I am restarting your game...");
					}
				</script>
			</div>
			</form>
        </body>
</html>