package org.ict.first.board.model.vo;

import java.sql.Date;


//기본생성자, getter, setter 등은 pom.xml에서 처리 후 임포트해서 자동생성한다

public class Board implements java.io.Serializable{
	private static final long serialVersionUID = -3585769086456548268L;

	private int board_num;                  			  //	게시글번호      
	private String board_writer;	       			  //	작성자아이디     
	private String board_title;	          			  //	게시글제목      
	private String board_conten;            		 //	게시글내용      
	private String board_original_filename;  	 //	원본첨부파일명    
	private String board_rename_filename;     //	바뀐첨부파일명    
	private int board_ref;	                			 //	원글번호       
	private int board_reply_ref;	        			 //	참조답글번호     
	private int board_lev;	                			 //	답글단계 (원글:1, 댓글2, 대댓글3)
	private int board_reply_seq;	        			 //	답글순번       
	private int board_readcount;	       			 //    조회수
	private java.sql.Date board_date;	    	 //	작성날짜       
	public Board() {
		super();
	}
	public Board(int board_num, String board_writer, String board_title, String board_conten,
			String board_original_filename, String board_rename_filename, int board_ref, int board_reply_ref,
			int board_lev, int board_reply_seq, int board_readcount, Date board_date) {
		super();
		this.board_num = board_num;
		this.board_writer = board_writer;
		this.board_title = board_title;
		this.board_conten = board_conten;
		this.board_original_filename = board_original_filename;
		this.board_rename_filename = board_rename_filename;
		this.board_ref = board_ref;
		this.board_reply_ref = board_reply_ref;
		this.board_lev = board_lev;
		this.board_reply_seq = board_reply_seq;
		this.board_readcount = board_readcount;
		this.board_date = board_date;
	}
	public int getBoard_num() {
		return board_num;
	}
	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}
	public String getBoard_writer() {
		return board_writer;
	}
	public void setBoard_writer(String board_writer) {
		this.board_writer = board_writer;
	}
	public String getBoard_title() {
		return board_title;
	}
	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}
	public String getBoard_conten() {
		return board_conten;
	}
	public void setBoard_conten(String board_conten) {
		this.board_conten = board_conten;
	}
	public String getBoard_original_filename() {
		return board_original_filename;
	}
	public void setBoard_original_filename(String board_original_filename) {
		this.board_original_filename = board_original_filename;
	}
	public String getBoard_rename_filename() {
		return board_rename_filename;
	}
	public void setBoard_rename_filename(String board_rename_filename) {
		this.board_rename_filename = board_rename_filename;
	}
	public int getBoard_ref() {
		return board_ref;
	}
	public void setBoard_ref(int board_ref) {
		this.board_ref = board_ref;
	}
	public int getBoard_reply_ref() {
		return board_reply_ref;
	}
	public void setBoard_reply_ref(int board_reply_ref) {
		this.board_reply_ref = board_reply_ref;
	}
	public int getBoard_lev() {
		return board_lev;
	}
	public void setBoard_lev(int board_lev) {
		this.board_lev = board_lev;
	}
	public int getBoard_reply_seq() {
		return board_reply_seq;
	}
	public void setBoard_reply_seq(int board_reply_seq) {
		this.board_reply_seq = board_reply_seq;
	}
	public int getBoard_readcount() {
		return board_readcount;
	}
	public void setBoard_readcount(int board_readcount) {
		this.board_readcount = board_readcount;
	}
	public java.sql.Date getBoard_date() {
		return board_date;
	}
	public void setBoard_date(java.sql.Date board_date) {
		this.board_date = board_date;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
