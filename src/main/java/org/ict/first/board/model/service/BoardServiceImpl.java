package org.ict.first.board.model.service;

import java.util.ArrayList;
import java.util.List;

import org.ict.first.board.model.dao.BoardDao;
import org.ict.first.board.model.vo.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("boardService")
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardDao boardDao;

	@Override
	public ArrayList<Board> selectTop5() {
		
		return boardDao.selectTop5();
	}
	
	
	
}
