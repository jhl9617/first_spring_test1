<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>first</title>

<script type="text/javascript" src="${ pageContext.servletContext.contextPath }/resources/js/jquery-3.6.3.min.js">	 </script>		<%-- jQuery 적용 --%>
<script type="text/javascript">
	//jQuery로 이벤트처리 : 검색 form을 보이게 & 안보이게 처리한다
	/* ★태그의 이벤트 적용방식은 여러가지가 있음
		1) <태그명 on이벤트="함수명();">
		2) window.onload = function() { 이벤트 적용과 작동 코드 작성};
		== 동일함 =>	$(function(){ 이벤트 적용과 작동 코드 작성}); 
	*/
	$(function(){
		
		showDiv();
		$('input[name=item]').on('change', function(){
				showDiv();		
		});
	});	//document ready
	
	function showDiv() {
		if($('input[name=item]').eq(0).is(':checked')) {
			$('#titleDiv').css('display', 'block');
			$('#writerDiv').css('display', 'none');
			$('#dateDiv').css('display', 'none')
		}
		if($('input[name=item]').eq(1).is(':checked')) {
			$('#titleDiv').css('display', 'none');
			$('#writerDiv').css('display', 'block');
			$('#dateDiv').css('display', 'none')
		}
		if($('input[name=item]').eq(2).is(':checked')) {
			$('#titleDiv').css('display', 'none');
			$('#writerDiv').css('display', 'none');
			$('#dateDiv').css('display', 'block')
		}
		
		
	}
	
	

</script>
</head>
<body>
<!-- 상대경로로 대상 파일의 위치를 지정한 경우 -->
<c:import url="../common/menubar.jsp"/>
<!-- jstl 에서 절대경로 표기 : "/WEB-INF/views/common/menubar.jsp" -->

<hr>
<h1 align="center">공지사항</h1>
<!-- 관리자만 공지글 등록할 수 있도록 처리함
		관리자가 로그인하면 공지글 등록 버튼이 보이게 함
 -->
 <center>
 	<c:if test="${ sessionScope.loginMember.admin eq 'Y' }">		<!-- 로그인했고(세션) 세션의 admin 이 Y 이면 -->
 		<button onclick="javascript:location.href='movewriter.do';">새 공지글 등록</button>
 	
 	</c:if>
 
 </center>
<hr>

<!-- 검색 항목 영역 -->
<center>
<div>
	<h2>검색할 항목을 선택하세요.</h2>
	<input type="radio" name="item" value="title" checked>제목
	&nbsp; &nbsp;
	<input type="radio" name="item" value="writer" >작성자
	&nbsp; &nbsp;
	<input type="radio" name="item" value="date" >날짜
</div>
<div id="titleDiv">
	<form action="nsearchTitle.do" method="post">
		<label>검색할 제목 키워드를 입력하세요 : 
			<input type="search" name="keyword">
		</label>
		<input type="submit" value="검색">
	</form>
</div>

<div id="writerDiv">
	<form action="nsearchWriter.do" method="post">
		<label>검색할 작성자 아이디를 입력하세요 : 
			<input type="search" name="keyword">
		</label>
		<input type="submit" value="검색">
	</form>
</div>

<div id="dateDiv">
	<form action="nsearchDate.do" method="post">
		<label>검색할 등록 날짜를 입력하세요 : 
			<input type="date" name="begin"> ~
			<input type="date" name="end">
		</label>
		<input type="submit" value="검색">
	</form>
</div>
<div id="writeDiv"></div>
<div id="dateDiv"></div>
</center>




<!-- 목록 출력 영역 -->
<center>
	<button onclick="javascript:location.href='${ pageContext.servletContext.contextPath }/nlist.do';">전체 목록 보기</button>


</center>
<br>
<table align="center" width="500" border="1" cellspacing="0" cellpadding="1">
	<tr>
		<th>번 호</th>
		<th>제 목</th>
		<th>작성자</th>
		<th>첨부파일</th>
		<th>날 짜</th>
	</tr>
	<c:forEach items="${ requestScope.list }" var="n">
		<tr align="center">
			<td>${ n.noticeno }</td>
			<!-- 공지 제목 클릭시 해당 공지의 상세보기로 넘어가게 함 -->
			<c:url var="ndt" value="/ndetail.do">
				<c:param name="noticeno" value="${ n.noticeno }"/>
			
			</c:url>
			<td><a href="${ ndt } ">${ n.noticetitle }</a></td>
			<td>${ n.noticewriter }</td>
			<td>
				<c:if test="${ !empty n.original_filepath }">◎</c:if>
				<c:if test="${ empty n.original_filepath }">&nbsp;</c:if>
			</td>
			<td>
				<fmt:formatDate value="${ n.noticedate }" pattern="yyyy-MM-dd" />
			</td>
		</tr>
	</c:forEach>

</table>
<hr>
<c:import url="/WEB-INF/views/common/footer.jsp" />
<hr>
</body>
</html>