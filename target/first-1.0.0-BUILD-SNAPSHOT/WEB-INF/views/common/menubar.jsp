<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- <%@ page import="org.ict.first.member.model.vo.Member" %> --%>
<%-- 페이지지시자는 한 페이지당 단 1번만 사용가능함! 단, 임포트설정만 구분해서 추가 작성 설정 가능 --%>
<%-- el을 사용하면, 임포트가 따로 필요없음 --%>
<%-- <%Member loginMember = (Member)session.getAttribute("loginMember");%>   --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%-- 페이지 지시자 태그임 //  절대안바뀌는 구문임! jstl core에서 사용하는 c태그를 이 문서에서 사용하겠다고 선언함 // JSPL홈페이지 core 에서 사용 구문 복붙해오면 됨 --%>


<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<style type="text/css">
header { margin: 0; padding: 0; }
header h1#logo{ 
	font-size: 36pt;
	font-style: italic;
	color: Coral;
	text-shadow: 2px 2px 2px Darkgray;
}

header ul#menubar {
	list-style: none;
	position: relative;		/* 원래태그가 표시되는 부분 기준으로 한다 */
	left: 150px; 
	top: -30px;
	
}

header ul#menubar li {
	float: left;
	width: 120px;
	height: 30px;
	margin-right: 5px;
	padding: 0;
}

header ul#menubar li a {
	text-decoration: none;		/* 링크의 밑줄표시 없앤다 */
	width: 120px;
	height: 30px;
	display: block;	/* 글자의 영역에만 가도 링크반응 표시 */
	background-color: Royalblue;
	text-align: center;
	color: Papayawhip;
	font-weight: bold;
	margin: 0;
	text-shadow: 2px 2px 3px gray;
	padding-top: 5px;
}

header ul#menubar li a:hover {
	text-decoration: none;		/* 링크의 밑줄표시 없앤다 */
	width: 120px;
	
	height: 30px;
	display: block;	/* 글자의 영역에만 가도 링크반응 표시 */
	background-color: Seagreen;
	test-align: center;
	color: white;
	font-weight: bold;
	margin: 0;
	text-shadow: 2px 2px 4px Orange;
	padding-top: 5px;
}

hr {clear: both; }		/* 박스이동처리한 float 속성을 해제한다 (제자리로 돌려놓는다! 꼭 해줘야함) */

</style>



</head><%-- 메뉴바이므로, 메인 타이틀과 중복될 수 있기 때문에 타이틀은 제거한다. --%>


<body>
<header>
	<h1 id = "logo">Spring project : first</h1>
	<%-- 로그인 안 한 경우 %-->
	<%--1) 일반적인 구문
		% if(loginMember == null){ %
		% }//if %  
	 el태그를 사용하면 임포트와 위같은 방식(get)이 필요없다 (sessionScope은 생략 가능)
	 --%>
	
	<%--2) el태그를 사용한 작성구문 방식 : c태그와 el을 함께 사용하는것이 원칙임 => [   c:if 속성 "구문"   ] 
			-> import와 get을 이 한 문장으로 모두 끝내는것임--%>
	<c:if test="${empty sessionScope.loginMember}"> 		<%-- sessionScope에 로그인정보가 empty 없으면 (로그인 안했다면)  --%>
		<ul id = "menubar">
			<%-- 스프링에서의 절대경로 표기법 :
					기존 => 		[  /context-root명  ]으로 표기
					el방식 => 	[  ${pageContext.servletContext.contextPath}  ] contextPath를 찾아서 get해오는 작업을 하는 구문임 (root가 바뀌어도 영향 없다)--%>
<%-- ★ 상대경로 : 현재 문서를 기준으로했을 때, 대상까지의 경로임
		★ 같은 폴더에 있을 때 =>  파일명.확장자,  폴더명/파일명.확장자
		★ 다른 폴더에 있을 때 =>  ./(현재폴더), ../(한단계 위 상위폴더)
		★ .do 요청은 무조건 '절대경로' 쓰기 --%>
			<li><a href="${pageContext.servletContext.contextPath}/nlist.do">공지사항</a></li>				<%-- /뒤에 작성하는 도메인명은 절대경로로 이동될 수 있도록 한다. --%>
			<li><a href="${pageContext.servletContext.contextPath}/blist.do?page=1">게시글</a></li>		<%-- 페이지단위로 표시되도록 함 => ?page=1 처럼, 쿼리문을 사용한다 --%>
			<li><a href="${pageContext.servletContext.contextPath}/test.do">test</a></li>
			<li><a href="${pageContext.servletContext.contextPath}/testLogin.do">소셜로그인</a></li>
			<li><a href="${pageContext.servletContext.contextPath}/moveAOP.do">AOP의개념</a></li>		<%-- AOP설명페이지로 이동 --%>
		</ul>
	</c:if>
	
	<%-- 로그인 한 경우 : 관리자인 경우 --%>
	<%-- 원래구문 예시
		<% 
			Member loginMember = (Member)session.getAttribute("loginMember");
			if(loginMember){ != null && loginMember.getAdmin().equals("Y")){
			}
	 --%>
	
	<c:if test="${!empty sessionScope.loginMember and loginMember.admin eq 'Y'}">  		<%-- 로그인 했다면 , eq: ==과 같은뜻임--%>
		<ul id = "menubar">
			<%-- 스프링에서의 절대경로 표기법 :
					기존 => 		[  /context-root명  ]으로 표기
					el방식 => 	[  ${pageContext.servletContext.contextPath}  ] contextPath를 찾아서 get해오는 작업을 하는 구문임 (root가 바뀌어도 영향 없다)--%>
			<li><a href="${pageContext.servletContext.contextPath}/nlist.do">공지사항관리</a></li>
			<li><a href="${pageContext.servletContext.contextPath}/blist.do?page=1">게시글관리</a></li>		
			<li><a href="${pageContext.servletContext.contextPath}/mlist.do">회원관리</a></li>
			<li><a href="${pageContext.servletContext.contextPath}/qnalist.do">QnA관리</a></li>
			<li><a href="${pageContext.servletContext.contextPath}/main.do">홈</a></li>		
		</ul>	
	</c:if>
	
	<%-- 로그인 한 경우 : 일반회원의 경우 --%>
	<c:if test="${!empty sessionScope.loginMember and loginMember.admin ne 'Y'}">  		<%-- 로그인 했다면, ne: !=과 같은뜻임--%>
		<ul id = "menubar">
			<%-- 스프링에서의 절대경로 표기법 :
					기존 => 		[  /context-root명  ]으로 표기
					el방식 => 	[  ${pageContext.servletContext.contextPath}  ] contextPath를 찾아서 get해오는 작업을 하는 구문임 (root가 바뀌어도 영향 없다)--%>
			<li><a href="${pageContext.servletContext.contextPath}/nlist.do">공지사항</a></li>
			<li><a href="${pageContext.servletContext.contextPath}/blist.do?page=1">게시글</a></li>		<%-- 페이지단위로 표시되도록 함 => ?page=1 처럼, 쿼리문을 사용한다 --%>
			<li><a href="${pageContext.servletContext.contextPath}/qna.do">QnA</a></li>
			<li><a href="${pageContext.servletContext.contextPath}/potolist.do">사진게시글</a></li>
			<li><a href="${pageContext.servletContext.contextPath}/main.do">홈</a></li>		<%-- AOP설명페이지로 이동 --%>
		</ul>	
	</c:if>
	
</header>
</body>
</html>












