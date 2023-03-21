package org.ict.first.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ict.first.common.SearchDate;
import org.ict.first.common.Searchs;
import org.ict.first.member.model.service.MemberService;
import org.ict.first.member.model.vo.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;



//★★★★★Dao로부터 받은 값을 처리(Servlet처리 및 url패턴처리)해서 page로 내보냄★★★★★
//★★★★★memberListView로부터 받은 값을 처리(Servlet처리 및 url패턴처리)해서  page로 내보냄★★★★★



//얘가 컨트롤러가 되려면, 반드시 맨앞에 @Controller라는 어노테이션을 붙여줘야 함  => xml에 자동등록됨 => servlet-context.xml 에 등록됨 (WEB-INF - spring - appServlet)
//자세한 설명 : E:\7_Spring\요약\스프링_1 => 6page부터 참고
// .do 로 오는 요청을 Controller에서 처리한다! (처리용 메소드들 작성함)
//★★★★★웹서비스 요청 하나당 메소드 하나씩 작성하는 방식임★★★★★

@Controller		//xml에 클래스를 controller로 자동 등록해줌
public class MemberController {
	
	//이 컨트롤러 클래스 안의 메소드들이 구동되었는지 확인하는 로그를 출력하기 위한 로그 객체를 생성
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired 											//자동 의존성 주입(DI 처리) : 자동 객체생성 됨 => memberService 클래스 Service어노테이션에 명시한 [ memberService ]를 가져와서 선언해준다. 
	private MemberService memberService;	//memberService랑 연결시켜줬음

	
	//뷰 페이지 이동 처리용 --------------------------------------------------------------------------------
	//로그인버튼을 누르면 view페이지를 연결시킨다. (로그인페이지 내보내기용 메소드) => @RequestMapping을 통해서 연결한 후 페이지주소를 리턴시키면 됨 
	@RequestMapping(value="loginPage.do", method= {RequestMethod.GET, RequestMethod.POST} ) 
									//url패턴 정해주기 => loginPage.do로 요청이오면, xml에서 연결시켜서 해당 메소드를 실행시킨다. (여기서 return한 페이지를 리턴한다.)
									//get 방식과, post방식 모두 받을 수 있다고 명시하는 방법임, 명시 없으면 get이 기본임
	public String moveLoginPage() {
		return "member/loginPage";
	}//method close
	
	//회원정보 수정페이지 내보내기용
	@RequestMapping(value="moveup.do", method= {RequestMethod.GET} )
	public String moveUpdatePage(@RequestParam("userid") String userid, //@RequestParam("userid") => userid로 온 값을 get parameter해라 => 값은 String userid에 넣어라 
												Model model) {		// view에 내보낼 정보는 model에 담는다.
		//받은 값(userid)로 다시 select 조회를 해와서 (다시 갖고와서) 처리한다. (실시간으로 현재 정보를 가져와서 처리하도록 함)
			//=> session에 담아서 그걸로 계속 활용해도 되지만, session이 자동 파기되는 경우도 있기때문에, 다시 조회하는 방식으로 한다.
		Member member = memberService.selectMember(userid);
		
		if(member != null) {
			model.addAttribute("member", member);
			return "member/updatePage";
		}else {
			model.addAttribute("message", userid + " : 회원조회 실패!");
			return "common/error";
		}//if
	}//method close
	
	
	
	//회원가입 페이지 이동 처리용 --------------------------------------------------------------------------------	
	//회원가입버튼을 누르면 views의 enrollPage.do페이지를 연결시킨다. => @RequestMapping을 통해서 연결한 후 페이지주소를 리턴시키면 됨
	@RequestMapping("enrollPage.do")
								//get 방식으로 가져올땐, [ value= ] 생략 가능
	public String moveEnrollPage() {
		return "member/enrollPage";		//enrollPage.do를 실행시킨다 (해당 페이지를 내보내는 명령을 함)
	}//method close
	
	
	
	
	

