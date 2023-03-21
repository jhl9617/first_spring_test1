package org.ict.first;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	//index.jsp가 웰컴될 때 포워딩된 요청을 받아서 common/main.jsp를 내보내기 위해 리턴하는 메소드
	//서블릿이 메소드로 바뀌었다고 생각하고 mapping 작업 진행하기
	
			//get방식으로 전송왔을때는 requestmapping만 해도 됨. 
			//post방식일때는 @RequestMapping(value = "/", method = RequestMethod.GET) 이렇게 작성해야함
	@RequestMapping("main.do")
	public String forwardMainView() {
		return "common/main";			//return "common/main" => 내보낼 view 파일명을 리턴한것임 (main.jsp파일임)
	}
	
	
	
}




