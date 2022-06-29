package com.mmpj.mmpj.repository;

import com.mmpj.mmpj.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Boardrepository extends JpaRepository<Board, Integer> {
    @Override
    List<Board> findAll();
}
