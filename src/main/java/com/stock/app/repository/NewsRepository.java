package com.stock.app.repository;

import com.stock.app.dto.NewsDto;
import com.stock.app.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByTitleContainingOrDescriptionContaining(String query, String query1);
}