	//서비스와 연결되는 요청 처리용-----------------------------------------------------------------------
	//welcome file로 내보내면 됨 -> 이 프로젝트에서 웰컴파일은 main임
	
/* [[ 로그인처리용 처리용 메소드 :: 서블릿방식 작성 방법임 ]]
@RequestMapping(value="login.do", method=RequestMethod.POST) 	
									//url패턴 정해주기 // 여러속성을 쓸때는 꼭 [ value= ] 를 명시해줄 것. (value만 쓸때는 생략 가능함) 
									//post방식으로 전송왔을땐 method 속성을 추가해서, post방식임을 명시한다. (이렇게 했는데 get으로 보내면 405번 에러가 발생함=>전송방식 에러)
	
	public String loginMethod(HttpServletRequest request, HttpServletResponse response, Model model) {			//이렇게 작성해두면 서블릿의 do-get 방식과 다를바 없음
		//원래의 do-get작성방식의 1번(인코딩과정) 생략 : 인코딩 필터를 거쳐서 왔기때문에, 인코딩과정생략한다
		//마이바티스는 1개값만 전달할 수 있기때문에, 객체를 새로 만들어서 ID와 PWD 두개 값을 넣어준다.
		
		//1. 전송온 값 꺼내기
		String userid = request.getParameter("userid");
		String userpwd = request.getParameter("userpwd");
		Member member = new Member();
		member.setUserid(request.getParameter("userid"));
		member.setUserpwd(request.getParameter("userpwd"));
		
		//2. Service로 전송하고 결과 받기
		Member loginMember = memberService.selectLogin(member);
		
		//3. 받은 결과를 가지고, 성공&실패 페이지를 선택해서 리턴함
		if (loginMember != null) {
			//세션 생성
			HttpSession session = request.getSession();
			session.setAttribute("loginMember", loginMember);
			return "common/main";
		}else {
			model.addAttribute("message", "로그인 실패!");	//message라는 이름으로, 메세지 정보를 보낸다
			return "common/error";
		}//if
	}//method close
*/

	
	
	
/* [[ 로그인처리용 처리용 메소드 :: command 객체 사용 방법임 ]]		*/
	//서버로 전송온 parameter 값을 저장하는 객체를 commend 객체라고 함
	//input 태그의 name과 vo객체의 필드명이 같으면 됨.
	@RequestMapping(value="login.do", method= RequestMethod.POST ) 
	public String loginMethod(Member member, 
											HttpSession session, SessionStatus status, 
											Model model) {
			//vor객체 [ Member member ] 를  소괄호() 안에 작성해두면, 자동으로 command가 되고, [1. 전송온 값 꺼내기 ]처리를 이 1문장으로 모두 해줌.
			//세션 생성에 필요한 HttpSession SessionStatus, status을 소괄호 안에 작성해서 미리 생성 완료한다. // Model은 리퀘스트디스펙쳐의 역할을 함
		logger.info("login.do : \n" + member.toString());				//println의 역할을 함
		
		//서비스 모델로 전달하고 결과 받기
		Member loginMember = memberService.selectLogin(member);
		
		//받은값 처리하기
		if(loginMember != null) {
			session.setAttribute("loginMember", loginMember);  //세션에 저장한다
			status.setComplete();  		//로그인 요청 성공 => 200을 전송함
			return "common/main";
		}else{
			model.addAttribute("message", "로그인 실패 : 아이디나 암호를 확인해주세요<br>" + "또는, 로그인 제한된 회원인지 관리자에게 문의하세요.");	
			//메세지를 담아서 내보낸다
			return "common/error";
		}//if
	}//method close
	
	
	
	
	
	
	@RequestMapping("logout.do")		//전송방식이 get방식임 (a태그의 href로 받는 등)	=> "value=logout.do" 에서 value=는 생략 가능
	public String logoutMethod(HttpServletRequest request,		
												Model model) {		//request가 필요함 => get session이 필요하기 때문임	//Model은 리퀘스트 디스펙쳐의 역할을 함
		HttpSession session = request.getSession(false);
		logger.info("logout.do : \n" + session);		//정보를 콘솔에 출력하고싶다면 logger를 이용한다
		if(session != null) {		//로그인상태가 없지 않다면 => 로그인되어있다면
			session.invalidate();
			return "common/main";
		}else{			//로그인이 실패되어있다면,
			model.addAttribute("message", "로그인 세션이 존재하지 않습니다.");	
			//에러메세지를 담아 내보낸다 (앞에 쓰는 message는 바꿀 수 없음 고정임)
			return "common/error";
		}//if
	}//method close
	
	
	
	
	
