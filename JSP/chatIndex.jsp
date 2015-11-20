<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Chat</title>
<style type="text/css">
<%@include file="../css/style.css" %>
</style>
</head>
<body>
<div id="page-wrap">
<h2></h2>	
<!-- <div id="ChatAtBigScreen">

</div> -->
</div>
<div id="chat_box">
	<div id="chat_head"></div>
	<div id = "chat_body">
		<div id ="user"></div>
	</div>
</div>

<div id="divChat">
		<div id="chat-wrap">
		<div id="chat_Header"></div>
		<div id = "close">X</div>
		<div id="chat-area"></div>
		</div>
		
<form id="send-message-area">	<!--onsubmit="javascript:return saveChats();-->
<table>
	<tr align="left">
		<td colspan="2" align="left"><textarea id="msg" rows="3"></textarea>
		
		</td>
	</tr>
</table>
</form>
</div>

<script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<script src="../dist/sweetalert.min.js"></script>
<link rel="stylesheet" type="text/css" href="../dist/sweetalert.css">
<script>
$(document).ready(function(){
$("#divChat").hide();
var uEnteredName;
swal({
	title : "Real Time Chat!",
	text : "Enter Username:",
	type : "input",
	showCancelButton : true,
	closeOnConfirm : false,
	animation : "slide-from-top",
	inputPlaceholder : "User Name"
}, function(inputValue) {
	if (inputValue === false)
		return false;
	if (inputValue === "") {
		swal.showInputError("You need to write something!");
		return false
	}
	swal("Welcome!" + inputValue);
	uEnteredName = inputValue;


		
		//var uEnteredName = inputValue;//prompt("Please Enter Your Name");
		if (uEnteredName) {
			alert(uEnteredName);
			$("#chat_head").html(uEnteredName);
			var enterPressed = false;
			var count = 0;

			$("#chat_head").click(function() {
				$("#chat_body").slideToggle('slow');
			});

			/* $("#chat_Header").click(function(){
				$("#divChat").slideToggle('slow');
			}); */

			$("#close").click(function() {
				$("#divChat").hide();
			});

			$("#user").click(function() {
				$("#divChat").show();
			});

			userStatus(uEnteredName);
			pollOnFirstEntry();

			var temp;
			var msg;

			$('#msg').keyup(function(e) {

				if (e.keyCode == 13) {
					enterPressed = true;
					msg = $("#msg").val();
					saveChats();
					$("#msg").val('');
					count = count + 1;
				}
			});

			function userStatus(uEnteredName) {
				var uName = $("#chat_head").html();
				if (uName == '') {
					alert('Please enter your name ');
					return false;
				}
				$
						.ajax({
							type : "GET",
							data : "uName=" + uName,
							url : "Onlinestatusprocess.do",
							error : function(jqXHR, textStatus, errorThrown) {
								alert(jqXHR + " - " + textStatus + " - "
										+ errorThrown);
							},
							success : function(data) {
								if (data != null) {
									$("#chat_Header").html(data);
									$("#user").html(data);
								}
							},
							complete : function() {
								//alert('count' + count);
								//pollOnFirstEntry();
							}
						});
				setTimeout(userStatus, 10000);
			}

			function saveChats() {
				var uName = $("#chat_head").html();
				if (uName == '') {
					alert('Please enter your name ');
					return false;
				}
				var message = "";
				/*$("input").on("keyup",function search(e) {
				    if(e.keyCode == 13) {
				    	msg = $("#msg").val();
				    }
				}); */
				if (enterPressed) {
					message = msg;
					enterPressed = false;
				}

				//var msg = $("#msg").val();
				var colorCode = $('input[name=nameColor]:checked',
						'#send-message-area').val();
				$
						.ajax({
							type : "POST",
							data : "uName=" + uName + "&msg=" + message
									+ "&colorCode=" + colorCode,
							url : "Chatprocess.do",
							error : function(jqXHR, textStatus, errorThrown) {
								alert(jqXHR + " - " + textStatus + " - "
										+ errorThrown);
							},
							success : function(data) {
								$("#chat-area").html(data);
								document.getElementById('chat-area').scrollTop = document
										.getElementById('chat-area').scrollHeight;
							},
							complete : function() {
								//alert('count' + count);
							}
						});
				setTimeout(saveChats, 5000);
				return false;
			}

			function pollOnFirstEntry() {
				var msg = "";
				$
						.ajax({
							type : "POST",
							url : "Chatprocess.do",
							data : "msg=" + msg,
							error : function(jqXHR, textStatus, errorThrown) {
								alert(jqXHR + " - " + textStatus + " - "
										+ errorThrown);
							},
							success : function(data) {
								$("#chat-area").html(data);
								document.getElementById('chat-area').scrollTop = document
										.getElementById('chat-area').scrollHeight;
							},
							complete : function() {
								alert("pollOnFirstEntry");
								setTimeout(saveChats, 2000);
							}
						});
				return false;
			}
		}
});

});
	</script>
</body>
</html>