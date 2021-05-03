<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<title>Game</title>
<link rel="stylesheet" type="text/css" href="gameCSS.css">
</head>
<body>
	<script>
		function scrollDown() {
			document.getElementById("outputTextArea").scrollTop = document.getElementById("outputTextArea").scrollHeight;
		}
		window.onload = scrollDown;
	</script>
	
	<form action="${pageContext.servletContext.contextPath}/game"
		method="post">
		<textarea readonly id="playerStatsTextArea" name="playerStatsTextArea" class="playerstats"><c:if test="${! empty player}">${player.statString}</c:if></textarea>
		<textarea readonly id="outputTextArea" name="outputTextArea" class="output"><c:if test="${! empty gameManager}">${gameManager.output}</c:if></textarea>

		<div class="inputprompt">
			<table>
				<tr>
					<td>Enter command:</td>
					<td><input type="text" name="commandInput" size="100" autocomplete="off" value="${commandInput}"/ autofocus></td>
				</tr>
			</table>
		</div>
		<input type="Submit" name="submit" value="Submit command" style="display: none;" />
	</form>

<script>
	document.getElementById("textarea").scrollTop = document.getElementById("textarea").scrollHeight
</script>	
	
</body>
</html>