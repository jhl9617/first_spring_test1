<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>		<%-- JSTL core를 쓰겠다는 선언 --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>		<%-- JSTL fmt를 쓰겠다는 선언 --%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>		<%-- JSTL functions를 쓰겠다는 선언 --%>


<%-- 검색필터링도 모두 가능하게 하는 페이지임 --%>
<%-- 여기서 처리요청하는 값들은 MemberController로 보내져서, Controller의 메소드에서 처리한다 --%>


<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>first List-All</title>
<style type="text/css">
form.sform {						/* 검색 폼 */
	display: none;				/* 처음엔 안보이게 함 */
	background: Antiquewhite;
}

</style>

<script type="text/javascript" src="${ pageContext.servletContext.contextPath }/resources/js/jquery-3.6.3.min.js">	 </script>		<%-- jQuery 적용 --%>
<script type="text/javascript">
	//jQuery로 이벤트처리 : 검색 form을 보이게 & 안보이게 처리한다
	/* ★태그의 이벤트 적용방식은 여러가지가 있음
		1) <태그명 on이벤트="함수명();">
		2) window.onload = function() { 이벤트 적용과 작동 코드 작성};
		== 동일함 =>	$(function(){ 이벤트 적용과 작동 코드 작성}); 
	*/
	$(function(){
		//$('선택자').이벤트함수(function(){ 실행코드});
		//$('선택자').on('이벤트종류', function(){ 실행코드});
		//=> 작성한 이벤트 실행코드는, 이벤트가 발생 전까지는 대기상태임
		
		//검색할 항목을 선택하면, 해당항목에 대한 폼이 보여지게함. (보여지고 있는 다른폼은 다시 안보일 수 있도록 바꿈)
		$("input[name=item]").on("change", function() {			//input태그 안에 name이 item인 태그들을 선택한다 // .on()으로 소괄호 안의 이벤트를 실행하겠다 
			//change 이벤트가 발생하면, 발생한 radio와 연결된 폼만 보여지게 하고, 나머지 폼들은 안보이게 처리함.
			$("input[name=item]").each(function(index){				//가져오는 값 => input[name=item] 이 리스트이기때문에, each()를 통해 for루프를 돌림
				if($(this).is(":checked")){
					$("form.sform").eq(index).css('display', 'block');			//block => 보여지게 해라
				}else{
					$("form.sform").eq(index).css('display', 'none');			//none => 바꾸지 말아라
				}//if
			}); //each
		});	//on
		
	});	//document ready
	
	
	// 로그인 제한&가능 여부에 radio의 'check'가 변경되었을 때, 실행되는 함수임 (체인지 이벤트가 발생하면)
	function changeLogin(element){
		//선택한 radio의 name속성의 이름에서 userid 분리 추출함 => [ name="login_ok_${m.userid}" ]의 login_ok_ 이후가 userid 이므로, 9번째글자부터가 userid임.
		var userid = element.name.substring(9);
		console.log("changeLogin : " + userid);				//아이디 분리추출되었는지 확인용
		
		if(element.checked == true && element.value == "N"){			//로그인 제한 체크했다면? => 해당 항목(element)이 체크되어있느냐(true) 그리고 그 값이 N 이라면,
			console.log("로그인 제한 체크함");
			location.href = "${pageContext.servletContext.contextPath}/loginok.do?userid=" + userid + "&login_ok=N";			//체크된 userid 에게 login_ok=N 값을 전달한다
		}else{
			console.log("로그인 가능 체크함");										//로그인 제한을 해제했다면? (로그인이 가능하다면?)
			location.href = "${pageContext.servletContext.contextPath}/loginok.do?userid=" + userid + "&login_ok=Y";			//체크된 userid 에게 login_ok=Y 값을 전달한다
		}//if
	}//method close
</script>
</head>


<body>
<c:import url="/WEB-INF/views/common/menubar.jsp" />
<hr>
<h1 align="center">회원 관리 페이지</h1>
<h2 align="center">현재 회원수 : ${ requestScope.list.size() }명 </h2>
<center>
	<button onclick="javascript:location.href='${pageContext.servletContext.contextPath}/mlist.do';">전체 보기</button>
	<br><br>
<%--항목별 검색기능 추가 --%>
	<fieldset id="ss">
		<legend>검색할 항목을 선택하세요.</legend>
		<input type="radio" name="item" id="uid">회원아이디 &nbsp;				<%-- 1개만 선택할 수 있도록 name 은 item이라고 통일함 (같은 item이라고 묶었기 때문에, 이중에 1개만선택가능함) --%>
		<input type="radio" name="item" id="ugen">성별 &nbsp;
		<input type="radio" name="item" id="uage">연령대 &nbsp;
		<input type="radio" name="item" id="uenroll">가입날짜 &nbsp;
		<input type="radio" name="item" id="ulogin">로그인 제한여부 &nbsp;
	</fieldset>
