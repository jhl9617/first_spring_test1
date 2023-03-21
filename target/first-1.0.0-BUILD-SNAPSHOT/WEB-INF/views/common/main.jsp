<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="error.jsp" %>
    <%-- jsp에서 에러가나면 자동으로 이 페이지로 넘어올 수 있도록 속성을 준다. => [ errorPage="경로주소" ]
    	errorPage와 main페이지가 같은폴더에 있으므로, 그냥 error.jsp라고 이름만 써주면 됨--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%-- 페이지 지시자 태그임 //  절대안바뀌는 구문임! jstl core에서 사용하는 c태그를 이 문서에서 사용하겠다고 선언함 // JSPL홈페이지 core 에서 사용 구문 복붙해오면 됨 --%>


<!DOCTYPE html>
<html lang = "ko">
<head>
<meta charset="UTF-8">
<title>first main page</title>

<style type="text/css">
div.lineA {
	height: 100px;
	border: 1px solid Royalblue;
	float: left;		/* 배너이미지 옆에 로그인폼이 나올 수 있도록 위치 배치 설정함 */
	position: relative; 	/* 상대위치를 기준으로 잡겠다(원래태그가 표시되는 부분 기준)  */
	left: 100px;
	margin: 5px;
	padding: 5px;
}

div#banner {
	width: 650px;
	padding: 0;
}

div#banner img {
	margin: 0;		/* 그림 바깥여백 없애기 */
	padding: 0;		/* 그림 안쪽여백 없애기 */
	width: 650px;
	height: 110px;
}

div#loginBox {
	width: 274px;
	font-size: 9pt;
	text-align: left;	
	padding-left: 20px;
}


div#loginBox button {
	width: 250px;
	height: 35px;
	background-color: Royalblue;
	color: Papayawhip;
	margin-top: 10px;
	margin-bottom: 15px;
	font-size: 14pt;
	font-witght: bold;
}

section {
	position: relative;
	left: 100px;
}

section>div {		/* section바로밑에있는 div태그에 적용한다 */
	width: 360px;
	background-color: Royalblue;
}

section div table {
	width: 350px;
	background: Moccasin;
}


</style>

<script type="text/javascript" src="${ pageContext.servletContext.contextPath }/resources/js/jquery-3.6.3.min.js">	 </script>		<%-- jQuery 적용 --%>
<script type="text/javascript">
function movePage(){
	//버튼 클릭하면, 로그인페이지로 이동하는 컨트롤러를 요청함
	location.href="loginPage.do";
}//close

$(function() {
	/* 주기적으로 시간 간격을 두고 자동 실행되게 하려면 자바스크립트 내장함수 setInterval(실행시킬 함수명, 시간 밀리초); 사용하면 됨
		실행시킬 함수명 대신 콜백함수 function() {} 사용 가능 */
		
		//최근 공지글 5개 출력되게 함 : ajax 사용
		//body table(#newnotice)에 출력시킬 문장에 대한 문자열 준비
		var values = $('#newnotice').html();
		console.log("values : " + values);
		$.ajax({
			url : "ntop5.do",
			type : "post",
			dataType: "json",
			success : function (data){
				console.log("success : " + data );		//Object 로 출력
				
				//받은 object => string 으로 바꿈 
				var jsonStr = JSON.stringify(data);
				//string => json 객체로 바꿈
				var json = JSON.parse(jsonStr);
				
				//for in 문 : 인덱스 변수를 0에서 자동 1씩 증가시키는 루프문
				for(var i in json.list) {
					values += "<tr><td>" + json.list[i].noticeno
									+ "</td><td><a href='ndetail.do?noticeno=" 
									+ json.list[i].noticeno +"'>" 
									+ decodeURIComponent(json.list[i].noticetitle).replace(/\+/gi, " ")		//replace(/\+/gi, " ") -> \ 문자를 공백" "으로 바꾸기 
									+ "</a></td><td>" + json.list[i].noticedate
									+ "</td></tr>";
				}	//for in
				
				$('#newnotice').html(values);
			},
			error : function(jqXHR, textStatus, errorThrown){
				console.log("ntop5.do error" + jqXHR + ", " + textStatus +", " + errorThrown );
			}
		});	//ntop5 ajax
		
		
		//조회수 많은 인기 게시글(원글) 5개 조회 출력 처리 : ajax  사용
		$.ajax({
			url : "btop5.do",
			type : "post",
			dataType: "json",
			success : function (data){
				console.log("success : " + data );		//Object 로 출력
				
				//받은 object => string 으로 바꿈 
				var jsonStr = JSON.stringify(data);
				//string => json 객체로 바꿈
				var json = JSON.parse(jsonStr);
				
				var bvalues = $('#toplist').html();
				
				//for in 문 : 인덱스 변수를 0에서 자동 1씩 증가시키는 루프문
				for(var i in json.list) {
					bvalues += "<tr><td>" + json.list[i].board_num
									+ "</td><td><a href='bdetail.do?board_num=" 
									+ json.list[i].board_num +"'>" 
									+ decodeURIComponent(json.list[i].board_title).replace(/\+/gi, " ")		//replace(/\+/gi, " ") -> \ 문자를 공백" "으로 바꾸기 
									+ "</a></td><td>" + json.list[i].board_readcount
									+ "</td></tr>";
				}	//for in
				
				$('#toplist').html(bvalues);
			},
			error : function(jqXHR, textStatus, errorThrown){
				console.log("ntop5.do error" + jqXHR + ", " + textStatus +", " + errorThrown );
			}
		});
		
		
		
		
});	//document ready

