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
			$("#queueHeader").append('<button onclick = "enqueue();" id = "add">Add me to the Queue</button></div>');
		}
		else {
			$("#queueHeader").append('<div id = "queue">No students'
				+ ' currently queued!</div><button onclick = "enqueue();" id = "add">Add me to the Queue</button>');
		}
		waitChange();
		var run = setInterval(waitChange, 1000);
	}
	
	window.onload = renderQueue();	

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
			if (this.responseText == "success") {
				if ($("#add").text() == "Add me to the Queue") {
					document.getElementById("add").innerHTML = "Remove me from the Queue";
					var newQ = <%= request.getAttribute("queue") %>;
					rerenderQueue(newQ);
				}
				else if ($("#add").text() == "Remove me from the Queue") {
					document.getElementById("add").innerHTML = "Add me to the Queue";
					var newQ = <%= request.getAttribute("queue") %>;
					rerenderQueue(newQ);
				}
			}
		}
		var func = "sendChange"
		$.ajax({
		  url: "QueueServlet",
		  data: {func: func},
		  success: function(results){
			  alert("a");
		  },
		  dataType: String
		});
	}
	
	function rerenderQueue(newQ) {
		$("#queueHeader").innerHTML = "";
		$("#queueHeader").append('<div id = "queue">');
		for (var i = 0; i < queue.length; i++) {
			$("#queue").append('<div class = "queueDisplay"><div class = "student"><div class = "img"><img class = "studentimg" src="profile.png"/>'
				+ '</div><p class = "studentName">'+(i+1)+'. '+queue[i].user.name+'</p></div>')
		}
		$("#queueHeader").append('<button onclick = "enqueue()" id = "add">Add me to the Queue</button></div>');
	}
	
	function waitChange() {
			console.log("x");
			var func = "pollChange"
			var res = "false";
			$.ajax({
				  url: "QueueServlet",
				  data: {func: func},
				  success: function(results){
					  res = results;
				  },
				  dataType: String
				});
			if(res == "True"){
				console.log("y");
				var func = "getNewQueue"
				$.ajax({
				  url: "QueueServlet",
				  data: {func: func},
				  success: function(results){
					  document.getElementById("queueHeader").innerHTML = results;
				  },
				  dataType: String
				});
		}
	}
	
</script>
<script type="text/javascript" src="main.js"></script>
</html>