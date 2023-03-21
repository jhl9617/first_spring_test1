package org.ict.first.notice.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
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
		String savePath = request.getSession().getServletContext().getRealPath("resources/notice_upfiles");		//el 코드로 하면 짧음

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
	
	
	
}	//Controller
