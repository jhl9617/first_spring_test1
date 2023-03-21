<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  <%--core라이브러리 사용하겠다는 선언 --%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<title>first in myinfoPage</title>
</head>


<body>
<h1 align = "center"> 마이페이지</h1>
<table id= "tbl" align="center" width="500" cellspacing="5" cellpadding="0" border="1">		
	<tr>
		<th width="120"> 이 름 </th>
			<%--
			만약 자바로 작업한다면? 작업코드
				<%@ page import="org.ict.first.member.model.vo.Member %>						1) 페이지 지시자로 임포트
				<% Member member = (Member)request.getAttirbute("member"); %>		2) 구문작성
				<td><%= member.getUsername() ></td>														3) getter로 값을 꺼낸다
			★★위 3개의 구문을 아래 1줄로 작성할 수 있음. (td태그 1줄)
			 --%>
			<td>  ${ requestScope.member.username }  </td>			<%-- [   requestScope.  ] 는 생략가능 --%>
	</tr>					
	<tr>
		<%-- 프라이머리 키로 설정해두었으므로, 중복확인해야함 --%>
		<th width="120"> 아이디</th>
			<td> ${ requestScope.member.userid } 	</td>
	</tr>		
	<tr>			
		<th width="120"> 나 이 </th>
			<td> ${ requestScope.member.age }  </td>
	</tr>					
	<tr>
		<th width="120" > 성 별 </th>
			<td> 
				 <c:if test= "${ member.gender eq 'M'}"> 			<%-- gender가 M이 선택되었다면, // eq뜻 :  == --%>
				 	남자
				</c:if>
				 <c:if test= "${ member.gender eq 'F' }"> 			<%-- gender가 F이 선택되었다면 --%>
				 	여자
				</c:if>
			</td>
			
	</tr>					
	<tr>
		<th width="120"> 전화번호 </th>
			<td> ${ requestScope.member.phone }	</td> 
	</tr>					
	<tr>
		<th width="120"> 이메일	</th>
			<td> ${ requestScope.member.email }	</td>
	</tr>	
	<tr>
		<th colspan="2">
			<%--변수를 이용해서 URL이동 처리하는 방법 확인용 // xml코드이므로, xml코드 규칙을 따라야함. 띄어쓰기 조심할것 --%>
			<c:url var="moveup" value="/moveup.do">		<%-- 수정페이지로 이동하기 --%>
				<c:param name="userid" value="${member.userid}"/>
			</c:url>
			<a href= "${moveup}">수정페이지로 이동 </a> &nbsp;
			<c:url var="mdel" value="mdel.do">	 		<%--탈퇴하기	--%>
				<c:param name="userid" value="${member.userid}"/>
			</c:url>
			<a href= "${mdel}">탈퇴하기</a> &nbsp;
			<a href= "main.do">시작페이지로 이동</a>
		</th>
	</tr>	
	
</table>
<hr>


<footer>
<hr>
<c:import url="/WEB-INF/views/common/footer.jsp" />
</footer>

</body>
</html>








