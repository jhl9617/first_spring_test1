<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Login</title>
<style type="text/css">
h1 {
	font-size: 48pt;
	color: Olivedrab;
}

div {
	width: 500px;
	height: 150px;
	border: 2px solid Olivedrab;
	position: relative;
	left: 300px;
}

div form {
	font-size: 16pt;
	color: Olivedrab;
	font-weight: bold;
	margin: 10px;
	padding: 10px;
}

div#loginForm form input.pos {		
	width: 300px;
	height: 25px;
	position: absolute;
	left: 120px;
	
}

div#loginForm form input[type=submit] {
	margin: 10px;
	width: 250px;
	height: 40px;
	position: absolute;
	left: 120px;
	background: Peachpuff;
	color: Midnightblue;
	font-size: 16pt;
	font-weight: bold;
}


</style>


</head>


<body>
<h1 align="center">first 로그인</h1>
<div id = "loginForm">
	<form action="login.do" method="post">
		<label>아이디 : <input type="text" name="userid" class="post"> </label>
		<br>
		<label>암호 : <input type="password" name="userpwd" class="post"> </label>
		<br>
		<input type="submit" value="로그인">
		
		
	</form>

</div>
<hr>
<!-- 카카오 로그인 창으로 이동 -->
<!-- 네이버 로그인 창으로 이동 -->
<!-- 구글 로그인 창으로 이동 -->

</body>
</html>