	//ajax 통신으로 아이디 중복확인 요청 처리용 메소드
	//ajax는 뷰리졸브 거치지 않음(새로고침 안됨) => 별도의 출력스트림을 만들어서 작업처리함. (return시키면 안됨 => void)
	@RequestMapping(value="idchk.do", method=RequestMethod.POST)		//url패턴은 value로 명시하며 // post방식으로 전송왔을땐 method 속성을 추가해서, post방식임을 명시한다. 
	public void dupCheckIdMethod(@RequestParam("userid") String userid, HttpServletResponse response) throws IOException {		
		//request와 get parameter 사용하는 어노테이션인 () 소괄호 안에 직접 작성해서 처리// 뒤에는 그것을 받아줄 변수 작성한다. (parameter : 매개변수라는 뜻임)
		//HttpServletResponse : 값을 저장하고 웹에 응답을 돌려줄 객체 생성
		int idCount = memberService.selectDupCheckId(userid);
		
		String returnStr = null;		//받아줄 string 객체인 returnStr을 만들어서 담아주고, 해당 값을 리턴할꺼임
		if(idCount == 0) {
			returnStr = "ok";
		}else{
			returnStr = "duble";
		}//if
		
	//response를 이용해서 클라이언트와 출력스트림을 연결하고 값 보냄
	response.setContentType("text/html; charset=UTF-8"); 		//인코딩처리 //오타내면 안됨..오타내면 자꾸 파일 넣는 창이 뜰것임
	
	//PrintWriter를 사용함 : 나갈때는 문자스트림인데, 실제로는 바이트스트림으로 연결되어있는 보조스트림
	//getWriter를 사용해서 문자를 받는다
	//append()를 사용해서 문자를 내보낸다
	//flush를 통해 밀어내고, close로 닫는다
	//에러처리는 throws 처리해서 다른데로 내보내버리긔 (여기는 ajax처리하는 구문이기 때문에, 여기서 에러를 처리할 수 없음=> enrollPage 에러구문에서 error처리할꺼임)
	PrintWriter out = response.getWriter();
	out.append(returnStr);
	out.flush();
	out.close();
	}//method close
	
	
	
	
	
	//회원가입 요청을 처리하는 용도의 메소드
	@RequestMapping(value="enroll.do", method=RequestMethod.POST)
	public String memberInsertMethod(Member member, Model model) {		//메소드이름은 내맘대로 작성	//에러메세지 출력을 위해 Model 준비
		logger.info("enroll.do에 잘 담겼는지 확인용(controller) : \n" + member);		//멤버에 잘 담겼는지 확인용
		
		if( memberService.insertMember(member)> 0) {	
						//회원가입을 성공했다면?
			return "common/main"; 		//리턴할 url패턴 리턴
		}else{			
						//회원가입을 실패했다면?
			model.addAttribute("message", "회원가입 실패! 요청사항을 다시 확인해주세요!");			//에러는 model의 addAttribute를 이용함
			return "common/error";
		}//if
	}//method close
	
	
	
	
	
	
	//마이페이지 클릭시 '내 정보 보기' 요청 처리용 메소드
	//리턴타입은 String, ModelAndView를 사용할 수 있음
	//Model을 사용해서 리턴하는 방법임 (ModelAndView) => 모델과 view를 합쳐놓은 형식임
	@RequestMapping(value="myinfo.do", method=RequestMethod.GET)		//get방식은 기본이므로, 구문 생략할수있음
	public ModelAndView memberDatailMethod(@RequestParam("userid") String userid,			//[  @RequestParam("userid") ]  어노테이션을 통해 값을 꺼내고, 객체 String userid에 저장한다
																		ModelAndView mv) {			//()소괄호안에 바로 ModelAndView객체생성(리턴용 객체)한다
		//서비스로 아이디 전달하고, 해당 회원정보 받기
		Member member = memberService.selectMember(userid);				// => MemberService 인터페이스로 연결시킴
		
		if(member != null) {
			mv.addObject("member", member);				//꺼내서 저장함 (get / set)
			//Model 또는 ModelAndView에 저장하는 것은 [  request.setAttribute("member", member); ] 과 같음
			mv.setViewName("member/myinfoPage");		//myinfoPage페이지로 이동
		}else{
			mv.addObject("message", userid + " : 회원정보 조회에 실패했습니다.");
			mv.setViewName("common/error");
		}//if
		return mv;
	}//method close

	
	
	
	
	//회원탈퇴 (삭제)요청 처리용  메소드
	//삭제일때는 따로 정보를 보낼필요가 없음 (자동 로그아웃 처리) => String을 리턴한다.
	@RequestMapping("mdel.do")
	public String memberDeleteMethod(@RequestParam("userid") String userid, 
															Model model) {
		logger.info("mdel.do 확인용 : \n" + userid);
		
		if(memberService.deleteMember(userid) > 0) {			//회원 탈퇴 성공했을때 (자동 로그아웃 처리해야함) 
			return "redirect:logout.do"; //Controller메소드에서 다른 Controller메소드 호출할 수 있음 (앞에  [    redirect:  ] 를 붙여준다
		}else {		//회원 탈퇴 실패했을때
			model.addAttribute("message", userid + " : 회원 삭제 실패! 요청사항을 다시 확인해주세요!");			//이 메세지를 message에 담아서 리턴함
			return "common/error";
		}//if
	}//method close
		
	
	
	
	
