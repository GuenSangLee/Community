$(document).ready(function() {
	$(window).resize(function() {
		for(var j = 0; j <= i; j++)
			replaceWhich(".alert_" + j);
	});
	
});

var height = -50;
var i = 0;

var arr = new Array();

function show(content) {
	
	setTimeout(function() {
		$("body").append("<div class='alert alert_"+i+" radius padding5'><pre>"+content+"</pre></div>");	
		$(".alert_"+i).hide();
		$(".alert_"+i).css("opacity", "0.6");
		height += (35 + $(".alert_"+i).height());
		rightInPage(".alert_"+i);
		$(".alert_"+i).fadeIn();
		arr.push(i);
		i++;
	}, 10); 
	
	setTimeout(function() {
		close(arr[0]);
		arr.splice(0,1); 
	}, 1500); 
}

function close(i) {
	height = -50;
	$(".alert_" + i).fadeOut();
}

function replaceWhich(element) {
	var width = $(element).attr("width");
	
	if ( width == undefined ) {
		width = $(element).css("width");
		width = parseInt(width);
	}
	
	width = parseInt(width) + 45;
	
	$(element).css({
		"left" : ($(window).width() - width) + "px"
	});
}

function rightInPage(element) {
	var width = $(element).attr("width");
	
	if ( width == undefined ) {
		width = $(element).css("width");
		width = parseInt(width);
	}
	
	width = parseInt(width) + 45;
	
	$(element).css({
		"top" : height + "px"
		, "left" : ($(window).width() - width) + "px"
	});
}

