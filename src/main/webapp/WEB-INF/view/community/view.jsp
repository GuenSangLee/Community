<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<c:url value="/static/css/alert.css"/>"/>
<title>ddd</title>
<script src="<c:url value="/static/js/jquery-3.3.1.min.js"/>" type="text/javascript"> </script>
<script src="<c:url value="/static/js/alert.js"/>" type="text/javascript"></script>
<script type="text/javascript">
	$().ready(function(){
		
		loadReplies(0);
		
		function loadReplies(scrollTop){
			$.get("<c:url value="/api/reply/${community.id}"/>",{}
				,function(response){
					console.log(response);
					for(var i in response){
						appendReplies(response[i]);
					}
					
					//스크롤 위치 설정
					$(window).scrollTop(scrollTop);
				});
		}
		
		
		
		$("#wirteBtn").click(function(){
			var mode= "${mode}";
			if( mode == "modify"){
				var url="<c:url value="/modify/${communityVO.id}"/>";
			}
			else {
				var url="<c:url value="/write/${communityVO.id}"/>";
			}
		});
		
		$("#writeReplyBtn").click(function(){
			$.post("<c:url value="/api/reply/${community.id}"/>",
				$("#writeReplyForm").serialize(),
				function(response) {
				
					if( response.status) {
						show("댓글 등록 됨");
						//appendReplies(response.reply);
						
						var scrollTop= $(window).scrollTop();
						$("#createReply").appendTo("#createReplyDiv");
						$("#createReplyId").val(0);
						
						$("#body").val("");
						//id가 replies인 것을 초기화
						$("#replies").html("");
						
						//스크롤바 좌표를 가져온다.
						alert(scrollTop);
						loadReplies(scrollTop);
						
						
						
					}else{
						alert("등록 실패!");
					}
			});
		});
		
		$("#replies").on("click", ".re-reply", function(){
			var parentReplyId= $(this).closest(".reply").data("id");
			$("#parentReplyId").val(parentReplyId);
			
			$("#createReply").appendTo($(this).closest(".reply"));
		});
		
		function appendReplies(reply){

			var replyDiv= $("<div class='reply' data-id='"+reply.id+"' style='padding-left:"+((reply.level-1)*20)+"px;'></div>");
			
			var nickname= reply.memberVO.nickname+"("+ reply.memberVO.email+")";
			
			var top= $("<span class='writer'>"+ nickname +"</span><span class='regist-date'> "+ reply.registDate +"</span>");
			replyDiv.append(top);
			
			var body=$("<div class='body'>"+ reply.body+"</div>")
			replyDiv.append(body);
			
			var registReReply= $("<div class='re-reply'>댓글 달기</div>");
			replyDiv.append(registReReply);
			
				$("#replies").append(replyDiv);
				
/*///			level적용 전에 사용하던 코드(댓댓의 위치를 잡아줌)
//			if(reply.parentReplyId==0){
//				$("#replies").append(replyDiv);
//			}
//			else{
				$(".reply").each(function(index, data){
					var replyId= $(data).data("id");
					if( reply.parentReplyId == replyId ){
						$(data).after(replyDiv);
					}
				});
			}*/
		}
		
		
	});
</script>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="/WEB-INF/view/template/menu.jsp"/>
		<h1>${community.title}</h1>
		<c:choose>
			<c:when test="${not empty community.memberVO}">
				<h3>(${community.memberVO.nickname})(${community.requestIp})</h3>			
			</c:when>
			<c:otherwise>
				<h3 style="display:inline-block">탈퇴한 회원</h3>(${community.requestIp})
			</c:otherwise>
		</c:choose>
		<p>${community.viewCount}| ${community.recommendCount} |
			${community.writeDate}</p>
		<p></p>

		<c:if test="${not empty community.displayFilename}">
			<p>
				<a href="<c:url value="/get/${community.id}" />">
					<p>${community.displayFilename}</p>
				</a>
			</p>

		</c:if>
		<p>
		${community.body}
		</p>
		<hr />
		<div id="replies"></div>
		<div id="createReplyDiv"> 
			<div id="createReply">
				<form id="writeReplyForm">
					<input type="hidden" id="parentReplyId" name="parentReplyId" value="0"/>
					<div>
						<textarea id="body" name="body"></textarea>
					</div>
					<div>
						<input type="button" id="writeReplyBtn" value="등록"/>
					</div>
				</form>
			</div>
		</div>
		<p>
		<c:if test="${sessionScope.__USER__.id == community.memberVO.id}">
		<a href="<c:url value="/modify/${community.id}"/>">수정하기</a>
		</c:if>
		<a href="<c:url value="/recommend/${community.id}"/>">[추천하기]</a>
		<a href="<c:url value="/delCommunity/${community.id}"/>">[삭제하기]</a>
		<a href="<c:url value="/"/>">[목록으로]</a>
		
	</p>
	</div>
</body>
</html>