package org.ict.first.member.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.ict.first.common.SearchDate;
import org.ict.first.common.Searchs;
import org.ict.first.member.model.vo.Member;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


//★★★★★★MemberServiceImpl로부터 받은 값을 처리해서 Controller로 넘기는 파트임!
//xml파일들과 연결되어있음 : (member-mapper.xml => db구문 관리 xml파일 
											// root-context.xml => 트랜잭션 관리 xml파일 ) 
//마이바티스 mapper를 동작시킴
//★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★



//Dao는 @Repository 어노테이션을 등록한다. ("memberDao") 를 사용하겠다고 명시해줌 => xml에 자동등록됨
//Service와 메소드형태와 이름은 같게 만든다. (바꾸면 번거로우니깐!)
@Repository("memberDao")
public class MemberDao {
	//dao가 쿼리문을 사용하는데, 쿼리문을 이 class에서 작성하는것이 아니고 별도로 작성함
	//=> 마이바티스 매퍼파일에 쿼리문 별도로 작성함
	//root-context.xml 에 생성된 마이바티스 매퍼 연결 객체를 사용함
	
	
	@Autowired		//@Autowired를 통해 의존성(DI)주입됨 => root-context.xml 에서 생성한 객체와 자동 연결됨=> mapper, config, DB까지 모두 연결됨
	private SqlSessionTemplate session;		//로그인세션하고 헷갈리면 안됨. 이것은 마이바티스 세션임!
	
	
	//객체정보 1개 받아서 리턴시킴
	public Member selectLogin(Member member) {
		return session.selectOne("memberMapper.selectLogin", member);		//member-mapper의 id명을 쓰면 됨 //이 곳에 member를 저장해서 리턴하겠다. 토~~~스
	}

//	select count(userid) from member
//	where userid = #{ uid }	
	public int selectDupCheckId(String userid) {
		return session.selectOne("memberMapper.selectDupCheckId", userid);		//1개 선택할때는 selectOne임
	}
	
	
	public ArrayList<Member> selectList() {
		// 받아온값은 List<Object> list 형태임. 		//selectList는 list<Object>타입으로 리턴하기때문에  List와 Object를 동시에 형변환할수가 없음 
		//=>   [   List<Object> list = (ArrayList<Member>)session.selectList("memberMapper.selectList");  ]   과 같은 방식 불가능 X
		//Member만 들어있는 object를 받아야하기 때문에, List와 Object를 각각 따로 형변환해준다. 
		List<Member> list = session.selectList("memberMapper.selectList");		//전달인자가 없으면 id만 지정하는 매개변수 1개를 넣는 방식으로 한다
		return (ArrayList<Member>)list;		//일단 list로 받고, 리턴시킬때 ArrayList로 형변환한다
	}
	
	
	public Member selectMember(String userid) {
		return session.selectOne("memberMapper.selectMember", userid);	
	}
	

	public int insertMember(Member member) {
		return session.insert("memberMapper.insertMember", member);		//Mapper파일에 받은 member변수를 리턴하겠다.
	}

	public int updateMember(Member member) {
		return session.update("memberMapper.updateMember", member);
	}
	
	public int updateLoginok(Member member) {
		return session.update("memberMapper.updateLoginok", member);
	}


	public int deleteMember(String userid) {
		return session.delete("deleteMember", userid);
	}


	
	
	
//	검색기능-----------------------------------------------------------------------------------------------------------------
	public ArrayList<Member> selectSearchUserid(Searchs searchs) {
		List<Member> list = session.selectList("memberMapper.selectSearchUserid", searchs);	
		return (ArrayList<Member>)list;		//일단 list로 받고, 리턴시킬때 ArrayList로 형변환한다
	}


	public ArrayList<Member> selectSearchGender(Searchs searchs) {
		List<Member> list = session.selectList("memberMapper.selectSearchGender", searchs);		
		return (ArrayList<Member>)list;		
		}


	public ArrayList<Member> selectSearchAge(Searchs searchs) {
		List<Member> list = session.selectList("memberMapper.selectSearchAge", searchs);		
		return (ArrayList<Member>)list;		
	}


	public ArrayList<Member> selectSearchEnrollDate(Searchs searchs) {
		List<Member> list = session.selectList("memberMapper.selectSearchEnrollDate", searchs);		
		return (ArrayList<Member>)list;		
	}


	public ArrayList<Member> selectSearchLoginOk(Searchs searchs) {
		List<Member> list = session.selectList("memberMapper.selectSearchLoginOk", searchs);		
		return (ArrayList<Member>)list;		
	}


	
	
}//close







