package org.ict.first.common;

import java.sql.Date;

import org.springframework.stereotype.Component;

//만약 공통모듈로 사용하는게 아니고 Member단에서만 사용한다고하면, vo에 이 클래스를 넣어주면 됨
//만약 이 클래스를 디스펙쳐서블릿쪽에 등록하겠다고 하면..? @Component 어노테이션 사용하면 됨.
//@Component("searchDate")	//자동 등록 : 자동의존성처리 주입하는 DI 사용 가능함
public class SearchDate implements java.io.Serializable{
	private static final long serialVersionUID = 2253546582582684455L;

	private Date begin;
	private Date end;

	public SearchDate() {
		super();
	}

	public SearchDate(Date begin, Date end) {
		super();
		this.begin = begin;
		this.end = end;
	}

	@Override
	public String toString() {
		return "SearchDate [begin=" + begin + ", end=" + end + "]";
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

}//class close











