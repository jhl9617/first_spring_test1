package org.ict.first.notice.model.service;

import java.util.ArrayList;

import org.ict.first.common.SearchDate;
import org.ict.first.notice.model.dao.NoticeDao;
import org.ict.first.notice.model.vo.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService{

	@Autowired
	NoticeDao noticedao;
	
	
	@Override
	public ArrayList<Notice> selectNewTop5() {
		return noticedao.selectNewTop5();
	}

	@Override
	public ArrayList<Notice> selectAll() {
		return noticedao.selectAll();
	}

	@Override
	public Notice selectNotice(int noticeno) {
		return noticedao.selectNotice(noticeno);
	}

	@Override
	public int insertNotice(Notice notice) {
		return noticedao.insertNotice(notice);
	}

	@Override
	public int updateNotice(Notice notice) {
		return noticedao.updateNotice(notice);
	}

	@Override
	public int deleteNotice(int noticeno) {
		return noticedao.deleteNotice(noticeno);
	}

	@Override
	public ArrayList<Notice> selectSearchTitle(String keyword) {
		return noticedao.selectSearchTitle(keyword);
	}

	@Override
	public ArrayList<Notice> selectSearchWriter(String keyword) {
		return noticedao.selectSearchWriter(keyword);
	}

	@Override
	public ArrayList<Notice> selectSearchDate(SearchDate date) {
		return noticedao.selectSearchDate(date);
	}

	@Override
	public Notice selectLast() {
		return noticedao.selectLast();
	}

}
