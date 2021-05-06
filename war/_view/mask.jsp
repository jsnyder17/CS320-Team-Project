<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
        <head>
		<title>
			Mask Up Bucko!
		</title>
		<style type="text/css">
                body {
                        font-size: 200%;
                        color: #ffffff;
                        background-color: black;
                }
				
				.center {
					text-align: center;
				}
				.regular {
					font-size:75%;
				}
				
				iframe {
					display: block;
					margin-left: auto;
					margin-right: auto;
				}
				
				.button {
					cursor: pointer;
					text-align: center;
					padding: 10px 10px;
					font-size: 100%;
					color:white;
					background-color: black;
					display: inline-block;
					border: none;
					margin: 4px 2px;
					text-decoration: none;
				}
				
                </style>
	</head>

        <body>
        	<form action="${pageContext.servletContext.contextPath}/bluekid"
        	method="post">
				<div class ="center">
					<h1>You lose...</h1>
					<p class = "regular">You should've put on that mask...</p>
					<iframe width = "1100" height = "600" src="https://letmegooglethat.com/?q=How+to+put+on+a+mask%3F&l=1" title="Let Me Google That For You" frameborder = "0"></iframe>
					<input type="Submit", name="Restart", class="button", onclick="alertFunction()", value="Restart"></input>
					<script>
						function alertFunction() {
						alert("I am restarting your game...");
						}
					</script>
				</div>
			</form>
        </body>
</html>