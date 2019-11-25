<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
<script type="text/javascript" src="main.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<link rel="stylesheet" href="MainPage.css">
</head>
<body>
	<div id = "header">
		<button>&#9776;</button>
		<form onsubmit = "showResults()">
			<input type = "text" name = "searchText" placeholder = "Search course or CP!"><br>
		</form>
		<img src = "angryFace.png">
	</div>
	<div id = "results">
	</div>
</body>
</html>