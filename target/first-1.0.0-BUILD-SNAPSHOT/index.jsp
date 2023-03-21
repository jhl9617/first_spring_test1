<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang = "ko">
<head>
<meta charset="UTF-8">
<title>first</title>
</head>
<body>

<%-- <h1>spring first project</h1> --%>
<%-- 첫페이지 접속요청이 오면, 실제 보여질 메인페이지를 포워딩함 --%>
<%--
	<%
	RequestDispatcher view = request.getRequestDispatcher("main.do");
	view.forward(request, response); /* main.do로 view를 보내겠음 => request, response를 보내겠음 */
	%> 
	★★★위 구문과 아래 코드는 같은 뜻임
--%>
<jsp:forward page="main.do"/> 		<%-- 메인이 구동될때 바로 view를 전달함 (view를 보내버린 후 접속되지않음. 진짜 welcome의 역할만 하고 끝) --%>

</body>
</html>