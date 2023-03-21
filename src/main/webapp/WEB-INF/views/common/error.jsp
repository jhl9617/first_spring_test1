<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true" %>		
    <%-- page 지시자 태그에서 에러를 담당하는 페이지라는것을 반드시 명시할 것 [ isErrorPage="true" ] 명시해야 jsp내장객체인 exception 객체를 사용할수있음 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%-- page 지시자 태그 작성 --%>

<%-- 다른 페이지들에 jsp에서 에러가나면 자동으로 이 페이지로 넘어올 수 있도록 속성을 준다. => [ errorPage="경로주소" ] --%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<%-- 메뉴바이므로, 메인 타이틀과 중복될 수 있기 때문에 타이틀은 제거한다. --%>

</head>


<body>
<h1>에러 페이지</h1>

<%-- 	★에러메세지 출력을 담당하는 페이지 => 보통 에러는 2가지 경우가 있으므로, 2가지경우를 모두 처리해준다. 
		★page 지시자 태그에 isErrorPage="true"라고 지정하면, jsp 내장객체인 exception객체를 사용할 수 있게 됨 --%>
<%-- 1) 다른 jsp페이지에서 발생된 에러가 넘어오는 경우 --%>
<c:set var="e" value="<%= exception %>" />	 <!-- 전달할값이 없으면 닫기태그 생략 가능 -->
<%-- c태그 set은 교육자료 PDF 파일 -> 5_WEB_BACK_END -> 2_JSP -> MVC Architecture파일에 50page 이후 참고할것--%>
<c:if test="${ !empty e }">
	<h3> jsp 페이지오류 : ${ message }</h3>
</c:if>

<%-- 2) 컨트롤러에서 에러메세지와 내보낼 페이지로 리턴한 경우  --%>
<c:if test="${ empty e }">
	<h3> 컨트롤러 요청 실패 메세지 : ${ message }</h3>
</c:if>
<hr>

<%-- main.do를 연결한다 --%>
<%-- ★ c:url태그를 이용해서 링크(연결) 정보를 미리 변수로 지정해서 사용할 수 있음 // 전달할값이 없으면 닫기태그 생략 가능 
		★ jsp 페이지에서 컨트롤러를 요청할때는 반드시 context root에서 실행되도록 해야 함! (context root == content directory) // wepapp폴더에서 실행되도록 해야한다.  
		★ context : web application 을 의미함
			context root : first/src/main/webapp 을 의미함
		★ root에서 출발하는 경로를 웹에서 '절대경로'라고 함
			* taglib에서 절대경로는 /만 표기하면 됨
			* html, javascript에서의 절대경로는 => ${ pageContext.servletContext.contextPath } 로 표기함 
		--%>
<c:url var = "movemain" value="/main.do" /> <%-- 태그 라이브러리에서 절대경로는, 앞에 /만 붙이면 됨 => [  /main.do  ] --%>
<a href = "${ movemain }" >시작페이지로 이동</a>
<%-- ★ 상대경로 : 현재 문서를 기준으로했을 때, 대상까지의 경로임
		★ 같은 폴더에 있을 때 =>  파일명.확장자,  폴더명/파일명.확장자
		★ 다른 폴더에 있을 때 =>  ./(현재폴더), ../(한단계 위 상위폴더)
		★ .do 요청은 무조건 '절대경로' 쓰기
 --%>


</body>
</html>









