<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<link rel="stylesheet" href="MainPage.css">
</head>
<body>
	<div style="display:none" id="mySidebar">
	  <button class="close navbar" onclick="closeSidebar()">Close &times;</button>
	  <a id = "settings" href="Settings.jsp" class="links navbar">Settings</a>
	  <a id = "signout" href = "<%=request.getContextPath()%>/logout" class="links navbar">Sign Out</a>
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
		<div id = "results">
			<h2 id = "title">Active Sessions</h2>
		</div>
	</div>
</body>
<script>
	
	var user = <%= session.getAttribute("userJson") %>;
	var data = <%= session.getAttribute("sessions") %>;
	console.log(data);
	var sessions = "";
	if (data != null) {
		sessions = data.list;
	}
		
	var webServiceUrl = "localhost:8080/CP-Finder/"; 

	
	function renderData() {
		for(i = 0; i < sessions.length; i++) {
			$("#results").append('<div id = "' + i + '"'+ '><div class = "container"><a href="\DetailsServ?id='
				/*  sessions[i].course.id+'"><img class = "img" src="profile.png"/></a>' */
				+ sessions[i].course.id + '&loc=' + sessions[i].location.id
				+'"><img class="img" src="images/photo-userid-' + sessions[i].CP.id + '.jpg"/></a>'
				+'<div class = "text"><h2>'+sessions[i].course.title+'</h2>'
				+'<h3>CP on Duty: '+sessions[i].CP.preferredName+'</h3>'
				+'<p>Session started: '+sessions[i].createdAt+'</p>'
				+'<p>Location: SAL Open Lab - '+sessions[i].location.name+'</p>'
				+'</div><p class = "status">Status: '+sessions[i].status.name+'<p></div>'
				+"<hr style='border-top: solid 1px; color: #110336;' /></div>");
		}
		if (user == "null" || user == null) {
			$("#settings").hide();
			$("#signout").hide();
			$("#mySidebar").append('<a id = "signin" href="login.jsp" class="links navbar">Sign In</a>');
			$("#mySidebar").append('<a id = "signin" href="Register.jsp" class="links navbar">Register</a>');
		}
	}
	
	$( "#search" ).click(function() {
		var searchQuery = document.getElementById("textbox").value.toLowerCase();
		console.log(searchQuery);
		for(i = 0; i < sessions.length; i++) {
			if (!((sessions[i].course.title).toLowerCase().includes(searchQuery)) &&
					!((sessions[i].CP.name).toLowerCase().includes(searchQuery))) {
				$("#"+i).hide();
			}
			else {
				$("#"+i).show();
			}
		}
		return false;
	});
	
	window.onload = renderData();
	
</script>
<script type="text/javascript" src="main.js"></script>
</html>