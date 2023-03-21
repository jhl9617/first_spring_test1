package org.ict.first.member.model.service;

import java.util.ArrayList;

import org.ict.first.common.SearchDate;
import org.ict.first.common.Searchs;
import org.ict.first.member.model.vo.Member;

//★★★★★★vo로부터 받은 값을 Dao로 넘기는 파트임!



//메소드 안에 public abstract는 생략해도 됨 => 생략되어있다는것 잊지 말 것
//class안에는 안에 내용이 없는 제목과 매개변수만 있는 메소드들만 만들어둔다. (빈깡통)

//스프링에서는 모델의 Service class는 반드시 interface로 만들도록 정해져있음(Spring에서 정한 방식임)
//but, 실제로 class가 있어야 하므로, 이 interface를 상속받는 후손클래스를 만들어준다.
public interface MemberService {
	
	Member selectLogin(Member member);
	int selectDupCheckId(String userid);
	Member selectMember(String userid);
	ArrayList<Member> selectList();
	int insertMember (Member member);
	int updateMember (Member member);
	int updateLoginok (Member member);
	int deleteMember (String userid);
	ArrayList<Member> selectSearchUserid(Searchs searchs);
	ArrayList<Member> selectSearchGender(Searchs searchs);
	ArrayList<Member> selectSearchAge(Searchs searchs);
	ArrayList<Member> selectSearchEnrollDate(Searchs searchs);
	ArrayList<Member> selectSearchLoginOk(Searchs searchs);
	
	//검색을 위한 메소드

	
}




