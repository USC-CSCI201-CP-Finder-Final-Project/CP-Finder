window.onscroll = function() { handleScroll() };

var offset = document.getElementById("header").offsetTop;

function handleScroll() {
	if (window.pageYOffset >= offset) {
		document.getElementById("header").classList.add("sticky");
	}
	else {
		document.getElementById("header").classList.remove("sticky");
	}
}


function closeSidebar() {
	document.getElementById("header").style.gridTemplateColumns = "10% 80% 20%";
	document.getElementById("main").style.marginLeft = "0%";
	document.getElementById("mySidebar").style.display = "none";
	document.getElementById("menu").style.visibility = "visible";
}

function openSidebar() {
	document.getElementById("header").classList.add("sticky");
	document.getElementById("header").style.gridTemplateColumns = "1% 74% 25%";
	document.getElementById("main").style.marginLeft = "15%";
	document.getElementById("mySidebar").style.width = "15%";
	document.getElementById("mySidebar").style.marginRight = "0px";
	document.getElementById("mySidebar").style.display = "inline-block";
	document.getElementById("menu").style.visibility = 'hidden';
}