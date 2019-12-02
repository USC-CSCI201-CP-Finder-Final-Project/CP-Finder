<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Settings</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<link rel="stylesheet" href="Settings.css">
</head>
<body>
	<div style="display:none" id="mySidebar">
	  <button class="close navbar" onclick="closeSidebar()">Close &times;</button>
	  <a href = "MainPage.jsp" class="links navbar">Home</a><br>
	  <a href="landingPage.jsp" class="links navbar">Sign Out</a><br>
	  <a href="#" class="links navbar">Settings</a><br>
	</div>
	<div id = "main">
		<div id = "header">
			<button id = "menu" class = "content" onclick="openSidebar()">&#9776;</button>
			<div id = "settingsHead">Settings</div>
			<a href = "landingPage.jsp"><img id = "icon" class = "content" src = "angryFace.png"></a>
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
			<div id = "userPref">
			<h2>User Preferences</h2>
				<form name = "userChanges" action="UpdateProfile" method="POST" enctype="multipart/form-data">
					<p>Name</p>
					<input name = "name" type="text"></br>
					<p>Email</p>
					<input name = "email" type="text"></br>
					<p>Preferred Name</p>
					<input name = "prefname" type="text"></br>
					<p>Password</p>
					<input name = "password" type="password"></br>
					<p>Profile Picture</p>
					<input name = "picture" type="file"></br>					
					<input type="submit" value="Update" />
				</form>
			</div>
			<div id = "cp">
			<h2>CP Check-In</h2>
				<label class = "switch">
					<input id = "cpCheck" type = "checkbox" onclick = "cpCheckin()">
					<span class="slider round"></span>
				</label>
				<p id = "status">Inactive</p>
			</div>
			</div>
		</div>
	</div>
	<script>
		
		var user = <%= session.getAttribute("userJson") %>;
		var course = <%= session.getAttribute("course") %>;
		
		
		function cpCheckin() {
			var command = "";
			if ($("#cpCheck").checked) {
				command = "in";
			}
			else {
				command = "out";
			}
			var xhttp = new XMLHttpRequest();
			var url = "enqueue?userID=" + user.id + "&courseID=" 
					+ course.id + "&command=" + command;
			xhttp.open("POST", url, true);
			xhttp.send();
			xhttp.onreadystatechange = function() {
				console.log("here");
				if ($("#status").text() == "Inactive") {
					document.getElementById("status").innerHTML = "Active";
				}
				else if ($("#status").text() == "Active") {
					document.getElementById("status").innerHTML = "Inactive";
				}
			}
		}
				
		function populateUser() {
			$("input[name='name']").val(user.name);
			$("input[name='email']").val(user.email);
			$("input[name='prefname']").val(user.preferredName);
			$("input[name='password']").val(user.password);
			
			if (user.userType == "CP") {
				$("#cp").show();
			}
			else {
				$("#cp").hide();
			}
		
		}
		
		window.onload = populateUser();
		
	</script>
	<script type="text/javascript" src="main.js"></script>
</body>
</html>