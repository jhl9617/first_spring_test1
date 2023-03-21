package org.ict.first.notice.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.ict.first.common.SearchDate;
import org.ict.first.member.model.vo.Member;
import org.ict.first.notice.model.service.NoticeService;
import org.ict.first.notice.model.vo.Notice;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class NoticeController {
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);

	@Autowired
	private NoticeService noticeService;
	
	@RequestMapping(value="ntop5.do", method=RequestMethod.POST)
	@ResponseBody		//response에 담아서 보낸다는 뜻
	public String noticeNewTop5method() throws UnsupportedEncodingException {
		//ajax 로 요청시, 리턴 방법은 여러가지가 있음
		//response 객체 이용시 
		//1. 출력스트림으로 응답 하는 방법(아이디 중복 체크 예)
		//2. 뷰 리졸버로 리턴하는 방법 : response body에 값을 저장함
		
		//최근 공지글 5개 조회해 옴
		ArrayList<Notice> list = noticeService.selectNewTop5();
		logger.info("ntop5.do :" + list.size());
		
		//전송용 json 객체 준비
		JSONObject sendJson = new JSONObject();
		//리스트 저장할 json 배열 객체 준비
		JSONArray jarr = new JSONArray();
		
		//list를 jarr에 옮기기 (복사)
		for(Notice notice : list) {
			//notice 의 각 필드값 저장할 json 객체생성함
			JSONObject job = new JSONObject();
			
			job.put("noticeno", notice.getNoticeno());
			
			//한글에 대해서는 인코딩해서 json에 담도록 함, 한글깨짐 방지
			job.put("noticetitle", URLEncoder.encode(notice.getNoticetitle(), "utf-8"));
			
			//날짜는 반드시 toString() 으로 문자열로 바꿔서 json에 담아야 함
			job.put("noticedate", notice.getNoticedate().toString());
			
			jarr.add(job);	//job를 jarr 에 추가함
		}	
		
		//전송용 객체에 jarr을 담음
		sendJson.put("list", jarr);
		
		//json을 json string 타입으로 바꿔서 전송되게 함
		return sendJson.toJSONString();		//뷰리졸버로 리턴함
		//servlet-context.xml 에 json string 내보내는 뷰리졸버 추가 등록 해야 함
	}
	
	//공지사항 전체 목록 보기 요청 처리용
	@RequestMapping("nlist.do")
	public String noticeListMethod(Model model) {
		ArrayList<Notice> list = noticeService.selectAll();
		
		if(list != null && list.size() > 0) {
			model.addAttribute("list", list);		//list라는 이름으로 list 객체 저장
			return "notice/noticeListView";
		} else {
			model.addAttribute("message", "등록된 공지사항 정보가 없습니다.");
			return "common/error";
		}
	}
	
	//공지글 제목 검색용
	@RequestMapping(value="nsearchTitle.do", method=RequestMethod.POST)
	public String noticeSearchTitleMethod(@RequestParam("keyword") String keyword, Model model) {
		ArrayList<Notice> list = noticeService.selectSearchTitle(keyword);
		if(list != null && list.size() > 0) {
			model.addAttribute("list", list);
			return "notice/noticeListView";
		}else {
			model.addAttribute("message", keyword = " 로 검색된 공지가 없습니다");
			return "common/error";
		}
	}
	
	@RequestMapping(value="nsearchWriter.do", method=RequestMethod.POST)
	public String noticeSearchWriterMethod(@RequestParam("keyword") String keyword, Model model) {
		ArrayList<Notice> list = noticeService.selectSearchWriter(keyword);
		if(list != null && list.size() > 0) {
			model.addAttribute("list", list);
			return "notice/noticeListView";
		}else {
			model.addAttribute("message", keyword = " 로 검색된 공지가 없습니다");
			return "common/error";
		}
	}
	
	
	@RequestMapping(value="nsearchDate.do", method=RequestMethod.POST)
	public String noticeSearchDateMethod(SearchDate date, Model model) {
		ArrayList<Notice> list = noticeService.selectSearchDate(date);
		if(list != null && list.size() > 0) {
			model.addAttribute("list", list);
			return "notice/noticeListView";
		}else {
			model.addAttribute("message", date + " 날짜의 공지가 없습니다");
			return "common/error";
		}
	}
	
	//공지글 상세 보기 요청 처리용
	@RequestMapping("ndetail.do")
	public String noticeDetailMethod(@RequestParam("noticeno") int noticeno, Model model, HttpSession session) { 
		//관리자용 상세보기 페이지와 일반 회원|비회원 상세보기 페이지를 구분 함
		//HttpSession 을 매개변수에 추가함
		
		Notice notice = noticeService.selectNotice(noticeno);
		logger.info("noticeno : " + noticeno);
		
		if(notice != null) {
			model.addAttribute("notice", notice);
			
			Member loginMember = (Member) session.getAttribute("loginMember");

			if(loginMember != null && loginMember.getAdmin().equals("Y")) {
				//로그인 한 관리자가 요청했다면
				return "notice/noticeAdminDetailView";
			}else {
				//관리자가 아닌 | 로그인 안 한 상태에서의 요청이라면
				return "notice/noticeDetailView";
			}
		}else {
			model.addAttribute("message", noticeno + "번 공지글 상세보기 조회 실패!");
			return "common/error";
		}
	}

	//첨부파일 다운로드 요청
	@RequestMapping("nfdown.do")
	public ModelAndView fileDownMethod(ModelAndView mv, HttpServletRequest request, @RequestParam("ofile") String originalFileName, @RequestParam("rfile") String renameFileName) {
		//공지사항 첨부파일 저장폴더에 대한 경로(path) 지정
		String savePath = request.getSession().getServletContext().getRealPath("resources/notice_upfiles");	//el 코드로 하면 짧음

		//저장 폴더에서 읽을 파일에 대한 파일 객체 생성함
		File renameFile = new File(savePath + "\\" + renameFileName);
		//파일 다운시 내보낼 원래 이름의 파일 객체 생성함
		File originalFile = new File(originalFileName);

		//파일 다운로드 뷰로 전달할 정보 저장
		mv.setViewName("filedown");		//등록된 파일 다운로드 뷰의 id명
		mv.addObject("renameFile", renameFile);
		mv.addObject("originFile", originalFile);
		return mv;
	}

	//공지글 수정페이지로 이동 요청 처리용
	@RequestMapping("nmoveup.do")
	public String moveUpdatepage(@RequestParam("noticeno") int noticeno,Model model) {
		//수정 페이지에 출력할 해당 공지글 다시 조회함
		Notice notice = noticeService.selectNotice(noticeno);

		if(notice != null) {
			model.addAttribute("notice", notice);
			return "notice/noticeUpdateForm";
		}else {
			model.addAttribute("message", noticeno + "번 공지글 수정페이지 이동 실패");
			return "common/error";
		}


	}

	//공지글 수정요청처리용 (파일 업로드기능 사용)---------------------------------------------
	@RequestMapping(value="nupdate.do", method= RequestMethod.POST)
	public String noticeUpdateMethod(Notice notice, Model model, HttpServletRequest request,
									 @RequestParam(name="delflag", required=false) String delFlag,
									 @RequestParam(name="upfile", required=false) MultipartFile mfile) {
		//공지사항 첨부파일 저장폴더 경로지정
		String savePath = request.getSession().getServletContext().getRealPath("resources/notice_upfiles");

		//★★첨부파일이 수정처리된 경우----------
		//1. 원래 첨부파일이 있는데 '파일삭제' 체크박스를 선택한 경우
		if(notice.getOriginal_filepath() != null  &&  delFlag != null  && delFlag.equals("yes")) {
			//notice에 이미 첨부파일이 있다면?   (  원래 첨부파일이 있음 && delFlag전송온값이 있음(뭔가체크했음) && delFlag에 'yes'라고 선택했다면  )
			//저장 폴더에 있는 파일을 삭제한다
			new File(savePath + "\\" + notice.getRename_filepath()).delete();      //file(바뀐파일명가져와서 지우기 )

			//notice의 이전 파일정보도 삭제한다
			notice.setOriginal_filepath(null);
			notice.setRename_filepath(null);
		}//if

		//2. 공지글 첨부파일은 1개만 첨부 가능할 때, 새로운 첨부파일이 있는 경우(원래 첨부파일이 있든&없든)
		if(!mfile.isEmpty()) {      //새로운 첨부파일이 있다면?

			//2-1. 이전 첨부파일이 있었다면?
			if(notice.getOriginal_filepath() != null) {   //이전 첨부파일이 있다면
				//저장폴더에 있는 이전파일을 삭제한다
				new File(savePath + "\\" + notice.getRename_filepath()).delete();
				//notice의 이전 파일정보도 삭제한다
				notice.setOriginal_filepath(null);
				notice.setRename_filepath(null);
			}//if


			//2-2. 이전 첨부파일이 없었다면? => 바로 폴더에 저장하면 됨
			//전송온 파일이름 추출을 먼저 한다
			String fileName = mfile.getOriginalFilename();         //getOriginalFilename() => 파일이름만 추출할때 사용하는 메소드

			//transferTo() 메소드를 이용해서 바로 저장한다면? (덮어쓰기됨) mfile.transferTo(new File(savePath + "\\" + fileName + exexexex));
			//다른 공지글의 첨부파일과 파일명이 중복되는 경우 => 덮어쓰기 되는것을 막기 위해, 파일명을 변경해서 폴더에 저장하는 방식을 사용할 수 있음
			//변경파일명 : 년월일시분초.확장자   => 이렇게 처리한다.
			if(fileName !=null && fileName.length() > 0)  {   //파일명에 파일name이 정확하게 들어왔다면,
				//바꿀 파일명에 대한 문자열만들기 작업
				//공지글 등록 | 수정 요청시점의 날짜시간정보를 이용함
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");      //포멧 문자열 데이터를 만들어준다
				//변경할 파일명 만들기
				String renameFileName = sdf.format(new java.sql.Date(System.currentTimeMillis()));      //System.currentTimeMillis()을 통해, 시스템으로부터 현재시간날짜를 가져온다.
				logger.info("변경 파일명 : " + renameFileName);
				//원본 파일의 확장자를 추출해서, 변경파일명에 붙여주는 작업(pdf, java, 등등)
				renameFileName += "." + fileName.substring(fileName.lastIndexOf(".") + 1);
				//fileName.lastIndexOf(".") => 뒤에서부터 처음만나는 . 이 '파일명'과 '확장자'를 구분하는 기준임 // +1 => 그 다음 글자부터 // substring => 끝글자까지 추출해라
				logger.info("변경 파일명 : " + renameFileName);

				//파일객체 만들기
				File renameFile = new File(savePath + "\\" + renameFileName);

				//폴더에 저장처리
				//예외처리를 Controller에서 직접 하겠다면? => exception처리를 해주며, catch파트에서 에러내용 처리를 해준다.
				try {
					mfile.transferTo(renameFile);
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("message", "첨부파일 저장 실패!");
					return "common/error";
				}//try

				//notice객체에 첨부파일 정보 기록 저장
				notice.setOriginal_filepath(fileName);            //원래파일명 저장함
				notice.setRename_filepath(renameFileName);   //바뀐파일명 저장함
			}//if  //이름바꾸기
		}//if      //새로운 첨부파일이 있을때

		if(noticeService.updateNotice(notice) > 0) {
			//공지글 수정 성공시 목록보기 페이지로 이동
			return "redirect:nlist.do";
		}else{
			model.addAttribute("message", notice.getNoticeno() + "번 공지글 수정 실패!");
			return "common/error";
		}//if
	}//method close

	//공지글 삭제 요청 처리용
	@RequestMapping("ndel.do")
	public String noticeDeleteMethod(@RequestParam("noticeno") int noticeno,
									 @RequestParam(name ="rfile", required = false) String renameFileName,
									 Model model, HttpServletRequest request) {
		if(noticeService.deleteNotice(noticeno) > 0) {
			//첨부된 파일이 있는 공지일 때는 저장 폴더에 있는 첨부 파일도 삭제함
			if(renameFileName != null){
				String savePath = request.getSession().getServletContext().getRealPath("resources/notice_upfiles");
				new File(savePath + "\\" +renameFileName).delete();
			}
			return "redirect:nlist.do";
		}else {
			model.addAttribute("message", noticeno + "번 공지 삭제 실패!");
			return "common/error";
		}
	}

	//새 공지글 등록 페이지로 이동 처리용
	@RequestMapping("movewriter.do")
	public String moveWritePage() {
		return "notice/noticeWriterForm";
	}

	//공지글 등록 요청 처리용 ( 파일 업로드 기능 사용)

	//새 공지글 등록요청처리용 (파일 업로드기능 사용)---------------------------------------------
	@RequestMapping(value="ninsert.do", method= RequestMethod.POST)
	public String noticeInsertMethod(Notice notice, Model model, HttpServletRequest request,               //request => 첨부파일 저장폴더 경로지정용
									 @RequestParam(name="upfile", required=false) MultipartFile mfile) {      //upfile => 첨부파일 저장용
		//공지사항 첨부파일 저장폴더 경로지정
		String savePath = request.getSession().getServletContext().getRealPath("resources/notice_upfiles");


		//첨부파일이 있을때
		if(!mfile.isEmpty()) {      //새로운 첨부파일이 있다면?
			//전송온 파일이름 추출을 먼저 한다
			String fileName = mfile.getOriginalFilename();         //getOriginalFilename() => 파일이름만 추출할때 사용하는 메소드

			//transferTo() 메소드를 이용해서 바로 저장한다면? (덮어쓰기됨) mfile.transferTo(new File(savePath + "\\" + fileName + exexexex));
			//다른 공지글의 첨부파일과 파일명이 중복되는 경우 => 덮어쓰기 되는것을 막기 위해, 파일명을 변경해서 폴더에 저장하는 방식을 사용할 수 있음
			//변경파일명 : 년월일시분초.확장자   => 이렇게 처리한다.
			if(fileName !=null && fileName.length() > 0)  {   //파일명에 파일name이 정확하게 들어왔다면,
				//바꿀 파일명에 대한 문자열만들기 작업
				//새 공지글 등록 | 수정 요청시점의 날짜시간정보를 이용함
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");      //포멧 문자열 데이터를 만들어준다
				//변경할 파일명 만들기
				String renameFileName = sdf.format(new java.sql.Date(System.currentTimeMillis()));      //System.currentTimeMillis()을 통해, 시스템으로부터 현재시간날짜를 가져온다.
				logger.info("변경 파일명 : " + renameFileName);
				//원본 파일의 확장자를 추출해서, 변경파일명에 붙여주는 작업(pdf, java, 등등)
				renameFileName += "." + fileName.substring(fileName.lastIndexOf(".") + 1);
				//fileName.lastIndexOf(".") => 뒤에서부터 처음만나는 . 이 '파일명'과 '확장자'를 구분하는 기준임 // +1 => 그 다음 글자부터 // substring => 끝글자까지 추출해라
				logger.info("변경 파일명 : " + renameFileName);

				//파일객체 만들기
				File renameFile = new File(savePath + "\\" + renameFileName);

				//폴더에 저장처리
				//예외처리를 Controller에서 직접 하겠다면? => exception처리를 해주며, catch파트에서 에러내용 처리를 해준다.
				try {
					mfile.transferTo(renameFile);
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("message", "첨부파일 저장 실패!");
					return "common/error";
				}//try

				//notice객체에 첨부파일 정보 기록 저장
				notice.setOriginal_filepath(fileName);            //원래파일명 저장함
				notice.setRename_filepath(renameFileName);   //바뀐파일명 저장함
			}//if  //이름바꾸기
		}//if      //새로운 첨부파일이 있을때

		if(noticeService.insertNotice(notice) > 0) {
			//새 공지글 등록 성공시 목록보기 페이지로 이동
			return "redirect:nlist.do";
		}else{
			model.addAttribute("message", notice.getNoticeno() + "새 공지글 등록 실패!");
			return "common/error";
		}//if
	}//method close



}	//Controller
