<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="db.*, models.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Details</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<link rel="stylesheet" href="Details.css">
</head>
<body>
	<div style="display:none" id="mySidebar">
	  <button class="close navbar" onclick="closeSidebar()">Close &times;</button>
	  <a href="#" class="links navbar">Profile</a>
	  <a href="landingPage.jsp" class="links navbar">Sign Out</a>
	  <a href="#" class="links navbar">Link 3</a>
	</div>
	
	<div id = "main">
		<div id = "header">
			<button id = "menu" class = "content" onclick="openSidebar()">&#9776;</button>
			<form class = "content">
				<div id = "courseName"></div>
			</form>
			<img id = "icon" class = "content" src = "angryFace.png">
		</div>
		<div id = "cps"></div>
		
		<div id = "queue"></div>
	</div>
</body>
<script>
	var queueObj = <%= session.getAttribute("queue") %>;
	var course = <%= session.getAttribute("course") %>;
	var queue = queueObj.queuedUsers.list;
	
	<% 
	Course c = (Course)session.getAttribute("course");
	QueueClient qc = new QueueClient("localhost", 6790, c.getId());
	%>
	
	function renderQueue() {
		$("#courseName").append(course.title);
		
		if (queue.length > 0) {
			console.log(queue.length);
			$("#queue").append('<div id = "queueHeader">' +
					'Students in Queue: <br> <hr style="border-top: dotted 1px;" />'
					+ '</div>');
			for (var i = 0; i < queue.length; i++) {
				$("#queue").append('<img class = "img" src="profile.png"/>'
					+ '<p>'+(i+1)+'. '+queue[i].user.name+'</p>')
			}
		}
		else {
			$("#queue").append('<div id = "queueHeader">No students'
				+ 'currently queued!</div>');
		}
	}
	
	window.onload = renderQueue();
	
	<%
	while (true){
		if(qc.newQueue == true){
			qc.newQueue = false;%>
			changeQueue();
		<%}
	}
	%>
	
	function changeQueue(){
		var lines = <%= qc.code%>
		document.getElementById("queue").innerHTML = lines;
	}
	
</script>
<script type="text/javascript" src="main.js"></script>
</html>