<%--검색 항목 제공 끝 --%>
	<br>
	<%--회원아이디로 검색 폼--%>
	<%--비슷한 여러개의 기능을 '하나의 .do ' 기능으로 사용할 수 있도록 함 --%>
	<form action="${pageContext.servletContext.contextPath}/msearch.do" method="post" id="idform" class="sform"> 
		<%-- msearch.do에서 여러개의 메소드를 실행하므로, 내가 지금 어떤메소드를 사용할지 알려주는 코드 작성이 필요함
				=> name에 힌트를 줌으로서, 이것이 어떤 메소드를 사용할지 알려줌 --%>
		<input type="hidden" name="action" value="id">	<%--action이 id라면 이것은 id검색이구나 알 수 있음  // [ input type="hidden"  ]  => 보내는 값을 숨겨두겠다 --%>
		<input type="search" name="keyword"> &nbsp;
		<input type="submit" value="검색">
	</form>


	<%--성별로 검색 폼--%>
	<form action="${pageContext.servletContext.contextPath}/msearch.do" method="post" id="genderform" class="sform"> 
		<input type="hidden" name="action" value="gender">		<%-- action이 gender라면 이것은 gender검색이구나 알 수 있음 --%>  
		<input type="radio" name="keyword" value="M">	남자 &nbsp;
		<input type="radio" name="keyword" value="F">	여자 &nbsp;
		<input type="submit" value="검색">
	</form>


	<%--연령대로 검색 폼--%>
	<form action="${pageContext.servletContext.contextPath}/msearch.do" method="post" id="ageform" class="sform"> 
		<input type="hidden" name="action" value="age">	
		<input type="radio" name="keyword" value="20">	20대 &nbsp;
		<input type="radio" name="keyword" value="30">	30대 &nbsp;
		<input type="radio" name="keyword" value="40">	40대 &nbsp;
		<input type="radio" name="keyword" value="50">	50대 &nbsp;
		<input type="radio" name="keyword" value="60">	60대이상 &nbsp;
		<input type="submit" value="검색">
	</form>


	<%--가입날짜로 검색 폼--%>
	<form action="${pageContext.servletContext.contextPath}/msearch.do" method="post" id="enrollform" class="sform"> 
		<input type="hidden" name="action" value="enroll">	
		<input type="date" name="begin"> ~ <input type="date" name="end"> &nbsp;
		<input type="submit" value="검색">
	</form>


	<%--로그인 제한여부로 검색 폼--%>
	<form action="${pageContext.servletContext.contextPath}/msearch.do" method="post" id="lokform" class="sform"> 
		<input type="hidden" name="action" value="login">	
		<input type="radio" name="keyword" value="Y">로그인 가능 회원 &nbsp;
		<input type="radio" name="keyword" value="N">로그인 제한 회원 &nbsp;
		<input type="submit" value="검색">
	</form>

</center>
<br><br>

<%--조회해온 회원 리스트 출력처리  
<%--파일 참고 =>  [ 	 file:///E:/5_WEB_BACK_END/2_JSP/pdf/MVC%20Architecture.pdf	]   72~85page--%>
<table align="center" border="1" cellsspacing="0" cellpadding="3">
	<tr>
		<th>아이디 </th>
		<th>이름 </th>
		<th>성별 </th>
		<th>나이 </th>
		<th>전화번호 </th>
		<th>이메일 </th>
		<th>가입날짜 </th>
		<th>마지막 정보수정날짜 </th>
		<th>로그인 제한여부 </th>
	</tr>
	<c:forEach items="${requestScope.list}" var="m">
	<%--날짜입력 파일 참고 =>  [ 	 file:///E:/5_WEB_BACK_END/2_JSP/pdf/MVC%20Architecture.pdf	]   96~page--%>
		<tr>
			<td>${m.userid}</td>
			<td>${m.username}</td>
			<td>${m.gender}</td>
			<td>${m.age}</td>
			<td>${m.phone}</td>
			<td>${m.email}</td>
			<td>		<%--	포메팅 적용	--%>
				<fmt:formatDate value="${m.enroll_date}" type="date" dateStyle="medium" />	
			</td>			
			<td>		<%--	포메팅 적용	--%>
				<fmt:formatDate value="${m.lastmodified}" type="date" dateStyle="medium" />	
			</td>
			<td>
				<c:if test="${m.login_ok eq 'Y'}">			<%-- 로그인 할 수 있는 회원이라면 --%>
					<%-- 같은값이 전송가는 상황이라면, 그 전송값이 누구의 전송값인지도 같이 보내줘야 함 (언더바 뒤에 표시해서 보냄) --%>
					<%--  checked onchange="changeLogin(this)"  ] => radio버튼에 change이벤트가 적용되면 (값이 바뀌었다면) changeLogin(this)함수를 실행한다. --%>
					<input type="radio" name="login_ok_${m.userid}" value="Y" onchange="changeLogin(this)" checked > 로그인가능★ &nbsp;
					<input type="radio" name="login_ok_${m.userid}" value="N" onchange="changeLogin(this)"> 로그인제한☆
				</c:if>
				<c:if test="${m.login_ok eq 'N'}">			<%-- 로그인 할 수 없는 회원이라면 --%>
					<input type="radio" name="login_ok_${m.userid}" value="Y" onchange="changeLogin(this)"> 로그인가능★ &nbsp;
					<input type="radio" name="login_ok_${m.userid}" value="N" onchange="changeLogin(this)" checked> 로그인제한☆
				</c:if>
			</td>
		</tr>
	</c:forEach>
</table>
<hr>

<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>












