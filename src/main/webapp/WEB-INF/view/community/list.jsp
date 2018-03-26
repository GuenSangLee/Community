<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List Page</title>
<script type="text/javascript" src="<c:url value="/static/js/jquery-3.3.1.min.js"/>"></script>
<script type="text/javascript">
	$().ready(function(){
		$("#searchKeyword").keyup(function(event){
			if(event.key == "Enter"){
				movePage('0');
				
			}
		});
	});



</script>

</head>
<body>
	<div id="wrapper">
		<jsp:include page="/WEB-INF/view/template/menu.jsp"/>
		<div>
			${pageExplorer.totalCount}
		</div>
		<table>
			<tr>
				<th>ID</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>조회수</th>
			</tr>

				<c:forEach items="${pageExplorer.list}" var="community">
				<tr>
					<td>${community.id}</td>
					<td><a href="<c:url value="/read/${community.id}"/>">${community.title}
					</a>
						<c:if test="${not empty community.displayFilename}">
							<img src="<c:url value="/static/img/file.png"/>
								 alt="${community.displayFilename}"
								 style="width:20px;"/>
						</c:if>
					</td>
					<c:choose>
						<c:when test="${not empty community.memberVO}">
							<td>${community.memberVO.nickname}(${community.memberVO.email})</td>
						</c:when>
						<c:otherwise>
							<td>탈퇴한 회원</td>
						</c:otherwise>
					</c:choose>
					<td>${community.writeDate}</td>
					<td>${community.viewCount}</td>
					<td></td>
				</tr>
				
			</c:forEach>
			<c:if test="${empty pageExplorer.list}">
				<tr>
					<td colspan="5">등록된 게시글이 없습니다.</td>
				</tr>
			</c:if>
		</table>
		<form id="searchForm" onsubmit="movePage('0')">
			${pageExplorer.make()}
			<div>
				<select id="searchType" name="searchType">
					<option value="1" ${ search.searchType eq 1 ? 'selected' : '' }>글 제목</option>
					<option value="2" ${ search.searchType eq 2 ? 'selected' : '' }>글 내용</option>
					<option value="3" ${ search.searchType eq 3 ? 'selected' : '' }>글 제목 + 글 내용</option>
					<option value="4" ${ search.searchType eq 4 ? 'selected' : '' }>작성자 Nickname</option>
					<option value="5" ${ search.searchType eq 5 ? 'selected' : '' }>작성자 Email</option>
					<option value="6" ${ search.searchType eq 6 ? 'selected' : '' }>첨부파일 명</option>
					<option value="7" ${ search.searchType eq 7 ? 'selected' : '' }>첨부파일 형식</option>
				</select>
				<input type="text" id="searchKeyword" name="searchKeyword" value="${search.searchKeyword}" />
				<a href="<c:url value="/reset"/>">[검색 초기화]</a>
			</div>
		
		</form>
		<div>
			<div style = "display:inline-block">
				<a href="/write">[글쓰기]</a>
			</div>
			<div style = "display:inline-block">
				<a href="/delete/process1">[회원탈퇴]</a>
			</div>
			<div style = "display:inline-block">
				<a href="<c:url value="/logout"/>">
					<input type="button" id="logoutBtn" value="Log out" />
				</a>
			</div>
		</div>
	</div>

</body>
</html>