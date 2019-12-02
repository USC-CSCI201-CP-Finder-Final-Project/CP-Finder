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
	                <h3 class = "seatingHeader">Seating Location</h3>
	
	                <img id="mapImage" src="images/map-a.jpg" data-high-res-src="images/map-a.jpg" width=100%/>
	
	            </div>
				<div id = "cps"></div>
			</div>
			
			<div id = "queueObj">
				<div id = "queueHeader">
					<h3 class = "queueText">Students in Queue</h3>
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
	var mycps = cps.list;
	
	function removeStudent(userID) {
		var xhttp = new XMLHttpRequest();
		var url = "enqueue?userID=" + userID + "&courseID=" + course.id + "&command=remove";
		xhttp.open("POST", url, true);
		xhttp.send();
		xhttp.onreadystatechange = function() {
			console.log("success");
		}
	}
	
	function renderQueue() {
		$("#courseName").append(course.title);
		//console.log(mycps);
		for (var i = 0; i < mycps.length; i++) {
			$("#cps").append('<div class = "cpDisplay"><div class = "img"><img class="img" src="images/photo-userid-' + mycps[i].CP.id + '.jpg"/>'
				 + '</div><div class = "cpName"><h3>CP: ' + mycps[i].CP.name +
				 '</h3><p>Session started: '+mycps[i].createdAt+'</p>'
					+'<p>Location: SAL Open Lab - '+mycps[i].location.name+'</p>'
					+'</div><p class = "status">Status: '+mycps[i].status.name+'<p></div></div></div>');
		}
		
		if (queue.length > 0) {
			//console.log(user.userType);
			$("#queueHeader").append('<div id = "queue">');
			for (var i = 0; i < queue.length; i++) {
				$("#queue").append('<div id = '+ i + ' class = "queueDisplay"><div class = "student"><div class = "img"><img class = "studentimg" src="profile.png"/>'
					+ '<p class = "studentName">'+(i+1)+'. '+queue[i].user.name+'</p>');
				if (user != null) {
					if (user.userType == "CP") {
						$("#queue").append('<button onclick = "removeStudent('+queue[i].user.id+')" class = "remove">Remove</div></div></div>');
					}
					else {
						$("#queue").append('</div></div></div>');
					}
				}
				$("#queueHeader").append('<button onclick = "enqueue();" id = "add">Add me to the Queue</button></div>');
				}
		}
		else {
			$("#queueHeader").append('<div id = "queue">No students'
				+ ' currently queued!</div><button onclick = "enqueue();" id = "add">Add me to the Queue</button>');
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
			case 8: mapFile = "images/map-h.jpg"; break;
			deafult: mapFile = "images/map.jpg";
		}
		$("#mapImage").attr("src", mapFile);		
		
	}
	
	window.onload = renderQueue();	

	function reRender(){
		if (queue.length > 0) {
			console.log(queue.length);
			$("#queueHeader").append('<div id = "queue">');
			for (var i = 0; i < queue.length; i++) {
				$("#queue").append('<div id = '+ i + ' class = "queueDisplay"><div class = "student"><div class = "img"><img class = "studentimg" src="profile.png"/>'
					+ '<p class = "studentName">'+(i+1)+'. '+queue[i].user.name+'</p>');
				if (user.userType == "CP") {
					$("#queue").append('<button onclick = "removeStudent('+queue[i].user.id+')" class = "remove">Remove</div></div></div>');
				}
				else {
					$("#queue").append('</div></div></div>');
				}
			}
			$("#queueHeader").append('<button onclick = "enqueue();" id = "add">Add me to the Queue</button></div>');
		}
		else {
			$("#queueHeader").append('<div id = "queue">No students'
				+ ' currently queued!</div><button onclick = "enqueue();" id = "add">Add me to the Queue</button>');
		}
	}
	
	
	function enqueue() {
		var command = "";
		if ($("#add").text() == "Add me to the Queue") {
			command = "add";
		}
		else if ($("#add").text() == "Remove me from the Queue") {
			command = "remove";
		}
		<%-- var xhttp = new XMLHttpRequest();
		var url = "enqueue?userID=" + user.id + "&courseID=" + course.id + "&command=" + command;
		xhttp.open("POST", url, true);
		xhttp.send();
		xhttp.onreadystatechange = function() {
			if ($("#add").text() == "Add me to the Queue") {
				document.getElementById("queueHeader").innerHTML = xhttp.responseText;
			}
			else if ($("#add").text() == "Remove me from the Queue") {
				document.getElementById("queueHeader").innerHTML = xhttp.responseText;
			}
		}--%>
		$.ajax({
			  url: "enqueue",
			  data: {userID: user.id, courseID: course.id, command: command},
			  complete: function(results){
				  document.getElementById('queueHeader').innerHTML = results.responseText;
			  },
			  dataType: String
			});
		
		var func = "sendChange";
		$.ajax({
		  url: "QueueServlet",
		  data: {func: func},
		  complete: function(results){

		  },
		  dataType: String
		});
	}
	
	
	function waitChange() {
		console.log("x");
		var func = "pollChange";
		$.ajax({
		  url: "QueueServlet",
		  data: {func: func},
		  success: function(results){
			 
		  	},
		  complete: function(results){
			  var z = results.responseText;
			  if(z != "false" ){
			  	console.log("true");
			  	
			  	document.getElementById('queueHeader').innerHTML = z;
			  }
		  },
		  dataType: String
		});
	} 
	
	
</script>
<script type="text/javascript" src="main.js"></script>
</html>