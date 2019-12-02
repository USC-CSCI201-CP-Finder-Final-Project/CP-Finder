<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="loginStyle.css">
<title>Register</title>
</head>
<body>
<a href="landingPage.jsp" id="imageButton">
	<header>
		<img src="whereCPFace.png" alt="AngryFace">
	</header>
	</a>
	<h1>Create an Account</h1>
	<form name="Register" method="POST" action="Register">
		<input type="text" name="fName" placeholder="First Name" required>
		<input type="text" name="lName" placeholder="Last Name" required>
		<input type="text" name="pName" placeholder="Preferred Name" required>
		<input type="text" name="email" placeholder="Email" required>
		<input type="password" name="password" placeholder="Password" required>
		<div class="radios">
			<p>Registering As</p> <label> <input type="radio"
				name="isCP" value="CP" onclick="document.getElementById('dropDownMenu').style.display = 'flex'"required> CP
			</label> <label> <input type="radio" name="isCP"
				value="student" onclick="document.getElementById('dropDownMenu').style.display = 'none'"required> Student
			</label>
		</div>
		<button type="submit" class="button">Sign Up</button>
		<a href="login.jsp">Already have an account?</a>

		<div id="error_msg">
			<%=request.getAttribute("error") != null ? request.getAttribute("error") : ""%></div>
	</form>
</body>
</html>