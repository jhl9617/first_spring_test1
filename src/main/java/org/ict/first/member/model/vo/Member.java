package org.ict.first.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//기본생성자, getter, setter 등은 pom.xml에서 처리 후 임포트해서 자동생성한다
//@Getter
//@Setter
//@ToString
//@NoArgsConstructor
//@AllArgsConstructor
public class Member implements java.io.Serializable{
	private static final long serialVersionUID = -1545563495388562497L;
	
	//스프링에서 데이터베이스 테이블 컬럼명과 vo클래스의 필드명을 일치시키면 
	//마이바티스 매퍼의 resultMap이 자동 작동됨
	//member.setUserId(rset.getString)("userid"));를 자동으로 실행해준다는 뜻
	
	private String userid;		        		//	회원아이디     
	private String userpwd;			  		//	회원패스워드    
	private String username;					//	회원이름      
	private String gender;             			//	회원성별      
	private int age;					 				//	회원나이      
	private String phone;              			//	회원전화번호    
	private String email;          			   	//	회원이메일     
	private java.sql.Date enroll_date;	//	회원가입날짜    
	private java.sql.Date lastmodified; 	//	마지막수정날짜   
	private String admin;           				//	관리자여부     
	private String login_ok;            		//	로그인가능여부   
//	private String 0signtype;	//가입방식      =>따로 테이블 만들어야하므로 제외함		
	
	
	
	public Member() {
		super();
	}
	
	public Member(String userid, String userpwd, String username, String gender, int age, String phone, String email,
			Date enroll_date, Date lastmodified, String admin, String login_ok) {
		super();
		this.userid = userid;
		this.userpwd = userpwd;
		this.username = username;
		this.gender = gender;
		this.age = age;
		this.phone = phone;
		this.email = email;
		this.enroll_date = enroll_date;
		this.lastmodified = lastmodified;
		this.admin = admin;
		this.login_ok = login_ok;
	}

	@Override
	public String toString() {
		return "Member [userid=" + userid + ", userpwd=" + userpwd + ", username=" + username + ", gender=" + gender
				+ ", age=" + age + ", phone=" + phone + ", email=" + email + ", enroll_date=" + enroll_date
				+ ", lastmodified=" + lastmodified + ", admin=" + admin + ", login_ok=" + login_ok + "]";
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserpwd() {
		return userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public java.sql.Date getEnroll_date() {
		return enroll_date;
	}

	public void setEnroll_date(java.sql.Date enroll_date) {
		this.enroll_date = enroll_date;
	}

	public java.sql.Date getLastmodified() {
		return lastmodified;
	}

	public void setLastmodified(java.sql.Date lastmodified) {
		this.lastmodified = lastmodified;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getLogin_ok() {
		return login_ok;
	}

	public void setLogin_ok(String login_ok) {
		this.login_ok = login_ok;
	}
			
	
	
	
	
}//close







