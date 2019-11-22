<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="loginStyle.css">
<title>Login</title>
</head>
<body bgcolor="white">
	<a href="landingPage.jsp" id="imageButton"> <header>
			<img src="whereCPFace.png" alt="AngryFace">
		</header>
	</a>
	<h1>Sign In</h1>
	<form name="Login" method="POST" action="Login">
		<input type="text" name="email" placeholder="Email" required>
		<input type="password" name="password" placeholder="Password" required>
		<button type="submit" class="button">Sign In</button>
		<a href="Register.jsp">Need an account?</a>
		<div id="error_msg">
			<%=request.getAttribute("error") != null ? request.getAttribute("error") : ""%></div>
	</form>
</body>
</html>