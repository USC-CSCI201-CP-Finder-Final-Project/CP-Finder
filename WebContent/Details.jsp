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
	  <a href="landingPage.jsp" class="links navbar">Sign Out</a>
	  <a href="Settings.jsp" class="links navbar">Settings</a>
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
	var user = <%= session.getAttribute("userJson") %>;
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
			$("#queueHeader").append('<button onclick = "enqueue()" id = "add">Add me to the Queue</button></div>');
		}
		else {
			$("#queueHeader").append('<div id = "queue">No students'
				+ ' currently queued!</div><button onclick = "enqueue()" id = "add">Add me to the Queue</button>');
		}
		waitChange();
		var run = setInterval(waitChange, 1000);
		
		
		// Populate map file based on location
		let locID = <%= request.getParameter("loc") %>;
		console.log(locID);
		let mapFile;		
		switch (locID) {
			case 1: mapFile = "images/map-a.jpg"; break;
			case 2: mapFile = "images/map-b.jpg"; break;
			case 3: mapFile = "images/map-c.jpg"; break;
			case 4: mapFile = "images/map-d.jpg"; break;
			case 5: mapFile = "images/map-e.jpg"; break;
			case 6: mapFile = "images/map-f.jpg"; break;
			case 7: mapFile = "images/map-g.jpg"; break;
			case 8: mapFile = "images/map-h.jpg"; breakl
			deafult: mapFile = "images/map.jpg";
		}
		$("#mapImage").attr("src", mapFile);		
		
	}
	
	window.onload = renderQueue();
	
	<% int id = (Integer)session.getAttribute("courseID"); %>
	<% QueueClient qc = new QueueClient("localhost", 6790, id); %>
	

	function enqueue() {
		var command = "";
		if ($("#add").text() == "Add me to the Queue") {
			command = "add";
		}
		else if ($("#add").text() == "Remove me from the Queue") {
			command = "remove";
		}
		var xhttp = new XMLHttpRequest();
		var url = "enqueue?userID=" + user.id + "&courseID=" + course.id + "&command=" + command;
		xhttp.open("POST", url, true);
		xhttp.send();
		xhttp.onreadystatechange = function() {
			if ($("#add").text() == "Add me to the Queue") {
				document.getElementById("add").innerHTML = "Remove me from the Queue";
			}
			else if ($("#add").text() == "Remove me from the Queue") {
				document.getElementById("add").innerHTML = "Add me to the Queue";
			}
		}
	}
	
	
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