package org.ict.first.member.model.service;

import java.util.ArrayList;

import org.ict.first.common.Searchs;
import org.ict.first.member.model.dao.MemberDao;
import org.ict.first.member.model.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//★★★★★★Service '인터페이스' 로부터 받은 값을 Dao로 넘기는 파트임!
//★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★



//스프링에서는 모델의 Service class는 반드시 interface로 만들도록 정해져있음(Spring에서 정한 방식임)
//but, 실제로 class가 있어야 하므로, 이 interface를 상속받는 후손클래스를 만들어준다.

//스프링에서는 service model class는 service interface를 '상속'받아서 만들도록 되어있음!
//Service를 사용하는 class에는 @Service 어노테이션을 붙여준다. (memberService) 사용하겠다고 명시해줌 => xml에 자동등록됨
@Service("memberService")	
public class MemberServiceImpl implements MemberService{
	
	//DAO와 연결처리한다. (get과 close는 collection이 모두 하므로, service에서는 다른 코드 설정을 한다)
	@Autowired			//자동 DI 처리됨 : 자동 객체 생성되어 연결된다.
	MemberDao memberDao;
	
	
	@Override
	public Member selectLogin(Member member) {
		return memberDao.selectLogin(member);
	}
	
	
	@Override
	public int selectDupCheckId(String userid) {
		//이미 모든 처리가 앞서 다 끝났기 때문에 return만 해주면 됨
		return memberDao.selectDupCheckId(userid);
	}
	
	
	@Override
	public Member selectMember(String userid) {
		return memberDao.selectMember(userid);
	}


	@Override
	public ArrayList<Member> selectList() {
		return memberDao.selectList();
	}

	@Override
	public int insertMember(Member member) {
		return memberDao.insertMember(member);		
	}
	

	@Override
	public int updateLoginok(Member member) {
		return memberDao.updateLoginok(member);
	}

	
	@Override
	public int updateMember(Member member) {
		return memberDao.updateMember(member);
	}

	@Override
	public int deleteMember(String userid) {
		return memberDao.deleteMember(userid);
	}


	
//	검색기능-----------------------------------------------------------------------------------------------------------------
	


	@Override
	public ArrayList<Member> selectSearchUserid(Searchs searchs) {
		return memberDao.selectSearchUserid(searchs);
	}



	@Override
	public ArrayList<Member> selectSearchGender(Searchs searchs) {
		// TODO Auto-generated method stub
		return memberDao.selectSearchGender(searchs);
	}


	@Override
	public ArrayList<Member> selectSearchAge(Searchs searchs) {
		return memberDao.selectSearchAge(searchs);
	}



	@Override
	public ArrayList<Member> selectSearchEnrollDate(Searchs searchs) {
		return memberDao.selectSearchEnrollDate(searchs);
	}



	@Override
	public ArrayList<Member> selectSearchLoginOk(Searchs searchs) {
		return memberDao.selectSearchLoginOk(searchs);
	}


	
	
	
	
	
}





