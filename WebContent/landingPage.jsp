<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="landingPageStyle.css">
<title>Landing Page</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
</head>
<body>
	<div id = "main">
		<img src="angryFace.png" alt="AngryFace">
		<h1>Where's My CP?</h1>
		<h2>I'll find you and kill you!</h2>
		<a href="login.jsp">Sign In</a>
		<a href="Register.jsp">Sign Up</a>
		<a href = "MainPage?">Continue as Guest</a>
	</div>
</body>
<script>
	function main(){
		$.ajax({
		  url: "MainPage",
		  success: function(results){
			  
		  },
		  dataType: String
		});
	}
	
</script>
</html>