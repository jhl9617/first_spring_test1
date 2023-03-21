package org.ict.first.notice.model.service;

import java.util.ArrayList;

import org.ict.first.common.SearchDate;
import org.ict.first.notice.model.vo.Notice;



public interface NoticeService {
	ArrayList<Notice> selectNewTop5();
	ArrayList<Notice> selectAll();
	Notice selectNotice (int noticeno);
	int insertNotice(Notice notice );
	int updateNotice(Notice notice);
	int deleteNotice(int noticeno);
	ArrayList<Notice> selectSearchTitle(String keyword);
	ArrayList<Notice> selectSearchWriter(String keyword);
	ArrayList<Notice> selectSearchDate(SearchDate date );
	Notice selectLast();
}
