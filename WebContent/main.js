window.onscroll = function() { handleScroll() };

var offset = document.getElementById("header").offsetTop;

function handleScroll() {
	if (window.pageYOffset > offset) {
		document.getElementById("header").classList.add("hold");
	}
	else {
		document.getElementById("header").classList.remove("hold");
	}
}

function showResults() {
	
}