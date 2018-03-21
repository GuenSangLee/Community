<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- css는 불러오는 순서가 중요하다 나중에 불러오는 것으로 덮어쓰여진다. -->
<link type="text/css" rel="stylesheet" href="<c:url value="/static/css/common.css"/>"/>
<link type="text/css" rel="stylesheet" href="<c:url value="/static/css/input.css"/>"/>
<link type="text/css" rel="stylesheet" href="<c:url value="/static/css/button.css"/>"/>
<link type="text/css" rel="stylesheet" href="<c:url value="/static/css/delete.css"/>"/>
<script type="text/javascript" src="<c:url value="/static/js/jquery-3.3.1.min.js" />"></script>
<script type="text/javascript">
	$().ready(function(){
		$("#moveLoginBtn").click(function(){
			location.href="<c:url value="/login"/>"
		});
	});
</script>
</head>
<body>
<%-- 회원 탈퇴시 탈퇴여부를 한번 더 확인하고, 게시글 삭제 여부를 물어본다. --%>
	<div id="wrapper">
		<jsp:include page="/WEB-INF/view/template/menu.jsp" />
		
		<div id="progress">
			<ul>
				<li class="active">본인확인</li>
				<li class="active">게시글 삭제</li>
				<li class="active">탈퇴 완료</li>
			</ul>
		</div>
		
		<div class="box">
			<p>본인인증을 위해 비밀번호를 입력하시오.</p>
			<div class="box" >
				<p style="text-align: center">
					탈퇴처리가 완료되었습니다.
				</p>
				<p style="text-align: center">
					그동안 이용해 주셔서 감사합니다.
				</p>
				<div style="text-align: center">
					<div id="moveLoginBtn" class="button">로그인 페이지로 이동.</div>
				</div>	
			</div>
		</div>
	
	</div>
</body>
</html>