package org.ict.first.common;



public class Searchs implements java.io.Serializable{

	private static final long serialVersionUID = 848374754136325690L;
	
	private String keyword;
	private int age;

	public Searchs() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Searchs(String keyword) {
		super();
		this.keyword = keyword;
	}

	public Searchs(int age) {
		super();
		this.age = age;
	}

	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "Searchs [keyword=" + keyword + ", age=" + age + "]";
	}
	
	
}
