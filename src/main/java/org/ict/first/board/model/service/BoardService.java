package org.ict.first.board.model.service;

import java.util.ArrayList;

import org.ict.first.board.model.vo.Board;

public interface BoardService {
	ArrayList<Board> selectTop5();
}
