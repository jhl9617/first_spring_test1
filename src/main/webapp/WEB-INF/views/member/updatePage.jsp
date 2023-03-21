<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  <%--core라이브러리 사용하겠다는 선언 --%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Update</title>

<script type="text/javascript" src="${ pageContext.servletContext.contextPath }/resources/js/jquery-3.6.3.min.js" }></script>
<%-- javascript 함수 사용 구문임 --%>
<script type="text/javascript">



//★유효성검사★  전송 보내기전 입력값들이 유효한지 검사
	function validate(){
		//[[ 암호확인의 포커스가 사라질 때 ]] 암호와 암호확인이 일치하는지 체크함 (밑에 두개 중 편한걸로 쓰면 됨)
		var pwd1 = document.getElementById("upwd1").value;		//자바코드임
		var pwd2 = $("#upwd2").val();											//jQuery코드임
		
		if(pwd1 !== pwd2) {		//pwd1과 pwd2 값이 다르다면,
			alert ("암호와 암호확인이 일치하지 않습니다.\n다시 입력해주세요(ﾉ›_‹)ﾉ");
			document.getElementById("upwd1").select();			//다시 입력하도록 함
		}//if
	}//close
</script>
</head>




<body>
<h1 align = "center"> 회원 정보 수정 페이지</h1>
<br>
<%-- DB전송을 위한 form태그 준비  --%>
<form action="mupdate.do" method="post" onsubmit="return validate();">			<%-- 이 form의 수정정보를 받을 mupdate.do 준비  --%>
<table id= "tbl" align="center" width="500" cellspacing="5" cellpadding="0" border="1">		
	<tr>
		<th width="120"> 이 름 </th>
			<td><input type="text"  name="username" value="${ requestScope.member.username }" readonly>  </td>			<%-- [  readonly  ] 하면 클릭안되고, 수정 불가됨 (읽기전용) --%>
	</tr>					
	<tr>
		<th width="120"> 아이디</th>
			<td> <input type="text"  name="userid" value="${ requestScope.member.userid }" readonly>	</td>
	</tr>		
	<tr>
		<th width="120"> 비밀번호</th>
			<td> <input type="password" name="userpwd" id="upwd1" value="${member.userpwd}"> 	</td>
	</tr>					
	<tr>
		<th width="120"> 비밀번호확인 </th>
			<td> <input type="password" id="upwd2" onblur="validate();"> 	</td>
	</tr>			
	<tr>			
		<th width="120"> 나 이 </th>
			<td><input type="number"  name="age" value=" ${ requestScope.member.age }" min="19">  </td>
	</tr>					
	<tr>
		<th width="120" > 성 별 </th>
			<td> 
				 <c:if test= "${ member.gender eq 'M'}"> 		
				 	<input type="radio" name="gender" value="M" checked> 남자
				 	<input type="radio" name="gender" value="F"> 여자
				</c:if>
				 <c:if test= "${ member.gender eq 'F' }"> 			<%-- gender가 F이 선택되었다면 --%>
				 	<input type="radio" name="gender" value="M"> 남자
				 	<input type="radio" name="gender" value="F" checked> 여자
				</c:if>
			</td>
	</tr>					
	<tr>
		<th width="120"> 전화번호 </th>
			<td> <input type="tel"  name="phone" value="${ requestScope.member.phone }">	</td> 
	</tr>					
	<tr>
		<th width="120"> 이메일	</th>
			<td> <input type="email"  name="email" value="${ requestScope.member.email }"> </td>
	</tr>	
	
	<tr>
		<th colspan="2">
			<%--변수를 이용해서 URL이동 처리하는 방법 확인용 // xml코드이므로, xml코드 규칙을 따라야함. 띄어쓰기 조심할것 --%>
			<input type="submit" value="수정하기( ੭ ･ᴗ･ )੭">&nbsp;
			<input type="reset" value="수정취소 ٩(•ิ˓̭ •ิ )ง">&nbsp;
			<a href= "javascript:history.go(-1);"><br>이전 페이지로 이동 </a>			<%--javascript:history.go(-1); => 접속한 히스토리를 기준으로, 이전 페이지 이동하는 코드--%>
			<a href= "main.do">시작페이지로 이동</a>
		</th>
	</tr>	
	
</table>
</form>
<hr>


<footer>
<hr>
<c:import url="/WEB-INF/views/common/footer.jsp" />
</footer>

</body>
</html>