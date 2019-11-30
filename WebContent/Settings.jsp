<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Settings</title>
<link rel="stylesheet" href="Settings.css">
</head>
<body>
	<div style="display:none" id="mySidebar">
	  <button class="close navbar" onclick="closeSidebar()">Close &times;</button>
	  <a href = "#" class="links navbar">Home</a>
	  <a href="#" class="links navbar">Profile</a>
	  <a href="landingPage.jsp" class="links navbar">Sign Out</a>
	  <a href="#" class="links navbar">Settings</a>
	</div>
	<div id = "main">
		<div id = "header">
			<button id = "menu" class = "content" onclick="openSidebar()">&#9776;</button>
			<form class = "content">
				<div id = "filter">
					<div id = "col1">
						<input id = "textbox" type = "text" name = "searchText" placeholder = "Search course or CP!"><br>
					</div>
					<div id = "col2">
						<input id = "search" src = "search.jpg" type = "image">
					</div>
				</div>
			</form>
			<img id = "icon" class = "content" src = "angryFace.png">
		</div>
		<div id = "settings">
			<div id = "settingsHeader">
				<div id = "settingscol1">
					<img id = "settingsIcon" src = "settings.png">
				</div>
				<div id = "settingscol2">
					<h3 id = "settingstext">Settings</h3>
				</div>
			</div>
			<div id = "settingsBody">
			<h2>User Preferences</h2>
				<form name = "userChanges">
					<p>Name</p>
					<input id = "name" type="text"></br>
					<p>Email</p>
					<input id = "email" type="text"></br>
					<p>Preferred Name</p>
					<input id = "prefname" type="text"></br>
					<p>Password</p>
					<input id = "password" type="text"></br>
				</form>
			</div>
		</div>
	</div>
</body>
</html>