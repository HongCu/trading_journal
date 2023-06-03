package com.stock.app.repository;

import com.stock.app.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByTitleContainingOrContentContaining(String query, String query1);
}