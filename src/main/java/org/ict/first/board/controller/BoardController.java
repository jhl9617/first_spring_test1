package org.ict.first.board.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.ict.first.board.model.service.BoardService;
import org.ict.first.board.model.vo.Board;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="btop5.do", method=RequestMethod.POST)
	@ResponseBody		//response에 담아서 보낸다는 뜻
	public String boardNewTop5method() throws UnsupportedEncodingException {
		//ajax 로 요청시, 리턴 방법은 여러가지가 있음
		//response 객체 이용시 
		//1. 출력스트림으로 응답 하는 방법(아이디 중복 체크 예)
		//2. 뷰 리졸버로 리턴하는 방법 : response body에 값을 저장함
		
		//조회수 많은 인기 게시글 5개 조회해 옴
		ArrayList<Board> list = boardService.selectTop5();
		logger.info("btop5.do :" + list.size());
		
		//전송용 json 객체 준비
		JSONObject sendJson = new JSONObject();
		//리스트 저장할 json 배열 객체 준비
		JSONArray jarr = new JSONArray();
		
		//list를 jarr에 옮기기 (복사)
		for(Board board: list) {
			//notice 의 각 필드값 저장할 json 객체생성함
			JSONObject job = new JSONObject();
			
			job.put("board_num", board.getBoard_num());
			
			//한글에 대해서는 인코딩해서 json에 담도록 함, 한글깨짐 방지
			job.put("board_title", URLEncoder.encode(board.getBoard_title(), "utf-8"));
			
			//날짜는 반드시 toString() 으로 문자열로 바꿔서 json에 담아야 함
			job.put("board_readcount", board.getBoard_readcount());
			
			jarr.add(job);	//job를 jarr 에 추가함
		}
		
		//전송용 객체에 jarr을 담음
		sendJson.put("list", jarr);
		
		//json을 json string 타입으로 바꿔서 전송되게 함
		return sendJson.toJSONString();		//뷰리졸버로 리턴함
		//servlet-context.xml 에 json string 내보내는 뷰리졸버 추가 등록 해야 함
	}
	
	
	
}
