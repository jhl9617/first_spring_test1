package org.ict.first.board.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.ict.first.board.model.vo.Board;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("boardDao")
public class BoardDao {
	
	@Autowired
	private SqlSessionTemplate session;

public ArrayList<Board> selectTop5() {
		
		List<Board> list = session.selectList("boardMapper.selectTop5");
		return (ArrayList<Board>)list;
	}
}