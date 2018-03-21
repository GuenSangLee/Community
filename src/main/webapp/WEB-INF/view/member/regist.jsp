<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/static/css/button.css" />
<link rel="stylesheet" type="text/css" href="/static/css/input.css" />
<script type="text/javascript"
	src="<c:url value="/static/js/jquery-3.3.1.min.js"/>"></script>
<script type="text/javascript">
	$().ready(function() {
		//email을 눌렀다 땟을때(keyup)
		$("#email").keyup(function() {
			var value = $(this).val();
			if (value != "") {
				//AJax Call(http://localhost.:8080/api/exists/email?email=ㅁㄴㅇ)
				//Ajax Call은 GET과 POST 두 가지 방식이 있다.
				
				//AJax Call 실행 $.post()  비동기방식
				//("URL",{파라미터})
				$.post("<c:url value="/api/exists/email"/>",{
					email: value
				},function(response){
					if( response.response) {
						$("#email").removeClass("valid");
						$("#email").addClass("invalid");
					}else{
						$("#email").removeClass("invalid");
						$("#email").addClass("valid");
					}
				});				
			} else {
				$("#email").removeClass("valid");
				$("#email").addClass("invalid");
			}
		});

		$("#nickname").keyup(function() {
			var value = $(this).val();
			
			if (value != "") {
				$.post("<c:url value="/api/exists/nickname"/>",{
					nickname: value
				},function(response){
					console.log(response.data)
					if(response.data){
						$("#nickname").removeClass("valid");
						$("#nickname").addClass("invalid");
					}else{
						$("#nickname").removeClass("invalid");
						$("#nickname").addClass("valid");
					}
				});
			} else {
				$("#nickname").removeClass("valid");
				$("#nickname").addClass("invalid");
			}
		});

		$("#password").keyup(function() {
			var value = $(this).val();
			if (value != "") {
				$(this).removeClass("invalid");
				$(this).addClass("valid");
				$(this).removeClass("valid");
				$(this).addClass("invalid");
			}

			var password = $("#password-confirm").val();

			if (value != password) {
				$(this).removeClass("valid");
				$(this).addClass("invalid");
				$("#password-confirm").removeClass("valid");
				$("#password-confirm").addClass("invalid");
			} else {
				$(this).removeClass("invalid");
				$(this).addClass("valid");
				$("#password-confirm").removeClass("invalid");
				$("#password-confirm").addClass("valid");
			}
		});

		$("#password-confirm").keyup(function() {
			var value = $(this).val();
			var password = $("#password").val();

			if (value != password) {
				$(this).removeClass("valid");
				$(this).addClass("invalid");
				$("#password").removeClass("valid");
				$("#password").addClass("invalid");
			} else {
				$(this).removeClass("invalid");
				$(this).addClass("valid");
				$("#password").removeClass("invalid");
				$("#password").addClass("valid");
			}
		});

		$("#registBtn").click(function() {
			if ($("#email").val() == "") {
				alert("이메일을 입력하세요.");
				$("#email").focus();
				$("#email").addClass("invalid");
				return false;
			}
			if ($("#email").hasClass("invalid")){
				alert("작성한 이메일은 사용할 수 없습니다.");
				$("#email").focus();
				return false;				
			}else{
				
				$.post("<c:url value="/api/exists/email"/>",{
					email: $("#email").val()
				},function(response){
					if(response.response){
						alert("작성한 이메일은 사용할 수 없습니다.");
						$("#email").addClass("invalid");
						$("#email").focus();
						return false;
					}
					if ($("#nickname").val() == "") {
						alert("닉네임을 입력하세요.");
						$("#nickname").focus();
						$("#nickname").addClass("invalid");
						return false;
					}

					if ($("#password").val() == "") {
						alert("비밀번호를 입력하세요.");
						$("#password").focus();
						$("#password").addClass("invalid");
						return false;
					}

					$("#registForm").attr({
						"method" : "post",
						"action" : "<c:url value="/regist"/>"
					}).submit();
				});			
			}
		});
	});
</script>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="/WEB-INF/view/template/menu.jsp" />
		<form:form modelAttribute="registForm">
			<div>
				
				<input type="email" id="email" class="invalid" name="email"
					placeholder="E-mail" value="${registForm.email }" />
				<div>
					<form:errors path="email" />
				</div>
			</div>
			<!-- TODO nickname 중복검사 하기(ajax) -->
			<div>
				<input type="text" id="nickname" class="invalid" name="nickname"
					placeholder="Nickname" value="${registForm.nickname }" />
				<div>
					<form:errors path="nickname" />
				</div>
			</div>
			<div>
				<input type="password" id="password" name="password"
					placeholder="Password" />
				<div>
					<form:errors path="password" />
				</div>
			</div>
			<div>
				<input type="password" id="password-confirm" name="password-confirm"
					placeholder="Password" />
			</div>
			<div style="text-align: center;">
				<div id="registBtn" class="button">회원가입</div>
			</div>
		</form:form>
	</div>
</body>
</html>