<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
<script type="text/javascript"
	src="<c:url value="/static/js/jquery-3.3.1.min.js"/>"></script>
<script type="text/javascript">
	$().ready(function() {

		// validation check
		$("#loginBtn").click(function() {
			// if ( $("#id").val() == "" ) {
			//$("#errorId").slideDown(300);
			//$("#errorId").focus();
			// 다음 진행(하단의 submit)을 종료한다.
			//return false;
			//}
			//else {
			//$("#errorId").slideUp(300);
			//}
			//if ( $("#password").val() == "" ) {
			//	$("#errorPassword").slideDown(300);
			//	$("#errorPassword").focus();
			//	return false;
			//}
			//else {
			//	$("#errorPassword").slideUp(300);
			//} 
			$("#loginForm").attr({
				"action" : "<c:url value ="/login"/>",
				"method" : "post"
			}).submit();
		});
	});
</script>
</head>
<body>
	<div id=wrapper>
		<jsp:include page="/WEB-INF/view/template/menu.jsp" />
		<form:form modelAttribute="loginForm">
			<%--   		 <c:if test="${sessionScope.status eq 'fail'}">
			<div id="invalidIdAndPassword">
				<div>아이디 혹은 비밀번호가 잘못되었습니다.</div>
				<div>한번 더 확인 후 시도해 주세요.</div>
			</div>
		</c:if> --%>
			<div>
				아이디 : <input type="email" id="email" name="email" placeholder="E-mail"
					value="${memberVO.email}" />
			</div>
			<div>
				<form:errors path="email" />
			</div>

			<%-- <div id ="errorId" style="display:none">아이디를 입력하세요!</div> --%>

			<div>
				패스워드 : <input type="password" id="password" name="password"
					placeholder="PASSWORD" value="${memberVO.password}" />
			</div>
			<div>
				<form:errors path="password" />
			</div>
			<%-- <div id ="errorPassword" style="display:none">비밀번호를 입력하세요!</div> --%>

			<div>
				<input type="button" id="loginBtn" value="Login" />
			</div>

		</form:form>
		
		<div>
			<a href="<c:url value="/regist"/>">아직 회원이 아니신가요?</a>
		</div>
		
	</div>
</body>
</html>