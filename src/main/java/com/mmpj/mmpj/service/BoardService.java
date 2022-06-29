package com.mmpj.mmpj.service;

import com.mmpj.mmpj.entity.Board;
import com.mmpj.mmpj.repository.Boardrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private Boardrepository boardrepository;

    public void boarddatasave(Board board) {
        boardrepository.save(board);
    }

    public List<Board> allboardlist() {
        return boardrepository.findAll();
    }

    public Board findboardpk(Integer pk) {
        Board board = boardrepository.findById(pk).get();
        board.setView(board.getView() + 1);
        boardrepository.save(board);
        return board;
    }

    public void boarddelete(Integer pk) {
        boardrepository.delete(boardrepository.findById(pk).get());
    }
}