	//회원정보수정 요청 처리용 메소드
	//수정 성공시 myinfoPage.jsp로 이동함 (수정한 내용으로 보여져야 함)
	/* 
	* [[ 로그인처리용 처리용 메소드 :: command 객체 사용 방법 ]]		
	* 서버로 전송온 parameter 값을 저장하는 객체를 commend 객체라고 함
	* input 태그의 name과 vo객체의 필드명이 같으면 됨.	*/
	@RequestMapping("mupdate.do")
	public String memberUpdateMethod(Member member,Model model ) {			//Commend 객체 사용함
		logger.info("mupdate.do : " + member); 				//데이터 입력값이 잘 넘어갔는지 log로 확인하는 용도
		if(memberService.updateMember(member) > 0) {		//수정이 성공했다면?		=> Controller의 다른 Method를 직접 호출할 수 있음.
			return "redirect:myinfo.do?userid=" + member.getUserid();		
			//myinfo.do 를 사용하려면, userid를 전달해주어야 함 (필요시 '쿼리 스트링'을 사용함) => [   ?이름=값&이름=값   ] => 여러개일때  '&'로 콤마를 대신함
		}else {																		//수정이 실패했다면?
			model.addAttribute("message", member + " : 회원 정보 수정 실패! 요청사항을 다시 확인해주세요!");
			return "common/error";
		}
	}//method close
	
	
	
	
	
	
	//회원관리용 회원전체목록 조회처리용
	@RequestMapping("mlist.do")
	public String memberListViewMethod(Model model) {		//담아낼 Model 객체 준비함
		ArrayList<Member> list = memberService.selectList(); //member만 저장된 ArrayList 준비
		
		if(list.size() > 0 && list != null) {		//list의 size가 0보다 크다면 (조회된 목록 정보가 있다면) or 값이 null 이라면
			model.addAttribute("list", list);
			return "member/memberListView";
		} else{
			model.addAttribute("message", "회원 정보가 존재하지 않습니다.");
			return "common/error";
		}//if
	}//method close
	
	
	
	
	
	//관리자기능 : 회원 로그인 제한&가능 처리용 메소드
	@RequestMapping("loginok.do") 
	public String changeLoginOKMethod(Member member, Model model) {			//commend객체를 이용해서 바로 받는다 (member)
		logger.info("loginok.do작동완료 : " + member.getUserid() + ", " + member.getLogin_ok());		//값 잘 받아왔는지 확인용
		
		if (memberService.updateLoginok(member) > 0) {		//updateLoginok 값 수정에 성공 했다면
			return "redirect:mlist.do";				// mlist.do가 실행됨 (수정된값으로 실시간으로 다시 조회해옴)
		}else {
			model.addAttribute("message", "로그인 제한/허용 처리 오류 발생!");
			return "common/error";
		}//if
	}//method close
	
	
	
	
	
	
	//회원 검색 처리용
	@RequestMapping(value="msearch.do", method=RequestMethod.POST)
		//값이 여러개가 오기 때문에 HttpServletRequest을 통해 각각의 값을 직접 꺼낼 필요가 있음
	public String memberSearchMethod(HttpServletRequest request, Model model) {	
		//전송온 값 꺼내기
		String action = request.getParameter("action");		//action으로 온 값을 action변수에 담아라
		String keyword = null;												//값 꺼내서 담을 keyword 변수 준비
		String beginDate = null;											//값 꺼내서 담을 beginDate 변수 준비
		String endDate = null;												//값 꺼내서 담을 endDate 변수 준비
		
		//전송오는 값을, 액션에 따른 값(value)별로 나누어서, 변수들에 각각 담아줌 (String타입임)
		if(action.equals("enroll")) {
			beginDate = request.getParameter("begin");
			endDate = request.getParameter("end");
		}else{
			keyword = request.getParameter("keyword");
		}//if
		
		//서비스메소드가 리턴하는 값을 받을 리스트를 준비한다
		ArrayList<Member> list = null;
		Searchs searchs = new Searchs();
		switch (action) {
		case "id" : searchs.setKeyword(keyword); 
		list = memberService.selectSearchUserid(searchs);
			break;
		case "gender" : searchs.setKeyword(keyword);
			list = memberService.selectSearchGender(searchs);
			break;
		case "age" : searchs.setAge(Integer.parseInt(keyword));
			list = memberService.selectSearchAge(searchs);		//request에서 꺼냈을땐 무조건 String인데, 넘길땐 int로 넘겨야하기때문에 parsing 필요함
			break;
		case "enroll" : searchs.setKeyword(keyword); 
			list = memberService.selectSearchEnrollDate(searchs);	
			break;
		case "login" : searchs.setKeyword(keyword); 
			list = memberService.selectSearchLoginOk(searchs);
			break;
		}//switch
		
		if(list != null && list.size() >0) {			//전달받은 list가 0일 경우도 있음...여기서는 error페이지로 넘길예정( else구문을 통해)
			model.addAttribute("list", list);
			return "member/memberListView";
		}else{
			model.addAttribute("message", action + " 검색에 대한 결과가 존재하지 않습니다.");
			return "common/error";
		}//if
	}//method close
	
	
	
	
}//close




