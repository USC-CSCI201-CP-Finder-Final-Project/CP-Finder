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
	  <a onclick = "history.back()" class="links navbar">Home</a>
	  <a href="#" class="links navbar">Profile</a>
	  <a href="landingPage.jsp" class="links navbar">Sign Out</a>
	  <a href="#" class="links navbar">Settings</a>
	</div>
	
	<div id = "main">
		<div id = "header">
			<button id = "menu" class = "content" onclick="openSidebar()">&#9776;</button>
			<form class = "content">
				<div id = "courseName"></div>
			</form>
			<img id = "icon" class = "content" src = "angryFace.png">
		</div>
		<div id = "mainContent">
			<div id = "firstHalf">
				<div class="map">
	                <h3 class = "seatingHeader"><span>Seating Location</span></h3>
	
	                <img id="mapImage" src="images/map-a.jpg" data-high-res-src="images/map-a.jpg" width=100%/>
	
	            </div>
				<div id = "cps"></div>
			</div>
			
			<div id = "queueObj">
				<div id = "queueHeader">
					<h3 class = "queueText"><span>Students in Queue</span></h3>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	var queueObj = <%= session.getAttribute("queue") %>;
	var course = <%= session.getAttribute("course") %>;
	if(queueObj!= null){
		var queue = queueObj.queuedUsers.list;
	}
	
	var queueObj = <%= session.getAttribute("queue") %>;
	var course = <%= session.getAttribute("course") %>;
	var cps = <%= session.getAttribute("cps") %>;
	var queue = queueObj.queuedUsers.list;
	
	function renderQueue() {
		$("#courseName").append(course.title);
		
		/*for (var i = 0; i < cps.length; i++) {
			$("#cps").append('<div class = "cpDisplay"><div class = "img"><img src="profile.png"/>'
				 + '</div><div class = "cpName">CP: ' + cps[i] + '</div></div>');
		}*/
		
		if (queue.length > 0) {
			console.log(queue.length);
			$("#queueHeader").append('<div id = "queue">');
			for (var i = 0; i < queue.length; i++) {
				$("#queue").append('<div class = "queueDisplay"><div class = "student"><div class = "img"><img class = "studentimg" src="profile.png"/>'
					+ '</div><p class = "studentName">'+(i+1)+'. '+queue[i].user.name+'</p></div>')
			}
			$("#queueHeader").append('<button id = "add">Add me to the Queue</button></div>');
		}
		else {
			$("#queueHeader").append('<div id = "queue">No students'
				+ ' currently queued!</div><button id = "add">Add me to the Queue</button>');
		}
		waitChange();
		var run = setInterval(waitChange, 1000);
	}
	
	window.onload = renderQueue();
	
	<% int id = (Integer)session.getAttribute("courseID"); %>
	<% QueueClient qc = new QueueClient("localhost", 6790, id); %>
	

	function changeQueue(){
		var lines = <%= qc.code%>;
		document.getElementById("queue").innerHTML = lines;
	}
	
	function waitChange() {
			console.log("x");
			var c = <%= qc.newQueue%>;
			if(c == true){
				<%qc.newQueue = false;%>
				changeQueue();
		}
	}
	
</script>
<script type="text/javascript" src="main.js"></script>
</html>