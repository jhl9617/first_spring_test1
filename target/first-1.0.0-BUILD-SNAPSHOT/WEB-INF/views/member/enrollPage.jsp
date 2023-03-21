<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  <%--core라이브러리 사용하겠다는 선언 --%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>first insert page</title>
<style type="text/css">
table th { background-color: Lightpink; }
table#tbl { border: 2px solid Indigo; }	 
</style>

<%-- jQuery 사용하겠다는 선언 --%>
<script type="text/javascript" src="${ pageContext.servletContext.contextPath }/resources/js/jquery-3.6.3.min.js" }></script>
<%-- javascript 함수 사용 구문임 --%>
<script type="text/javascript">



//★유효성검사★  전송 보내기전 (submit 버튼 클릭시) 입력값들이 유효한지 검사
	function validate(){
		//암호와 암호확인이 일치하는지 체크함 (밑에 두개 중 편한걸로 쓰면 됨)
		var pwd1 = document.getElementById("upwd1").value;		//자바코드임
		var pwd2 = $("#upwd2").val();											//jQuery코드임
		
		if(pwd1 !== pwd2) {		//pwd1과 pwd2 값이 다르다면,
			alert ("암호와 암호확인이 일치하지 않습니다.\n다시 입력해주세요(ﾉ›_‹)ﾉ");
			document.getElementById("upwd1").select();			//다시 입력하도록 함
			return false;				//false로 끝내서, 입력값을 DB로 전송 X
		}//if
		return true;			//pwd1과 pwd2 값이 맞다면 DB로 전송함
	}//close
	
	
	
	
	//아이디 중복을 확인하기위한 ajax 요청 처리용 함수
	//ajax(Asynchronous Javascript And Xml) :  페이지를 바꾸거나 새로고침하지 않고, 서버와 통신하는 기술임 => 서버로 서비스 요청하고 결과받음
	function dupCheckId() {	//클릭이벤트가 다른 클릭들에 영향가지않도록 클릭설정 해제하는 메소드임 (따라서 return값이 false로 해줘야함)
		$.ajax({
			url: "idchk.do",
			type: "post",   		//전송방식 생략하면 기본이 get임 // id는 url에 기록되거나 기억되지 않도록 하기위해 post방식을 사용함
			data: { userid: $("#userid").val()},			//이름을 선택하겠다 => $() 제이쿼리 선택자를 통해 선택함! //#userid => userid에 기록된 // .val() => 입력된 값

			success: function (data){						//성공했을때의 callback	// data 입력값을 받아서 리턴함
				console.log("success : " + data);			//입력값 중도 확인용 코드
				if(data == "ok"){
					alert("사용 가능한 아이디입니다! (*●⁰ꈊ⁰●)ﾉ");
					$("#upwd1").focus();					//사용가능한 아이디가 맞다면, 다음칸(upwd1)으로 커서 포인트를 옮겨라
				}else{
					alert("이미 사용중인 아이디입니다 •᷄⌓•᷅ \n다시 입력해주세용~");		//실패했을때의 callback
					$("#userid").select();					//이미 있는 아이디라면, userid를 다시 선택하도록 한다
				}//if
			},
			
			error: function(jqXHR, textStatus, errorThrown){		//에러발생시의 callback
				console.log("error발생했읍니다ʅ(｡◔︎‸◔︎｡)ʃ : " + jqXHR + ", " + textStatus + ", " + errorThrown);		//에러메세지만 콘솔에 출력하고 끝냄
			}
		});
		return false; 		
	}//close
</script>


</head>


<body>
<h1 align="center">회원가입 페이지</h1>
<br>

<form  action = "enroll.do" method="post" onsubmit="return validate();">
							<%-- enroll.do를 찾아서 전송시켜라 [  action = "enroll.do" ]--%>
							<%-- submit을 작동시켜라, validate()가 true이면 [  onsubmit="return validate();" ]--%>
<table id= "tbl" align="center" width="500" cellspacing="5" cellpadding="0">		
	<tr>
		<th colspan="2">
		회원정보를 입력해 주세요. <br>(*표시는 필수입력 항목입니다.)
		</th>
	</tr>					
	<tr>
		<th width="120"> * 이 름 </th>
			<td> <input type="text" name="username" required> 	</td>
	</tr>					
	<tr>
		<%-- 프라이머리키로 설정해두었으므로, 중복확인해야함 --%>
		<th width="120"> * 아이디</th>
			<td> <input type="text" name="userid" id="userid" required> &nbsp; &nbsp;
					<input type="button" value="중복확인" onclick="return dupCheckId()">	
									<%-- 다른 버튼들에 영향이가지 않도록 false로 리턴받는 함수를 별도 작성 필요--%>
			</td>
	</tr>					
	<tr>
		<th width="120"> * 비밀번호</th>
			<td> <input type="password" name="userpwd" id="upwd1" required> 	</td>
	</tr>					
	<tr>
		<th width="120"> * 비밀번호확인 </th>
			<td> <input type="password" id="upwd2" required> 	</td>
	</tr>					
	<tr>
		<th width="120"> * 나 이 </th>
			<td> <input type="number" min="19" value="19" name="age" required> 	</td>
			<%-- 넘버는 스핀버튼이 제공되므로, 최소값&최대값 정해도 됨 // value는 기본값 지정임--%>
	</tr>					
	<tr>
		<th width="120"> * 성 별 </th>
			<td> <input type="radio" name="gender" value="M" checked> 남자 &nbsp;
					<input type="radio" name="gender" value="F"> 여자 &nbsp;
			</td>
	</tr>					
	<tr>
		<th width="120"> 전화번호 </th>
			<td> <input type="tel" name="phone" placeholder="-빼고 입력" > 	</td> 
			<%-- placeholder: 입력전에 흐릿하게 작성되어있는 내용 --%>
	</tr>					
	<tr>
		<th width="120"> * 이메일	</th>
			<td> <input type="email" name="email" required> 	</td>
	</tr>	
	<tr>
		<th colspan="2"> 
			<input type="submit" value="가입하기"> &nbsp;
			<input type="reset" value="작성취소"> &nbsp;
			<a href="main.do" >
			 <br>	시작페이지로 이동
			 </a>
		</th>
	</tr>					
	
</table>
</form>

<footer>
<hr>
<c:import url="/WEB-INF/views/common/footer.jsp" />
</footer>



</body>


</html>