</script>




</head>

<body>
<%--
	<%@ include file="menubar.jsp" %>	=> 같은폴더 안에 있으므로, 바로 경로 지정할 수 있음  --%>
<c:import url="/WEB-INF/views/common/menubar.jsp" />
<hr>

<center>
	<%-- 	배너 이미지 표시	
			절대경로 표기 :
			jstl 표기 : /
			el 표기 : ${ pageContext.servletContext.contextPath} --%>
	<div id = "banner" class="lineA">
		<img src="${pageContext.servletContext.contextPath}/resources/images/photo2.jpg">		<%-- 될수있으면 절대경로 쓸것 --%>
	</div>
	<%-- 로그인 영역 표시 --%>
	<%-- 1) 로그인 안 했을 때 : 세션 객체 안에 loginMember가 없다면, --%>
	<c:if test="${empty sessionScope.loginMember}">
		<div id="loginBox" class="lineA">
			first 사이트 방문을 환영합니다.<br>
			<button onclick="movePage()">로그인하긔 ٩(•ิ˓̭ •ิ )ง</button>
			<br>
			<a href ="#">아이디/비밀번호 조회</a>	 &nbsp; &nbsp;
			<a href ="${pageContext.servletContext.contextPath}/enrollPage.do">회원가입</a>
		</div>
	</c:if>
	
	<%-- 2) 로그인 했을 때 : 일반회원인 경우 --%>
	<c:if test="${ !empty sessionScope.loginMember and loginMember.admin ne 'Y'}">	<%-- ne : !=의 의미 --%>
		<div id="loginBox" class="lineA">
			${ loginMember.username} 님 <br>		<%--loginMember.username => getter 작용함  --%>
			<button onclick="javascript:location.href='logout.do';">로그인 ٩(•ิ˓̭ •ิ )ง</button>		<%-- 간단한함수는 코드를 "" 안에 바로 써도 됨 // location=> 위치지정태그(a태그의 역할과 같음) --%>
			<br>
			<a href ="#"> 쪽지</a>	&nbsp; &nbsp;
			<a href ="#"> 메일</a>	&nbsp; &nbsp;
			<%-- 마이페이지 클릭시, 연결대상과 전달값 지정함 --%>
			<c:url var="callMyInfo" value="/myinfo.do" >															<%-- controller에서 여기로 (myinfo.do) 연결함 --%>
				<c:param name="userid" value="${ sessionScope.loginMember.userid }" />   			<%-- [  sessionScope.   ]  는 생략가능 --%>
			</c:url>
			<a href ="${callMyInfo}">My Page</a>
		</div>	
	</c:if>

	<%-- 3) 로그인 했을 때 : 관리자인 경우 --%>
		<c:if test="${ !empty sessionScope.loginMember and loginMember.admin eq 'Y'}">		<%-- eq : ==의 의미 --%>
		<div id="loginBox" class="lineA">
			${ loginMember.username} 님 <br>		
			<button onclick="javascript:location.href='logout.do';">로그아웃</button>		
			<br>
			<a href ="#"> 관리페이지로 이동</a>	&nbsp; &nbsp;
			<%-- 마이페이지 클릭시, 연결대상과 전달값 지정함 --%>
			<c:url var="callMyInfo" value="/myinfo.do" >									<%-- controller에서 여기로 (myinfo.do) 연결함 --%>
				<c:param name="userid" value="${ loginMember.userid }" />
			</c:url>
			<a href ="${callMyInfo}">My Page</a>
		</div>	
	</c:if>
</center>
<hr>

<section>
	<%-- 최근 등록 신규 공지글 5개 조회 출력 : ajax  --%>
	<div style="float:left; border: 1px solid navy; padding: 5px; margin:5px;">
		<h4> 최근 공지글</h4>
		<table id="newnotice" border="1" cellspacing="0">
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>날짜</th>
			</tr>
		</table>
	</div>
	
	<%-- 조회수 많은 게시글 5개 조회 출력 : ajax  --%>
	<div style="float:left; border: 1px solid Midnightblue; padding: 5px; margin:5px;">
		<h4> 인기 게시글</h4>
		<table id="toplist" border="1" cellspacing="0">
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>조회수</th>
			</tr>
		</table>		
	</div>
	

</section>

<hr>
<c:import url="/WEB-INF/views/common/footer.jsp" />


</body>
</html>











